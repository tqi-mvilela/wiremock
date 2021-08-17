package com.conexao.api.wiremock;

import com.conexao.api.wiremock.dto.ZipCodeDTO;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZipCodeMatchTests {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${zipcode.root.url}")
	private String rootUrl;

	private static final String ZIP_CODE_PATH = "/v1/cep/{zipCode}";

	@Test
	public void a_test_zip_code_match_path_param() {
		String url = rootUrl.concat(ZIP_CODE_PATH);
		String zipCodeParam = "01001000";
		ResponseEntity<ZipCodeDTO> result = this.restTemplate.getForEntity(url, ZipCodeDTO.class, zipCodeParam);
		Assert.assertEquals("Status code different 200", HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals("Zipcode not equals", zipCodeParam, result.getBody().getZipCode());
		Assert.assertEquals("UF not equals", "SP", result.getBody().getUf());
	}

	@Test
	public void b_test_zip_code_match_path_array_list() {
		String url = rootUrl.concat(ZIP_CODE_PATH);
		String zipCodeParam = "38140000";
		ResponseEntity<ZipCodeDTO> result = this.restTemplate.getForEntity(url, ZipCodeDTO.class, zipCodeParam);
		Assert.assertEquals("Status code different 200", HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals("Zipcode not equals", zipCodeParam, result.getBody().getZipCode());
		Assert.assertEquals("UF not equals", "MG", result.getBody().getUf());
	}

	@Test
	public void c_test_zip_code_not_found() {
		String url = rootUrl.concat(ZIP_CODE_PATH);
		String zipCodeParam = "00000000";
		try {
			this.restTemplate.getForEntity(url, String.class, zipCodeParam);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("ZipCode not found", HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

}
