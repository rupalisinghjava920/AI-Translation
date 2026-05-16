package com.ai.translation.dto;


import com.ai.translation.unit.Constant;
import jakarta.validation.constraints.NotBlank;

public class TranslationRequest {

    @NotBlank(message = Constant.REQUIRED_FIELDS)
    private String text;

    @NotBlank(message = Constant.REQUIRED_FIELDS)
    private String targetLang;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }
}
