package com.hps.userservice.services.implementations;

import com.hps.userservice.dtos.*;
import com.hps.userservice.dtos.lightDTOs.DeveloperLightDTO;
import com.hps.userservice.dtos.lightDTOs.DirectorLightDTO;
import com.hps.userservice.dtos.lightDTOs.ProjectManagerLightDTO;
import com.hps.userservice.dtos.lightDTOs.UserLightDTO;
import com.hps.userservice.dtos.request.AssignTechnologiesRequest;
import com.hps.userservice.entities.*;
import com.hps.userservice.exceptions.ResourceNotFoundException;
import com.hps.userservice.factory.UserFactory;
import com.hps.userservice.repositories.*;
import com.hps.userservice.services.interfaces.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProjectManagerRepository projectManagerRepository;
    private final DeveloperRepository developerRepository;
    private final DirectorRepository directorRepository;
    private final TechnologieRepository technologieRepository;
    private final UserFactory userFactory;

//    private UserDTO toDTO(User user){
////        UserRole userRole = user.getRoles().stream().findFirst()
////                .orElseThrow(()->new RuntimeException("The role for this user not found."));
//
//        return new UserDTO(
//                user.getId(),
//                user.getKeycloakId(),
//                user.getEmail(),
//                user.getFirstName(),
//                user.getLastName()
////                user.getRoles(),
////                userRole
//        );
//    }

    private DirectorDTO toDirectorDTO(Director director){

        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setId(director.getId());
        directorDTO.setKeycloakId(director.getKeycloakId());
        directorDTO.setFirstName(director.getFirstName());
        directorDTO.setLastName(director.getLastName());
        directorDTO.setEmail(director.getEmail());
        directorDTO.setTechnologies(director.getTechnologyList());

        List<ProjectManager> listProjectManagersAffected = director.getListProjectManagersAffected();
        List<ProjectManagerLightDTO> projectManagerLightDTOS = new ArrayList<>();
        if(director.getListProjectManagersAffected() != null){
            for (ProjectManager projectManager: listProjectManagersAffected){
                ProjectManagerLightDTO  projectManagerLightDTO = new ProjectManagerLightDTO();
                projectManagerLightDTO.setId(projectManager.getId());
                projectManagerLightDTO.setEmail(projectManager.getEmail());
                projectManagerLightDTO.setFirstName(projectManager.getFirstName());
                projectManagerLightDTO.setLastName(projectManager.getLastName());
                projectManagerLightDTOS.add(projectManagerLightDTO);
            }
        }
        directorDTO.setProjectManagers(projectManagerLightDTOS);

        List<Developer> developerList = director.getListDevelopersAffected();
        List<DeveloperLightDTO> developerLightDTOS = new ArrayList<>();
        if(director.getListDevelopersAffected()!= null){
            for (Developer developer:developerList){
                DeveloperLightDTO developerLightDTO = new DeveloperLightDTO();
                developerLightDTO.setId(developer.getId());
                developerLightDTO.setEmail(developer.getEmail());
                developerLightDTO.setFirstName(developer.getFirstName());
                developerLightDTO.setLastName(developer.getLastName());
                developerLightDTOS.add(developerLightDTO);
            }
        }
        directorDTO.setDevelopers(developerLightDTOS);

        return directorDTO;
    }

    private ProjectManagerDTO ToProjectManagerDTO(ProjectManager projectManager){
        ProjectManagerDTO projectManagerDTO = new ProjectManagerDTO();
        projectManagerDTO.setId(projectManager.getId());
        projectManagerDTO.setKeycloakId(projectManager.getKeycloakId());
        projectManagerDTO.setFirstName(projectManager.getFirstName());
        projectManagerDTO.setLastName(projectManager.getLastName());
        projectManagerDTO.setEmail(projectManager.getEmail());
        projectManagerDTO.setTechnologies(projectManager.getTechnologyList());

        List<Developer> developerList = projectManager.getListDeveloper();
        List<DeveloperLightDTO> developerLightDTOS = new ArrayList<>();
        if (projectManager.getListDeveloper() != null){
            for (Developer developer : developerList){
                DeveloperLightDTO developerLightDTO = new DeveloperLightDTO();
                developerLightDTO.setId(developer.getId());
                developerLightDTO.setEmail(developer.getEmail());
                developerLightDTO.setFirstName(developer.getFirstName());
                developerLightDTO.setLastName(developer.getLastName());
                developerLightDTOS.add(developerLightDTO);
            }
        }
        projectManagerDTO.setDevelopers(developerLightDTOS);

        if(projectManager.getDirector() != null){
            projectManagerDTO.setDirector(new DirectorLightDTO(
                    projectManager.getDirector().getId(),
                    projectManager.getDirector().getEmail(),
                    projectManager.getDirector().getFirstName(),
                    projectManager.getDirector().getLastName()
            ));
        }

        return projectManagerDTO;
    }

    private DeveloperDTO toDeveloperDTO(Developer developer){
        DeveloperDTO developerDTO = new DeveloperDTO();
        developerDTO.setId(developer.getId());
        developerDTO.setKeycloakId(developer.getKeycloakId());
        developerDTO.setFirstName(developer.getFirstName());
        developerDTO.setLastName(developer.getLastName());
        developerDTO.setEmail(developer.getEmail());
        developerDTO.setTechnologies(developer.getTechnologyList());

        if(developer.getDirector() != null){
            developerDTO.setDirector(new DirectorLightDTO(
                    developer.getDirector().getId(),
                    developer.getDirector().getEmail(),
                    developer.getDirector().getFirstName(),
                    developer.getDirector().getLastName()
            ));
        }

        if (developer.getProjectManager() != null){
            developerDTO.setProjectManager(new ProjectManagerLightDTO(
                    developer.getProjectManager().getId(),
                    developer.getProjectManager().getEmail(),
                    developer.getProjectManager().getFirstName(),
                    developer.getProjectManager().getLastName()
            ));
        }

        return developerDTO;
    }

    private UserLightDTO ToLighTDTO(User user){
        return new UserLightDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public UserServiceImpl(UserRepository userRepository, ProjectManagerRepository projectManagerRepository, DeveloperRepository developerRepository, DirectorRepository directorRepository, TechnologieRepository technologieRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.projectManagerRepository = projectManagerRepository;
        this.developerRepository = developerRepository;
        this.directorRepository = directorRepository;
        this.technologieRepository = technologieRepository;
        this.userFactory = userFactory;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO user) {
        //User user1
        return null;
    }

    @Override
    public User createUserFromToken(Jwt jwt) {
        String keycloakId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String firstName = jwt.getClaimAsString("given_name");
        String lastName =  jwt.getClaimAsString("family_name");

        //extract the roles from realm_access
        List<String> roles = jwt.getClaimAsMap("realm_access") != null
                ? (List<String>) ((Map<String, Object>) jwt.getClaim("realm_access")).get("roles")
                : List.of();

        if (roles.contains("DIRECTOR")) {
            Director director = new Director();
            director.setKeycloakId(keycloakId);
            director.setEmail(email);
            director.setFirstName(firstName);
            director.setLastName(lastName);
            return userRepository.save(director);
        } else if (roles.contains("PROJECT_MANAGER")) {
            ProjectManager pm = new ProjectManager();
            pm.setKeycloakId(keycloakId);
            pm.setEmail(email);
            pm.setFirstName(firstName);
            pm.setLastName(lastName);
            return userRepository.save(pm);
        } else if (roles.contains("DEVELOPER")) {
            Developer dev = new Developer();
            dev.setKeycloakId(keycloakId);
            dev.setEmail(email);
            dev.setFirstName(firstName);
            dev.setLastName(lastName);
            return userRepository.save(dev);
        } else if (roles.contains("ADMIN")) {
            Admin admin = new Admin();
            admin.setKeycloakId(keycloakId);
            admin.setEmail(email);
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            return userRepository.save(admin);
        }

        throw new RuntimeException("Rôle non reconnu pour le compte utilisateur.");
    }

    @Override
    public UserDTO getCurrentUserFromToken(Jwt jwt) {
        System.out.println("Appelle de la méthode getCurrentUserFromToken dans UserServiceImpl");
        String keycloak = jwt.getSubject();

        //get or create the local locaUser
        User locaUser = this.userRepository.findByKeycloakId(keycloak)
                .orElseGet(()->this.createUserFromToken(jwt));

        //UserDTO base
        UserDTO userDTO = new UserDTO();
        userDTO.setId(locaUser.getId());
        userDTO.setKeycloakId(locaUser.getKeycloakId());
        userDTO.setFirstName(locaUser.getFirstName());
        userDTO.setLastName(locaUser.getLastName());
        userDTO.setEmail(locaUser.getEmail());
//        userDTO.setTechnologies(
//                locaUser.getTechnologyList().stream().map(Technology::getName).collect(Collectors.toSet())
//        );
        userDTO.setTechnologies(locaUser.getTechnologyList());

        //mapping selon le type
        if(locaUser instanceof Director director){
            System.out.println("le cas de director: ");
            DirectorDTO directorDTO = new DirectorDTO();
            BeanUtils.copyProperties(userDTO, directorDTO);
            directorDTO.setRole("DIRECTOR");

            List<ProjectManager> listProjectManagersAffected = director.getListProjectManagersAffected();
            List<ProjectManagerLightDTO> projectManagerLightDTOS = new ArrayList<>();
            if(director.getListProjectManagersAffected() != null){
                for (ProjectManager projectManager: listProjectManagersAffected){
                    ProjectManagerLightDTO  projectManagerLightDTO = new ProjectManagerLightDTO();
                    projectManagerLightDTO.setId(projectManager.getId());
                    projectManagerLightDTO.setEmail(projectManager.getEmail());
                    projectManagerLightDTO.setFirstName(projectManager.getFirstName());
                    projectManagerLightDTO.setLastName(projectManager.getLastName());
                    projectManagerLightDTOS.add(projectManagerLightDTO);
                }
            }
            directorDTO.setProjectManagers(projectManagerLightDTOS);

            List<Developer> developerList = director.getListDevelopersAffected();
            List<DeveloperLightDTO> developerLightDTOS = new ArrayList<>();
            if(director.getListDevelopersAffected()!= null){
                for (Developer developer:developerList){
                    DeveloperLightDTO developerLightDTO = new DeveloperLightDTO();
                    developerLightDTO.setId(developer.getId());
                    developerLightDTO.setEmail(developer.getEmail());
                    developerLightDTO.setFirstName(developer.getFirstName());
                    developerLightDTO.setLastName(developer.getLastName());
                    developerLightDTOS.add(developerLightDTO);
                }
            }
            directorDTO.setDevelopers(developerLightDTOS);

            return directorDTO;
        } else if (locaUser instanceof ProjectManager projectManager) {
            System.out.println("le cas de projectManager: ");
            ProjectManagerDTO projectManagerDTO = new ProjectManagerDTO();
            BeanUtils.copyProperties(userDTO, projectManagerDTO);
            projectManagerDTO.setRole("PROJECT_MANAGER");

            List<Developer> developerList = projectManager.getListDeveloper();
            List<DeveloperLightDTO> developerLightDTOS = new ArrayList<>();
            if (projectManager.getListDeveloper() != null){
                for (Developer developer : developerList){
                    DeveloperLightDTO developerLightDTO = new DeveloperLightDTO();
                    developerLightDTO.setId(developer.getId());
                    developerLightDTO.setEmail(developer.getEmail());
                    developerLightDTO.setFirstName(developer.getFirstName());
                    developerLightDTO.setLastName(developer.getLastName());
                    developerLightDTOS.add(developerLightDTO);
                }
            }
            projectManagerDTO.setDevelopers(developerLightDTOS);

            if(projectManager.getDirector() != null){
                projectManagerDTO.setDirector(new DirectorLightDTO(
                     projectManager.getDirector().getId(),
                     projectManager.getDirector().getEmail(),
                     projectManager.getDirector().getFirstName(),
                     projectManager.getDirector().getLastName()
                ));
            }

            return projectManagerDTO;

        } else if (locaUser instanceof Developer developer) {
            System.out.println("le cas de developer: ");
            DeveloperDTO developerDTO = new DeveloperDTO();
            BeanUtils.copyProperties(userDTO, developerDTO);
            developerDTO.setRole("DEVELOPER");

            if(developer.getDirector() != null){
                developerDTO.setDirector(new DirectorLightDTO(
                        developer.getDirector().getId(),
                        developer.getDirector().getEmail(),
                        developer.getDirector().getFirstName(),
                        developer.getDirector().getLastName()
                ));
            }

            if (developer.getProjectManager() != null){
                developerDTO.setProjectManager(new ProjectManagerLightDTO(
                        developer.getProjectManager().getId(),
                        developer.getProjectManager().getEmail(),
                        developer.getProjectManager().getFirstName(),
                        developer.getProjectManager().getLastName()
                ));
            }

            return developerDTO;
        }else if(locaUser instanceof Admin admin){
            System.out.println("le cas de admin: ");
            AdminDTO adminDTO = new AdminDTO();
            BeanUtils.copyProperties(userDTO, adminDTO);
            adminDTO.setRole("ADMIN");

            return adminDTO;
        }

        return null;
    }

    @Override
    public List<CollaboratorDTO> getCollaborators(Jwt jwt) {
        System.out.println("Appelle de la méthode getCollaborators dans UserServiceImpl");
        String keycloak = jwt.getSubject();

        //get the local locaUser
        User localUser = this.userRepository.findByKeycloakId(keycloak)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with keycloak id: " + keycloak));


        // List that will contain me + the collaborators
        List<CollaboratorDTO> collabs = new ArrayList<>();

        //UserDTO base
        CollaboratorDTO me = new CollaboratorDTO();
        me.setId(localUser.getId());
        me.setFirstName(localUser.getFirstName());
        me.setLastName(localUser.getLastName());
        me.setEmail(localUser.getEmail());
        me.setImage(localUser.getImage());
        me.setPhoneNumber(localUser.getPhoneNumber());
        me.setTechnologies(localUser.getTechnologyList());

        //mapping selon le type
        if(localUser instanceof Director director){
            me.setRole("DIRECTOR");
            collabs.add(me);

            List<ProjectManager> listProjectManagersAffected = director.getListProjectManagersAffected();
            if(director.getListProjectManagersAffected() != null){
                for (ProjectManager projectManager: listProjectManagersAffected){
                    CollaboratorDTO  pmCollaborator = new CollaboratorDTO();
                    pmCollaborator.setRole("PROJECT_MANAGER");
                    pmCollaborator.setId(projectManager.getId());
                    pmCollaborator.setFirstName(projectManager.getFirstName());
                    pmCollaborator.setLastName(projectManager.getLastName());
                    pmCollaborator.setEmail(projectManager.getEmail());
                    pmCollaborator.setImage(projectManager.getImage());
                    pmCollaborator.setPhoneNumber(projectManager.getPhoneNumber());
                    pmCollaborator.setTechnologies(projectManager.getTechnologyList());
                    collabs.add(pmCollaborator);
                }
            }

            List<Developer> developerList = director.getListDevelopersAffected();
            if(director.getListDevelopersAffected()!= null){
                for (Developer developer:developerList){
                    CollaboratorDTO  devCollaborator = new CollaboratorDTO();
                    devCollaborator.setId(developer.getId());
                    devCollaborator.setFirstName(developer.getFirstName());
                    devCollaborator.setLastName(developer.getLastName());
                    devCollaborator.setEmail(developer.getEmail());
                    devCollaborator.setImage(developer.getImage());
                    devCollaborator.setPhoneNumber(developer.getPhoneNumber());
                    devCollaborator.setTechnologies(developer.getTechnologyList());
                    devCollaborator.setRole("DEVELOPER");
                    collabs.add(devCollaborator);

                }

            }


        } else if (localUser instanceof ProjectManager projectManager) {
            me.setRole("PROJECT_MANAGER");
            collabs.add(me);

            Director director = projectManager.getDirector();
            if(director != null){
                CollaboratorDTO  directorCollaborator = new CollaboratorDTO();
                directorCollaborator.setId(director.getId());
                directorCollaborator.setFirstName(director.getFirstName());
                directorCollaborator.setLastName(director.getLastName());
                directorCollaborator.setEmail(director.getEmail());
                directorCollaborator.setImage(director.getImage());
                directorCollaborator.setPhoneNumber(director.getPhoneNumber());
                directorCollaborator.setTechnologies(director.getTechnologyList());
                directorCollaborator.setRole("DIRECTOR");
                collabs.add(directorCollaborator);
            }
            List<Developer> developerList = projectManager.getListDeveloper();
            if(projectManager.getListDeveloper() != null){
                for (Developer developer : developerList){
                    CollaboratorDTO  devCollaborator = new CollaboratorDTO();
                    devCollaborator.setId(developer.getId());
                    devCollaborator.setFirstName(developer.getFirstName());
                    devCollaborator.setLastName(developer.getLastName());
                    devCollaborator.setEmail(developer.getEmail());
                    devCollaborator.setImage(developer.getImage());
                    devCollaborator.setPhoneNumber(developer.getPhoneNumber());
                    devCollaborator.setTechnologies(developer.getTechnologyList());
                    devCollaborator.setRole("DEVELOPER");
                    collabs.add(devCollaborator);
                }
            }

        } else if (localUser instanceof Developer developer) {
            me.setRole("DEVELOPER");
            collabs.add(me);

            if(developer.getDirector() != null){
                CollaboratorDTO  directorCollaborator = new CollaboratorDTO();
                directorCollaborator.setId(developer.getDirector().getId());
                directorCollaborator.setFirstName(developer.getDirector().getFirstName());
                directorCollaborator.setLastName(developer.getDirector().getLastName());
                directorCollaborator.setEmail(developer.getDirector().getEmail());
                directorCollaborator.setImage(developer.getDirector().getImage());
                directorCollaborator.setPhoneNumber(developer.getDirector().getPhoneNumber());
                directorCollaborator.setTechnologies(developer.getDirector().getTechnologyList());
                directorCollaborator.setRole("DIRECTOR");
                collabs.add(directorCollaborator);
            }


            if (developer.getProjectManager() != null){
                CollaboratorDTO  pmCollaborator = new CollaboratorDTO();
                pmCollaborator.setId(developer.getProjectManager().getId());
                pmCollaborator.setFirstName(developer.getProjectManager().getFirstName());
                pmCollaborator.setLastName(developer.getProjectManager().getLastName());
                pmCollaborator.setEmail(developer.getProjectManager().getEmail());
                pmCollaborator.setImage(developer.getProjectManager().getImage());
                pmCollaborator.setPhoneNumber(developer.getProjectManager().getPhoneNumber());
                pmCollaborator.setTechnologies(developer.getProjectManager().getTechnologyList());
                pmCollaborator.setRole("PROJECT_MANAGER");
                collabs.add(pmCollaborator);

                for (Developer dev : developer.getProjectManager().getListDeveloper()){
                    if(dev.getId().equals(developer.getId())) continue;
                    CollaboratorDTO  devCollaborator = new CollaboratorDTO();
                    devCollaborator.setId(dev.getId());
                    devCollaborator.setFirstName(dev.getFirstName());
                    devCollaborator.setLastName(dev.getLastName());
                    devCollaborator.setEmail(dev.getEmail());
                    devCollaborator.setImage(dev.getImage());
                    devCollaborator.setPhoneNumber(dev.getPhoneNumber());
                    devCollaborator.setTechnologies(dev.getTechnologyList());
                    devCollaborator.setRole("DEVELOPER");
                    collabs.add(devCollaborator);
                }
            }


        }

        return collabs;

    }

    @Override
    public User updateAssignedTechnologies(Long userId, List<Long> techIds) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User, to assign technologies, not found with id: " + userId));
        List<Technology> techs = this.technologieRepository.findAllByIdIn(techIds);
        user.setTechnologyList(techs);
        return this.userRepository.save(user);
    }

    @Override
    public void assignProjectManagerToDirector(Long pmId, Long directorId) {
        ProjectManager pm = projectManagerRepository.findById(pmId)
                .orElseThrow(() -> new RuntimeException("PM not found"));
        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new RuntimeException("Director not found"));

        pm.setDirector(director);
        projectManagerRepository.save(pm);
    }

    @Override
    public void assignDeveloperToHierarchy(Long devId, Long pmId, Long directorId) {
        Developer dev = developerRepository.findById(devId)
                .orElseThrow(() -> new RuntimeException("Developer not found"));
        ProjectManager pm = projectManagerRepository.findById(pmId)
                .orElseThrow(() -> new RuntimeException("PM not found"));
        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new RuntimeException("Director not found"));

        dev.setProjectManager(pm);
        dev.setDirector(director);
        developerRepository.save(dev);
    }

    @Override
    public void assignDirectorToUsers(Long directorId, List<Long> projectManagerIds, List<Long> developerIds) {
        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found with ID: " + directorId));

        if (projectManagerIds != null) {
            for (Long pmId : projectManagerIds) {
                ProjectManager pm = projectManagerRepository.findById(pmId)
                        .orElseThrow(() -> new ResourceNotFoundException("ProjectManager not found with ID: " + pmId));
                pm.setDirector(director);
                projectManagerRepository.save(pm);
            }
        }

        if (developerIds != null) {
            for (Long devId : developerIds) {
                Developer dev = developerRepository.findById(devId)
                        .orElseThrow(() -> new ResourceNotFoundException("Developer not found with ID: " + devId));
                dev.setDirector(director);
                developerRepository.save(dev);
            }
        }
    }

   /*
   avec cette methode
   le director peut etre ajouté et meme modifier ms n'est pas supprimer
   Dans la table developer si le champ project_manager_id est null il sera remplis si non l'ancienne valeur sera écraser
   */
    @Override
    public void assignToProjectManagerRequest(Long pmId, Long directorId, List<Long> developerIds){
        ProjectManager pm = projectManagerRepository.findById(pmId)
                .orElseThrow(()-> new ResourceNotFoundException("PM not found with id: "+pmId));

        //assign a director
        if(directorId != null){
            Director director = directorRepository.findById(directorId)
                    .orElseThrow(()-> new ResourceNotFoundException("Director not found with id : "+directorId));
            pm.setDirector(director);
        }

        //assign developers
        if(developerIds != null && !developerIds.isEmpty()){
            for (Long developerId : developerIds){
                Developer developer = developerRepository.findById(developerId)
                        .orElseThrow(()-> new ResourceNotFoundException("Developer not found with id : "+developerId));
                developer.setProjectManager(pm);
                developerRepository.save(developer); // mise à jour du lien avec le PM
            }
        }

        projectManagerRepository.save(pm); // sauvegarde finale du PM

    }

    /*
    cette méthode sert à ajouter et modifier et supprimer les technologies de l'utilisateur
    car elle Remplace toute la liste avec la nouvelle sélection 'si la sélection est vide simule la supprission'
     */
    @Override
    public void assignTechnologiesToUser(AssignTechnologiesRequest request, Jwt jwt) throws AccessDeniedException {
        String keycloakId = jwt.getSubject();
        User currentUser = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur connecté introuvable"));

        User targetUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur cible introuvable"));

        // 🔐 Vérification des permissions
        if (currentUser instanceof Director director) {
            if (targetUser instanceof Developer developer && !director.getListDevelopersAffected().contains(developer)) {
                throw new AccessDeniedException("Ce développeur n’est pas affecté à ce directeur");
            }
            if (targetUser instanceof ProjectManager pm && !director.getListProjectManagersAffected().contains(pm)) {
                throw new AccessDeniedException("Ce chef de projet n’est pas affecté à ce directeur");
            }
        } else if (currentUser instanceof ProjectManager pm) {
            if (targetUser instanceof Developer developer && !pm.getListDeveloper().contains(developer)) {
                throw new AccessDeniedException("Ce développeur n’est pas affecté à ce chef de projet");
            }
        } else {
            throw new AccessDeniedException("Rôle non autorisé");
        }

        // 🔁 Mise à jour des technologies
        List<Technology> technologies = new ArrayList<>();
        for (String name : request.getTechnologyNames()) {
            Technology tech = technologieRepository.findTechnologieByName(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Technology introuvable : " + name));
            technologies.add(tech);
        }

        targetUser.setTechnologyList(technologies);
        userRepository.save(targetUser);
    }


//    @Override
//    public List<UserDTO> getAllUsers() {
//        System.out.println("Apple de la méthode getAllUsers dans le service.");
//        List<User> users = this.userRepository.findAll();
//        List<UserDTO> listUserDTO = new ArrayList<>();
//        for(User user:users){
//            UserDTO userDTO = toDTO(user);
//            listUserDTO.add(userDTO);
//        }
//        return listUserDTO;
//    }

    @Override
    public UserLightDTO getUserById(Long id) {
        System.out.println("Appelle de la méthode getUserById dans le UserServiceImpl.");
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+id));
        UserLightDTO userDTOFinded = ToLighTDTO(user);
        return userDTOFinded;
    }

    @Override
    public DirectorDTO getDirectorById(Long id) {
        Director director = this.directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found with id: "+id) );
        DirectorDTO userDTOFinded = toDirectorDTO(director);
        return userDTOFinded;
    }

    @Override
    public ProjectManagerDTO getPMById(Long id) {
        ProjectManager pm = this.projectManagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project Manager not found with id: "+id) );
        ProjectManagerDTO projectManagerDTO = ToProjectManagerDTO(pm);
        return projectManagerDTO;
    }

    @Override
    public List<ProjectManagerLightDTO> getPMsByDirector(Long id) {
        return List.of();
    }

    @Override
    public DeveloperDTO getDeveloperById(Long id) {
        Developer developer = this.developerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Developer not found with id: "+id) );
        DeveloperDTO developerDTO = toDeveloperDTO(developer);
        return developerDTO;
    }

