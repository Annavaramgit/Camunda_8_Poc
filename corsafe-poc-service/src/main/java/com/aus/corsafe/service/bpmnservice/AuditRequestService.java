package com.aus.corsafe.service.bpmnservice;

import com.aus.corsafe.dto.CompleteTaskDto;
import com.aus.corsafe.dto.CompleteTaskModel;
import com.aus.corsafe.dto.StartCamundadto;
import com.aus.corsafe.entity.Order;
import com.aus.corsafe.entity.UserRegister;
import com.aus.corsafe.entity.auditrequest.ProcessDetails;
import com.aus.corsafe.exceptions.UserNotFoundExceptionCls;
import com.aus.corsafe.repository.OrderRepo;
import com.aus.corsafe.repository.ProcessDetailsRepository;
import com.aus.corsafe.model.AssignTask;
import com.aus.corsafe.model.SearchTask;
import com.aus.corsafe.repository.UserRegisterRepo;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.*;


@Slf4j
@Service
public class AuditRequestService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private WebClient webClientConnectors;

    @Autowired
    ZeebeClient client;

    @Autowired
    UserRegisterRepo userRegisterRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ProcessDetailsRepository processDetailsRepository;

    @JobWorker(type = "userDetailsVerifier", autoComplete = true)
    public void GettingVariableAndSettingVariable(ActivatedJob job) {
        /*
        log.info("strted userDetailsVerifier");
        System.out.println("strted userDetailsVerifier");
        boolean knowMe = true;

        String userEmail = (String) job.getVariable("email");
        Optional<UserRegister> user = userRegisterRepo.findByEmail(userEmail);

        knowMe = user.isPresent();

        //knowMe = user.isPresent() ? true : false;

        Map<String, Object> variables = new HashMap<>();
        variables.put("knowMe", knowMe);
        log.info("updated knowMe: {}", knowMe);
        System.out.println("updated knowMe");

        ProcessDetails processDetails = ProcessDetails.builder()
                .firstName((String) job.getVariable("firstName"))
                .lastName((String) job.getVariable("lastName"))
                .abn((String) job.getVariable("abn"))
                .email((String) job.getVariable("email"))
                .companyName((String) job.getVariable("companyName"))
                .companyAddress((String) job.getVariable("companyAddress"))
                .state((String) job.getVariable("state"))
                .postalCode((String) job.getVariable("postalCode"))
                .phoneNumber((Long) job.getVariable("phoneNumber"))
                .processInstanceKey(job.getProcessInstanceKey())
                .taskId(job.getKey())
                .createdAt(new Date())
                .assignee(job.getCustomHeaders().get("assignee"))
                .implementation(job.getType())
                .build();

        log.info("prepared processDetails: {}", processDetails);
        System.out.println("prepared processDetails");
        processDetailsRepository.save(processDetails);

        client.newSetVariablesCommand(job.getElementInstanceKey())
                .variables(variables)
                .send()
                .join();

        log.info("exists from userDetailsVerifier");
        System.out.println("exits from userDetailsVerifier");*/
        log.info("second task");
    }


    /**
     * it call getuserststus camunds task for save detials
     */

    @JobWorker(type = "saveDataInDb", autoComplete = true)
    public void handleGetUserStatusCamundaTask(ActivatedJob job) {

        log.info("Entered saveDetails task for order ID: {} and processIntanceIs : {}", job.getVariable("orderId"),job.getProcessInstanceKey());

        ProcessDetails processDetails = ProcessDetails.builder()
                .orderId((Integer) job.getVariable("orderId"))
                .firstName((String) job.getVariable("firstName"))
                .lastName((String) job.getVariable("lastName"))
                .abn((String) job.getVariable("abn"))
                .email((String) job.getVariable("email"))
                .companyName((String) job.getVariable("companyName"))
                .companyAddress((String) job.getVariable("companyAddress"))
                .state((String) job.getVariable("state"))
                .postalCode((String) job.getVariable("postalCode"))
                .phoneNumber((Long) job.getVariable("phoneNumber"))
                .processInstanceKey(job.getProcessInstanceKey())
               // .taskId(job.getKey())
                .createdAt(new Date())
                .assignee(job.getCustomHeaders().get("assignee"))
                .implementation(job.getType())
                .build();

        processDetailsRepository.save(processDetails);
        log.info("Saved process details for order ID: {}", job.getVariable("orderId"));




    }

    //for start the camunda
    public Object startCamunda(StartCamundadto dto) {
        log.info("start camunda service entered :{}",dto);
        return webClientConnectors.post()
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    @JobWorker(type = "auditChargeCalculation", autoComplete = true)
    public void chargeCalculation(ActivatedJob job) {
        System.out.println("Calculating charges");
    }


    /**
     * for complete the task with using zeebe client
     */
    public String completeTaskWithZeebe(CompleteTaskModel completeTask) {
        client.newCompleteCommand(completeTask.getTaskId())
                .variables(completeTask.getVariables())
                .send()
                .join();

        return "Task Completed with jobKey: " + completeTask.getTaskId();

    }

    /**
     * complete the task with rest api
     */
    public Object completeTask(CompleteTaskDto completeTask) {
        String url = "/tasks/" + completeTask.getTaskId() + "/complete";

        return webClient.patch()
                .uri(url)
                .body(Mono.just(completeTask), CompleteTaskDto.class)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    /**
     * for assign the task
     */
    public Object getAssignTask(AssignTask assignTask) {
        String url = "/tasks/" + assignTask.getTaskId() + "/assign";
        Object claimed = webClient.patch()
                .uri(url)
                .body(Mono.just(assignTask), assignTask.getClass())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        return claimed;
    }

    /**
     * get get-assigned tasks and get-unassigned tasks based on body (true ,false)
     */
    public List<Object> getSearchTask(SearchTask searchTask) {

        List<Object> list = new ArrayList<>();
        log.info("search task service class entered");
        webClient.post()
                .uri("/tasks/search")
                .body(Mono.just(searchTask), searchTask.getClass())
                .retrieve()
                .bodyToFlux(Object.class)
                .doOnNext(list::add) // Add each received object to the list
                .blockLast(); // Wait for the Flux to complete

        log.info("search task service class end ...");
        return list;
    }
}