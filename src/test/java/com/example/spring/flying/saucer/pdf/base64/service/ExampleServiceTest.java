package com.example.spring.flying.saucer.pdf.base64.service;

import com.example.spring.flying.saucer.pdf.base64.entity.ContractEntity;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExampleServiceTest {

    @InjectMocks
    ExampleService exampleService;

    ContractEntity contractEntity;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(exampleService, "logo", "LOGO_TEST");

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
    public void testcContractPDFGenerate() throws IOException {
        String response = exampleService.contractPDFGenerate(contractEntity);

        Assertions.assertThat(response).isNotNull();
    }
}
