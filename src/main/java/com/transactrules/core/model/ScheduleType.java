package com.transactrules.core.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBDocument
public class ScheduleType {

    private String name;
    private String scheduleFrequency;
    private String scheduleEndType;
    private String startDateExpression;
    private String endDateExpression;
    private String numberOfRepeatsExpression;
    private String intervalExpression;
    private Boolean isCalculated;

    public ScheduleType(){

    }

    public ScheduleType(
            String name,
            ScheduleFrequency frequency,
            ScheduleEndType endType,
            String startDateExpression,
            String endDateExpression,
            String numberOfRepeatsExpression,
            String intervalExpression,
            Boolean isCalculated) {
        this.name = name;
        this.scheduleFrequency =  frequency.desc();
        this.scheduleEndType = endType.desc();
        this.startDateExpression = startDateExpression;
        this.endDateExpression = endDateExpression;
        this.numberOfRepeatsExpression = numberOfRepeatsExpression;
        this.intervalExpression = intervalExpression;
        this.isCalculated = isCalculated;
    }

    @DynamoDBAttribute
    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute()
    public String getScheduleFrequency() {
        return scheduleFrequency;
    }

    public void setScheduleFrequency(String scheduleFrequency) {
        this.scheduleFrequency = scheduleFrequency;
    }

    @DynamoDBIgnore
    public ScheduleEndType getScheduleEndType() {
        if (scheduleEndType != null)
            return ScheduleEndType.valueOf(scheduleEndType);
        else
            return null;
    }

    public void setScheduleEndType(ScheduleEndType scheduleEndType) {
        this.scheduleEndType = scheduleEndType.desc();
    }

    @DynamoDBAttribute
    public String getStartDateExpression() {
        return startDateExpression;
    }

    public void setEndDateExpression(String endDateExpression) {
        this.endDateExpression = endDateExpression;
    }

    @DynamoDBAttribute
    public String getEndDateExpression() {
        return endDateExpression;
    }

    public void setStartDateExpression(String startDateExpression) {
        this.startDateExpression = startDateExpression;
    }

    @DynamoDBAttribute
    public String getNumberOfRepeatsExpression() {
        return numberOfRepeatsExpression;
    }

    public void setNumberOfRepeatsExpression(String numberOfRepeatsExpression) {
        this.numberOfRepeatsExpression = numberOfRepeatsExpression;
    }

    @DynamoDBAttribute
    public String getIntervalExpression() {
        return intervalExpression;
    }

    public void setIntervalExpression(String intervalExpression) {
        this.intervalExpression = intervalExpression;
    }

    @DynamoDBAttribute
    public Boolean getIsCalculated() {
        return isCalculated;
    }

    public void setIsCalculated(Boolean calculated) {
        isCalculated = calculated;
    }
}