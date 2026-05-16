package com.ai.translation.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.Map;


@Service
public class AIService {

    @Autowired
    private RestTemplate restTemplate;

    public String translate(String text, String targetLang) {

        try {
            String url = "https://api.mymemory.translated.net/get?q="
                    + URLEncoder.encode(text, "UTF-8")
                    + "&langpair=en|" + targetLang;

            ResponseEntity<Map> response =
                    restTemplate.getForEntity(url, Map.class);

            Map body = response.getBody();

            if (body == null || !body.containsKey("responseData")) {
                throw new RuntimeException("Invalid API response");
            }

            Map responseData = (Map) body.get("responseData");

            return responseData.get("translatedText").toString();

        } catch (Exception e) {
            e.printStackTrace();

            return text + " (translated to " + targetLang + ")";
        }
    }
}




