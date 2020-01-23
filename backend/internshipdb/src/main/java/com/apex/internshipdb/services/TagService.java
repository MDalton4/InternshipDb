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

import com.apex.internshipdb.models.Tag;
import com.apex.internshipdb.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public void delete(Long longID) {
        tagRepository.deleteById(longID);
    }

    public void add(Tag t){
        tagRepository.save(t);
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    /**
     * Like getAll(), but returns records ordered by name
     * @return list of tag records
     */
    public List<Tag> findByOrderByName(){
        return tagRepository.findByOrderByName();
    }

    /**
     * Like getAll(), but returns records ordered by type
     * @return list of tag records
     */
    public List<Tag> findByOrderByType(){
        return tagRepository.findByOrderByType();
    }

    /**
     * Like getAll(), but returns records ordered by times searched
     * @return list of tag records
     */
    public List<Tag> findByOrderByTimesSearched(){
        return tagRepository.findByOrderByTimesSearched();
    }

    //Search methods

    /**
     * Find all by tag type
     * @param type to search by
     * @return List of Tags with that type
     */
    public List<Tag> findAllByType(String type) {return tagRepository.findAllByType(type);}
    public List<Tag> findAllByName(String name) {return tagRepository.findAllByName(name);}

    /**
     * Given the name of a tag, return the tag from the database
     * @param name of the tag to find
     * @return tag object of that name
     */
    public Tag findByName(String name){
        return tagRepository.findByName(name);
    }

    public void resetTagCount(){
        List<Tag> tags = tagRepository.findAll();
        for(int i=0; i<tags.size(); i++) {
            tags.get(i).setTimesSearched(0);
            tagRepository.save(tags.get(i));
        }
    }

    @EventListener
    public void preLoadTags(ApplicationReadyEvent event) {
        tagRepository.save(new Tag("Therapist/ Clinician/ Psychologist", "Interest"));
        tagRepository.save(new Tag("Psychiatrist", "Interest"));
        tagRepository.save(new Tag("Human Resources", "Interest"));
        tagRepository.save(new Tag("School Adjustment/Guidance Counselor", "Interest"));
        tagRepository.save(new Tag("Expressive Therapy", "Interest"));
        tagRepository.save(new Tag("Child Life Specialist", "Interest"));
        tagRepository.save(new Tag("Occupational Therapy", "Interest"));
        tagRepository.save(new Tag("Law Enforcement", "Interest"));
        tagRepository.save(new Tag("Applied Behavioral Analysis", "Interest"));
        tagRepository.save(new Tag("Equine Therapy", "Interest"));
        tagRepository.save(new Tag("Animal Support", "Interest"));
        tagRepository.save(new Tag("Research", "Interest"));
        tagRepository.save(new Tag("Teaching", "Interest"));
        tagRepository.save(new Tag("Non-profit community organization", "Interest"));

        tagRepository.save(new Tag("Infant and Toddlers", "Group"));
        tagRepository.save(new Tag("School Aged Children (5-9)", "Group"));
        tagRepository.save(new Tag("Preadolescent (9-12)", "Group"));
        tagRepository.save(new Tag("Teenagers (13-17)", "Group"));
        tagRepository.save(new Tag("Young Adults (18-25)", "Group"));
        tagRepository.save(new Tag("Adults", "Group"));
        tagRepository.save(new Tag("Elderly", "Group"));

        tagRepository.save(new Tag("Addictions", "Population"));
        tagRepository.save(new Tag("Alzheimers/Dementia", "Population"));
        tagRepository.save(new Tag("Developmental Delays Disabilities", "Population"));
        tagRepository.save(new Tag("Autism Spectrum", "Population"));
        tagRepository.save(new Tag("Eating Disorders", "Population"));
        tagRepository.save(new Tag("Trauma", "Population"));
        tagRepository.save(new Tag("Veterans", "Population"));
        tagRepository.save(new Tag("Homeless", "Population"));
        tagRepository.save(new Tag("Traumatic Brain Injuries", "Population"));

        tagRepository.save(new Tag("Outpatient Clinics or Offices", "Setting"));
        tagRepository.save(new Tag("Schools", "Setting"));
        tagRepository.save(new Tag("Home/Community", "Setting"));
        tagRepository.save(new Tag("Hospital inpatient settings", "Setting"));
        tagRepository.save(new Tag("Psychiatric hospitals", "Setting"));
        tagRepository.save(new Tag("General medical/Surgical hospitals", "Setting"));
        tagRepository.save(new Tag("Residential mental health treatment environments", "Setting"));
        tagRepository.save(new Tag("Psychiatric residential centers", "Setting"));
        tagRepository.save(new Tag("Alcohol/Drug rehabilitation facilities", "Setting"));
        tagRepository.save(new Tag("Nursing homes", "Setting"));
        tagRepository.save(new Tag("Outpatient settings", "Setting"));
        tagRepository.save(new Tag("Partial hospitalization programs", "Setting"));
        tagRepository.save(new Tag("Intensive outpatient programs", "Setting"));
        tagRepository.save(new Tag("Community Mental Health Centers", "Setting"));
        tagRepository.save(new Tag("Private Business", "Setting"));
        tagRepository.save(new Tag("Other non-profit agencies", "Setting"));

    }

}
