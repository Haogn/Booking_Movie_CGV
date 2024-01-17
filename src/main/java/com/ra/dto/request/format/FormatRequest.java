package com.ra.dto.request.format;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FormatRequest {
    private String formatName;
    private Boolean isDeleted;
}
