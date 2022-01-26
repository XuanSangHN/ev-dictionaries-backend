package com.evdictionaries.controllers;

import com.evdictionaries.error.CustomErrorException;
import com.evdictionaries.models.ERole;
import com.evdictionaries.models.Role;
import com.evdictionaries.models.User;
import com.evdictionaries.payload.request.LoginRequest;
import com.evdictionaries.payload.request.RolesRequest;
import com.evdictionaries.payload.request.SignupRequest;
import com.evdictionaries.payload.request.VerificationEmailRequest;
import com.evdictionaries.payload.response.BaseResponse;
import com.evdictionaries.payload.response.MessageResponse;
import com.evdictionaries.payload.response.UserResponse;
import com.evdictionaries.repository.RoleRepository;
import com.evdictionaries.repository.UserRepository;
import com.evdictionaries.security.jwt.JwtUtils;
import com.evdictionaries.security.services.UserDetailsImpl;
import com.evdictionaries.service.impl.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    FilesStorageService storageService;

    @Autowired
    private JavaMailSender mailSender;

    private String PATH_FILE = "http://localhost:8081/Files/";

    @PostMapping("/Login")
    public BaseResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        if (userDetails.getStatus()!=1){
            throw new CustomErrorException(
                    HttpStatus.NOT_FOUND,
                    "Email Chưa Được Xác Minh Vui Lòng Thử Lại",
                    null
            );
        }else {
            return new BaseResponse(
                    HttpStatus.OK,
                    "Truy Cập Thành Công!",
                    null,new UserResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles,
                    userDetails.getFirstname(),
                    userDetails.getLastname(),
                    userDetails.getPhonenumber(),
                    userDetails.getAvatar(),
                    userDetails.getStatus(),
                    userDetails.getAddress(),
                    userDetails.getCreatedBy(),
                    userDetails.getCreatedDate()
            ));
        }
    }

    @PostMapping("/Register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody @ModelAttribute SignupRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new CustomErrorException(
                    HttpStatus.NOT_FOUND,
                    "Username Đã Được Sử Dụng Vui Lòng Thử Lại",
                    null
            );
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomErrorException(
                    HttpStatus.NOT_FOUND,
                    "Email Đã Được Sử Dụng Vui Lòng Thử Lại",
                    null
            );
        }

        if (userRepository.existsByPhonenumber(signUpRequest.getPhonenumber())) {
            throw new CustomErrorException(
                    HttpStatus.NOT_FOUND,
                    "Số Điện Thoại Đã Được Sử Dụng Vui Lòng Thử Lại",
                    null
            );
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getPhonenumber(),signUpRequest.getAddress());

        try {
            storageService.uploadImgeProfile(signUpRequest.getAvatar());
            user.setAvatar(PATH_FILE + signUpRequest.getAvatar().getOriginalFilename());
        } catch (Exception e) {
            throw new CustomErrorException(
                    HttpStatus.NOT_FOUND,
                    "Không thể lưu trữ tệp",
                    null
            );
        }

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByCode(ERole.ROLE_USER)
                    .orElseThrow(() -> new CustomErrorException(
                            HttpStatus.NOT_FOUND,
                            "Không Có Quyền Như Trên",
                            null
                    ));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByCode(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new CustomErrorException(
                                        HttpStatus.NOT_FOUND,
                                        "Không Có Quyền Như Trên",
                                        null
                                ));
                        roles.add(adminRole);

                        break;
                    case "collaborators":
                        Role modRole = roleRepository.findByCode(ERole.ROLE_COLLABORATORS)
                                .orElseThrow(() -> new CustomErrorException(
                                        HttpStatus.NOT_FOUND,
                                        "Không Có Quyền Như Trên",
                                        null
                                ));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByCode(ERole.ROLE_USER)
                                .orElseThrow(() -> new CustomErrorException(
                                        HttpStatus.NOT_FOUND,
                                        "Không Có Quyền Như Trên",
                                        null
                                ));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setStatus(0L);
        Random rand = new Random();
        int upperbound = 999999;
        int otp = rand.nextInt(upperbound);
        user.setVerificationCode(String.valueOf(otp));
        userRepository.save(user);
        sendVerificationEmail(user,String.valueOf(otp));
        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Đăng Ký Tài Khoản Thành Công"));
    }

    private void sendVerificationEmail(User user, String verificationLink) throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration";
        String senderName = "EV Dictionaries Support";
        String mailContent ="<p>Dear "+user.getUsername()+",</p>";
        mailContent += "<p> This OTP is used to verify and activate your account, your OTP is: "+verificationLink+"</p>";
        mailContent += "<p>The EV Dictionaries Team</p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("ev.dictionaries@gmail.com",senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent,true);
        mailSender.send(message);
    }

    @PostMapping("/VerificationEmail")
    public void verificationEmail(@Valid @RequestBody VerificationEmailRequest verificationEmailRequest){
        User user = userRepository.findByVerificationCode(verificationEmailRequest.getVerification_code());
        user.setStatus(1L);
        if (user == null){

        }else {
            userRepository.save(user);
        }
    }

    @GetMapping(value = "/UserAdmins")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COLLABORATORS')")
    public BaseResponse getUserAdmin() {
        List<User> users = userRepository.findAllByUserAdmin();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            List<String> listRole = new ArrayList<>();
            userResponse.setProfile_id(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setFirstname(user.getFirstname());
            userResponse.setLastname(user.getLastname());
            userResponse.setPhonenumber(user.getPhonenumber());
            userResponse.setAvatar_url(user.getAvatar());
            for (Role element : user.getRoles()) {
                listRole.add(element.getCode().name());
            }
            userResponse.setRoles(listRole);
            userResponse.setCreatedBy(user.getCreatedBy());
            userResponse.setCreatedDate(user.getCreatedDate());
            userResponse.setStatus(user.getStatus());
            userResponse.setAddress(user.getAddress());
            userResponses.add(userResponse);
        }
        return new BaseResponse(HttpStatus.OK,"Truy Cập Thành Công!",null,userResponses);
    }

    @PostMapping(value = "/UserAdminsByRoles")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COLLABORATORS')")
    public BaseResponse getUserAdminByRoles(@Valid @RequestBody RolesRequest rolesRequest) {
        List<User> users = userRepository.findByRoles(rolesRequest.getRoles());
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            List<String> listRole = new ArrayList<>();
            userResponse.setProfile_id(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setFirstname(user.getFirstname());
            userResponse.setLastname(user.getLastname());
            userResponse.setPhonenumber(user.getPhonenumber());
            userResponse.setAvatar_url(user.getAvatar());
            for (Role element : user.getRoles()) {
                listRole.add(element.getCode().name());
            }
            userResponse.setRoles(listRole);
            userResponse.setCreatedBy(user.getCreatedBy());
            userResponse.setCreatedDate(user.getCreatedDate());
            userResponse.setStatus(1L);
            userResponse.setAddress(user.getAddress());
            userResponses.add(userResponse);
        }
        return new BaseResponse(HttpStatus.OK,"Truy Cập Thành Công!",null,userResponses);
    }

    @PostMapping("/UpdateUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateUser(@Valid @RequestBody @ModelAttribute SignupRequest userUpdate) {
        User user = new User(
                userUpdate.getFirstname(),
                userUpdate.getLastname(),
                userUpdate.getPhonenumber(),
                userUpdate.getAddress());
        user.setId(userUpdate.getId());
        try {
            storageService.uploadImgeProfile(userUpdate.getAvatar());
            user.setAvatar(PATH_FILE + userUpdate.getAvatar().getOriginalFilename());
        } catch (Exception e) {
            throw new CustomErrorException(
                    HttpStatus.NOT_FOUND,
                    "Không thể lưu trữ tệp",
                    null
            );
        }
        userRepository.updateUser(user.getFirstname(),user.getLastname(),"dsadasdsa",user.getAddress(),user.getPhonenumber(),user.getId());
        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Sửa Đổi Thông Tin Tài Khoản Thành Công!"));
    }

    @DeleteMapping(value = "/DeleteUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteUser(@RequestBody Long[] ids) {
        for (Long idNational : ids) {
            userRepository.delete(idNational);
        }
        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Xóa Tài Khoản Thành Công!"));
    }
}
