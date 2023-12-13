package com.consultant.application.exception;

public class NotFoundException extends CustomException{

    public NotFoundException(ErrorConstant errorConstant) {
        super(errorConstant);
    }
}
