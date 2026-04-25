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
    private User FavoriteAccount;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "FavoriteAccount [id=" + id + ", FavoriteAccount=" + FavoriteAccount + ", accountName=" + accountName
				+ ", iban=" + iban + ", bankMapping=" + bankMapping + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}

	public User getFavoriteAccount() {
		return FavoriteAccount;
	}

	public void setFavoriteAccount(User favoriteAccount) {
		FavoriteAccount = favoriteAccount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BankMapping getBankMapping() {
		return bankMapping;
	}

	public void setBankMapping(BankMapping bankMapping) {
		this.bankMapping = bankMapping;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

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