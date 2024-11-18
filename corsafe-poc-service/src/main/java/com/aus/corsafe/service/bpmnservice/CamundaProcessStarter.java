package com.aus.corsafe.service.bpmnservice;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import java.util.HashMap;
import java.util.Map;

public class CamundaProcessStarter {

    private static final String PROCESS_ID = "Audit";  // Replace with the actual BPMN process ID

    public static void main(String[] args) {
        try (ZeebeClient client = ZeebeClient.newClientBuilder()
                .gatewayAddress("localhost:26500")  // Update the gateway address as needed
                .usePlaintext()
                .build()) {

            // Start process for user "abc"
            startProcessInstance(client, "abc");

            // Start process for user "xyz"
            startProcessInstance(client, "xyz");
        }
    }

    public static void startProcessInstance(ZeebeClient client, String startedBy) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("startedBy", startedBy);  // Set the user who started the process

        ProcessInstanceEvent event = client.newCreateInstanceCommand()
                .bpmnProcessId(PROCESS_ID)
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        System.out.println("Started process instance for user '" + startedBy +
                "' with key: " + event.getProcessInstanceKey());
    }
}
