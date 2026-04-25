package org.bank.hcl.controller;

import lombok.RequiredArgsConstructor;
import org.bank.hcl.dto.AddFavoriteAccountDto;
import org.bank.hcl.dto.FavoriteAccountResponseDTO;
import org.bank.hcl.service.FavoriteAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FavoriteAccountController {

    private  final FavoriteAccountService account;

    @GetMapping("/customers/{customerId}/favorite-accounts")
    public ResponseEntity<List<FavoriteAccountResponseDTO>>
    fetchFavouriteAccount(@PathVariable String customerId){
    return new ResponseEntity<>(account.fetchAllFavoriteAccount(customerId), HttpStatus.OK);
    }

    @PostMapping("/customers/{customerId}/favorite-accounts")
    public ResponseEntity<List<FavoriteAccountResponseDTO>>
    addFavoriteAccount(@PathVariable String customerId,
                       @RequestBody AddFavoriteAccountDto addFavoriteAccount){
        return new ResponseEntity<>(account.fetchAllFavoriteAccount(customerId), HttpStatus.OK);
    }
}
