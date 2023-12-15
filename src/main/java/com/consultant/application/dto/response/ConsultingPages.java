package com.consultant.application.dto.response;

import com.consultant.application.entity.consulting.Consulting;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * swagger @Schema를 등록하기 위한 Pagination Wrapper Class
 */
@Schema(title = "상담 내역 페이징 조회 결과")
public class ConsultingPages extends PageImpl<ConsultingResponse> {

    public ConsultingPages(List<Consulting> content, Pageable pageable, long total) {
        super(content.stream().map(ConsultingResponse::of).toList(), pageable, total);
    }
}
