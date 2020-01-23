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

import com.apex.internshipdb.models.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {

    Internship findById(long id);
    @Query("select i from Internship i where lower(i.location) like lower(concat('%', ?1, '%'))")
    List<Internship> findByLocationContaining(String location);

    @Query("select i from Internship i where i.areaInterestTag=?1")
    List<Internship> findByAreaInterestTag(String areaInterest);

    @Query("select i from Internship i where i.populationTag=?1")
    List<Internship> findByPopulationTag(String population);

    @Query("select i from Internship i where i.settingTag=?1")
    List<Internship> findBySettingTag(String setting);

    @Query("select i from Internship i where i.groupTag=?1")
    List<Internship> findByGroupTag(String group);

    @Query("select i from Internship i where lower(i.description) like lower(concat('%', ?1, '%'))")
    List<Internship> findByDescriptionContaining(String keyword);

}
