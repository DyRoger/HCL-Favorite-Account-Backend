package org.bank.hcl.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.hcl.dto.BankNameResponseDto;
import org.bank.hcl.model.AuditLog;
import org.bank.hcl.model.BankMapping;
import org.bank.hcl.repository.AuditLogRepository;
import org.bank.hcl.repository.BankMappingRepository;
import org.bank.hcl.service.BankingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankingServiceImpl implements BankingService {

    private final BankMappingRepository bankMappingRepository;
    private final AuditLogRepository auditLogRepository;

    @Override
    public BankNameResponseDto fetchBankName(String iban) {
        BankMapping bankMapping = bankMappingRepository
                .findByCode(iban.substring(4, 8))
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        String customerId = getCustomerId();
        log.info("Bank name fetched | customerId: {} | bankCode: {}", customerId, iban.substring(4, 8));

        auditLogRepository.save(AuditLog.builder()
                .customerId(customerId)
                .action("FETCH_BANK")
                .resource("BANK_MAPPING")
                .status("SUCCESS")
                .message("Fetched bank: " + bankMapping.getBankName())
                .createdAt(LocalDateTime.now())
                .build());

        return BankNameResponseDto.builder().bankName(bankMapping.getBankName()).build();
    }

    private String getCustomerId() {
        try {
            return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}
