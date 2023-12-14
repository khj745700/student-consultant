package com.consultant.application.service.consulting;


import com.consultant.application.entity.consulting.Consulting;

public interface FindConsultingService {
    Consulting findConsulting(Long consultingId, String managerId);
}
