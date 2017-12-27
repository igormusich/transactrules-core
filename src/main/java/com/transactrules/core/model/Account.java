package com.transactrules.core.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DynamoDBTable(tableName = "Account")
public class Account {
    private String accountNumber;
    private String accountTypeId;
    private List<Position> positions = new ArrayList<Position>();

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

    public Optional<Position> getPositionByPositionTypeId(String positionTypeId){
        return positions.stream().filter(p->p.getPositionTypeId().equalsIgnoreCase(positionTypeId)).findFirst();
    }

    public Position initializePosition(PositionType positionType) {
        Position position = new Position(positionType);
        positions.add(position);
        return position;
    }

}
