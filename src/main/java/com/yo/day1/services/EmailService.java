package com.yo.day1.services;

public interface EmailService {
    void sendAccountInfoEmail(String toEmail, String fullName, String username, String password);
}
