package com.lingyun.auth.service;

import org.springframework.http.ResponseEntity;

public interface ApiManagerService {


    ResponseEntity<String> checkApiKey(String uuid, String apikey);
}
