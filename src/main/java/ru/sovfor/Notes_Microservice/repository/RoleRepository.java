package ru.sovfor.Notes_Microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sovfor.Notes_Microservice.domain.Role;
import ru.sovfor.Notes_Microservice.domain.User;


public interface RoleRepository extends JpaRepository<Role,Long> {

}
