package com.example.springecom.user;

import com.example.springecom.user.exception.UserExistsException;
import com.example.springecom.user.exception.UserNotExistsException;
import com.example.springecom.user.exception.UserPersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(long id) {
       return this.userRepository.getUserById(id);
    }

    public void createUser(User user) throws UserPersistentException {
        this.userRepository.save(user);
    }

    public void updateUser(User user) throws UserExistsException {
        if (this.userRepository.existsById(user.getId())){
            throw new UserExistsException();
        }

        this.userRepository.save(user);
    }

    public void deleteUser(long id) throws UserNotExistsException {
        if (!this.userRepository.existsById(id)){
            throw new UserNotExistsException();
        }
        this.userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }
}
