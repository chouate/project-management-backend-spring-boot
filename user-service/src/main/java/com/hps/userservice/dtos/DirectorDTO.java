package com.hps.userservice.dtos;

import com.hps.userservice.dtos.lightDTOs.DeveloperLightDTO;
import com.hps.userservice.dtos.lightDTOs.ProjectManagerLightDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class DirectorDTO extends UserDTO{
    private List<ProjectManagerLightDTO> projectManagers;
    private List<DeveloperLightDTO> developers;
}
