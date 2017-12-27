package com.transactrules.core.repository;

import com.transactrules.core.model.Transaction;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface TransactionRepository extends CrudRepository<Transaction, String> {
    //List<Transaction> findByAccountNumber(String accountNumber);
}
