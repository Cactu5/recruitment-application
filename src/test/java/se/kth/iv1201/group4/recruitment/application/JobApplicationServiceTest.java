package se.kth.iv1201.group4.recruitment.application;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Availability;
import se.kth.iv1201.group4.recruitment.domain.JobApplication;
import se.kth.iv1201.group4.recruitment.domain.JobStatus;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.dto.AvailabilityDTO;
import se.kth.iv1201.group4.recruitment.repository.AvailabilityRepository;
import se.kth.iv1201.group4.recruitment.repository.JobApplicationRepository;
import se.kth.iv1201.group4.recruitment.repository.JobStatusRepository;


@SpringBootTest
public class JobApplicationServiceTest {
    @Autowired
    private JobApplicationService service;

    @MockBean
    private JobApplicationRepository jobApplicationRepo;

    @MockBean
    private AvailabilityRepository availabilityRepo;

    @MockBean
    private JobStatusRepository jobStatusRepo;

    @Test
    void testToAddAvailability(){
        Availability a1 = new Availability(LocalDate.of(2021,1,1),LocalDate.of(2021,03,03),
                getDummyApplication());
        
        doReturn(a1).when(availabilityRepo).saveAndFlush(a1);

        Availability a2 = (Availability) service.addAvailability(a1);

        assertFalse(a2 == null);
        assertSame(a1,a2);
    }

    @Test
    void testToAddAvailabilities(){
        Availability a1 = new Availability(LocalDate.of(2021,1,1),LocalDate.of(2021,03,03),
                getDummyApplication());
         Availability a2 = new Availability(LocalDate.of(2021,4,4),LocalDate.of(2021,8,8),
                getDummyApplication());       
        
        doReturn(Arrays.asList(a1,a2)).when(availabilityRepo).saveAll(anyList());
        
        List<Availability> as1 = new ArrayList<Availability>();
        as1.add(a1);
        as1.add(a2);
        List<AvailabilityDTO> as2 = service.addAvailabilities(as1);//service.addAvailabilities(as1);

        assertFalse(as2.isEmpty());
        assertEquals(2,as2.size());
    }

    @Test
    void testToAddJobApplication(){
        JobApplication ja1 = getDummyApplication();

        doReturn(ja1).when(jobApplicationRepo).saveAndFlush(any());

        JobApplication ja2 = (JobApplication) service.addJobApplication(ja1);

        assertFalse(ja2 == null);
        assertSame(ja2, ja1);
    }
    
    @Test
    void testToAddJobStatus(){
        JobStatus js1 = new JobStatus("denied");
        
        doReturn(js1).when(jobStatusRepo).saveAndFlush(js1);

        JobStatus js2 = (JobStatus) service.addJobStatus(js1);
        
        assertFalse(js2 == null);
        assertSame(js2, js1);
    }

    @Test
    void testToGetJobStatusByName(){
        JobStatus js1 = new JobStatus("denied");

        doReturn(js1).when(jobStatusRepo).findJobStatusByName("denied");

        JobStatus js2 = service.getJobStatus("denied");

        assertFalse(js2 == null);
        assertSame(js1, js2);
    }

    @Test
    void testToGetJobApplicationByApplicant(){
        Person person = new Person("name", "surname","email@email.com", "199201010101", "gamer", "qooqWan123!"); 
        Applicant a = new Applicant(person);
        JobApplication ja = getDummyApplication(person);

        doReturn(Arrays.asList(ja)).when(jobApplicationRepo).findJobApplicationsByApplicant(a);

        List<JobApplication> jas = service.getJobApplications(a);
        
        assertFalse(jas.isEmpty());
        assertEquals(1,jas.size());
    }

    private JobApplication getDummyApplication(){
        Person person = new Person("name", "surname","email@email.com", "199201010101", "gamer", "qooqWan123!"); 
        return getDummyApplication(person);
    }
    private JobApplication getDummyApplication(Person p){
        Applicant app1 = new Applicant(p);
        JobStatus js = new JobStatus("denied");
        JobApplication ja = new JobApplication(app1, js);
        return ja;
    }
    private JobApplication getDummyApplication(Applicant a){
        JobStatus js = new JobStatus("denied");
        JobApplication ja = new JobApplication(a, js);
        return ja;       
    }
}
