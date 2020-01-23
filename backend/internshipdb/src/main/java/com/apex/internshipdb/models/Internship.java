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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;

@Entity
public class Internship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String position;
    private String company;
    private String link;
    @Column(columnDefinition="LONGVARCHAR")
    private String description;
    private String location;
    private String startDate;
    private String areaInterestTag;
    private String populationTag;
    private String settingTag;
    private String groupTag;

    public Internship() {

    }

    public Internship(String pos, String com, String link, String des, String loc, String date,
                      String interests, String pops, String setting, String group) {
        this.position = pos;
        this.company = com;
        this.link = link;
        this.description = des;
        this.location = loc;
        this.startDate = date;
        this.areaInterestTag = interests;
        this.populationTag = pops;
        this.settingTag = setting;
        this.groupTag = group;
    }

    @Override
    public String toString() {
        return String.format("Internship with id: %d, position: %s", id, position);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCompany(String comapny) {
        this.company = comapny;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setAreaInterestTag(String areaInterestTag) {
        this.areaInterestTag = areaInterestTag;
    }

    public void setPopulationTag(String populationTag) {
        this.populationTag = populationTag;
    }

    public void setSettingTag(String settingTag) {
        this.settingTag = settingTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }

    public long getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public String getCompany() {
        return company;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getAreaInterestTag() {
        return areaInterestTag;
    }

    public String getPopulationTag() {
        return populationTag;
    }

    public String getSettingTag() {
        return settingTag;
    }

    public String getGroupTag() {
        return groupTag;
    }
}

