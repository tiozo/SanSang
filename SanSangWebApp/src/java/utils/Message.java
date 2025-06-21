/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author tiozo
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Message {
    public enum Type {
        SUCCESS("success", "fa-check-circle"),
        ERROR("error", "fa-times-circle"),
        WARNING("warning", "fa-exclamation-triangle"),
        INFO("info", "fa-info-circle");

        private final String cssClass;
        private final String icon;

        Type(String cssClass, String icon) {
            this.cssClass = cssClass;
            this.icon = icon;
        }

        public String getCssClass() { return cssClass; }
        public String getIcon() { return icon; }
    }

    private final Type type;
    private final String key;
    private final List<Object> params;
    private final String rawText;
    private String title;

    private Message(Builder builder) {
        this.type = builder.type;
        this.key = builder.key;
        this.params = builder.params;
        this.rawText = builder.rawText;
        this.title = builder.title;
    }

    // ---------- Builder Pattern ----------
    public static class Builder {
        private Type type;
        private String key;
        private List<Object> params = new ArrayList<>();
        private String rawText;
        private String title;

        public Builder(Type type) {
            this.type = type;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withKey(String key, Object... params) {
            this.key = key;
            this.params = Arrays.asList(params); // Thay thế List.of() bằng Arrays.asList()
            return this;
        }

        public Builder withText(String text) {
            this.rawText = text;
            return this;
        }

        public Message build() {
            if (key == null && rawText == null) {
                throw new IllegalStateException("Message must have either key or text");
            }
            return new Message(this);
        }
    }

    // ---------- Factory Methods ----------
    public static Builder success() { return new Builder(Type.SUCCESS); }
    public static Builder error() { return new Builder(Type.ERROR); }
    public static Builder warning() { return new Builder(Type.WARNING); }
    public static Builder info() { return new Builder(Type.INFO); }

    // ---------- Utility Methods ----------
    public String getFormattedText(Locale locale) {
        if (rawText != null) return rawText;
        
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        String pattern = bundle.getString(key);
        return String.format(pattern, params.toArray());
    }

    // ---------- Getters ----------
    public Type getType() { return type; }
    public String getTitle() { return title; }
    public String getKey() { return key; }
    public List<Object> getParams() { return params; }
    public String getRawText() { return rawText; }
}
