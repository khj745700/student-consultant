package com.consultant.application.dto.response;

import com.consultant.application.entity.consulting.Consulting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Schema(title = "상담 피드백 응답")
public class ConsultingResponse {
    @Schema(description = "상담원 ID", example = "abcd1234")
    private String consultantId;

    @Schema(description = "학생 ID", example = "abcd1234")
    private String studentId;

    @Schema(description = "담당자 ID", example = "abcd1234")
    private String managerId;

    @Schema(description = "상담 내용", example = "이 학생은 ....")
    private String description;

    @Schema(description = "피드백", example = "진행하시면 될 것 같습니다.")
    private String feedback;

    @Schema(description = "읽음여부", example = "true")
    private Boolean isReading;

    @Schema(description = "상담일시", example = "2023-12-15T00:18:39.098")
    private LocalDateTime consultingDate;

    @Schema(description = "최종 수정일시", example = "2023-12-15T00:18:39.098")
    private LocalDateTime modifiedAt;


    public static ConsultingResponse of(Consulting consulting) {
        return ConsultingResponse.builder()
                .consultantId(consulting.getConsultant().getId())
                .studentId(consulting.getStudent().getId())
                .managerId(consulting.getManager() != null ? consulting.getManager().getId() : null)
                .description(consulting.getDescription())
                .feedback(consulting.getFeedback())
                .consultingDate(consulting.getCreatedAt())
                .modifiedAt(consulting.getModifiedAt())
                .isReading(consulting.isReading())
                .build();
    }
}
