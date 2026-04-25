package org.bank.hcl.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.bank.hcl.model.BankMapping;
import org.bank.hcl.repository.BankMappingRepository;
import org.bank.hcl.service.BankingService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankingServiceImpl implements BankingService {

    private final BankMappingRepository bankMappingRepository;

    @Override
    public String fetchBankName(String iban) {
        System.out.println(iban.substring(4,8));
       BankMapping bankMapping =
               bankMappingRepository.
                       findByCode(iban.substring(4,8))
                       .orElseThrow(()->new RuntimeException("Bank not found"));

       return bankMapping.getBankName();
    }
}
