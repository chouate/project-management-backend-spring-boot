package com.hps.projectservice;

import com.hps.projectservice.entities.Phase;
import com.hps.projectservice.entities.StatusPhase;
import com.hps.projectservice.repositories.PhaseRepository;
import com.hps.projectservice.repositories.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(StatusRepository statusRepository,
										PhaseRepository phaseRepository
	){
		return args -> {
			//create statues if only respository is empty
			if (statusRepository.count() == 0){
				statusRepository.save(new StatusPhase("Upcomming","The phase is not started yet"));
				statusRepository.save(new StatusPhase("In Progress","The phase is currently being worked on"));
				statusRepository.save(new StatusPhase("Pending Validation","The phase is awaiting approval"));
				statusRepository.save(new StatusPhase("Completed","The phase has been completed"));
				statusRepository.save(new StatusPhase("Closed","The phase is officially closed and no longer under responsibility"));
			}

			//create phases only if repository is empty
			if(phaseRepository.count() == 0){
				phaseRepository.save(new Phase("study","Analysis and understandinf of the project"));
				phaseRepository.save(new Phase("Workload Estimation","Effort and time estimation for the project"));
				phaseRepository.save(new Phase("Developemnt","Implementation of the project's features"));
				phaseRepository.save(new Phase("Unit Testing","Testing individual components for correctness"));
				phaseRepository.save(new Phase("Testing With Client","Client side validation and testing"));
				phaseRepository.save(new Phase("Production Deployement","Deploying the project to production"));
			}
		};
	}
}
