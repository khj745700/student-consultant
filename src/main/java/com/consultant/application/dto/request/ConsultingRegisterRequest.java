package com.consultant.application.dto.request;

import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.entity.staff.Staff;
import com.consultant.application.entity.student.Student;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ConsultingRegisterRequest {
    @NotNull(message = "상담자 아이디를 입력해주세요.")
    private String consultantId;
    @NotNull(message = "학생 아이디를 입력해주세요.")
    private String studentId;
    @NotNull(message = "내용이 있어야 합니다.")
    private String description;

    public Consulting toEntity(Staff staff, Student student) {
        return Consulting.builder()
                .description(description)
                .consultant(staff)
                .student(student)
                .build();
    }
}
