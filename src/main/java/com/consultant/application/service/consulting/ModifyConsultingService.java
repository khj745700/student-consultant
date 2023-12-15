package com.consultant.application.service.consulting;


import com.consultant.application.dto.request.ConsultingModifyRequest;
import com.consultant.application.entity.consulting.Consulting;

public interface ModifyConsultingService {
    Consulting modifyConsulting(ConsultingModifyRequest feedbackRequest);
}
