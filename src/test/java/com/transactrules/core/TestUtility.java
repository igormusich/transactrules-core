package com.transactrules.core;

import com.transactrules.core.model.*;

import java.util.UUID;

public class TestUtility {

    public static AccountType CreateLoanGivenAccountType() {
        AccountType loanGiven = new AccountType("LoanGiven");

        //loanGiven.setId(UUID.randomUUID().toString());

        PositionType conversionInterestPosition = loanGiven.addPositionType("ConversionInterest");
        PositionType earlyRedemptionFeePosition = loanGiven.addPositionType("EarlyRedemptionFee");
        PositionType interestAccruedPosition = loanGiven.addPositionType("InterestAccrued");
        PositionType interestCapitalizedPosition = loanGiven.addPositionType("InterestCapitalized");
        PositionType principalPosition = loanGiven.addPositionType("Principal");

        DateType startDate = loanGiven.addDateType( "StartDate" );
        DateType accrualStart = loanGiven.addDateType( "AccrualStart" );
        DateType endDate = loanGiven.addDateType( "EndDate" );

        ScheduleType accrualSchedule = loanGiven.addCalculatedScheduleType(
                "AccrualSchedule",
                ScheduleFrequency.Daily,
                ScheduleEndType.NoEnd,
                "this.StartDate()",
                "",
                "",
                "1"
        );

        ScheduleType interestSchedule =loanGiven.addUserInputScheduleType(
                "InterestSchedule",
                ScheduleFrequency.Monthly,
                ScheduleEndType.EndDate,
                null,
                null,
                null,
                "1");

        ScheduleType redemptionSchedule = loanGiven.addUserInputScheduleType(
                "RedemptionSchedule",
                ScheduleFrequency.Monthly,
                ScheduleEndType.EndDate,
                null,
                null,
                null,
                "1"
        );

        TransactionType interestAccrued=  loanGiven.addTransactionType("InterestAccrued", true)
                .addRule(interestAccruedPosition, TransactionOperation.Add);

        TransactionType interestCapitalized= loanGiven.addTransactionType("InterestCapitalized")
                .addRule(principalPosition,  TransactionOperation.Add )
                .addRule(interestAccruedPosition,  TransactionOperation.Subtract )
                .addRule(interestCapitalizedPosition, TransactionOperation.Add );


        loanGiven.addTransactionType("Redemption")
                .addRule(principalPosition, TransactionOperation.Subtract );

        TransactionType advanceTransactionType = loanGiven.addTransactionType("Advance")
                .addRule( principalPosition,  TransactionOperation.Add );

        loanGiven.addTransactionType("AdditionalAdvance")
                .addRule(principalPosition, TransactionOperation.Add);

        loanGiven.addTransactionType("ConversionInterest")
                .addRule(conversionInterestPosition, TransactionOperation.Add);

        loanGiven.addTransactionType("EarlyRedemptionFee")
                .addRule(earlyRedemptionFeePosition, TransactionOperation.Add);

        loanGiven.addTransactionType("FXResultInterest")
                .addRule(interestAccruedPosition, TransactionOperation.Add);

        loanGiven.addTransactionType("FXResultPrincipal")
                .addRule(principalPosition, TransactionOperation.Add);

        loanGiven.addTransactionType("InterestPayment")
                .addRule(interestAccruedPosition, TransactionOperation.Subtract);



        return loanGiven;
    }

    public static AccountType createSavingsAccountType()
    {
        AccountType accountType = new AccountType( "SavingsAccount");

        PositionType currentPosition = accountType.addPositionType( "Current");
        PositionType interestAccruedPosition = accountType.addPositionType( "InterestAccrued" );

        TransactionType depositTransaction = accountType.addTransactionType("Deposit", false);

        depositTransaction.addRule(currentPosition, TransactionOperation.Add);

        TransactionType withdrawalTransaction = accountType.addTransactionType("Withdrawal",false);

        withdrawalTransaction.addRule(currentPosition, TransactionOperation.Subtract);

        TransactionType interestAccruedTransaction = accountType.addTransactionType("InterestAccrued", true);

        interestAccruedTransaction.addRule(interestAccruedPosition, TransactionOperation.Add);

        TransactionType interestCapitalizedTransaction = accountType.addTransactionType("InterestCapitalized", false);

        interestCapitalizedTransaction.addRule(interestAccruedPosition, TransactionOperation.Subtract );
        interestCapitalizedTransaction.addRule(currentPosition, TransactionOperation.Add );

        return accountType;
    }

}
