package com.postgresql.pirotecnicamassa.service;

import com.postgresql.pirotecnicamassa.model.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
}