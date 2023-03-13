package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.entity.Attendance;
import com.codeinside.attendancesystem.entity.Student;

import java.util.List;

public interface AttendanceService {
    void createAttendanceStudentsForLesson(List<Attendance> attendances);
    void initializationAttendanceEntity(List<Student> students, Long lessonId);
}
