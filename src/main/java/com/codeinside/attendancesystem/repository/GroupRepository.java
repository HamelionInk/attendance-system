package com.codeinside.attendancesystem.repository;

import com.codeinside.attendancesystem.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Modifying
    @Query(
            value = "UPDATE students SET group_id= :groupId WHERE id= :studentId",
            nativeQuery = true
    )
    void updateGroupIdForStudent(@Param("groupId") Long groupId,
                                 @Param("studentId") Long studentId);
}
