package com.transactrules.core.repository;

import com.transactrules.core.model.Account;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface AccountRepository extends CrudRepository<Account,String> {
}
