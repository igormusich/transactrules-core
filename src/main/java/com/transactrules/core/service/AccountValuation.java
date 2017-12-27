package com.transactrules.core.service;

import com.transactrules.core.model.*;
import com.transactrules.core.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AccountValuation
{
    protected Account account;
    protected AccountType accountType;
    protected LocalDate actionDate;
    protected LocalDate valueDate;
    protected List<Transaction> transactions = new ArrayList<>();

    /*
    public List<Transaction> transactions() {

        return transactionRepository.findByAccountNumber(account.getAccountNumber());
    }

    public Map<Long,Position> positions() {

        return account.positions();
    }
*/

    public AccountValuation(Account account, AccountType accountType) {
        this.account = account;
        this.accountType = accountType;
        actionDate = LocalDate.now();
        valueDate = LocalDate.now();
        account.initialize(accountType);
    };

    public abstract void processTransaction(Transaction transaction);

    public abstract void startOfDay();
    public abstract void endOfOfDay();

    public abstract void onDataChanged();

    public abstract void calculateInstaments();

    public Transaction createTransaction(TransactionType transactionType, BigDecimal amount) {

        Transaction transaction = new Transaction(transactionType,account, amount, actionDate, valueDate);

        processTransaction(transaction);

        transactions.add(transaction);

        return transaction;
    }

    public void forecast(LocalDate futureDate)
    {
        LocalDate originalValueDate = valueDate;

        LocalDate iterator = originalValueDate;

        if (!account.getActive())
        {
            startOfDay();
        }

        while (valueDate.isBefore(futureDate) || valueDate.isEqual(futureDate) )
        {
            endOfOfDay();

            valueDate = valueDate.plusDays(1);

            startOfDay();
        }

        valueDate = originalValueDate;
    }

    public Optional<LocalDate> StartDate()  {
        Optional<DateType> dateType = accountType.getDateTypeByName("StartDate");

        if(!dateType.isPresent()) {
            return Optional.empty();
        }

        Optional<DateValue> dateValue=  account.getDateByDateType(dateType.get());

        if(!dateValue.isPresent()){
            return Optional.empty();
        }

        return Optional.of(dateValue.get().getDate());
    }

    public LocalDate ValueDate(){
        return valueDate;
    }

    /*public void SetFutureInstalmentValue(string instalmentType, ScheduledTransactionTiming timing, decimal value)
    {
        foreach (var instalment in Account.GetInstalments(instalmentType))
        {
            if (!instalment.HasFixedValue)
            {
                if ( (timing == ScheduledTransactionTiming.StartOfDay && instalment.ValueDate > SessionState.Current.ValueDate)
                        || (timing == ScheduledTransactionTiming.EndOfDay && instalment.ValueDate >= SessionState.Current.ValueDate))
                {
                    instalment.Amount = value;
                }
            }
        }
    }*/
}
