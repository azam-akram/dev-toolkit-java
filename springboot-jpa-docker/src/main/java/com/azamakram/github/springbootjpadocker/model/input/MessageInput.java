package com.azamakram.github.springbootjpadocker.model.input;

import jakarta.validation.constraints.NotBlank;

public record MessageInput(@NotBlank String sender) {}
