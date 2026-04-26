package com.ainativecms.dto;

public class AiTextResult {
    private String text;

    public AiTextResult() {}

    public AiTextResult(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
