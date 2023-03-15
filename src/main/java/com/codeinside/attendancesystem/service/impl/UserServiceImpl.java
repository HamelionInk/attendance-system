package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.entity.Person;
import com.codeinside.attendancesystem.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String numberPhone) throws UsernameNotFoundException {
        Person person = personRepository.findByNumberPhone(numberPhone);

        if(person == null) {
            throw new UsernameNotFoundException("User " + numberPhone + " was not found in the database");
        }

        return new User(person.getUsername(), passwordEncoder.encode(person.getPassword()), person.getAuthorities());
    }
}
