package com.transactrules.core.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DynamoDBDocument
public class TransactionType {

    private String transactionTypeId;
    private String name;
    private boolean maximumPrecision;

    private List<TransactionRuleType> transactionRules;

    public TransactionType(String name,  boolean hasMaximumPrecission) {
        this.transactionTypeId = UUID.randomUUID().toString();
        this.name = name;
        this.maximumPrecision = hasMaximumPrecission;
        this.transactionRules = new ArrayList<>();
    }

    public TransactionType(){

    }

    @DynamoDBAttribute
    public String getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(String transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public boolean getMaximumPrecision() {
        return maximumPrecision;
    }

    public void setMaximumPrecision(boolean maximumPrecision) {
        this.maximumPrecision = maximumPrecision;
    }

    @DynamoDBAttribute
    public List<TransactionRuleType> getTransactionRules() {

        return transactionRules;
    }

    public void setTransactionRules(List<TransactionRuleType> transactionRules) {
        this.transactionRules = transactionRules;
    }

    public TransactionType addRule(PositionType positionType, TransactionOperation operation) {
        transactionRules.add(new TransactionRuleType(positionType, operation));

        return this;
    }
}
