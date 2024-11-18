package com.aus.corsafe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignTask {
     long taskId;
     public String assignee;
     public Boolean allowOverrideAssignment;

}
