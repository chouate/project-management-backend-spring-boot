package com.hps.taskservice;

import com.hps.taskservice.entities.TaskStatus;
import com.hps.taskservice.enums.TaskStatusEnum;
import com.hps.taskservice.repositories.TaskStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class TaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner( TaskStatusRepository taskStatusRepository
                                        ){
        return args -> {
            //create task statues if only respository is empty
            if (taskStatusRepository.count() == 0){
                taskStatusRepository.save(new TaskStatus(TaskStatusEnum.TO_COME.toString(),"The task is not started yet"));
                taskStatusRepository.save(new TaskStatus(TaskStatusEnum.IN_PROGRESS.toString(),"The task is currently being worked on"));
                taskStatusRepository.save(new TaskStatus(TaskStatusEnum.COMPLETED.toString(),"The task has been completed"));
            }
        };
    }
}
