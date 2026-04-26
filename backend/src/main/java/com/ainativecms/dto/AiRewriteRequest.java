package com.ainativecms.dto;

public class AiRewriteRequest {
    private String text;
    private String style;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
