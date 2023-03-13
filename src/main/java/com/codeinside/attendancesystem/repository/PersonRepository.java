package com.codeinside.attendancesystem.repository;

import com.codeinside.attendancesystem.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByNumberPhone(String numberPhone);
}
