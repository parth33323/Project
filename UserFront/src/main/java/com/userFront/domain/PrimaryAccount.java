package com.userFront.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PrimaryAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int accountNumber;
	//to avoid bin issues related to double, use bigdecimal
	//syso(1.03 - 0.42);
	//prints out 0.610000000000001;
	//but need to use add() and mult() methods
	private BigDecimal accountBalance;
	
	//within the primaryTransactionList, there exists primaryAccount type; cascadeType.All will propagate actions in this list to the primaryAccount object which is the related classes; fetch.lazy: when loading primary account, the corresponding primaryTransaction will not be loaded
	@OneToMany(mappedBy = "primaryAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore // primaryTransactionList and primaryAccount refer to each; which will cause infinite loop when creating json obj if jsonIgnore not used so when creating json of primaryAccount, ignore PrimaryTransactionList 
	private List<PrimaryTransaction> primaryTransactionList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public List<PrimaryTransaction> getPrimaryTransactionList() {
		return primaryTransactionList;
	}

	public void setPrimaryTransactionList(List<PrimaryTransaction> primaryTransactionList) {
		this.primaryTransactionList = primaryTransactionList;
	}
	
	

}
