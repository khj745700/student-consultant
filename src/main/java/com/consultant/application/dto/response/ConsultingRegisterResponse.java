package com.consultant.application.dto.response;

import com.consultant.application.entity.consulting.Consulting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ConsultingRegisterResponse {
    private String consultantId;
    private String studentId;
    private String description;
    private LocalDateTime consultingDate;

    public static ConsultingRegisterResponse of(Consulting entity) {
        return ConsultingRegisterResponse.builder()
                .consultantId(entity.getConsultant().getId())
                .description(entity.getDescription())
                .studentId(entity.getStudent().getId())
                .consultingDate(entity.getCreatedAt())
                .build();
    }
}
