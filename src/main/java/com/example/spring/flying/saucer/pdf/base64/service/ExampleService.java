package com.example.spring.flying.saucer.pdf.base64.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.example.spring.flying.saucer.pdf.base64.entity.ContractEntity;

@Component
public class ExampleService {
	private final String FILE_PATH = "templates";
	private final String FILE_NAME = "/TEMPLATE-CONTRACT_EXAMPLE.html";
	
	@Value("${image.logo}")
	private String logo;
	
	public String contractPDFGenerate(ContractEntity contract) throws IOException {
		String fileTemplate = FILE_PATH.concat(FILE_NAME);
		ClassPathResource cpr = new ClassPathResource(fileTemplate);
		String html = new String(FileCopyUtils.copyToByteArray(cpr.getInputStream()), StandardCharsets.UTF_8);	
		
		LocalDate today = LocalDate.now();
		
		html = html.replace("$IMAGE_ONE", logo);
		html = html.replace("$NUMBER_CONTRACT", contract.getContractNumber().toString());
		html = html.replace("$NAME", contract.getClientName());
		html = html.replace("$CITY", contract.getCity());
		html = html.replace("$MONTH", getFullMonth(today));
		html = html.replace("$DAY", String.valueOf(today.getDayOfMonth()));
		html = html.replace("$YEAR", String.valueOf(today.getYear()));
		html = html.replace("$DUE_DATE", dateFormat(contract.getDueDate()));
		html = html.replace("$OVERDUE_DAYS", contract.getOverdueDays().toString());
		html = html.replace("$DEBT_AMOUNT", decimalFormat(contract.getDebtAmount()));
		html = html.replace("$DISCOUNT", decimalFormat(contract.getDiscount()));
		html = html.replace("$INVOICE_AMOUNT", decimalFormat(contract.getInvoiceAmount()));
		
		return convertToBase64Pdf(html);
	}
	
	private String convertToBase64Pdf(String html) {
		ITextRenderer rend = new ITextRenderer();
		String contractBase64 = "";
		byte[] bytes;
		
		rend.setDocumentFromString(html);
		rend.layout();
		
		try (OutputStream out = new ByteArrayOutputStream()) {
			rend.createPDF(out);
			rend.finishPDF();
			
			bytes = ((ByteArrayOutputStream) out).toByteArray();
			contractBase64 = Base64.getEncoder().encodeToString(bytes);
			
			out.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return contractBase64;
	}
	
	private String getFullMonth(LocalDate date) {
		Locale local = new Locale("en","US");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM", local);
		
		return dateFormat.format(date);
	}
	
	private String dateFormat(LocalDate date) {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		return formater.format(date);
	}
	
	private String decimalFormat(BigDecimal value) {
		DecimalFormat df = new DecimalFormat("0.00");
		String formatValue = df.format(value);
		
		return formatValue.replace(".", ",");
	}
}
