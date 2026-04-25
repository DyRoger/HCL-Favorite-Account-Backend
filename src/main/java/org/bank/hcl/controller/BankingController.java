package org.bank.hcl.controller;

import lombok.RequiredArgsConstructor;
import org.bank.hcl.dto.BankNameResponseDto;
import org.bank.hcl.service.BankingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BankingController {

    private final BankingService bankingService;

    @GetMapping("/bank/{iban}")
    public ResponseEntity<Object> fetchBankName(@PathVariable String iban){
        if(iban.length() >=8 ) {
            return new ResponseEntity<>(bankingService.fetchBankName(iban), HttpStatus.OK);
        }
      return new ResponseEntity<>("No valid bank present ",HttpStatus.NOT_FOUND);
    }
}
