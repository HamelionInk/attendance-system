package com.codeinside.attendancesystem.repository;

import com.codeinside.attendancesystem.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Modifying
    @Query(
            value = "SELECT * FROM groups OFFSET :offset LIMIT :limit ",
            nativeQuery = true
    )
    Optional<List<Group>> selectAllWithOffsetAndLimit(@Param(value = "offset") Long offset,
                                                      @Param(value = "limit") Long limit);

}
