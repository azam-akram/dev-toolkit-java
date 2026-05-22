package com.azamakram.github.springbootjpadocker.model.output;

import java.time.LocalDateTime;

public record MessageOutput(String messageKey, String sender, LocalDateTime savedAt) {}
