package com.consultant.application.entity.staff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, String> {

    Optional<Staff> findByIdAndSort(String id, StaffSort sort);

    Optional<Staff> findByIdAndSortAndStaffStatus(String id, StaffSort sort, StaffStatus staffStatus);

}
