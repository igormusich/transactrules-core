package com.transactrules.core.utility;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.transactrules.core.model.AccountType;

public class DynamoDBUtility {
    public static void CreateTable(Class<?> clazz, DynamoDBMapper dynamoDBMapper, AmazonDynamoDB amazonDynamoDB){
        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(clazz);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));


        amazonDynamoDB.createTable(tableRequest);
    }
}
