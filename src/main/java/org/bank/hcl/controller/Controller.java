package org.bank.hcl.controller;

import org.bank.hcl.model.FavoriteAccount;
import org.bank.hcl.service.PayeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class Controller {
	private final PayeeService payeeService;

    // Constructor Injection
    public Controller(PayeeService payeeService) {
        this.payeeService = payeeService;
    }
    @PostMapping("/login")
    public String getToken(){
        return null;
    }
    
    @PostMapping("/customers/{customerId}/favorite-payees")
    public ResponseEntity<org.bank.hcl.model.ApiResponse<FavoriteAccount>> addPayee(
            @PathVariable("customerId") Long customerId,
            @Valid @RequestBody FavoriteAccount request) {

        FavoriteAccount result = payeeService.addPayee(customerId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(org.bank.hcl.model.ApiResponse.success(result, "Favorite payee added successfully"));
    }
    @PutMapping("/customers/{customerId}/favorite-payees/{payeeId}")
    public ResponseEntity<org.bank.hcl.model.ApiResponse<FavoriteAccount>> updatePayee(
            @PathVariable("customerId") Long customerId,
            @PathVariable("payeeId") Long payeeId,
            @Valid @RequestBody FavoriteAccount request) {

        FavoriteAccount result = payeeService.updatePayee(customerId, payeeId, request);

        return ResponseEntity.ok(
                org.bank.hcl.model.ApiResponse.success(result, "Favorite payee updated successfully"));
    }
}
