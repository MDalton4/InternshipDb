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

import com.apex.internshipdb.models.Internship;
import com.apex.internshipdb.resources.SortById;
import com.apex.internshipdb.models.Tag;
import com.apex.internshipdb.models.User;
import com.apex.internshipdb.services.InternshipService;
import com.apex.internshipdb.services.TagService;
import com.apex.internshipdb.services.UserService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value="/api")
public class DatabaseController {

    @Autowired
    private InternshipService internshipService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;


    /**
     * Method to add an internship entity
     * @param internship object to be added
     * @param authentication user making request
     * @return success if admin, forbidden if not admin, unauthorized if not logged in
     */
    @RequestMapping(value = "/add/internship", method=RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity addInternship(@RequestBody Internship internship, Authentication authentication) {

        if(authentication != null && userService.isAdmin(authentication.getName())) {
            internshipService.add(internship);
            return ResponseEntity.ok("added");
        } else if(authentication != null && !userService.isAdmin(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to add a tag entity
     * @param tag object to be added
     * @param authentication current user making request
     * @return success if admin, forbidden if not admin, unauthorized if not logged in
     */
    @RequestMapping(value = "/add/tag", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity addTag(@RequestBody Tag tag, Authentication authentication) {

        if(authentication != null && userService.isAdmin(authentication.getName())) {
            tagService.add(tag);
            return ResponseEntity.ok("added");
        } else if(authentication != null && !userService.isAdmin(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to add an internship to a users favorites
     * @param request incoming request with internship id
     * @param authentication user making request
     * @return success or error
     */
    @RequestMapping(value = "/add/user/favorite", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity addToFavorites(@RequestBody Map<String, String> request, Authentication authentication){

        if(authentication != null) {
            String id = request.get("id");
            String email = request.get("email");

            User user = userService.find(email);
            Internship internship = internshipService.find(Long.parseLong(id));

            user.getFavorites().add(internship);
            userService.add(user);

            return ResponseEntity.ok("added");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Method to remove internship from favorites
     * @param request being made with information
     * @param authentication current user making request
     * @return success or error
     */
    @RequestMapping(value = "/remove/user/favorite", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity removeFromFavorites(@RequestBody Map<String, String> request, Authentication authentication){

        if(authentication != null) {
            String id = request.get("id");
            String email = request.get("email");

            User user = userService.find(email);
            Internship internship = internshipService.find(Long.parseLong(id));

            user.getFavorites().remove(internship);
            userService.add(user);

            return ResponseEntity.ok("removed");

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to get all internship entries
     * @param authentication user sending request
     * @return List of all internship objects or unauthorized request
     */
    @RequestMapping(value="/internship/get/all", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllInternships(Authentication authentication) {

        if(authentication != null) {
            return ResponseEntity.ok(internshipService.getAll());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to get all tag entries
     * @param authentication current user
     * @return List of all tag objects
     */
    @RequestMapping(value="/tag/get/all", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllTags(Authentication authentication) {

        if(authentication != null) {
            return ResponseEntity.ok(tagService.getAll());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to get all user entries
     * @param authentication user making request
     * @return List of all user objects or error
     */
    @RequestMapping(value = "/user/get/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllUsers(Authentication authentication) {

        if(authentication != null) {
            return ResponseEntity.ok(userService.getAll());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to get a specific user
     * @param email of user
     * @param authentication user making request
     * @return user info or error
     */
    @RequestMapping(value = "/user/get/{email}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getUserByEmail(@PathVariable String email, Authentication authentication){

        if(authentication != null) {
            return ResponseEntity.ok(userService.find(email));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Gets user from email and returns user's favorites
     * @param email of user to get
     * @param authentication current user
     * @return long array of favorites
     */
    @RequestMapping(value = "/user/get/favorites/{email}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getUserFavorites(@PathVariable String email, Authentication authentication){

        if(authentication != null) {
            return ResponseEntity.ok(userService.find(email).getFavorites());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to get internship by id
     * @param id of internship
     * @param authentication current user
     * @return Internship object
     */
    @RequestMapping(value = "/internship/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getInternshipById(@PathVariable String id, Authentication authentication) {

        if(authentication != null) {
            long idToFind = Long.parseLong(id);
            return ResponseEntity.ok(internshipService.find(idToFind));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to update entry with edited internship
     * @param id of internship
     * @param internship object with edited fields
     * @param authentication current user
     * @return success or unauthorized or forbidden
     */
    @RequestMapping(value = "/edit/internship/{id}", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity editInternship(@PathVariable String id, @RequestBody Internship internship, Authentication authentication) {

        if(authentication != null && userService.isAdmin(authentication.getName())) {
            Internship toUpdate = internshipService.find(Long.parseLong(id));

            toUpdate.setPosition(internship.getPosition());
            toUpdate.setCompany(internship.getCompany());
            toUpdate.setLink(internship.getLink());
            toUpdate.setDescription(internship.getDescription());
            toUpdate.setLocation(internship.getLocation());
            toUpdate.setStartDate(internship.getStartDate());
            toUpdate.setAreaInterestTag(internship.getAreaInterestTag());
            toUpdate.setPopulationTag(internship.getPopulationTag());
            toUpdate.setSettingTag(internship.getSettingTag());
            toUpdate.setGroupTag(internship.getGroupTag());

            internshipService.add(toUpdate);
            return ResponseEntity.ok("edited");
        } else if(authentication != null && !userService.isAdmin(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to delete internship
     * @param id of internship
     * @param authentication current user
     * @return success or unauthorized or forbidden
     */
    @RequestMapping(value = "/delete/internship/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity deleteInternship(@PathVariable String id, Authentication authentication) {

        if(authentication != null && userService.isAdmin(authentication.getName())) {
            Long longID = Long.parseLong(id);
            internshipService.delete(longID);
            return ResponseEntity.ok("deleted");
        } else if(authentication != null && !userService.isAdmin(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to delete tag
     * @param id of tag
     * @param authentication current user
     * @return success or unauthorized or forbidden
     */
    @RequestMapping(value = "/delete/tag/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity deleteTag(@PathVariable String id, Authentication authentication) {

        if(authentication != null && userService.isAdmin(authentication.getName())) {
            long longID = Long.parseLong(id);
            tagService.delete(longID);
            return ResponseEntity.ok("deleted");
        } else if(authentication != null && !userService.isAdmin(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method delete user
     * @param id of user
     * @param authentication current user
     * @return success or unauthorized or forbidden
     */
    @RequestMapping(value = "/delete/user/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity deleteUser(@PathVariable String id, Authentication authentication) {

        if(authentication != null && userService.isAdmin(authentication.getName())) {
            long longID = Long.parseLong(id);
            userService.delete(longID);
            return ResponseEntity.ok("deleted");
        } else if(authentication != null && !userService.isAdmin(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to promote user
     * @param id of user
     * @param authentication current user
     * @return success or unauthorized or forbidden
     */
    @RequestMapping(value = "/user/promote/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity promoteUser(@PathVariable String id, Authentication authentication){

        if(authentication != null && userService.isAdmin(authentication.getName())) {
            long longID = Long.parseLong(id);
            userService.promoteUser(longID);
            return ResponseEntity.ok("promoted");
        } else if(authentication != null && !userService.isAdmin(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to demote user
     * @param id of user
     * @param authentication current user
     * @return success or unauthorized or forbidden
     */
    @RequestMapping(value = "/user/demote/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity demoteUser(@PathVariable String id, Authentication authentication){

        if(authentication != null && userService.isAdmin(authentication.getName())) {
            long longID = Long.parseLong(id);
            userService.demoteUser(longID);
            return ResponseEntity.ok("demoted");
        } else if(authentication != null && !userService.isAdmin(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to get interest tags
     * @param authentication current user
     * @return list of interest tags or error
     */
    @RequestMapping(value = "/tag/get/interests", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAreaInterestTags(Authentication authentication) {

        if(authentication != null) {
            return ResponseEntity.ok(tagService.findAllByType("Interest"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to change student area of interest
     * @param request being made
     * @param authentication current user
     * @return success or unauthorized
     */
    @RequestMapping(value = "/edit/interest", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity updateAreaOfInterest(@RequestBody Map<String, String> request, Authentication authentication) {

        if(authentication != null) {

            String interest = request.get("interest_option");
            String email = request.get("email");

            User user = userService.find(email);

            user.setAreaInterest(interest);

            userService.add(user);

            return ResponseEntity.ok("updated");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Looks at every user's area of interest and tallies the values for each,
     * building a hashmap of interests:# of instances.
     * @return The hashmap showing how many users selected each area of interest.
     */
    public Map<String, Long> interestCount(){
        HashMap<String, Long> interests = new HashMap<>();
        for(User user :  userService.getAll()){
            String interest = user.getAreaInterest();
            // If interest isn't in hashmap, add it to the hashmap
            interests.putIfAbsent(interest, 0L);
            // Increment the value in the hashmap
            interests.put(interest, interests.get(interest) + 1);
        }
        return interests;
    }

    /**
     * Method to search for a keyword or location
     * @param keyword being serached for
     * @param locationKeyword location being searched for
     * @param authentication current user
     * @return list or unauthorized
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity search(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String locationKeyword,
                                 Authentication authentication) {

        if(authentication != null) {

            List<Internship> descriptionContaining = internshipService.findByDescription(keyword);

            List<Internship> locationContaining = internshipService.findByLocationContaining(locationKeyword);

            List<Internship> masterList = internshipService.getAll();

            if(!Strings.isNullOrEmpty(keyword)){
                masterList.retainAll(descriptionContaining);
            }
            if(!Strings.isNullOrEmpty(locationKeyword)){
                masterList.retainAll(locationContaining);
            }

            return ResponseEntity.ok(masterList);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Method to filter internships
     * @param interests filters selected
     * @param populations filters selected
     * @param settings filters selected
     * @param groups filters selected
     * @param authentication current user
     * @return list of internships or unauthorized
     */
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity filter(@RequestParam(value = "interests", required = false) List<String> interests,
                                   @RequestParam(value = "populations", required = false) List<String> populations,
                                   @RequestParam(value = "settings", required = false) List<String> settings,
                                   @RequestParam(value = "groups", required = false) List<String> groups,
                                   Authentication authentication) {

        if(authentication != null) {

            // Lists containing internships pertaining to keywords passed in.
            List<Internship> masterList = internshipService.getAll();
            List<Internship> interestsList = internshipsFromInterests(interests);
            List<Internship> populationsList = internshipsFromPopulations(populations);
            List<Internship> settingsList = internshipsFromSettings(settings);
            List<Internship> groupsList = internshipsFromGroups(groups);

            // If a tag is selected, update masterList, otherwise, ignore that category.
            if(interests.size() != 0){
                masterList.retainAll(interestsList);
            }
            if(populations.size() != 0) {
                masterList.retainAll(populationsList);
            }
            if(settings.size() != 0){
                masterList.retainAll(settingsList);
            }
            if(groups.size() != 0){
                masterList.retainAll(groupsList);
            }

            updateTagCounts(interests, populations, settings, groups);

            // Sort the master list by id.
            Collections.sort(interestsList, new SortById());

            return ResponseEntity.ok(masterList);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to get newest internships
     * @param authentication current user
     * @return 6 newest internships or unauthorized
     */
    @RequestMapping(value = "/internship/get/new", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getNew(Authentication authentication) {

        if(authentication != null) {
            return ResponseEntity.ok(getLastNInternships(6));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to reset tag analytics
     * @param authentication current user
     * @return success or unauthorized or forbidden
     */
    @RequestMapping(value = "/reset/tags", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity resetAnalytics(Authentication authentication) {

        if(authentication != null && userService.isAdmin(authentication.getName())) {
            tagService.resetTagCount();
            return ResponseEntity.ok("reset");
        } else if(authentication != null && !userService.isAdmin(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    /**
     * Method to get total areas of interest from students
     * @param authentication current user
     * @return success or unauthorized
     */
    @RequestMapping(value = "/tag/get/interests/analytics", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity interestAnalytics(Authentication authentication) {

        Map<String, Integer> count = new HashMap<>();

        List<User> users = userService.getAll();
        List<Tag> tags = tagService.findAllByType("Interest");
        List<String> areas = new ArrayList<>();

        for(int i=0; i<users.size(); i++) {
            areas.add(users.get(i).getAreaInterest());
        }

        for(int x=0; x<tags.size(); x++) {
            count.put(tags.get(x).getName(), Collections.frequency(areas, tags.get(x).getName()));
        }

        if(authentication != null) {
            return ResponseEntity.ok(count);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }


    /**
     * Takes a number of lists of strings and updates the tags' timesSearched by their names
     * @param strLists variable length list of lists of strings
     */
    public void updateTagCounts(List<String>...strLists){
        List<Tag> tags = new ArrayList<>();
        for(List<String> strList : strLists){
            for(String str: strList){
                Tag tag = tagService.findByName(str);
                tag.increment();
                tagService.add(tag);
            }
        }
    }

    /**
     * Gets the last n internships in the table of internships.
     * @param n is the number of internships to get, starting from the bottom
     * @return the sublist of internships. If n>list.size(), just return the whole list.
     */
    public List<Internship> getLastNInternships(int n){
        List<Internship> list = internshipService.getAll();
        if(n <= list.size()) {
            return list.subList(list.size() - n, list.size());
        } else {
            return list;
        }
    }

//    /**
//     * Merges several lists of internships via intersection, so only internships present in all lists are merged
//     * @param internshipLists to merge
//     * @return
//     */
//    private List<Internship> internshipsIntersection(List<Internship>...internshipLists){
//        List<Internship> baseList = internshipService.getAll();
//        int i = 0;
//
//        // Set base list to the first non-empty list
//        while(i < internshipLists.length){
//            if(!internshipLists[i].isEmpty()) {
//                baseList = internshipLists[i];
//                break;
//            }
//            i++;
//        }
//        int j = 0;
//        // Merge lists with baseList if the merging list isn't empty
//        while(j < internshipLists.length){
//            if (!internshipLists[j].isEmpty()) {
//                baseList.retainAll(internshipLists[j]);
//            }
//            j++;
//        }
//        // To remove duplicates from baseList, add to set (which cannot contain dupes),
//        // clear list, add set elements to list
//        baseList = removeDuplicates(baseList);
//        return baseList;
//    }
//
//    private List<Internship> internshipUnion(List<Internship>...internshipLists){
//        List<Internship> baseList = internshipLists[0];
//        for(List<Internship> internshipList : internshipLists){
//            baseList.addAll(internshipList);
//        }
//        return baseList;
//    }

    /**
     * Goes through a list of interests and finds all internships with those areas of interest.
     * @param interests to search internships by
     * @return list of internships related to the given interests (no duplicates)
     */
    private List<Internship> internshipsFromInterests(List<String> interests){
        List<Internship> result = new ArrayList<>();
        for(String interest : interests){
            result.addAll(internshipService.findByAreaInterestTag(interest));
        }
        return removeDuplicates(result);
    }


    /**
     * Goes through a list of populations and finds all internships with those populations.
     * @param populations to search internships by
     * @return list of internships related to the given populations (no duplicates)
     */
    private List<Internship> internshipsFromPopulations(List<String> populations){
        List<Internship> result = new ArrayList<>();
        for(String population: populations){
            result.addAll(internshipService.findByPopulationTag(population));
        }
        return removeDuplicates(result);
    }

    /**
     * Goes through a list of settings and finds all internships with those settings.
     * @param settings to search internships by
     * @return list of internships related to the given settings (no duplicates)
     */
    private List<Internship> internshipsFromSettings(List<String> settings){
        List<Internship> result = new ArrayList<>();
        for(String setting: settings){
            result.addAll(internshipService.findBySettingTag(setting));
        }
        return removeDuplicates(result);
    }

    /**
     * Goes through a list of groups and finds all internships with those settings.
     * @param groups to search internships by
     * @return list of internships related to the given groups (no duplicates)
     */
    private List<Internship> internshipsFromGroups(List<String> groups){
        List<Internship> result = new ArrayList<>();
        for(String group: groups){
            result.addAll(internshipService.findByGroupTag(group));
        }
        return removeDuplicates(result);
    }

    /**
     * Removes duplicate values from a list
     * @param list list without duplicates
     */
    private List<Internship> removeDuplicates(List<Internship> list) {
        Set<Internship> set = new HashSet<>(list);
        list.clear();
        list.addAll(set);
        return list;
    }

}
