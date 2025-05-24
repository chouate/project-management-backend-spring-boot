package com.hps.userservice.factory;

import com.hps.userservice.entities.Developer;
import com.hps.userservice.entities.Director;
import com.hps.userservice.entities.ProjectManager;
import com.hps.userservice.entities.User;
import com.hps.userservice.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public User createUserByRole(UserRole role){
        switch (role){
            case DEVELOPER: return new Developer();
            case DIRECTOR: return  new Director();
            case PROJECT_MANAGER: return new ProjectManager();
            default: throw new IllegalArgumentException("Unknown role: "+role);
        }
    }

    public User createUserByRole(String roleString){
        try{
            UserRole role = UserRole.valueOf(roleString.toUpperCase());
            return createUserByRole(role);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalide role: "+roleString);
        }
    }

}
