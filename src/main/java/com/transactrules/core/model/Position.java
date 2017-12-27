package com.transactrules.core.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.math.BigDecimal;

@DynamoDBDocument
public class Position {

    private BigDecimal amount;
    private String name;
    private String positionTypeId;

    Position() {

    }

    public Position(PositionType positionType) {
        this.positionTypeId = positionType.getPositionTypeId();
        this.name = positionType.getName();
        this.amount = BigDecimal.ZERO;
    }

    @DynamoDBAttribute
    public BigDecimal getAmount() {
        return amount;
    }

    @DynamoDBAttribute
    public String getPositionTypeId() {
        return positionTypeId;
    }

    public void setPositionTypeId(String positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void applyOperation(int operation, BigDecimal value) {
        switch (operation) {
            case -1:
                amount = amount.subtract(value);
                break;
            case 1:
                amount = amount.add(value);
                break;
            default:
                break;
        }
    }

    public Boolean isPositionType(PositionType positionType){
        return positionTypeId.equalsIgnoreCase(positionType.getPositionTypeId());
    }

    public Boolean isPositionType(String positionTypeId){
        return positionTypeId.equalsIgnoreCase(positionTypeId);
    }
}
