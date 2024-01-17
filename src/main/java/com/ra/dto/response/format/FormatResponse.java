package com.ra.dto.response.format;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FormatResponse {
    private Long id;
    private String formatName;
    private Boolean isDeleted;
}
