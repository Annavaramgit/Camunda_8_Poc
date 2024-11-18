package com.aus.corsafe.commandLineRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aus.corsafe.entity.Auditor;
import com.aus.corsafe.repository.AuditorRepository;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class AuditorCommandLineRunner implements CommandLineRunner {

    private final AuditorRepository repository;

    @Override
    public void run(String... args) throws Exception {

        List<Auditor> auditors = new ArrayList<>();
        Auditor auditor1 = new Auditor();
        auditor1.setName("Auditor A");
        auditor1.setCurrentTaskCount(2);
        auditor1.setLocation("Location A");
        auditor1.setNearByArea(Arrays.asList("Area 1", "Area 2", "Area 3"));
        auditors.add(auditor1);

        Auditor auditor2 = new Auditor();
        auditor2.setName("Auditor B");
        auditor2.setCurrentTaskCount(2);
        auditor2.setLocation("Location B");
        auditor2.setNearByArea(Arrays.asList("Area 1", "Area 2", "Area 4"));
        auditors.add(auditor2);

        Auditor auditor3 = new Auditor();
        auditor3.setName("Auditor C");
        auditor3.setCurrentTaskCount(0);
        auditor3.setLocation("Location C");
        auditor3.setNearByArea(Arrays.asList("Area 4", "Area 5", "Area 6"));
        auditors.add(auditor3);

        Auditor auditor4 = new Auditor();
        auditor4.setName("Auditor D");
        auditor4.setCurrentTaskCount(3);
        auditor4.setLocation("Location D");
        auditor4.setNearByArea(Arrays.asList("Area 5", "Area 4", "Area 3"));
        auditors.add(auditor4);

        Auditor auditor5 = new Auditor();
        auditor5.setName("Auditor E");
        auditor5.setCurrentTaskCount(3);
        auditor5.setLocation("Location E");
        auditor5.setNearByArea(Arrays.asList("Area 1", "Area 5", "Area 2"));
        auditors.add(auditor5);

        Auditor auditor6 = new Auditor();
        auditor6.setName("Auditor F");
        auditor6.setCurrentTaskCount(3);
        auditor6.setLocation("Location F");
        auditor6.setNearByArea(Arrays.asList("Area 4", "Area 2", "Area 6"));
        auditors.add(auditor6);

        Auditor auditor7 = new Auditor();
        auditor7.setName("Auditor G");
        auditor7.setCurrentTaskCount(3);
        auditor7.setLocation("Location G");
        auditor7.setNearByArea(Arrays.asList("Area 3", "Area 8", "Area 7"));
        auditors.add(auditor7);

        for(Auditor auditor: auditors){
            if(!(repository.findByName(auditor.getName()).isPresent())){
                repository.save(auditor);
            }
        }

    }
}
