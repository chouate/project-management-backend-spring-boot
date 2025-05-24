package com.hps.userservice.dtos;

import com.hps.userservice.dtos.lightDTOs.DeveloperLightDTO;
import com.hps.userservice.dtos.lightDTOs.DirectorLightDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectManagerDTO extends UserDTO{
    private List<DeveloperLightDTO> developers;
    private DirectorLightDTO director;
}
