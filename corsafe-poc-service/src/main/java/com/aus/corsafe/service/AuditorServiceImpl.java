package com.aus.corsafe.service;

import com.aus.corsafe.entity.Auditor;
import com.aus.corsafe.exceptions.BadCrediantialsCls;
import com.aus.corsafe.repository.AuditorRepository;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditorServiceImpl implements  AuditorService{


    private final AuditorRepository auditorRepository;

    @Override
    public List<Auditor> getNearsetAuditor(String area) {
        log.info("users are there:  "+String.valueOf(auditorRepository.findByNearByAreaContaining(area).isEmpty()));
        List<Auditor> auditorsList = auditorRepository.findByNearByAreaContaining(area);
        if(!auditorRepository.findByNearByAreaContaining(area).isEmpty()){
            return auditorsList;
        }
        else{
            throw new BadCrediantialsCls("this area not be available !!!");
        }


        //return auditorRepository.findByNearByAreaContaining(area);
    }

    @Override
    public List<Auditor> getAllAuditor() {

        List<Auditor> allAuditors = auditorRepository.findAll();
        log.info("all auditor's::  {allAuditors}",allAuditors);
        allAuditors.stream().forEach(System.out::println);
        return allAuditors;


    }

    public String changeTaskCountOfAuditor(Integer auditorId) {

        Optional<Auditor> optionalAuditor = auditorRepository.findById(auditorId);
        if (optionalAuditor.isPresent()) {
            Auditor auditor = optionalAuditor.get();


            auditor.setCurrentTaskCount(auditor.getCurrentTaskCount() + 1);

            auditorRepository.save(auditor);

            return "Task count updated successfully for Auditor with ID: " + auditorId;
        } else {
            return "Auditor with ID: " + auditorId + " not found.";
        }
    }
}
