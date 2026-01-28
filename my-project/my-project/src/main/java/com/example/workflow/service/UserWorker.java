//package com.example.workflow.service;
//import com.example.workflow.repository.UserRepository;
//import io.camunda.zeebe.client.api.response.ActivatedJob;
//import io.camunda.zeebe.client.api.worker.JobClient;
//import io.camunda.zeebe.spring.client.annotation.JobWorker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import java.util.Collections;
//import java.util.Map;
//@Component
//public class UserWorker {
//    @Autowired
//    private UserRepository userRepository;
//    @JobWorker(type = "check-username-worker")
//    public Map<String, Object> handleCheckUsername(final JobClient client, final ActivatedJob job) {
//        Map<String, Object> variables = job.getVariablesAsMap();
//        String username = (String) variables.get("username");
//        boolean isExist = userRepository.existsByUsername(username);
//        return Collections.singletonMap("isExist", isExist);
//    }
//}