package com.consultant.application.dao;

import com.consultant.application.entity.student.Student;
import com.consultant.application.entity.student.StudentRepository;
import com.consultant.application.entity.student.StudentStatus;
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
public class StudentDao {
    private final StudentRepository studentRepository;

    /**
     *
     * @param id 재직중인 학생의 아이디가 필요합니다.
     * @return 재직중인 학생을 반환합니다.
     * @throws NotFoundException 존재하지 않거나, 퇴직한 경우 반환됩니다.
     */
    public Student findStudentActiveById(String id){
         Student student = findById(id, StudentStatus.ACTIVE, ExceptionHandler.notFound(id));
         if(!student.isActive()) {
             throw ExceptionHandler.retired(id).get();
         }

         return student;
    }

    private Student findById(String id, StudentStatus status, Supplier<CustomException> supplier) {
        return studentRepository.findByIdAndStudentStatus(id, status).orElseThrow(supplier);
    }


    /**
     * 에외 처리 시 발생하는 핸들러는 계속 생성되면 메모리 누수가 발생할 수 있기 때문에,
     * inner static class 로 관리합니다.
     */
    private static class ExceptionHandler {

        private static final Supplier<CustomException> NOT_FOUND;
        private static final Supplier<CustomException> RETIRED;

        static {
            NOT_FOUND = () -> new NotFoundException(ErrorConstant.STUDENT_NOT_FOUND);
            RETIRED = () -> new NotFoundException(ErrorConstant.STUDENT_IS_RETIRED);
        }

        private static Supplier<CustomException> notFound(String id) {
            log.trace("학생 아이디를 찾을 수 없습니다. {}", id);
            return NOT_FOUND;
        }

        private static Supplier<CustomException> retired(String id) {
            log.trace("해당 학생은 퇴원하였습니다. {}", id);
            return RETIRED;
        }
    }

}
