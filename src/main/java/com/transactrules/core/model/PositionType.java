package com.transactrules.core.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.util.UUID;

@DynamoDBDocument
public class PositionType {

    private String positionTypeId;
    private String name;

    public PositionType(){

    }

    public PositionType(String name) {
        this.positionTypeId = UUID.randomUUID().toString();
        this.name = name;
    }

    @DynamoDBAttribute
    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public String getPositionTypeId() {
        return positionTypeId;
    }

    public void setPositionTypeId(String positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

}