//    @Override
//    public UserDTO getUserByEmail(String email) {
//        System.out.println("Appelle de la méthode getUserByEmail dans le UserServiceImpl.");
//        User user = this.userRepository.findByEmail(email)
//                .orElseThrow(()-> new ResourceNotFoundException("User not found with email: "+email));
//
//        return toDTO(user);
//    }
//
//    @Override
//    public void createUser(UserDTO userDTO) {
//        System.out.println("Appelle de la méthode createUser dans le UserServiceImpl.");
//        Optional<User> userOptional = this.userRepository.findByEmail(userDTO.getEmail());
//
//        if(userOptional.isPresent()){
//            throw new ResourceAlreadyExistException("The user already exist with email : "+userDTO.getEmail());
//        }
//
//        User newUser = this.userFactory.createUserByRole(userDTO.getRole());
//
//        this.userRepository.save(newUser);
//    }
//
//    @Override
//    public UserDTO updateUser(Long id, UserDTO user) {
//        System.out.println("Appelle de la méthode deleteUser dans le UserServiceImpl.");
//        return null;
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//        System.out.println("Appelle de la méthode deleteUser dans le UserServiceImpl.");
//        User userExisting = this.userRepository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException("The user, to delete, not found with id: "+id));
//
//        this.userRepository.deleteById(id);
//    }
//
//    @Override
//    public List<UserDTO> getUsersByRole(UserRole role) {
//        System.out.println("Appelle de la méthode getUsersByRole dans le UserServiceImpl.");
//        return null;
//    }


}
