package com.aus.corsafe.controller;

import com.aus.corsafe.dto.CompleteTaskDto;
import com.aus.corsafe.dto.CompleteTaskModel;
import com.aus.corsafe.dto.StartCamundadto;
import com.aus.corsafe.entity.Order;
import com.aus.corsafe.entity.auditrequest.ProcessDetails;
import com.aus.corsafe.exceptions.UserNotFoundExceptionCls;
import com.aus.corsafe.model.AssignTask;
import com.aus.corsafe.model.SearchTask;
import com.aus.corsafe.repository.OrderRepo;
import com.aus.corsafe.repository.ProcessDetailsRepository;
import com.aus.corsafe.service.bpmnservice.AuditRequestService;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auditRequest")
@Slf4j
@ToString
@CrossOrigin(origins = "http://localhost:3000/**", allowCredentials = "true")
public class AuditRequestCamundaController {

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProcessDetailsRepository processDetailsRepo;
    @Autowired
    AuditRequestService auditRequestService;

    // for complete the task with using zeebe client
    @PostMapping("/completeTask")
    public Object completeTask(@RequestBody CompleteTaskModel completeTask) {
        return auditRequestService.completeTaskWithZeebe(completeTask);
    }

    //complete the task with rest api
    @PostMapping("/completeTask1")
    public Object completeTask1(@RequestBody CompleteTaskDto cm) {

        return auditRequestService.completeTask(cm);
    }

    //assign the task
    @PostMapping("/assignTask")
    public Object assignTask(@RequestBody AssignTask assignTask) {
        return auditRequestService.getAssignTask(assignTask);
    }

    //search the task
    @GetMapping("/searchTask")
    public Object searchTask(@RequestBody SearchTask searchTask) {
        log.info("search task controller entered");

        return auditRequestService.getSearchTask(searchTask);
    }


    //to start camunda along with checking conditions
    @PostMapping("/startTask")
    public ResponseEntity<Object> startCamunda(@RequestBody StartCamundadto dto) {
        log.info("Entered startCamunda controller for user ID: {}", dto.getUserId());

        List<Order> orders = orderRepo.findByUserId(dto.getUserId());

        if (orders.isEmpty()) {
            throw new UserNotFoundExceptionCls("No orders found for user ID: " + dto.getUserId());
        }

        boolean processStartedForAny = false;

        for (Order order : orders) {
            Integer orderId = order.getOrderId();

            Optional<ProcessDetails> detailsOpt = processDetailsRepo.findAllByOrderId(orderId);

            if (detailsOpt.isPresent()) {
                log.info("Order ID {} is already in processDetails. Skipping service call.", orderId);
                continue; // Skip this order
            }
            if ("paid".equalsIgnoreCase(order.getOrderStatus())) {
                dto.setOrderId(orderId);
                log.info("Order ID {} is eligible. Starting Camunda process.", orderId);

                Object response = auditRequestService.startCamunda(dto);
                log.info("Camunda process started for order ID: {}", orderId);
                processStartedForAny = true;
            } else {
                log.info("Order ID {} is not eligible (status is not 'paid'). Skipping.", orderId);
            }
        }

        if (processStartedForAny) {
            return ResponseEntity.ok("Camunda processes started for eligible orders.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No eligible orders found to start processes.");
        }
    }


}




