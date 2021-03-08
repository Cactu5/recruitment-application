package se.kth.iv1201.group4.recruitment.application;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.kth.iv1201.group4.recruitment.repository.AvailabilityRepository;
import se.kth.iv1201.group4.recruitment.repository.JobApplicationRepository;


@SpringBootTest
public class JobApplicationServiceTest {
    @Autowired
    private JobApplicationService service;

    @MockBean
    private JobApplicationRepository jobApplicatiionRepo;

    @MockBean
    private AvailabilityRepository availabilityRepo;
}
