package com.codeinside.attendancesystem.repository;

import com.codeinside.attendancesystem.entity.Coach;
import com.codeinside.attendancesystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Modifying
    @Query(
            value = "UPDATE students SET group_id= :groupId WHERE id= :studentId",
            nativeQuery = true
    )
    void updateGroupIdForStudent(@Param("groupId") Long groupId,
                                 @Param("studentId") Long studentId);

    @Modifying
    @Query(
            value = "UPDATE students SET group_id=null WHERE id= :studentId",
            nativeQuery = true
    )
    void updateGroupIdOnNullForStudent(@Param("studentId") Long studentId);

    @Modifying
    @Query(
            value = "SELECT * FROM students OFFSET :offset LIMIT :limit ",
            nativeQuery = true
    )
    List<Student> selectAllWithOffsetAndLimit(@Param(value = "offset") Long offset,
                                            @Param(value = "limit") Long limit);

}
