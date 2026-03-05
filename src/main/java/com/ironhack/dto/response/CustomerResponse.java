package com.ironhack.dto.response;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String name,
        String email,
        Integer age,
        String address
) {}