package com.hps.taskservice.restClients;


import com.hps.taskservice.models.Project;
import com.hps.taskservice.models.Technology;
import com.hps.taskservice.models.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserRestClient {
    @CircuitBreaker(name = "userService", fallbackMethod = "getDefaultOwnerById")
    @GetMapping("/api/users/{id}")
    public User getOwnerById(@PathVariable Long id);

    @CircuitBreaker(name = "userService", fallbackMethod = "getTechnologyByIdFallback")
    @GetMapping("/technologies/{id}")
    Technology getTechnologyById(@PathVariable Long id);

    default Technology getTechnologyByIdFallback(Long id, Exception e) {
        Technology tech = new Technology();
        tech.setId(id);
        tech.setName("Technology not available");
        return tech;
    }

    default User getDefaultOwnerById(Long id, Exception exception){
        User owner = new User();

        owner.setId(id);
        owner.setName("Name Not Available");
        owner.setEmail("Email Not Available");
        owner.setFirstName("First Name Not Available");
        owner.setLastName("Last Name Not Available");
        owner.setImage("Image Not Available");
        owner.setRole("Role Not Available");
        return owner;
    }
}
