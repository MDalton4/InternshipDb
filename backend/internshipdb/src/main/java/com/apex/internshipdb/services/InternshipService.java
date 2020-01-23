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

import com.apex.internshipdb.exceptions.InternshipNotFoundException;
import com.apex.internshipdb.models.Internship;
import com.apex.internshipdb.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    @Autowired
    InternshipRepository internshipRepo;

    public void delete(Long id){
        internshipRepo.deleteById(id);
    }

    public void add(Internship i) {
        internshipRepo.save(i);
    }

    public Internship find(long id) {
        return Optional.ofNullable(internshipRepo.findById(id)).orElseThrow(() ->
                new InternshipNotFoundException("Internship not found with id: " + id));
    }
    public List<Internship> findByLocationContaining(String location){return internshipRepo.findByLocationContaining(location);}

    public List<Internship> findByAreaInterestTag(String areaInterest){return internshipRepo.findByAreaInterestTag(areaInterest);}

    public List<Internship> findByPopulationTag(String population){return internshipRepo.findByPopulationTag(population);};

    public List<Internship> findBySettingTag(String setting){return internshipRepo.findBySettingTag(setting);}

    public List<Internship> findByGroupTag(String group){return internshipRepo.findByGroupTag(group);}

    public List<Internship> findByDescription(String keyword){return internshipRepo.findByDescriptionContaining(keyword);}

    public List<Internship> getAll() { return internshipRepo.findAll(); }

    @EventListener
    public void preLoadInternships(ApplicationReadyEvent event) {

        String description1 = "Overall Position Responsibilities:\n" +
                "Interns are responsible for attending the following monthly meetings:\n" +
                "The All Staff Meeting-the first Thursday of every month at 8:00am\n" +
                "The Neighborhood/Floor Meetings- Bi-monthly at 8:15am, the dates for each meeting will be posted in the neighborhood by the Classroom Coordinators\n" +
                "Post Clinic Meetings: Occur once a month, for each student in an intern’s case load. This meeting is to keep everyone on the student’s team up to date with their program and/or behavior plan.\n" +
                "Following NLG’s emergency procedures whenever needed.\n" +
                "\n" +
                "ABA Therapist Responsibilities:\n" +
                "Providing highly individualized, one-to-one Applied Behavior Analysis (ABA) therapy to 2 students/ a small group of members per the daily schedule and according to the protocols established by the Clinical Director/ Clinical Leader.\n" +
                "All Interns will be trained on each student’s/ members' academic programs, daily living skills, vocational skills and individualized behavior plan.\n" +
                "Consistently collecting accurate data for academic programs, daily living skills, vocational skills and behavior (frequency, antecedent-behavior-consequence, duration, etc.)\n" +
                "Taking the students on community outings, when necessary according to the objectives outlined in each specific student’s IEP and/or behavior plan\n" +
                "Interns CANNOT drive students/function as a driver for NLG unless they are 21+ years old AND have completed the correct insurance paperwork\n" +
                "Ensuring the safety and well-being of all students within NLG and while out in the community.\n" +
                "Ensuring that all materials used and the classroom as a whole are left in a neat and orderly fashion at the end of each school day.";

        String description2 = "This is a full-time, 12-month appointment in a busy college mental health clinic. This position is one of three postdoctoral fellowships; fellows will work alongside psychologists, social workers, and psychiatric staff to provide a full range of clinical and outreach services to the Tufts community. In addition to the work outlined above, fellows will receive 2 hours of individual supervision and 1 hour of group supervision per week. Fellows will also participate in weekly staff meetings, clinical team meetings, and training seminars.\n" +
                "\n" +
                "Qualifications\n" +
                "Basic Requirements:\n" +
                "• Bachelor's degree and working towards a Doctorate in Psychology (PhD, PsyD, EdD) - All degree requirements, including the dissertation defense, must be completed by August 20, 2020, and receipt of the degree will need to be verified no later than September 1, 2020.\n" +
                "• Minimum of three (3) years of related experience.\n" +
                "• Excellent communication, organizational and writing skills.\n" +
                "Preferred Qualifications:\n" +
                "• Specific training and experience working with diverse populations including—but not limited to—students of color, LGBTQ students, and students of different ethnicities and immigration histories is desirable.\n" +
                "• Clinical experience in a college mental health setting is desirable but not required.\n" +
                "• Proficiency with Windows, word processing software, and electronic medical records is helpful.\n" +
                "• Multilingual candidates are encouraged to apply.\n" +
                "An employee in this position must complete all appropriate background checks at the time of hire, promotion, or transfer.";

        internshipRepo.save(new Internship("Therapist Intern", "Nashoba Learning Group", "https://www.paycomonline.net/v4/ats/web.php/jobs/ViewJobDetails?job=13264&clientkey=6D263CD730D11C9E69DCDD9CDA086589",
                description1, "Bedford, MA", "2019-11-12",
                "Teaching", "Homeless", "Nursing homes", "Adults"));
        internshipRepo.save(new Internship("School Psychology Intern", "Nashua Schools", "https://www.paycomonline.net/v4/ats/web.php/jobs/ViewJobDetails?job=13264&clientkey=6D263CD730D11C9E69DCDD9CDA086589",
                description2, "Peabody, MA", "2019-11-12",
                "Therapist/ Clinician/ Psychologist", "Veterans", "Nursing homes", "Elderly"));
        internshipRepo.save(new Internship("Counseling and Mental Health Service", "Tufts University", "https://www.paycomonline.net/v4/ats/web.php/jobs/ViewJobDetails?job=13264&clientkey=6D263CD730D11C9E69DCDD9CDA086589",
                description1, "Boston, MA", "2019-11-12",
                "Teaching", "Homeless", "Nursing homes", "Adults"));
        internshipRepo.save(new Internship("Psychology Intern", "Boston Medical Center", "Link 4",
                description2, "Boston, MA", "2019-11-12",
                "Teaching", "Homeless", "Nursing homes", "Elderly"));
        internshipRepo.save(new Internship("Mental Health Service Line Manager", "US Department of Veterans Affairs", "Link 5",
                description1, "Boston, MA", "2019-11-12",
                "Teaching", "Homeless", "Nursing homes", "Adults"));
        internshipRepo.save(new Internship("Living Laboratory Research and Science Communication", "Museum of Science", "Link 6",
                description2, "Boston, MA", "2019-11-12",
                "Therapist/ Clinician/ Psychologist", "Homeless", "Nursing homes", "Elderly"));
    }
}
