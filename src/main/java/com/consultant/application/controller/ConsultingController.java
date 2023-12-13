package com.consultant.application.controller;

import com.consultant.application.dto.request.ConsultingRegisterRequest;
import com.consultant.application.dto.response.ConsultingRegisterResponse;
import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.service.consulting.RegisterConsultingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RequiredArgsConstructor
@RestController("/api/consulting")
public class ConsultingController {
    private final RegisterConsultingService registerConsultingService;

    @PostMapping("")
    public ResponseEntity<Object> saveConsulting(@RequestBody @Valid ConsultingRegisterRequest request) {
        Consulting consulting = registerConsultingService.registerConsulting(request);
        ConsultingRegisterResponse consultingRegisterResponse = ConsultingRegisterResponse.of(consulting);
        return ResponseEntity.status(HttpStatus.CREATED).body(consultingRegisterResponse);
    }

}
