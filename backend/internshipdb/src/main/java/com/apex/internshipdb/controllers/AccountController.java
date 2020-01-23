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

package com.apex.internshipdb.controllers;

import com.apex.internshipdb.exceptions.UserNotFoundException;
import com.apex.internshipdb.models.User;
import com.apex.internshipdb.security.JwtTokenProvider;
import com.apex.internshipdb.services.UserService;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value="/api")
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    /**
     * Method to login a user
     * @param request token from google to authenticate the user
     * @return JWT with user info or error
     */
    @RequestMapping(value = "/account/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody Map<String, String> request) {

        final HttpTransport transport = new NetHttpTransport();
        final JacksonFactory jsonFactory = new JacksonFactory();

        final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("208869903658-dn8phr16v5tqenf62oo14gvbobmd7vmp.apps.googleusercontent.com"))
                .build();

        User user = null;

        try {

            GoogleIdToken idToken = verifier.verify(request.get("id_token"));

            if (idToken != null) {

                Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String domain = payload.getHostedDomain();

                if (domain.equals("merrimack.edu")) {

                    try {

                        user = userService.find(email);

                    } catch (UserNotFoundException e) {

                        String name = (String) payload.get("name");
                        ArrayList<SimpleGrantedAuthority> studentPriv = new ArrayList<>();

                        studentPriv.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

                        user = new User();
                        user.setUsername(email);
                        user.setName(name);
                        user.setAuthorities(studentPriv);
                        userService.add(user);

                    } finally {

                        String username = user.getUsername();
                        String token = jwtTokenProvider.createToken(username, userService.find(username).getAuthorities());
                        Map<Object, Object> model = new HashMap<>();

                        model.put("token", token);

                        return ResponseEntity.ok(model);

                    }

                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to Authenticate");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to Authenticate");
    }

    /**
     * Method to check if the token is still valid
     * @param token token of user
     * @return true or unauthorized
     */
    @RequestMapping(value = "/account/checktoken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity checkToken(@RequestParam(value = "token") String token) {
        if(jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
