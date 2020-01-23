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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String type;

    private long timesSearched;

    public Tag() {

    }

    /**
     * New tag is added, timesSearched = 0.
     * @param name of the tag
     * @param type of tag (area of interest, population, etc.)
     */
    public Tag(String name, String type) {
        this.name = name;
        this.type = type;
        this.timesSearched = 0;
    }

    public Tag(String name, String type, long timesSearched){
        this.name = name;
        this.type = type;
        this.timesSearched = timesSearched;
    }

    /**
     * Increment timesSearched by 1
     */
    public void increment(){
        timesSearched += 1;
    }

    @Override
    public String toString() {
        return String.format("Tag with id: %d, name: %s, type: %s ", id, name, type);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTimesSearched(long timesSearched) {
        this.timesSearched = timesSearched;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public long getTimesSearched() {
        return timesSearched;
    }
}
