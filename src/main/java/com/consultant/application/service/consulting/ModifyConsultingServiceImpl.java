package com.consultant.application.service.consulting;

import com.consultant.application.dao.ConsultingDao;
import com.consultant.application.dao.StaffDao;
import com.consultant.application.dto.request.ConsultingModifyRequest;
import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.entity.staff.Staff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ModifyConsultingServiceImpl implements ModifyConsultingService {

    private final ConsultingDao consultingDao;
    private final StaffDao staffDao;

    @Transactional
    public Consulting modifyConsulting(ConsultingModifyRequest modifyRequest) {
        Consulting consulting = consultingDao.findByIdWithFetchJoinAllProperties(modifyRequest.getConsultingId());
        Staff manager = staffDao.findConsultantActiveById(modifyRequest.getManagerId());

        //JPA의 더티 체킹으로, save 하지 않습니다.
        consulting.assignManager(manager);
        consulting.setFeedback(modifyRequest.getFeedback());
        return consulting;
    }
}
