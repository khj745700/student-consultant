package com.consultant.application.entity.consulting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomConsultingRepository {
    Page<Consulting> findConsultingList(String consultantId, String managerId, Boolean isReading, Boolean isFeedback, Boolean consultingDateAsc, Pageable pageable);
}
