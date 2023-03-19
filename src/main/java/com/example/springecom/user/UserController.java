package com.example.springecom.user;

import com.example.springecom.user.exception.UserExistsException;
import com.example.springecom.user.exception.UserNotExistsException;
import com.example.springecom.user.exception.UserPersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") long id) {
        return this.userService.getUserById(id);
    }

    @PostMapping("/")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<>("Successfully created user", HttpStatus.CREATED);
        }
        catch (UserPersistentException exception) {
            return new ResponseEntity<>("Cannot create user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updateUser(@RequestBody User user, Model model) {
        try {
            userService.updateUserById(user);
            return new ResponseEntity<>("Successfully updated user", HttpStatus.ACCEPTED);
        }
        catch (UserNotExistsException exception) {
            return new ResponseEntity<>("Cannot update user. User doesn't exists", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("Successfully deleted user", HttpStatus.ACCEPTED);
        }
        catch (UserNotExistsException exception) {
            return new ResponseEntity<>("Cannot delete user. User doesn't exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) boolean error) {
        return "login";
    }

//    @PostMapping("/login")
//    public String login(User user) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//
//        Authentication auth = authenticationManager.authenticate(authenticationToken);
//
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);
//
//        HttpSession session = req.getSession(true);
//        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
////
//        String returnPage = "login";
//
////        Optional<User> userFromDbOptional = this.userService.getUserByUsername(user.getUsername());
////        if (userFromDbOptional.isPresent()) {
////            User userFromDb = userFromDbOptional.get();
////            if (userFromDb.getPassword().equals(user.getPassword())) {
////                model.addAttribute("user", userFromDb);
////                returnPage = "index";
////            }
////        }
////        else {
////            returnPage = "login";
////            model.addAttribute("error", "User not found");
////        }
//        return "login";
//    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute(name = "registerform") User user, Model model) {
        System.out.println("a");
        try {
            this.userService.createUser(user);
            model.addAttribute("user", user);
            return "index";
        }
        catch (UserPersistentException exception) {
            model.addAttribute("error", "Cannot create user");
            return "register";
        }
    }

//    @GetMapping("/logout")
//    public String logout() {
//        return "login";
//    }
}
