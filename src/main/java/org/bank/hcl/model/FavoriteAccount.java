package org.bank.hcl.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_account")
public class FavoriteAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK → bank_user.customer_id
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private User bankUser;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "iban", nullable = false, length = 20)
    private String iban;

    // FK → bank_mapping.code
    @ManyToOne
    @JoinColumn(name = "bank_code", referencedColumnName = "code", nullable = false)
    private BankMapping bankMapping;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}