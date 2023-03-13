package com.codeinside.attendancesystem.repository;

import com.codeinside.attendancesystem.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

    @Modifying
    @Query(
            value = "SELECT * FROM coaches OFFSET :offset LIMIT :limit ",
            nativeQuery = true
    )
    List<Coach> selectAllWithOffsetAndLimit(@Param(value = "offset") Long offset,
                                             @Param(value = "limit") Long limit);
}
