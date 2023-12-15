package com.consultant.application.dao;

import com.consultant.application.entity.staff.Staff;
import com.consultant.application.entity.staff.StaffRepository;
import com.consultant.application.entity.staff.StaffSort;
import com.consultant.application.exception.CustomException;
import com.consultant.application.exception.ErrorConstant;
import com.consultant.application.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;


/**
 * <h3>Dao 클래스를 생성한 이유?</h3>
 * <p>
 *      차 후 Spring Cache 등 추가 될 가능성이나 여러 상황이 존재할 수 있습니다.
 *      그럴 때 다른 서비스에서 Repository의 의존성이 생기는 것은 맞지 않습니다.
 *      그래서 Repository와 Service 사이에 Dao를 만들어 무조건 데이터베이스에 엑세스 하는 클래스가 필요합니다.
 * </p>
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class StaffDao {
    private final StaffRepository staffRepository;

    /**
     *
     * @param id 상담원의 아이디를 반환합니다.
     * @return 현재 재직중이며 상담중인 상담원을 조회합니다.
     * @throws NotFoundException 존재하지 않거나 퇴직한 경우 NotFoundException을 반환합니다.
     */
    public Staff findConsultantActiveById(String id) {
        Staff staff = findById(id, StaffSort.CONSULTANT, ExceptionHandler.notFound(id));
        if(!staff.isActive()) {
            throw ExceptionHandler.retired(id).get();
        }
        return staff;
    }

    private Staff findById(String id, StaffSort staffSort, Supplier<CustomException> supplier) {
        return staffRepository.findByIdAndSort(id, staffSort).orElseThrow(supplier);
    }

    /**
     * 에외 처리 시 발생하는 핸들러는 계속 생성되면 메모리 누수가 발생할 수 있기 때문에,
     * inner static class 로 관리합니다.
     */
    private static class ExceptionHandler {
        private static final Supplier<CustomException> NOT_FOUND;
        private static final Supplier<CustomException> RETIRED;

        static {
            NOT_FOUND = () -> new NotFoundException(ErrorConstant.STAFF_NOT_FOUND);
            RETIRED = () -> new NotFoundException(ErrorConstant.STAFF_IS_RETIRED);
        }
        private static Supplier<CustomException> notFound(String id) {
            log.trace("직원 아이디를 찾을 수 없습니다. {}", id);
            return NOT_FOUND;
        }

        private static Supplier<CustomException> retired(String id) {
            log.trace("해당 직원은 퇴직하였습니다. {}", id);
            return RETIRED;
        }
    }
}
