package com.sdc.tradeo.controller;

import com.sdc.tradeo.request.ForgetPasswordTokenRequest;
import com.sdc.tradeo.service.EmailService;
import com.sdc.tradeo.service.ForgetPasswordService;
import com.sdc.tradeo.service.UserService;
import com.sdc.tradeo.service.VerificationCodeService;
import com.sdc.tradeo.domain.VerificationType;
import com.sdc.tradeo.model.ForgetPasswordToken;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.VerificationCode;
import com.sdc.tradeo.request.ResetPasswordRequest;
import com.sdc.tradeo.response.ApiResponse;
import com.sdc.tradeo.response.AuthResponse;
import com.sdc.tradeo.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailService emailService;
    private String jwt;

    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp
            (@RequestHeader("Authorization") String jwt,
             @PathVariable VerificationType verificationType ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        if (verificationCode == null) {
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }

        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(),verificationCode.getOtp());
        }


        return new ResponseEntity<>("Verification Otp sent Successfully", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo=verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);


        if (isVerified) {
            User updatedUser = userService.enableTwoFactorAuthentication(
                    verificationCode.getVerificationType(), sendTo, user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("Incorrect Otp");
    }

//    //password Update
//    //otp sent
//    @PostMapping("/auth/users/reset-password/send-otp")
//    public ResponseEntity<AuthResponse> sendForgetPasswordOtp(
//
//            @RequestBody ForgetPasswordTokenRequest req) throws Exception {
//
//        User user = userService.findUserByEmail(req.getSendTo());
//        String otp = OtpUtils.generateOTP();
//        UUID uuid = UUID.randomUUID();
//        String id = uuid.toString();
//
//        ForgetPasswordToken token = forgetPasswordService.findByUser(user.getId());
//
//        if (token == null) {
//            token=forgetPasswordService.createToken(user,id,otp,req.getVerificationType(), req.getSendTo());
//        }
//        if (req.getVerificationType().equals(VerificationType.EMAIL)) {
//            emailService.sendVerificationOtpEmail(
//                    user.getEmail(), token.getOtp());
//        }
//
//        AuthResponse response = new AuthResponse();
//        response.setSession(token.getId());
//        response.setMessage("Password reset Successfully");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    //Verify reset password
//    @PatchMapping("/api/users/reset-password/verify-otp")
//    public ResponseEntity<ApiResponse> resetPassword(
//            @RequestParam String id,
//            @RequestBody ResetPasswordRequest req,
//            @RequestHeader("Authorization") String jwt) throws Exception {
//
//
//        ForgetPasswordToken forgetPasswordToken = forgetPasswordService.findById(id);
//
//        boolean isVerified = forgetPasswordToken.getOtp().equals(req.getOtp());
//
//        if (isVerified) {
//            userService.updatePassword(forgetPasswordToken.getUser(), req.getPassword());
//            ApiResponse res= new ApiResponse();
//            res.setMessage("Password update Successfully");
//            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
//        }
//        throw new Exception("Incorrect Otp");
//    }

}
