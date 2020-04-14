package com.example.demo;

public interface SecurityService {
    String createToken(String subject, long ttlMillis);
    String getSubject(String token);
}
