package it.dipendentepubico.concorsiparenti.spring.websocket;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("concorsiparenti.websocket")
public class WebSocketProperties {
    private String applicationPrefix = "/app";
    private String userPrefix = "/user";
    private String allowedOrigins = "http://localhost:24401/";

    private String[] topicPrefixes = {"/topic", "/user/queue/specific-user"};

    private String endpoint = "/websocket";

    public String getApplicationPrefix() {
        return applicationPrefix;
    }

    public void setApplicationPrefix(String applicationPrefix) {
        this.applicationPrefix = applicationPrefix;
    }

    public String getUserPrefix() {
        return userPrefix;
    }

    public void setUserPrefix(String userPrefix) {
        this.userPrefix = userPrefix;
    }

    public String getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public String[] getTopicPrefixes() {
        return topicPrefixes;
    }

    public void setTopicPrefixes(String[] topicPrefixes) {
        this.topicPrefixes = topicPrefixes;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
