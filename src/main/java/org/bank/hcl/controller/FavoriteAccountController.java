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

    private final FavoriteAccountService favoriteAccountService;

    @GetMapping("/customers/{customerId}/favorite-accounts")
    public ResponseEntity<List<FavoriteAccountResponseDTO>>
    fetchFavouriteAccount(@PathVariable String customerId) {
        return new ResponseEntity<>(favoriteAccountService.fetchAllFavoriteAccount(customerId), HttpStatus.OK);
    }

    @PostMapping("/customers/{customerId}/favorite-accounts")
    public ResponseEntity<Void> addFavoriteAccount(
            @PathVariable String customerId,
            @RequestBody AddFavoriteAccountDto addFavoriteAccount) {
        favoriteAccountService.addFavoriteAccount(customerId, addFavoriteAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/customers/{customerId}/favorite-accounts/{iban}")
    public ResponseEntity<Object>
    deleteFavoriteAccount(@PathVariable String customerId,@PathVariable String iban){
        favoriteAccountService.deleteFavouriteAccount(customerId,iban);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
