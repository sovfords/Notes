package ru.sovfor.Notes_Microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sovfor.Notes_Microservice.domain.Note;
import ru.sovfor.Notes_Microservice.domain.Role;
import ru.sovfor.Notes_Microservice.domain.User;
import ru.sovfor.Notes_Microservice.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;


    private  BCryptPasswordEncoder passwordEncoder;

    public UserService() {
        passwordEncoder = new BCryptPasswordEncoder();

    }

    public User getUserByName(String name){

        return userRepository.getUserByName(name);
    }



    public boolean saveUser(User user){

            User userFromDB = userRepository.getUserByName(user.getUsername());

            if (userFromDB != null) {
                return false;
            }

            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println(user);
        return user;
    }

    public User updateUser(User user, long id){

        User oldUser = userRepository.findById(id).orElse(null);
        if(user.getNickname() != null){
            oldUser.setNickname(user.getNickname());
        }
        else{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            oldUser.setNickname(authentication.getName());
        }

        oldUser.setAge(user.getAge());
        oldUser.setInformationAbout(user.getInformationAbout());
        return userRepository.save(oldUser);
    }

    public User findUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
}
