package com.hps.clientservice.restClients;

import com.hps.clientservice.models.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserRestClient {
    @CircuitBreaker(name = "userService", fallbackMethod = "getDefaultUserById")
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id);

    default User getDefaultUserById(Long id, Exception exception){
        User user = new User();

        user.setId(id);
        user.setFirstName("Fist Name Not available");
        user.setLastName("Las Name Not available");
        user.setEmail("Email Not available");
        user.setPhoneNumber("Phone Number Not available");
        user.setRole("Role Not available");

        return user;
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "getDefaultUserById")
    @GetMapping("/api/directors/{id}")
    public User getDirectorById(@PathVariable Long id);

    @CircuitBreaker(name = "userService", fallbackMethod = "getDefaultUserById")
    @GetMapping("/api/project_managers/{id}")
    public User getPMById(@PathVariable Long id);

}
