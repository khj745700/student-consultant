package com.consultant.application.entity.consulting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConsultingRepository extends JpaRepository<Consulting, Long>, CustomConsultingRepository {

    @Query("SELECT c FROM Consulting c JOIN FETCH ALL PROPERTIES WHERE c.id = :id")
    Optional<Consulting> findByIdWithFetchJoinAllProperties(@Param("id") Long id);
}
