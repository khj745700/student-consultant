package com.consultant.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorConstant {
    INTERNAL_SERVER_ERROR("알 수 없는 오류가 발생했습니다." , HttpStatus.INTERNAL_SERVER_ERROR),
    STUDENT_NOT_FOUND("학생을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    STUDENT_IS_RETIRED("퇴원한 학생입니다.", HttpStatus.NOT_FOUND),
    STAFF_NOT_FOUND("직원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    STAFF_IS_RETIRED("퇴직한 직원입니다", HttpStatus.NOT_FOUND),
    CONSULTING_NOT_FOUND("상담 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);


    private final String message;
    private final HttpStatus httpStatus;

    ErrorConstant(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
