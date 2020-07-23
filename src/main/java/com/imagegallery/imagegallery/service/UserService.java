package com.imagegallery.imagegallery.service;

import com.imagegallery.imagegallery.model.User;
import com.imagegallery.imagegallery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User getOne(int id) {
        return userRepository.getOne(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public java.util.Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}