package com.consultant.application.controller;

import com.consultant.application.dao.ConsultingDao;
import com.consultant.application.dto.request.ConsultingModifyRequest;
import com.consultant.application.dto.request.ConsultingRegisterRequest;
import com.consultant.application.dto.response.ConsultingResponse;
import com.consultant.application.entity.consulting.Consulting;
import com.consultant.application.entity.consulting.ConsultingRepository;
import com.consultant.application.entity.staff.Staff;
import com.consultant.application.entity.staff.StaffRepository;
import com.consultant.application.entity.staff.StaffSort;
import com.consultant.application.entity.staff.StaffStatus;
import com.consultant.application.entity.student.Student;
import com.consultant.application.entity.student.StudentRepository;
import com.consultant.application.entity.student.StudentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsultingControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/consulting";
    private static final String STUDENT_ID = "test";
    private static final String CONSULTANT_ID = "testConsultant";
    private static final String MANAGER_ID = "testManager";
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ConsultingRepository consultingRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ConsultingDao consultingDao;


    @BeforeAll
    void init() throws Exception{
        Student dummyStudent = Student.builder()
                .id(STUDENT_ID)
                .name("testStudent1")
                .studentStatus(StudentStatus.ACTIVE)
                .build();

        Staff dummyConsultant = Staff.builder()
                .id(CONSULTANT_ID)
                .name("testConsultant")
                .sort(StaffSort.CONSULTANT)
                .staffStatus(StaffStatus.ACTIVE)
                .build();

        Staff dummyManager = Staff.builder()
                .id(MANAGER_ID)
                .name("testManager")
                .sort(StaffSort.CONSULTANT)
                .staffStatus(StaffStatus.ACTIVE)
                .build();

        Consulting dummyConsulting = Consulting.builder()
                .description("test")
                .consultant(dummyConsultant)
                .student(dummyStudent)
                .manager(dummyManager)
                .build();

        staffRepository.save(dummyManager);
        staffRepository.save(dummyConsultant);
        studentRepository.save(dummyStudent);
        consultingRepository.save(dummyConsulting);
    }

    @Test
    @DisplayName("[상담] 등록 API - 성공")
    void 상담등록API_성공() throws Exception {
        String body = objectMapper.writeValueAsString(
                ConsultingRegisterRequest.builder()
                        .consultantId(CONSULTANT_ID)
                        .studentId(STUDENT_ID)
                        .description("test")
                        .build()
        );

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("[상담] 등록 API - 실패 : 상담자ID_NULL")
    void 상담등록API_상담자ID_없음() throws Exception {
        String body = objectMapper.writeValueAsString(
                ConsultingRegisterRequest.builder()
                        .studentId(STUDENT_ID)
                        .description("test")
                        .build()
        );

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("[상담] 등록 API - 실패 : 학생ID_NULL")
    void 상담등록API_학생ID_없음() throws Exception {
        String body = objectMapper.writeValueAsString(
                ConsultingRegisterRequest.builder()
                        .consultantId(CONSULTANT_ID)
                        .description("test")
                        .build()
        );

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("[상담] 등록 API - 실패 : 상담내역_NULL")
    void 상담등록API_상담내역_없음() throws Exception {
        String body = objectMapper.writeValueAsString(
                ConsultingRegisterRequest.builder()
                        .consultantId(CONSULTANT_ID)
                        .studentId(STUDENT_ID)
                        .build()
        );

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("[상담] 피드백 등록/수정 API - 성공")
    void 상담_피드백_API_등록_수정_성공() throws Exception {

        String body = objectMapper.writeValueAsString(
                ConsultingModifyRequest.builder()
                        .consultingId(1L)
                        .feedback("test")
                        .managerId(MANAGER_ID)
                        .build()
        );
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("[상담] 피드백 등록/수정 API - 실패 : 상담 ID 존재하지 않음")
    void 상담_피드백_API_등록_수정_아이디_비존재() throws Exception {

        String body = objectMapper.writeValueAsString(
                ConsultingModifyRequest.builder()
                        .consultingId(0L)
                        .feedback("test")
                        .managerId(MANAGER_ID)
                        .build()
        );
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("[상담] 피드백 등록/수정 API - 실패 : 피드백 존재하지 않음")
    void 상담_피드백_API_등록_수정_피드백_내용없음() throws Exception {

        String body = objectMapper.writeValueAsString(
                ConsultingModifyRequest.builder()
                        .consultingId(0L)
                        .managerId(MANAGER_ID)
                        .build()
        );
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("[상담] 피드백 등록/수정 API - 실패 : 담당자 ID 없음")
    void 상담_피드백_API_등록_수정_담당자_ID_없음() throws Exception {
        String body = objectMapper.writeValueAsString(
                ConsultingModifyRequest.builder()
                        .consultingId(0L)
                        .feedback("test")
                        .build()
        );
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("[상담] 상담 상세 보기 및 담당자 읽음 처리 API - 성공(담당자 읽음)")
    void 상담_상세조회_담당자_읽음() throws Exception {
        Long consultId = 1L;
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + consultId + "/" + MANAGER_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        if(!result.getResponse().getContentAsString().contains("\"isReading\":true")) {
            throw new Exception("읽음 처리되지 않았습니다.");
        }
    }

    @Test
    @DisplayName("[상담] 상담 상세 보기 및 담당자 읽음 처리 API - 성공(담당자 읽지 않음)")
    void 상담_상세조회_담당자_읽지않음() throws Exception {
        Long consultId = 1L;
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + consultId + "/" + CONSULTANT_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        if(result.getResponse().getContentAsString().contains("\"isReading\":true")) {
            throw new Exception("읽음 처리되었습니다.");
        }
    }

    @Test
    @DisplayName("[상담] 페이지네이션 조회 API - 성공")
    void 상담_페이지네이션조회() throws Exception{
        Long consultId = 1L;
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
