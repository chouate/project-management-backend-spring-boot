package com.hps.userservice.dtos;

import com.hps.userservice.dtos.lightDTOs.DirectorLightDTO;
import com.hps.userservice.dtos.lightDTOs.ProjectManagerLightDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DeveloperDTO extends UserDTO{
    private ProjectManagerLightDTO projectManager;
    private DirectorLightDTO director;
}
