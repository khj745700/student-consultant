package com.consultant.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Schema(title = "상담 피드백 수정/등록 요청")
public class ConsultingModifyRequest {
    @NotNull(message = "상담ID는 필수 입력입니다.")
    @Schema(description = "상담 ID", example = "1")
    private Long consultingId;

    @NotNull(message = "담당자ID는 필수 입력입니다.")
    @Schema(description = "직원 ID", example = "abcd1234")
    private String managerId;

    @NotNull(message = "피드백은 필수 입력입니다.")
    @Schema(description = "피드백", example = "진행하시면 될 것 같습니다.")
    private String feedback;
}
