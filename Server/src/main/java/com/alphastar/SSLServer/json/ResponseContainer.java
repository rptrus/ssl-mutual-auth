
package com.alphastar.SSLServer.json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "response"
})
public class ResponseContainer implements Serializable
{

    @JsonProperty("response")
    @Valid
    private List<Response> response = null;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1419256679959105827L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseContainer() {
    }

    /**
     * 
     * @param response
     */
    public ResponseContainer(List<Response> response) {
        super();
        this.response = response;
    }

    @JsonProperty("response")
    public List<Response> getResponse() {
        return response;
    }

    @JsonProperty("response")
    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public ResponseContainer withResponse(List<Response> response) {
        this.response = response;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(response).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ResponseContainer) == false) {
            return false;
        }
        ResponseContainer rhs = ((ResponseContainer) other);
        return new EqualsBuilder().append(response, rhs.response).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
