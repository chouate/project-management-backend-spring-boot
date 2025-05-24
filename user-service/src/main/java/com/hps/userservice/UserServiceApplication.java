package com.hps.userservice;

import com.hps.userservice.entities.Technology;
import com.hps.userservice.repositories.DeveloperRepository;
import com.hps.userservice.repositories.DirectorRepository;
import com.hps.userservice.repositories.UserRepository;
import com.hps.userservice.services.interfaces.TechnologieService;
import com.hps.userservice.services.interfaces.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService,
                             TechnologieService technologieService,
                             UserRepository userRepository,
                             DeveloperRepository developerRepository,
                             DirectorRepository directorRepository
                             ){
        return args -> {
            Technology competenceJava = new Technology();
            Technology competencePLSQL = new Technology();
            Technology competenceC = new Technology();
            competenceJava.setName("Java");
            competencePLSQL.setName("PLSQL");
            competenceC.setName("C++");

//            technologieService.createTechnologie(competenceC);
//            technologieService.createTechnologie(competenceJava);
//            technologieService.createTechnologie(competencePLSQL);

//            List competenceList =new ArrayList();
//            competenceList =  technologieService.getAllTechnologies();
//            Developer developer = (Developer) userRepository.findById(5L).get();
//
//            developer.setTechnologyList(competenceList);
//
//            userRepository.save(developer);
//
//            List<Developer> allDevelopers = developerRepository.findAll();
//            Director director = directorRepository.findById(1L)
//                    .orElseThrow(()-> new ResourceNotFoundException("Director not found with id : 1"));
//            allDevelopers.forEach(dev -> {
//                dev.setDirector(director);
//                developerRepository.save(dev);
//            });
//            System.out.println("size of list developers : "+allDevelopers.size());
//            System.out.println("avant director.setListDevelopersAffected(allDevelopers);");
//            director.setListDevelopersAffected(allDevelopers);
//            System.out.println("apres director.setListDevelopersAffected(allDevelopers);");
//            System.out.println("avant userRepository.save(director);");
//            //directorRepository.save(director);
//            System.out.println("apres userRepository.save(director);");


//
//            Director userDirector = new Director();
//            userDirector.setFirstName("mehdi");
//            userDirector.setLastName("echeouati");
//            userDirector.setEmail("echeouati.elmehdi@gmail.com");
//            userDirector.setRole(UserRole.DIRECTOR.toString());
//            userDirector.setTechnologyList(competenceList);
//            userService.createUser(userDirector);
//
//            ProjectManager projectManager = new ProjectManager();
//            projectManager.setFirstName("Jalal");
//            projectManager.setLastName("el mhamedi");
//            projectManager.setEmail("elmhamedi@gmail.com");
//            projectManager.setRole(UserRole.PROJECT_MANAGER.toString());
//            projectManager.setTechnologyList(List.of(competenceC));
//            userService.createUser(projectManager);
//
//            Developer developer= new Developer();
//            developer.setFirstName("hanae");
//            developer.setLastName("bendali");
//            developer.setEmail("bendali@gmail.com");
//            developer.setRole(UserRole.DEVELOPER.toString());
//            userService.createUser(developer);
//
//            User user = new User();
//            user.setFirstName("nada");
//            user.setLastName("aakioui");
//            user.setEmail("aakioui@gmail.com");
//            user.setRole(UserRole.DEVELOPER.toString());
//            userService.createUser(user);
//
//            userService.getAllUsers().forEach(user1 -> {
//                System.out.println("user: "+user1.getFirstName()+" role: "+ user1.getRole());
//                System.out.println(user1);
//
//            });
//            System.out.println("le directeur N° 1 : ");
//
//            Director director1 = directorRepository.findById(1L)
//                    .orElseThrow(()-> new ResourceNotFoundException("Director not found witd id: "+1));
//
//
//            Developer developer1 =  developerRepository.findById(5L)
//                    .orElseThrow(()-> new ResourceNotFoundException("Developer not found witd id: "+2));
//
//            Set<Developer> developers = new HashSet<>();
//            developers.add(developer1);
//
//            director1.setListDevelopersAffected(developers);
//
//            System.out.println("le directeur N° 1 : "+director1);

        };
    }
}
