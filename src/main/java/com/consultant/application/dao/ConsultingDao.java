package com.consultant.application.dao;

import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.entity.consulting.ConsultingRepository;
import com.consultant.application.exception.CustomException;
import com.consultant.application.exception.ErrorConstant;
import com.consultant.application.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultingDao {

    private final ConsultingRepository consultingRepository;

    /**
     *
     * @param id 상담의 ID를 입력합니다.
     * @throws NotFoundException 상담이 존재하지 않을 시 던집니다.
     * @return 상담과 연관된 모든 entity가 조회된 엔티티를 반환합니다.
     */
    public Consulting findByIdWithFetchJoinAllProperties(Long id) {
        return consultingRepository.findByIdWithFetchJoinAllProperties(id).orElseThrow(ExceptionHandler.notFound(id));
    }

    public Page<?> findConsultingPagination(String consultantId, String managerId, Boolean isReading, Boolean isFeedback, Boolean consultingDateAsc, Pageable pageable){
        return consultingRepository.findConsultingList(consultantId, managerId, isReading, isFeedback, consultingDateAsc, pageable);
    }

    private static class ExceptionHandler {
        private static final Supplier<CustomException> NOT_FOUND;

        static {
            NOT_FOUND = () -> new NotFoundException(ErrorConstant.CONSULTING_NOT_FOUND);
        }

        private static Supplier<CustomException> notFound(Long id) {
            log.trace("상담 내역을 찾을 수 없습니다. {}", id);
            return NOT_FOUND;
        }
    }
}
