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

package com.apex.internshipdb.repository;

import com.apex.internshipdb.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t where t.id = ?1")
    Tag findById(long id);

    @Query("select t from Tag t where t.name = ?1")
    Tag findByName(String name);

    @Query("select t from Tag t where t.type = ?1")
    List<Tag> findAllByType(String type);
    @Query("select t from Tag t where t.name = ?1")
    List<Tag> findAllByName(String name);

    @Query("update Tag t set timesSearched = 0")
    void resetTagCount();

    List<Tag> findByOrderByName();
    List<Tag> findByOrderByType();
    List<Tag> findByOrderByTimesSearched();

}