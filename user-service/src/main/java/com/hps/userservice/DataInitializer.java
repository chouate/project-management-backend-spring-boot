package com.hps.userservice;

import com.hps.userservice.entities.Admin;
import com.hps.userservice.entities.Director;
import com.hps.userservice.enums.UserRole;
import com.hps.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Value("${default.admin.userName}")
    private String userNameAdmine;
    @Value("${default.admin.password}")
    private String passwordAdmin;

    private final UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        if(userRepository.count() == 0){
//            Admin admin = new Admin();
//            admin.setEmail(this.userNameAdmine+"@hps-wordwide.com");
//            admin.setPassword(passwordEncoder.encode(this.passwordAdmin));
//            admin.setFirstName("Super");
//            admin.setLastName("admin");
//            admin.getRoles().add(UserRole.ADMIN);
//            admin.getRoles().add(UserRole.DIRECTOR);
//
//            this.userRepository.save(admin);
//            System.out.println("Default admin account created: email: "+admin.getEmail()+", password: "+admin.getPassword());
//        }
    }
}
