package org.bank.hcl.service;

import org.bank.hcl.dto.BankNameResponseDto;

public interface BankingService {


    public BankNameResponseDto fetchBankName(String iban);
}
