package com.example.spring.flying.saucer.pdf.base64.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.flying.saucer.pdf.base64.entity.ContractEntity;
import com.example.spring.flying.saucer.pdf.base64.service.ExampleService;

@RestController
public class ExampleController {
	@Autowired
	ExampleService exampleService;
	
	@PostMapping(path = "/test-contract", produces = MediaType.APPLICATION_JSON_VALUE)
	public String test(@RequestBody ContractEntity contract) {
		
		try {
			return exampleService.contractPDFGenerate(contract);
		} catch (IOException e) {
			return e.getMessage();
		}
	}
}
