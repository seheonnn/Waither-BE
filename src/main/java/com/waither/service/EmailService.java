package com.waither.service;

public interface EmailService {
    String sendSimpleMessage(String to) throws Exception;

    String sendPassword(String to) throws Exception;
}

