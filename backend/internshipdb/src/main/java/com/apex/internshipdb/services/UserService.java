/*
 * Copyright (C) 2019  apex
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.apex.internshipdb.services;

import com.apex.internshipdb.exceptions.UserNotFoundException;
import com.apex.internshipdb.models.User;
import com.apex.internshipdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class UserService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UserNotFoundException("Username: " + username + " not found"));
    }

    /**
     * Find user by id
     * @param id to search by
     * @return user that matches the id.
     */
    public User find(long id) {
        return Optional.ofNullable(userRepository.findById(id)).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + id));
    }

    /**
     * Find user by email
     * @param email to search by
     * @return user that matches the id.
     */
    public User find(String email) {
       return Optional.ofNullable(userRepository.findByUsername(email)).orElseThrow(() ->
                new UserNotFoundException("User not found with email: " + email));
    }

    /**
     *
     * @return list of all users in database
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @EventListener
    public void preLoadUsers(ApplicationReadyEvent event) {

        ArrayList<SimpleGrantedAuthority> studentPriv = new ArrayList<>();
        ArrayList<SimpleGrantedAuthority> adminPriv = new ArrayList<>();

        studentPriv.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

        adminPriv.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        adminPriv.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        userRepository.save(new User("Mark", "daltonm@merrimack.edu", adminPriv));
        userRepository.save(new User("Bernard", "heresb@merrimack.edu", adminPriv));
        userRepository.save(new User("Kyle", "magerk@merrimack.edu", studentPriv));
        userRepository.save(new User("Katie Dewey-Rosenfeld", "deweyrosenfk@merrimack.edu", adminPriv));
        userRepository.save(new User("Christopher Stuetzle", "stuetzlec@merrimack.edu", adminPriv));
        userRepository.save(new User("Zachary Kissel", "kisselz@merrimack.edu", studentPriv));
    }

    public void promoteUser(long longID) {
        User userAtID = userRepository.findById(longID);
        userAtID.getAuthorities().add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //userAtID.setRole("admin");
        userRepository.save(userAtID);
    }

    public void demoteUser(long longID) {
        User userAtID = userRepository.findById(longID);
        userAtID.getAuthorities().remove(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //userAtID.setRole("student");
        userRepository.save(userAtID);
    }

    public void delete(long longID) {
        userRepository.deleteById(longID);
    }

    public boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username);

        return user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

    }

}

