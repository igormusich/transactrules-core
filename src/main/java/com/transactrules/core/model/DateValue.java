package com.transactrules.core.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.math.BigDecimal;
import java.time.LocalDate;

@DynamoDBDocument
public class DateValue {

    private LocalDate date;
    private String name;
    private String dateTypeId;

    DateValue() {

    }

    public DateValue(DateType dateType, LocalDate date) {
        this.dateTypeId = dateType.getDateTypeId();
        this.name = dateType.getName();
        this.date = date;
    }

    @DynamoDBAttribute
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    @DynamoDBAttribute
    public String getDateTypeId() {
        return dateTypeId;
    }

    public void setDateTypeId(String dateTypeId) {
        this.dateTypeId = dateTypeId;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isDateType(DateType dateType) {
        return dateTypeId.equalsIgnoreCase(dateType.getDateTypeId());
    }
}
