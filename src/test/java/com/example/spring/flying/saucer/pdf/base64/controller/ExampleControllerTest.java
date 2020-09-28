package com.example.spring.flying.saucer.pdf.base64.controller;

import com.example.spring.flying.saucer.pdf.base64.entity.ContractEntity;
import com.example.spring.flying.saucer.pdf.base64.service.ExampleService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExampleControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    ExampleService exampleService;

    ContractEntity contractEntity;

    @Before
    public void setup() {
        contractEntity = new ContractEntity();
        contractEntity.setCity("São Paulo");
        contractEntity.setClientName("Erison");
        contractEntity.setContractNumber(123);
        contractEntity.setDebtAmount(new BigDecimal(1000.00));
        contractEntity.setDiscount(new BigDecimal(0.00));
        contractEntity.setDueDate(LocalDate.now().plusDays(3));
        contractEntity.setInvoiceAmount(new BigDecimal(1000.00));
        contractEntity.setOverdueDays(100L);
    }

    @Test
    public void testContract() throws IOException {
        Mockito
                .when(exampleService.contractPDFGenerate(Mockito.any(ContractEntity.class)))
                .thenReturn("BASE64");

        String response = restTemplate.postForObject("/test-contract", contractEntity, String.class);
        Assertions.assertThat(response).isEqualTo("BASE64");
    }

    @Test
    public void testContractException() throws IOException {
        Mockito
                .doThrow(new IOException("TEST"))
                .when(exampleService)
                .contractPDFGenerate(Mockito.any(ContractEntity.class));

        String response = restTemplate.postForObject("/test-contract", contractEntity, String.class);
        Assertions.assertThat(response).isEqualTo("TEST");
    }
}
