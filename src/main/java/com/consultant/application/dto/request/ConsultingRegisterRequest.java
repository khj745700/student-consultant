package com.consultant.application.dto.request;

import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.entity.staff.Staff;
import com.consultant.application.entity.student.Student;
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
@Schema(title = "상담 등록 요청")
public class ConsultingRegisterRequest {
    @NotNull(message = "상담자 아이디를 입력해주세요.")
    @Schema(description = "직원 ID", example = "abcd1234")
    private String consultantId;

    @NotNull(message = "학생 아이디를 입력해주세요.")
    @Schema(description = "학생 ID", example = "abcd1234")
    private String studentId;
    @NotNull(message = "내용이 있어야 합니다.")
    @Schema(description = "상담 내용", example = "이 학생은...")
    private String description;

    public Consulting toEntity(Staff staff, Student student) {
        return Consulting.builder()
                .description(description)
                .consultant(staff)
                .student(student)
                .build();
    }
}
