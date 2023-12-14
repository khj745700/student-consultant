package com.consultant.application.dto.response;

import com.consultant.application.entity.consulting.Consulting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ConsultingModifyResponse {
    private String consultantId;
    private String studentId;
    private String managerId;
    private String description;
    private String feedback;
    private LocalDateTime consultingDate;
    private LocalDateTime modifiedAt;


    public static ConsultingModifyResponse of(Consulting consulting) {
        return ConsultingModifyResponse.builder()
                .consultantId(consulting.getConsultant().getId())
                .studentId(consulting.getStudent().getId())
                .managerId(consulting.getManager().getId())
                .description(consulting.getDescription())
                .feedback(consulting.getFeedback())
                .consultingDate(consulting.getCreatedAt())
                .modifiedAt(consulting.getModifiedAt())
                .build();
    }
}
