package com.conexao.api.wiremock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebtsDTO {

    private BigDecimal amount;
    private String code;

    @JsonProperty("sent_at")
    private String sentAt;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("expires_at")
    private String expiresAt;

    @JsonProperty("client_code")
    private String clientCode;

    @JsonProperty("reference_code")
    private String referenceCode;

}
