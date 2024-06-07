package org.example.bullsandcowsapi.dto;

public record GameDto(int id, int number, byte[] session, byte[] userSession, String rule) {}
