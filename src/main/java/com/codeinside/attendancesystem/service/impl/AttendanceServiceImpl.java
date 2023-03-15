package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.entity.Attendance;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.repository.AttendanceRepository;
import com.codeinside.attendancesystem.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Override
    public void createAttendanceStudentsForLesson(List<Attendance> attendances) {
        attendanceRepository.saveAll(attendances);
    }

    public void initializationAttendanceEntity(List<Student> students, Long lessonId) {
        List<Attendance> attendances = new ArrayList<>();
        students.forEach(data -> {
            Attendance attendance = new Attendance();
            attendance.setStudent_id(data.getId());
            attendance.setStudentName(data.getPerson().getFirstName() + " " + data.getPerson().getLastName());
            attendance.setLessonId(lessonId);
            attendances.add(attendance);
        });
        createAttendanceStudentsForLesson(attendances);
    }
}
