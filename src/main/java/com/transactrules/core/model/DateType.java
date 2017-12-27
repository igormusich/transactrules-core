package com.transactrules.core.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.util.UUID;

@DynamoDBDocument
public class DateType {

    private String dateTypeId;
    private String name;

    public DateType(){

    }

    public DateType(String name) {
        this.dateTypeId = UUID.randomUUID().toString();
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
    public String getDateTypeId() {
        return dateTypeId;
    }

    public void setDateTypeId(String dateTypeId) {
        this.dateTypeId = dateTypeId;
    }

}
