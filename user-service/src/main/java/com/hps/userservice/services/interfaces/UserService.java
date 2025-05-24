package com.hps.userservice.services.interfaces;

import com.hps.userservice.dtos.*;
import com.hps.userservice.dtos.lightDTOs.ProjectManagerLightDTO;
import com.hps.userservice.dtos.lightDTOs.UserLightDTO;
import com.hps.userservice.dtos.request.AssignTechnologiesRequest;
import com.hps.userservice.entities.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UserService {
//    List<UserDTO> getAllUsers();
    UserLightDTO getUserById(Long id);
    DirectorDTO getDirectorById(Long id);
    ProjectManagerDTO getPMById(Long id);
    List<ProjectManagerLightDTO> getPMsByDirector(Long id);
    DeveloperDTO getDeveloperById(Long id);
//    UserDTO getUserByEmail(String email);
//    void createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO user);
//    void deleteUser(Long id);
//   List<UserDTO> getUsersByRole(UserRole role);
    User createUserFromToken(Jwt jwt);
    UserDTO getCurrentUserFromToken(Jwt jwt);
    List<CollaboratorDTO> getCollaborators(Jwt jwt);
    User updateAssignedTechnologies(Long userId, List<Long> techIds);

    public void assignProjectManagerToDirector(Long pmId, Long directorId);
    public void assignDeveloperToHierarchy(Long devId, Long pmId, Long directorId);
    public void assignDirectorToUsers(Long directorId, List<Long> projectManagerIds, List<Long> developerIds);
    public void assignToProjectManagerRequest(Long pmId, Long directorId, List<Long> developerIds);
    public void assignTechnologiesToUser(AssignTechnologiesRequest request, Jwt jwt) throws AccessDeniedException;
}
