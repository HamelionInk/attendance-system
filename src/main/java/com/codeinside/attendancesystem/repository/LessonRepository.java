package com.codeinside.attendancesystem.repository;

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
            value = "UPDATE lessons SET lesson_finish= :flag WHERE id= :id",
            nativeQuery = true
    )
    void updateEndOfClass(@Param(value = "id") Long id,
                          @Param(value = "flag") Boolean flag);

}
