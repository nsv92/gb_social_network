package ru.gb.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.backend.entity.User;
import ru.gb.backend.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserForId(Long id) {
        return userRepository.findById(id);
    }

    public User getUserForIds(Long id){
        return userRepository.findById(id)
                .orElse(new User());
    }
}
