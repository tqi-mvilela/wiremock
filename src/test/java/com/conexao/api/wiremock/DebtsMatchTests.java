package com.conexao.api.wiremock;

import com.conexao.api.wiremock.dto.DebtsDTO;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DebtsMatchTests {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${debts.root.url}")
	private String rootUrl;

	private static final String DEBTS_PATH = "/debts";

	@Test
	public void a_test_debt_create() {
		String url = rootUrl.concat(DEBTS_PATH);
		String amountValue = "150.00";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HashMap<String, String> requestBody = new HashMap<>();
		requestBody.put("amount", amountValue);
		requestBody.put("client_code", "ABC-1234567890");

		HttpEntity<HashMap> request = new HttpEntity<>(requestBody, headers);
		ResponseEntity<DebtsDTO> result = restTemplate.postForEntity(url, request, DebtsDTO.class);

		Assert.assertEquals("Status code different 200", HttpStatus.OK, result.getStatusCode());

		LocalDate createDate = LocalDate.parse(result.getBody().getCreatedAt());
		LocalDate expiredDate = LocalDate.parse(result.getBody().getExpiresAt());

		Assert.assertTrue("Expire date is not greater than Created Date", expiredDate.isAfter(createDate));
		Assert.assertTrue("Amount value has OK", result.getBody().getAmount().compareTo(new BigDecimal(amountValue)) == 0);
	}

}
