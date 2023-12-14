package com.consultant.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ConsultingModifyRequest {
    @NotNull(message = "상담ID는 필수 입력입니다.")
    private Long consultingId;

    @NotNull(message = "담당자ID는 필수 입력입니다.")
    private String managerId;

    @NotNull(message = "피드백은 필수 입력입니다.")
    private String feedback;
}
