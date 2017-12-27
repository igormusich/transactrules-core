package com.transactrules.core;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.transactrules.core.model.*;
import com.transactrules.core.repository.AccountRepository;
import com.transactrules.core.repository.AccountTypeRepository;
import com.transactrules.core.repository.TransactionRepository;
import com.transactrules.core.service.AccountValuationService;
import com.transactrules.core.utility.DynamoDBUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test2" })
public class AccountTypeRepositoryIntegrationTest {
    private DynamoDBMapper dynamoDBMapper;
    public static boolean dbInit = false;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountValuationService accountValuationService;

   @Before
    public void setup() throws Exception {

       if(dbInit)
       {
           return;
       }

        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        String[] tableNames = {"AccountType","Account","Transaction"};

       List<String> listTablesResult = amazonDynamoDB.listTables().getTableNames();


       for (String tableName: tableNames) {
           if(listTablesResult.stream().anyMatch(t-> t.equalsIgnoreCase(tableName))){
               amazonDynamoDB.deleteTable(tableName);
           }
       }


       DynamoDBUtility.CreateTable(AccountType.class, dynamoDBMapper, amazonDynamoDB);
       DynamoDBUtility.CreateTable(Transaction.class, dynamoDBMapper, amazonDynamoDB);
       DynamoDBUtility.CreateTable(Account.class,dynamoDBMapper, amazonDynamoDB);

       dbInit = true;
    }

    @Test
    public void test_save_LoanGivenAccountType() {
        AccountType loanGivien = TestUtility.CreateLoanGivenAccountType();
        accountTypeRepository.save(loanGivien);

        AccountType result = accountTypeRepository.findOne(loanGivien.getId());

        assertTrue("Contains item with expected name",
                result.getName().equalsIgnoreCase("LoanGiven"));

        assertTrue("Not empty", result.getPositionTypes().size()>0);
        assertTrue("Contains position Type with expected name",
                result.getPositionTypes().stream().anyMatch(pt ->pt.getName().equalsIgnoreCase("Principal")));
    }

    @Test
    public void test_save_SavingsAccount_and_Transactions(){

        AccountType savingsAccountType = TestUtility.createSavingsAccountType();
        accountTypeRepository.save(savingsAccountType);

        String accountNumber = "ACC-002-001-00000001";

        accountRepository.save(new Account(savingsAccountType,accountNumber));

        Account account = accountRepository.findOne(accountNumber);

        assertTrue("Contains account with expected account number", account.getAccountNumber().equalsIgnoreCase(accountNumber));

        Optional<TransactionType> depositTransactionType = savingsAccountType.getTransactionTypeByName("Deposit");
        Optional<PositionType> currentPositionType = savingsAccountType.getPositionTypeByName("Current");

        assertTrue("Has Deposit transaction type", depositTransactionType.isPresent());
        assertTrue("Has Current position Type", currentPositionType.isPresent());

        accountValuationService.initialize(account);

        Transaction transaction = accountValuationService.createTransaction(depositTransactionType.get(), BigDecimal.valueOf(100));

        transactionRepository.save(transaction);

        accountRepository.save(account);


        Optional<Position> currentPosition = account.getPositionByPositionTypeId(currentPositionType.get().getPositionTypeId());

        assertThat(currentPosition.get().getAmount(), is(BigDecimal.valueOf(100)));
    }

}


