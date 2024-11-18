package com.aus.corsafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class CompleteTaskModel {

    long taskId;
    Map<String, Object> variables;

}
