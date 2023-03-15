package com.codeinside.attendancesystem.repository;

import com.codeinside.attendancesystem.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Modifying
    @Query(
            value = "SELECT * FROM administration OFFSET :offset LIMIT :limit ",
            nativeQuery = true
    )
    List<Admin> selectAllWithOffsetAndLimit(@Param(value = "offset") Long offset,
                                            @Param(value = "limit") Long limit);
}
