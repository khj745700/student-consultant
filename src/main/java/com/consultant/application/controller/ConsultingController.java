package com.consultant.application.controller;

import com.consultant.application.dto.request.ConsultingModifyRequest;
import com.consultant.application.dto.request.ConsultingRegisterRequest;
import com.consultant.application.dto.response.ConsultingPages;
import com.consultant.application.dto.response.ConsultingResponse;
import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.service.consulting.FindConsultingService;
import com.consultant.application.service.consulting.ModifyConsultingService;
import com.consultant.application.service.consulting.RegisterConsultingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/consulting")
@Tag(name = "상담", description = "상담 API")
public class ConsultingController {
    private final RegisterConsultingService registerConsultingService;
    private final ModifyConsultingService modifyConsultingService;
    private final FindConsultingService findConsultingService;

    @PostMapping("")
    @Operation(summary = "상담 등록 API", description = " 학생과 상담원 간의 상담 정보를 등록하는 API")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "생성됨", content = @Content(schema = @Schema(implementation = ConsultingResponse.class))),
            @ApiResponse(responseCode = "400", description = "파라미터가 정상적으로 입력되지 않은 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"값이 유효하지 않습니다. (???는 필수 입력입니다.)\"\n}"))),
            @ApiResponse(responseCode = "404", description = "학생이 존재하지 않는 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"학생을 찾을 수 없습니다.\"\n}"))),
            @ApiResponse(responseCode = "404 ", description = "학생이 퇴원한 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"퇴원한 학생입니다.\"\n}"))),
            @ApiResponse(responseCode = "404  ", description = "직원이 존재하지 않는 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"직원을 찾을 수 없습니다.\"\n}"))),
            @ApiResponse(responseCode = "404   ", description = "직원이 퇴직한 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"퇴직한 직원입니다.\"\n}")))
    })
    public ResponseEntity<Object> saveConsulting(@RequestBody @Valid ConsultingRegisterRequest request) {
        Consulting consulting = registerConsultingService.registerConsulting(request);
        ConsultingResponse response = ConsultingResponse.of(consulting);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("")
    @Operation(summary = "상담 피드백 등록/수정 API", description = "담당자가 피드백을 등록하는 API")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ConsultingResponse.class))),
            @ApiResponse(responseCode = "400", description = "파라미터가 정상적으로 입력되지 않은 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"???를 입력해주세요.\"\n}"))),
            @ApiResponse(responseCode = "404", description = "학생이 존재하지 않는 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"학생을 찾을 수 없습니다.\"\n}"))),
            @ApiResponse(responseCode = "404 ", description = "학생이 퇴원한 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"퇴원한 학생입니다.\"\n}"))),
            @ApiResponse(responseCode = "404  ", description = "직원이 존재하지 않는 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"직원을 찾을 수 없습니다.\"\n}"))),
            @ApiResponse(responseCode = "404   ", description = "직원이 퇴직한 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"퇴직한 직원입니다.\"\n}")))
    })
    public ResponseEntity<Object> consultingModify(@RequestBody @Valid ConsultingModifyRequest request) {
        Consulting consulting = modifyConsultingService.modifyConsulting(request);
        ConsultingResponse consultingResponse = ConsultingResponse.of(consulting);
        return ResponseEntity.ok(consultingResponse);
    }


    @GetMapping("/{consultingId}/{managerId}")
    @Operation(summary = "상담 상세 보기 및 담당자 읽음 처리 API", description = "상담 정보 조회 및 담당자 읽음 처리 API")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ConsultingResponse.class))),
            @ApiResponse(responseCode = "400", description = "파라미터가 정상적으로 입력되지 않은 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"값이 유효하지 않습니다. (???는 필수 입력입니다.)\"\n}"))),
            @ApiResponse(responseCode = "404", description = "학생이 존재하지 않는 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"학생을 찾을 수 없습니다.\"\n}"))),
            @ApiResponse(responseCode = "404 ", description = "학생이 퇴원한 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"퇴원한 학생입니다.\"\n}"))),
            @ApiResponse(responseCode = "404  ", description = "직원이 존재하지 않는 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"직원을 찾을 수 없습니다.\"\n}"))),
            @ApiResponse(responseCode = "404   ", description = "직원이 퇴직한 경우", content = @Content(schema = @Schema(example = "{\n\t\"message\" : \"퇴직한 직원입니다.\"\n}")))
    })
    public ResponseEntity<Object> consultDetails(@PathVariable("consultingId") @Schema(description = "상담 ID", example = "1") Long consultingId,
                                                 @PathVariable("managerId")  @Schema(description = "담당자 ID", example = "abcd1234") String managerId ) {
        ConsultingResponse consultingResponse = ConsultingResponse.of(findConsultingService.findConsulting(consultingId, managerId));
        return ResponseEntity.ok(consultingResponse);
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
