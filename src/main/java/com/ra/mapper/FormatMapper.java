package com.ra.mapper;

import com.ra.dto.request.format.FormatRequest;
import com.ra.dto.response.format.FormatResponse;
import com.ra.entity.Format;
import org.springframework.stereotype.Component;

@Component
public class FormatMapper {
    public FormatResponse toResponse(Format format) {
        return FormatResponse.builder()
                .id(format.getId())
                .formatName(format.getFormatName())
                .isDeleted(format.getIsDeleted())
                .build();
    }

    public Format toEntity(FormatRequest formatRequest) {
        return Format.builder()
                .formatName(formatRequest.getFormatName())
                .isDeleted(formatRequest.getIsDeleted())
                .build();
    }
}
