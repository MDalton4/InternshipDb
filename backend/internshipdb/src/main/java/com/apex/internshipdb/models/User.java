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

package com.apex.internshipdb.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String username;
    private String areaInterest;

    private ArrayList<SimpleGrantedAuthority> roles;

    @OneToMany
    private List<Internship> favorites;

    public User() {
        this.roles = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public User(String name, String email, ArrayList<SimpleGrantedAuthority> roles) {
        this.name = name;
        this.username = email;
        this.roles = roles;
        this.favorites = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("User with id: %d, email: %s, interest: %s ", id, username, areaInterest);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(ArrayList<SimpleGrantedAuthority> roles) {
        this.roles = roles;
    }

    public void setAreaInterest(String interest) {
        this.areaInterest = interest;
    }

    public void setFavorites(List<Internship> favorites) {
        this.favorites = favorites;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAreaInterest() { return areaInterest; }

    public List<Internship> getFavorites() {
        return favorites;
    }

    @Override
    public ArrayList<SimpleGrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}