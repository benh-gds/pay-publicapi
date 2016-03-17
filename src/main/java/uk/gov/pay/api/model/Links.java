package uk.gov.pay.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="paymentLinks", description = "Resource links of a Payment")
public class Links {
    private Link self;
    
    @JsonProperty("next_url")
    private Link nextUrl;

    public void setSelf(String url) {
        this.self = Link.get(url);
    }

    public void setNextUrl(String url) {
        this.nextUrl = Link.get(url);
    }

    @ApiModelProperty(value = "self", dataType = "uk.gov.pay.api.model.Link")
    public Link getSelf() {
        return self;
    }

    @ApiModelProperty(value = "next_url", dataType = "uk.gov.pay.api.model.Link")
    public Link getNextUrl() {
        return nextUrl;
    }

    @Override
    public String toString() {
        return "Links{" +
                "self=" + self +
                ", nextUrl=" + nextUrl +
                '}';
    }
}
