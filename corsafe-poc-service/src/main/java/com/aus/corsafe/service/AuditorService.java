package com.aus.corsafe.service;

import com.aus.corsafe.entity.Auditor;

import java.util.List;

public interface AuditorService {
    List<Auditor> getNearsetAuditor(String area);
    List<Auditor> getAllAuditor();
    String changeTaskCountOfAuditor(Integer auditorId);
}
