package com.example.spring.flying.saucer.pdf.base64.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractEntity {
	private Integer contractNumber;
	private String clientName;
	private String city;
	private LocalDate dueDate;
	private Long overdueDays;
	private BigDecimal debtAmount;
	private BigDecimal discount;
	private BigDecimal invoiceAmount;
}
