package ru.itmentor.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService extends UserDetailsImpl implements UserDetailsService {
    private  UserRepository userRepository;
    private RoleService roleService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void updateUser(User user, Long id) {

        User existingUser = userRepository.getById(id);
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setDepartment(user.getDepartment());
        existingUser.setSalary(user.getSalary());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword()); // Возможно, нужно добавить логику шифрования пароля
    }

    public void deleteUser(Long id) {
        // Проверяем, существует ли пользователь с заданным идентификатором
        Optional<User> userOptional = userRepository.findById(id);
            // Если пользователь существует, удаляем его из базы данных
            userRepository.delete(userOptional.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new UserDetails(user.get());
    }
}
