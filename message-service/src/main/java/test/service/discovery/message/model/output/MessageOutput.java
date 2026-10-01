package test.service.discovery.message.model.output;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageOutput {

    @NotNull
    private String messageKey;

    @NotBlank
    private String sender;

    @NotNull
    private LocalDateTime savedAt;
}