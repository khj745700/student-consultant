package com.consultant.application.controller;

import com.consultant.application.dto.request.ConsultingModifyRequest;
import com.consultant.application.dto.request.ConsultingRegisterRequest;
import com.consultant.application.dto.response.ConsultingModifyResponse;
import com.consultant.application.dto.response.ConsultingRegisterResponse;
import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.service.consulting.FindConsultingService;
import com.consultant.application.service.consulting.ModifyConsultingService;
import com.consultant.application.service.consulting.RegisterConsultingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RequiredArgsConstructor
@RestController("/api/consulting")
public class ConsultingController {
    private final RegisterConsultingService registerConsultingService;
    private final ModifyConsultingService modifyConsultingService;
    private final FindConsultingService findConsultingService;

    @PostMapping("")
    public ResponseEntity<Object> saveConsulting(@RequestBody @Valid ConsultingRegisterRequest request) {
        Consulting consulting = registerConsultingService.registerConsulting(request);
        ConsultingRegisterResponse consultingRegisterResponse = ConsultingRegisterResponse.of(consulting);
        return ResponseEntity.status(HttpStatus.CREATED).body(consultingRegisterResponse);
    }

    @PutMapping("")
    public ResponseEntity<Object> consultingModify(@RequestBody @Valid ConsultingModifyRequest request) {
        Consulting consulting = modifyConsultingService.modifyConsulting(request);
        ConsultingModifyResponse consultingModifyResponse = ConsultingModifyResponse.of(consulting);
        return ResponseEntity.ok(consultingModifyResponse);
    }

    
    @GetMapping("/{consultingId}/{managerId}")
    public ResponseEntity<Object> consultDetails(@PathVariable("consultingId")Long consultingId, @PathVariable("managerId") String managerId ) {
        ConsultingModifyResponse consultingModifyResponse = ConsultingModifyResponse.of(findConsultingService.findConsulting(consultingId, managerId));
        return ResponseEntity.ok(consultingModifyResponse);
    }

}
