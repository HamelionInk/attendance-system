package com.codeinside.attendancesystem.repository;

import com.codeinside.attendancesystem.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Modifying
    @Query(
            value = "SELECT * FROM administrations OFFSET :offset LIMIT :limit ",
            nativeQuery = true
    )
    Optional<List<Admin>> selectAllWithOffsetAndLimit(@Param(value = "offset") Long offset,
                                                      @Param(value = "limit") Long limit);
}
