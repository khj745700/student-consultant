package com.consultant.application.controller;

import com.consultant.application.dto.request.ConsultingModifyRequest;
import com.consultant.application.dto.request.ConsultingRegisterRequest;
import com.consultant.application.dto.response.ConsultingPages;
import com.consultant.application.dto.response.ConsultingResponse;
import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.service.consulting.FindConsultingService;
import com.consultant.application.service.consulting.ModifyConsultingService;
import com.consultant.application.service.consulting.RegisterConsultingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/consulting")
public class ConsultingController {
    private final RegisterConsultingService registerConsultingService;
    private final ModifyConsultingService modifyConsultingService;
    private final FindConsultingService findConsultingService;

    @PostMapping("")
    public ResponseEntity<Object> saveConsulting(@RequestBody @Valid ConsultingRegisterRequest request) {
        Consulting consulting = registerConsultingService.registerConsulting(request);
        ConsultingResponse response = ConsultingResponse.of(consulting);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("")
    public ResponseEntity<Object> consultingModify(@RequestBody @Valid ConsultingModifyRequest request) {
        Consulting consulting = modifyConsultingService.modifyConsulting(request);
        ConsultingResponse consultingResponse = ConsultingResponse.of(consulting);
        return ResponseEntity.ok(consultingResponse);
    }


    @GetMapping("/{consultingId}/{managerId}")
    public ResponseEntity<Object> consultDetails(@PathVariable("consultingId")Long consultingId, @PathVariable("managerId") String managerId ) {
        ConsultingModifyResponse consultingModifyResponse = ConsultingModifyResponse.of(findConsultingService.findConsulting(consultingId, managerId));
        return ResponseEntity.ok(consultingModifyResponse);
    }

    @GetMapping("")
    @Operation(summary = "상담 목록 조회 API", description = "필터 및 정렬 기능이 적용된 상담 목록 조회 API")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ConsultingPages.class)))
    })
    public ResponseEntity<Object> consultingList(@RequestParam @Schema(description = "상담자 ID", example = "abcd1234") String consultantId,
                                                 @RequestParam @Schema(description = "담당자 ID", example = "abcd1234") String managerId,
                                                 @RequestParam @Schema(description = "담당자 읽음 여부", example = "true") Boolean isReading,
                                                 @RequestParam @Schema(description = "피드백 내용 유무 여부", example = "true") Boolean isFeedback,
                                                 @RequestParam @Schema(description = "상담일시 오름차순 여부, default : false", example = "false") Boolean consultingDateAsc,
                                                 @RequestParam @Schema(description = "페이지 번호, default : 0", example = "0") Integer page,
                                                 @RequestParam @Schema(description = "페이지 내 요소 개수, default : 20", example = "20") Integer size) {
        int pageNum = page != null ? page : 0;
        int sizeNum = size != null ? size : 20;
        Page<?> consultingPage = findConsultingService.findConsultingPage(consultantId, managerId, isReading, isFeedback, consultingDateAsc, PageRequest.of(pageNum, sizeNum));
        return ResponseEntity.ok(consultingPage);
    }

}
