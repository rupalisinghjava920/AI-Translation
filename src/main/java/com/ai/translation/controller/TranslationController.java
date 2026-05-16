package com.ai.translation.controller;

import com.ai.translation.config.RateLimiter;
import com.ai.translation.dto.TranslationRequest;
import com.ai.translation.entity.Translation;
import com.ai.translation.repository.TranslationRepository;
import com.ai.translation.service.AIService;
import com.ai.translation.unit.Constant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class TranslationController {

//    @GetMapping("/translate")
//    public String hello() {
//        return "Hello Working";
//    }



    @Autowired
    private AIService aiService;

    @Autowired
    private TranslationRepository repo;

    @Autowired
    private RateLimiter limiter;

    @PostMapping("/translate")
    public ResponseEntity<?> translate(@Valid @RequestBody TranslationRequest req) {

        String text = req.getText();
        String target = req.getTargetLang();

        //  Validation
        if (text == null || target == null || text.isEmpty() || target.isEmpty()) {
            return ResponseEntity.badRequest().body(Constant.REQUIRED_FIELDS);
        }

        String source = "en";
        if (source.equalsIgnoreCase(target)) {
            return ResponseEntity.badRequest()
                    .body(Constant.SAME_LANGUAGE);
        }

        List<String> validLangs = List.of("en", "hi", "fr", "es", "de");

        if (!validLangs.contains(target)) {
            return ResponseEntity.badRequest()
                    .body(Constant.INVALID_LANGUAGE);
        }

        // Rate Limit
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        if (!limiter.allow(username)) {
            return ResponseEntity.status(429)
                    .body(Constant.TOO_MANY_REQUESTS);
        }

        try {
            String result = aiService.translate(text, target);

            Translation t = new Translation();
            t.setInputText(text);
            t.setTranslatedText(result);
            t.setSourceLang(source);
            t.setTargetLang(target);


            repo.save(t);

            return ResponseEntity.ok(Map.of(
                    "message", Constant.TRANSLATION_SUCCESS,"translatedText", result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Constant.TRANSLATION_FAILED);
        }
    }

    // History
    @GetMapping("/history")
    public ResponseEntity<?> history() {

        List<Translation> list = repo.findAll();

        if (list.isEmpty()) {
            return ResponseEntity.ok(Constant.HISTORY_EMPTY);
        }

        return ResponseEntity.ok(Map.of(
                "message", Constant.HISTORY_FETCH_SUCCESS,
                "data", list
        ));
    }


}
