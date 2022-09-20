package com.example.evently.services.email;

import com.example.evently.models.EmailDetails;

public interface EmailService {
    void sendSimpleMail(EmailDetails details);
}
