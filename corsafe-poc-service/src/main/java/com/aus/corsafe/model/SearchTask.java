package com.aus.corsafe.model;

import jakarta.annotation.sql.DataSourceDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchTask {

    private boolean assigned;


    private String processInstanceKey;


}
