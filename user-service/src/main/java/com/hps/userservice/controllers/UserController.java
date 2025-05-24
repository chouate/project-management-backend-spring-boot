package com.hps.userservice.controllers;


import com.hps.userservice.dtos.*;
import com.hps.userservice.dtos.lightDTOs.ProjectManagerLightDTO;
import com.hps.userservice.dtos.lightDTOs.UserLightDTO;
import com.hps.userservice.dtos.request.*;
import com.hps.userservice.entities.User;
import com.hps.userservice.services.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api")
//@Validated
//@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

//    // find all users (ADMIN only)
//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<UserDTO>> getAllUsers(){
//        System.out.println("appel de la méthode getAllUsers dans le RestController.");
//        return ResponseEntity.ok(this.userService.getAllUsers());
//    }

//    //   find authentificated user profil
//    @GetMapping("/me")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<UserDTO> getProfile(Authentication auth){
//        System.out.println("appel de la méthode getProfile dans le RestController.");
//        UserDTO me = userService.getUserByEmail(auth.getName());
//        return ResponseEntity.ok(me);
//    }



//    //create new user (ADMIN ONLY)
//    @PostMapping()
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO){
//        System.out.println("appel de la méthode createUser dans le RestController.");
//        this.userService.createUser(userDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }


//    //update a user (ADMIN ONLY)
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO){
//        System.out.println("appel de la méthode updateUser dans le RestController.");
//        UserDTO userDTOUpdated = this.userService.updateUser(id, userDTO);
//        return ResponseEntity.ok(userDTOUpdated);
//    }


//    //Delete user by id (ADMIN ONLY)
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
//        System.out.println("appel de la méthode deleteUserById dans le RestController.");
//        this.userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }

//    //Find All project managers (ADMIN only)
//    @GetMapping("/project-managers")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<UserDTO>> listProjectManagers(){
//        System.out.println("appel de la méthode listProjectManagers dans le RestController.");
//        List<UserDTO> listProjectManagers = this.userService.getUsersByRole(UserRole.PROJECT_MANAGER);
//        return ResponseEntity.ok(listProjectManagers);
//    }

//    //Find All directors (ADMIN only)
//    @GetMapping("/directors")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<UserDTO>> listDirectors(){
//        System.out.println("appel de la méthode listDirectors dans le RestController.");
//        List<UserDTO> listDirectors = this.userService.getUsersByRole(UserRole.DIRECTOR);
//        return ResponseEntity.ok(listDirectors);
//    }


//    //Find All developers (ADMIN only)
//    @GetMapping("/developers")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<UserDTO>> listDevelopers(){
//        System.out.println("appel de la méthode listDevelopers dans le RestController.");
//        List<UserDTO> listDevelopers = this.userService.getUsersByRole(UserRole.DEVELOPER);
//        return ResponseEntity.ok(listDevelopers);
//    }

    //*****************************************************************************************************
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/assign/project-manager")
//    public ResponseEntity<Void> assignProjectManagerToDirector(@RequestBody AssignPMRequest request) {
//        userService.assignProjectManagerToDirector(request.getProjectManagerId(), request.getDirectorId());
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/me")
    public ResponseEntity<? extends UserDTO> getCurrentUser(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok(
                this.userService.getCurrentUserFromToken(jwt)
        );
    }
    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @GetMapping("/collaborators")
    public ResponseEntity<List<CollaboratorDTO>> getCollaborators(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok(
                this.userService.getCollaborators(jwt)
        );
    }

    @PutMapping("/users/{id}/assign/technologies")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROJECT_MANAGER')")
    public ResponseEntity<User> updateTechnologies(
            @PathVariable Long id,
            @RequestBody List<Long> techIds) {
        User updated = this.userService.updateAssignedTechnologies(id, techIds);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/assign/developer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignDeveloper(@RequestBody AssignDeveloperRequest request) {
        userService.assignDeveloperToHierarchy(request.getDeveloperId(), request.getProjectManagerId(), request.getDirectorId());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign/director")
    public ResponseEntity<Void> assignDirectorToUsers(@RequestBody AssignDirectorRequest request) {
        userService.assignDirectorToUsers(request.getDirectorId(), request.getProjectManagerIds(), request.getDeveloperIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign/project_manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignToProjectManager(@RequestBody AssignToProjectManagerRequest request){
        userService.assignToProjectManagerRequest(request.getProjectManagerId(),request.getDirectorId(),request.getDeveloperIds());
        return ResponseEntity.ok().build();
    }

    //@PreAuthorize("hasAnyRole('DIRECTOR', 'PROJECT_MANAGER')")
    @PostMapping("/assign/technologies")
    public ResponseEntity<Void> assignTechnologies(@RequestBody AssignTechnologiesRequest request,
                                                   @AuthenticationPrincipal Jwt jwt) throws AccessDeniedException {
        userService.assignTechnologiesToUser(request, jwt);
        return ResponseEntity.ok().build();
    }



    // find user by id (ADMIN only)
    @GetMapping("/users/{id}")
    public ResponseEntity<UserLightDTO> getUserByID(@PathVariable Long id){
        System.out.println("appel de la méthode getUserByID dans le RestController.");
        UserLightDTO userDTO = this.userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/directors/{id}")
    public ResponseEntity<DirectorDTO> getDirectorByID(@PathVariable Long id){
        DirectorDTO directorDTO = this.userService.getDirectorById(id);
        return ResponseEntity.ok(directorDTO);
    }

    @GetMapping("/project_managers/{id}")
    public ResponseEntity<ProjectManagerDTO> getPMByID(@PathVariable Long id){
        ProjectManagerDTO userDTO = this.userService.getPMById(id);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/developers/{id}")
    public ResponseEntity<DeveloperDTO> getDeveloperByID(@PathVariable Long id){
        DeveloperDTO developerDTO = this.userService.getDeveloperById(id);
        return ResponseEntity.ok(developerDTO);
    }

    @GetMapping("/directors/{id}/projectManagers")
    public ResponseEntity<List<ProjectManagerLightDTO>> getPMsByDirector(@PathVariable Long id) {
        List<ProjectManagerLightDTO> projectManagerLightDTOS = this.userService.getDirectorById(id).getProjectManagers();
        return ResponseEntity.ok(projectManagerLightDTOS);
    }
}
