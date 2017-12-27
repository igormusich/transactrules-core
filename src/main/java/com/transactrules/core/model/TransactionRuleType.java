package com.transactrules.core.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class TransactionRuleType {
    private String positionTypeId;
    private int transactionOperation;

    public TransactionRuleType(){

    }

    public TransactionRuleType(PositionType positionType, TransactionOperation operation){
        this.positionTypeId= positionType.getPositionTypeId();
        this.transactionOperation = operation.value();
    }

    @DynamoDBAttribute
    public String getPositionTypeId() {
        return positionTypeId;
    }

    public void setPositionTypeId(String positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    @DynamoDBAttribute
    public int getTransactionOperation() {
        return transactionOperation;
    }

    public void setTransactionOperation(int transactionOperation) {
        this.transactionOperation = transactionOperation;
    }




}
