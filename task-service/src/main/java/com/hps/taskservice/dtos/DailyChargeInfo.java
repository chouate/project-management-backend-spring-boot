package com.hps.taskservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DailyChargeInfo {
    private String date;
    private boolean available;
    private String taskName;
    private String projectName;
}
