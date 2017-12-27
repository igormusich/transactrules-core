package com.transactrules.core.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DynamoDBTable(tableName = "Account")
public class Account {
    private String accountNumber;
    private String accountTypeId;
    private Boolean isActive;
    private List<Position> positions = new ArrayList<>();
    private List<DateValue> dates = new ArrayList<>();

    public Account(){

    }

    public Account(AccountType accountType, String accountNumber){
        this.accountTypeId = accountType.getId();
        this.accountNumber = accountNumber;
    }

    @DynamoDBHashKey(attributeName = "accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @DynamoDBAttribute
    public String getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(String accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    @DynamoDBAttribute
    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @DynamoDBAttribute
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Optional<Position> getPositionByPositionTypeId(String positionTypeId){
        return positions.stream().filter(p->p.isPositionType(positionTypeId) ).findFirst();
    }

    public Optional<DateValue> getDateByDateType(DateType dateType){
        return dates.stream().filter(d->d.isDateType(dateType)).findFirst();
    }

    public void initialize(AccountType accountType){
        for(PositionType positionType: accountType.getPositionTypes()){
            if(positions.stream().noneMatch(p-> p.isPositionType(positionType))){
                initializePosition(positionType);
            }
        }

        for (DateType dateType: accountType.getDateTypes()) {
            if(dates.stream().noneMatch(d-> d.isDateType(dateType)) ){
                initializeDate(dateType);
                //dates.put(dateType.id(), new DateValue(this, LocalDate.MIN));
            }
        }
    }

    private DateValue initializeDate(DateType dateType) {
        DateValue dateValue = new DateValue(dateType, LocalDate.MIN);
        dates.add(dateValue);
        return dateValue;
    }

    public Position initializePosition(PositionType positionType) {
        Position position = new Position(positionType);
        positions.add(position);
        return position;
    }

}
