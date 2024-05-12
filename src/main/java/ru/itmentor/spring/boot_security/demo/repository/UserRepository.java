package ru.itmentor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.models.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User save(User user);

    List<User> findAll();

    Optional<User> findById(long id);

    List<User> findByDepartment(String department);

    List<User> findBySalary(int salary);

    void deleteById(long id);

    void delete(User user);
}

