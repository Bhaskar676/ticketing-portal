package com.ticketingportal.repository;

import com.ticketingportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByUsernameOrEmail(String username, String email);
    List<User> findByLocationIgnoreCase(String location);
    List<User> findByUsernameContainingIgnoreCase(String query);
    List<User> findByEmailContainingIgnoreCase(String query);
    List<User> findByLocationContainingIgnoreCase(String query);


}
