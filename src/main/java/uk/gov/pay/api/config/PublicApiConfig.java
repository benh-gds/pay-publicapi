package uk.gov.pay.api.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PublicApiConfig extends Configuration {
    @NotNull
    private String connectorUrl;
    @NotNull
    private String publicAuthUrl;

    @Valid
    @NotNull
    @JsonProperty("jerseyClientConfig")
    private RestClientConfig restClientConfig;

    public RestClientConfig getRestClientConfig() {
        return restClientConfig;
    }

    public String getConnectorUrl() {
        return connectorUrl;
    }

    public String getPublicAuthUrl() {
        return publicAuthUrl;
    }
}
