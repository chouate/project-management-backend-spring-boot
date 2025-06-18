package com.hps.projectservice;


import com.hps.projectservice.entities.Phase;
import com.hps.projectservice.entities.StatusPhase;
import com.hps.projectservice.enums.PhaseEnum;
import com.hps.projectservice.enums.PhaseStatus;
import com.hps.projectservice.repositories.PhaseRepository;
import com.hps.projectservice.repositories.PhaseStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class ProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(PhaseStatusRepository phaseStatusRepository,
										PhaseRepository phaseRepository
	){
		return args -> {
			//create statues if only respository is empty
			if (phaseStatusRepository.count() == 0){
				phaseStatusRepository.save(new StatusPhase(PhaseStatus.TO_COME.toString(),"The phase is not started yet"));
				phaseStatusRepository.save(new StatusPhase(PhaseStatus.IN_PROGRESS.toString(),"The phase is currently being worked on"));
				phaseStatusRepository.save(new StatusPhase(PhaseStatus.PENDING_VALIDATION.toString(),"The phase is awaiting approval"));
				phaseStatusRepository.save(new StatusPhase(PhaseStatus.COMPLETED.toString(),"The phase has been completed"));
				//phaseStatusRepository.save(new StatusPhase(PhaseStatus.CLOSED.toString(),"The phase is officially closed and no longer under responsibility"));
			}

			//create phases only if repository is empty
			if(phaseRepository.count() == 0){
				phaseRepository.save(new Phase(PhaseEnum.STUDY.toString(),"Analysis and understanding of the project"));
				phaseRepository.save(new Phase(PhaseEnum.WORKLOAD_ESTIMATION.toString(),"Effort and time estimation for the project"));
				phaseRepository.save(new Phase(PhaseEnum.DEVELOPMENT.toString(),"Implementation of the project's features"));
				phaseRepository.save(new Phase(PhaseEnum.UNIT_TESTING.toString(),"Testing individual components for correctness"));
				phaseRepository.save(new Phase(PhaseEnum.TESTING_WITH_CLIENT.toString(),"Client side validation and testing"));
				phaseRepository.save(new Phase(PhaseEnum.PRODUCTION_DEPLOYMENT.toString(),"Deploying the project to production"));
			}
		};
	}
}
