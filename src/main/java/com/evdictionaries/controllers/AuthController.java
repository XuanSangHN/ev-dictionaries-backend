package com.evdictionaries.controllers;

import com.evdictionaries.error.CustomErrorException;
import com.evdictionaries.models.ERole;
import com.evdictionaries.models.Role;
import com.evdictionaries.models.User;
import com.evdictionaries.payload.request.LoginRequest;
import com.evdictionaries.payload.request.SignupRequest;
import com.evdictionaries.payload.response.BaseResponse;
import com.evdictionaries.payload.response.UserResponse;
import com.evdictionaries.payload.response.MessageResponse;
import com.evdictionaries.repository.RoleRepository;
import com.evdictionaries.repository.UserRepository;
import com.evdictionaries.security.jwt.JwtUtils;
import com.evdictionaries.security.services.UserDetailsImpl;
import com.evdictionaries.service.impl.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/Register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody @ModelAttribute SignupRequest signUpRequest) {
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
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getPhonenumber(),signUpRequest.getStatus(),signUpRequest.getAddress());

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
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
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
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new CustomErrorException(
                                        HttpStatus.NOT_FOUND,
                                        "Không Có Quyền Như Trên",
                                        null
                                ));
                        roles.add(adminRole);

                        break;
                    case "collaborators":
                        Role modRole = roleRepository.findByName(ERole.ROLE_COLLABORATORS)
                                .orElseThrow(() -> new CustomErrorException(
                                        HttpStatus.NOT_FOUND,
                                        "Không Có Quyền Như Trên",
                                        null
                                ));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
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
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Đăng Ký Tài Khoản Thành Công"));
    }

    @GetMapping(value = "/UserAdmins")
//    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse showUserAdmin() {
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
                listRole.add(element.getName().name());
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
}
