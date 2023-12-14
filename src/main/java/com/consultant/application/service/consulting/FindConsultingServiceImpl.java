package com.consultant.application.service.consulting;

import com.consultant.application.dao.ConsultingDao;
import com.consultant.application.entity.consulting.Consulting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindConsultingServiceImpl implements FindConsultingService{

    private final ConsultingDao consultingDao;

    public Consulting findConsulting(Long consultingId, String managerId) {
        Consulting consulting = consultingDao.findByIdWithFetchJoinAllProperties(consultingId);

        if(consulting.getManager().getId().equals(managerId))
            consulting.read();

        return consulting;
    }
}
