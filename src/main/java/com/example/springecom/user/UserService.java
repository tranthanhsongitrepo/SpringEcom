package com.example.springecom.user;

import com.example.springecom.user.exception.UserNotExistsException;
import com.example.springecom.user.exception.UserPersistentException;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

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

    @Transactional
    public void createUser(User user) throws UserPersistentException {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User ID must not be specified");
        }

        try {
            this.userRepository.save(user);
        }
        catch (EntityExistsException exception) {
            throw new UserPersistentException("User with ID %d already exists".formatted(user.getId()), exception);
        }
        catch (DataAccessException dataAccessException) {
            throw new UserPersistentException("User with username %s already exists".formatted(user.getUsername()), dataAccessException);
        }
        catch (ConstraintViolationException constraintViolationException) {
            if (constraintViolationException.getConstraintName().equals("username")) {
                throw new UserPersistentException("User with username %s already exists".formatted(user.getUsername()), constraintViolationException);
            }
            else {
                throw new UserPersistentException("Unknown constraint violation", constraintViolationException);
            }
        }
    }

    public void updateUserById(User user) throws UserNotExistsException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        try {
            this.userRepository.save(user);
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new UserNotExistsException("User with ID %d doesn't exists".formatted(user.getId()), dataIntegrityViolationException);
        }
    }

    public void deleteUser(long id) throws UserNotExistsException {
        try {
            this.userRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new UserNotExistsException("User with ID %d doesn't exists".formatted(id), dataIntegrityViolationException);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }
}
