package com.sdc.tradeo.model;

import com.sdc.tradeo.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Setter @Getter
public class VerificationCode {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String otp;

    @OneToOne
    private User user;

    private String email;

    private String mobile;

    private VerificationType verificationType;

    // Getters
    public Long getId() {
        return id;
    }

    public String getOtp() {
        return otp;
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public VerificationType getVerificationType() {
        return verificationType;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setVerificationType(VerificationType verificationType) {
        this.verificationType = verificationType;
    }

}
