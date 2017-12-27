package com.transactrules.core.repository;

import com.transactrules.core.model.Transaction;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface TransactionRepository extends CrudRepository<Transaction, String> {
}
