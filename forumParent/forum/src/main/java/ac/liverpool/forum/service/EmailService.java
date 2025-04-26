package ac.liverpool.forum.service;

public interface EmailService {
    void sendVerificationCode(String to, String code);
} 