package com.transactrules.core.service;

import com.transactrules.core.model.Account;
import com.transactrules.core.model.Transaction;
import com.transactrules.core.model.TransactionType;

import java.math.BigDecimal;

public interface AccountValuationService {

    void initialize(Account account);

    Transaction createTransaction(TransactionType transactionType, BigDecimal amount);

}