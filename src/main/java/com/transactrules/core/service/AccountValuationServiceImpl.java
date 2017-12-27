package com.transactrules.core.service;

import com.transactrules.core.model.*;
import com.transactrules.core.repository.AccountTypeRepository;
import com.transactrules.core.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountValuationServiceImpl implements AccountValuationService {

    private Account account;
    private AccountType accountType;
    private Map<String,Position> positionMap;
    private LocalDate actionDate;
    private LocalDate valueDate;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void initialize(Account account) {
        this.account = account;
        positionMap = new HashMap<>();
        accountType = accountTypeRepository.findOne(account.getAccountTypeId());
        actionDate = LocalDate.now();
        valueDate = LocalDate.now();

        initializePositions(account);
    }

    private void initializePositions(Account account) {
        for (PositionType positionType: accountType.getPositionTypes()) {

            Optional<Position> position = account.getPositionByPositionTypeId(positionType.getPositionTypeId());


            if(position.isPresent())
            {
                positionMap.put(positionType.getPositionTypeId(), position.get());
            }
            else{
                Position newPosition = account.initializePosition(positionType);

                positionMap.put(positionType.getPositionTypeId(), newPosition);
            }
        }
    }

    private void processTransaction(TransactionType transactionType, BigDecimal amount) {

        for (TransactionRuleType rule: transactionType.getTransactionRules()) {
            Position position = positionMap.get(rule.getPositionTypeId());

            position.applyOperation( rule.getTransactionOperation(), amount);
        }
    }

    @Override
    public Transaction createTransaction(TransactionType transactionType, BigDecimal amount) {

        Transaction transaction = new Transaction(transactionType,account, amount,actionDate, valueDate);

        processTransaction(transactionType, amount);

        return transaction;
    }
}
