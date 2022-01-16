package com.evdictionaries.controllers;

import com.evdictionaries.config.Utility;
import com.evdictionaries.error.CustomErrorException;
import com.evdictionaries.models.User;
import com.evdictionaries.payload.request.ResetPasswordRequest;
import com.evdictionaries.payload.request.ForgotPasswordRequest;
import com.evdictionaries.service.impl.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PasswordResetController {
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/ResetPassword")
    @PreAuthorize("hasRole('ADMIN')")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        String email = resetPasswordRequest.getEmail();
        String token = RandomString.make(45);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        try {
           userService.updateResetPasswordToken(token,email);
            String resetPasswordLink = Utility.getSiteURL(request).replace("8081","4200") + "/ForgotPassword?token="+token;
            sendEmail(email,resetPasswordLink);
        }catch (CustomErrorException exception){

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/ForgotPassword")
    public void forgotPassword(@Valid @RequestBody ForgotPasswordRequest token){
        User user = userService.get(token.getToken());
        if (user == null){

        }else {
            userService.updatePassword(user,token.password);
        }
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("ev.dictionaries@gmail.com","EV Dictionaries Support");
        helper.setTo(email);
        String subject = "Here's the link to reset your password";
        String content ="<p>Hello,</p>"
                +"<p>You have requested to reset your password.</p>"
                +"<p>Click the link below to change your password:</p>"
                +"<p><b><a href=\""+resetPasswordLink+"\">Change my password</a></b></p>"
                +"<p>Ignore this email if you do remember your password, or you have not made the request.</p>";
        helper.setSubject(subject);
        helper.setText(content,true);
        mailSender.send(message);
    }
}
