package com.consultant.application.service.consulting;


import com.consultant.application.entity.consulting.Consulting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindConsultingService {
    Consulting findConsulting(Long consultingId, String managerId);

    Page<Consulting> findConsultingPage(String consultantId, String managerId, Boolean isReading, Boolean isFeedback, Boolean consultingDateAsc, Pageable pageable);
}
