package com.transactrules.core.repository;

import com.transactrules.core.model.AccountType;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface AccountTypeRepository extends CrudRepository<AccountType, String> {
    //List<AccountType> findById(String id);

}

