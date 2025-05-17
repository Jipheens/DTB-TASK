package com.example.accountservice.Utils.service_caller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class ServiceCaller {
    @Value("${API_GATWAY_URL}")
    private String API_GATEWAY_URL;

    private final WebClient webClient;
    private ExecutorService executorService;
    @Value("${AUDIT_TRAIL_REQUEST}")
    private  String AUDIT_TRAIL_REQUEST;

    public ServiceCaller(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_GATEWAY_URL).build();
    }

    private void shutdownExecutorService() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
    @Value("${SUPPLIER_MANAGEMENT_URL}")
    private String SUPPLIER_MANAGEMENT_URL;

    public boolean isJSONValid(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException e) {
            try {
                new JSONArray(json);
            } catch (JSONException ne) {
                return false;
            }
        }
        return true;
    }

}
