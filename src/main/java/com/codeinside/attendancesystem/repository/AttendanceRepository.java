package com.codeinside.attendancesystem.repository;

import com.codeinside.attendancesystem.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
