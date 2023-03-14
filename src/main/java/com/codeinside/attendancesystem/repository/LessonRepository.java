package com.codeinside.attendancesystem.repository;

import com.codeinside.attendancesystem.entity.Coach;
import com.codeinside.attendancesystem.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(
            value = "SELECT * FROM lessons WHERE group_id= :groupId",
            nativeQuery = true
    )
    List<Lesson> selectClassesByGroupId(@Param(value = "groupId") Long id);

    @Modifying
    @Query(
            value = "SELECT * FROM lessons OFFSET :offset LIMIT :limit ",
            nativeQuery = true
    )
    List<Lesson> selectAllWithOffsetAndLimit(@Param(value = "offset") Long offset,
                                             @Param(value = "limit") Long limit);

    @Modifying
    @Query(
            value = "UPDATE lessons SET lesson_finish= :flag WHERE id= :id",
            nativeQuery = true
    )
    void updateEndOfClass(@Param(value = "id") Long id,
                          @Param(value = "flag") Boolean flag);

    @Modifying
    @Query(
            value = "UPDATE attendance SET attendance= :attendance WHERE lesson_id= :lessonId AND student_id= :studentId",
            nativeQuery = true
    )
    void updateAttendanceLessonForStudent(@Param(value = "lessonId") Long lessonId,
                                          @Param(value = "studentId") Long studentId,
                                          @Param(value = "attendance") Boolean attendance);
}
