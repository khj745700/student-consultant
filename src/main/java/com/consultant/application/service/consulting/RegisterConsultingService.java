package com.consultant.application.service.consulting;

import com.consultant.application.dto.request.ConsultingRegisterRequest;
import com.consultant.application.entity.consulting.Consulting;

public interface RegisterConsultingService {
    Consulting registerConsulting(ConsultingRegisterRequest request);
}
