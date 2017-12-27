package com.transactrules.core.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.transactrules.core.utility.LocalDateTimeConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@DynamoDBTable(tableName = "Transaction")
public class Transaction {

    private String accountNumber;
    private String transactionId;
    private String transactionTypeId;
    private BigDecimal amount;
    private LocalDate actionDate;
    private LocalDate valueDate;

    public Transaction() {

    }

    public Transaction(TransactionType transactionType, Account account, BigDecimal amount, LocalDate actionDate, LocalDate valueDate) {
        this.transactionId = UUID.randomUUID().toString();
        this.transactionTypeId = transactionType.getTransactionTypeId();
        this.accountNumber = account.getAccountNumber();
        this.amount = amount;
        this.actionDate = actionDate;
        this.valueDate = valueDate;
    }

    @DynamoDBHashKey(attributeName = "accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @DynamoDBAttribute
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @DynamoDBAttribute
    public String getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(String transactionTypeId) {
        this.transactionId = transactionTypeId;
    }

    @DynamoDBAttribute
    public BigDecimal getAmount() {
        return amount;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate){
        this.actionDate = actionDate;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate){
        this.valueDate = valueDate;
    }

}