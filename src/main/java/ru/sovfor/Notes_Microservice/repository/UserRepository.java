package ru.sovfor.Notes_Microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sovfor.Notes_Microservice.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT * FROM users where nickname = ?1",nativeQuery = true)
    User getUserByName(String name);

    @Query(value = "DELETE  FROM users WHERE nickname = ?1",nativeQuery = true)
    void deleteUserByName(String name);

}
