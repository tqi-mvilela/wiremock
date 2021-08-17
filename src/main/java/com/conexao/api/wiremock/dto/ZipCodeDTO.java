package com.conexao.api.wiremock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZipCodeDTO {

    @JsonProperty("cep")
    private String zipCode;

    @JsonProperty("logradouro")
    private String address;

    @JsonProperty("bairro")
    private String district;

    @JsonProperty("cidade")
    private String city;

    @JsonProperty("estado")
    private String uf;

}
