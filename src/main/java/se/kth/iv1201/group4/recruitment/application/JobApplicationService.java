package se.kth.iv1201.group4.recruitment.application;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Availability;
import se.kth.iv1201.group4.recruitment.domain.JobApplication;
import se.kth.iv1201.group4.recruitment.domain.JobStatus;
import se.kth.iv1201.group4.recruitment.dto.AvailabilityDTO;
import se.kth.iv1201.group4.recruitment.dto.JobApplicationDTO;
import se.kth.iv1201.group4.recruitment.dto.JobStatusDTO;
import se.kth.iv1201.group4.recruitment.repository.AvailabilityRepository;
import se.kth.iv1201.group4.recruitment.repository.JobApplicationRepository;
import se.kth.iv1201.group4.recruitment.repository.JobStatusRepository;

/**
 * A service for accessing or adding jobApplicate from and to the jobApplicaiton
 * repositories. Rolls back on all exceptions and supports current transactions,
 * or creates a new if none exist.
 * 
 * @author Filip Garamvölgyi
 * @version %I%
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public class JobApplicationService {
    @Autowired
    private JobApplicationRepository jobApplicaitonRepo;

    @Autowired
    private AvailabilityRepository availabilityRepo;

    @Autowired
    private JobStatusRepository jobStatusRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    /**
     * Adds a job application to the {@link JobStatusRepository}.
     * 
     * @param jobApplication The job application to add
     * 
     * @return returns the {@link JobApplicationDTO} if successful otherwise null is returned.
     */
    public JobApplicationDTO addJobApplication(JobApplication jobApplication) {
        if (jobApplication != null) {
            JobApplicationDTO dto = jobApplicaitonRepo.saveAndFlush(jobApplication);
            LOGGER.info(String.format("%s created a job application", "applicant"));
            return dto;
        }
        return null;
    }

    /**
     * Adds a job status to the {@link JobStatusRepository}.
     *
     * @param jobStatus     job status to add.
     * @return returns the {@link JobStatusDTO} if successful otherwise null is returned.
     */
    public JobStatusDTO addJobStatus(JobStatus jobStatus){
        if(jobStatus != null){
            JobStatusDTO dto = jobStatusRepo.saveAndFlush(jobStatus);
            LOGGER.info(String.format("Added %s as a job status", jobStatus.getName()));
            return dto;
        }
        return null;
    }

    /**
     * Adds a list of availabilites to the {@link AvailabilityRepository}.
     *
     * @param availabilities    list of availabilities to add.
     * @return returns list of added {@link AvailabilityDTO} if successful otherwise an empty list 
     * is returned.
     */
    public List<AvailabilityDTO> addAvailabilities(List<Availability> availabilities){
        List<AvailabilityDTO> dtos = new ArrayList<AvailabilityDTO>(); 
        for(Availability a : availabilityRepo.saveAll(availabilities)) {
            dtos.add(a);
        }
        availabilityRepo.flush();
        return dtos;
    }

    /**
     * Adds an availability to the {@link AvailabilityRepository}.
     *
     * @param availability  availability to add.
     * @return returns the added {@link AvailabilityDTO} if successful otherwise null is returned.
     */
    public AvailabilityDTO addAvailability(Availability availability){
        if(availability != null){
            AvailabilityDTO dto = availabilityRepo.saveAndFlush(availability);
            LOGGER.debug("Added availability");
            return dto;
        }
        return null;
    }

    /**
     * Returns job applications given the applicant.
     *
     * @param applicant     applicant associated with the job applications to return.
     * @return              returned cjob application given the applicant.      
     */
    public List<JobApplication> getJobApplications(Applicant applicant) {
        if (applicant == null ) return null;

        return jobApplicaitonRepo.findJobApplicationsByApplicant(applicant);
    }

    public JobStatus getJobStatus(String name){
        if(name == null || name.isEmpty() || name.isBlank()) 
            return null;
        
        return jobStatusRepo.findJobStatusByName(name);
    }
}
