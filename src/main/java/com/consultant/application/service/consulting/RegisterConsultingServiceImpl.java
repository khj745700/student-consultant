package com.consultant.application.service.consulting;

import com.consultant.application.dao.StaffDao;
import com.consultant.application.dao.StudentDao;
import com.consultant.application.dto.request.ConsultingRegisterRequest;
import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.entity.consulting.ConsultingRepository;
import com.consultant.application.entity.staff.Staff;
import com.consultant.application.entity.student.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterConsultingServiceImpl implements RegisterConsultingService{

    private final ConsultingRepository consultingRepository;
    private final StudentDao studentDao;
    private final StaffDao staffDao;

    @Override
    @Transactional
    public Consulting registerConsulting(ConsultingRegisterRequest request) {
        Staff consultant = staffDao.findConsultantActiveById(request.getConsultantId());
        Student student = studentDao.findStudentActiveById(request.getStudentId());

        Consulting consultingEntity = request.toEntity(consultant, student);
        consultingRepository.save(consultingEntity);

        return consultingEntity;
    }
}
