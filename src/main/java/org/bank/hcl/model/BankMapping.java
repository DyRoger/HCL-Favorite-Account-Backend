package org.bank.hcl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bank_mapping")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankMapping {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}