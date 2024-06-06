package org.example.bullsandcowsapi.dto;

import java.util.UUID;

public record GameDto(int id, int number, byte[] session, byte[] userSession, String rule) {}
