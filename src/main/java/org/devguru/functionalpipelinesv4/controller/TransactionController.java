package org.devguru.functionalpipelinesv4.controller;

import org.devguru.functionalpipelinesv4.standard.Transaction;
import org.devguru.functionalpipelinesv4.standard.TransactionProcessor;
import org.devguru.functionalpipelinesv4.standard.TransactionResult;
import org.devguru.functionalpipelinesv4.standard.TransactionStatus;
import org.devguru.functionalpipelinesv4.vavr.VavrTransaction;
import org.devguru.functionalpipelinesv4.vavr.VavrTransactionProcessor;
import org.devguru.functionalpipelinesv4.vavr.VavrTransactionStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

  @GetMapping
  public String transaction() {
    VavrTransactionProcessor processor = new VavrTransactionProcessor();
    List<VavrTransaction> transactions = List.of(
            new VavrTransaction("1", new BigDecimal("100"), "A", "B", LocalDateTime.now(), VavrTransactionStatus.INITIATED),
            new VavrTransaction("2", new BigDecimal("200"), "C", "D", LocalDateTime.now(), VavrTransactionStatus.INITIATED)
    );
    processor.executeTransactions((io.vavr.collection.List<VavrTransaction>) transactions);
    return "Hello World!";
  }

  @GetMapping("/standard")
  public String standardTransaction() {
    standardJava();
    return "Hello World!";
  }

  private static final Logger logger = Logger.getLogger(TransactionController.class.getName());

  public void standardJava() {
    TransactionProcessor processor = new TransactionProcessor();

    // Process a single transaction
    Transaction singleTransaction = new Transaction("1", new BigDecimal("100"), "A", "B", LocalDateTime.now(), TransactionStatus.INITIATED);
    TransactionResult result = processor.processTransaction(singleTransaction);
    logResult(result);

    // Process multiple transactions
    List<Transaction> transactions = List.of(
            new Transaction("2", new BigDecimal("200"), "C", "D", LocalDateTime.now(), TransactionStatus.INITIATED),
            new Transaction("3", new BigDecimal("300"), "E", "F", LocalDateTime.now(), TransactionStatus.INITIATED),
            new Transaction("4", new BigDecimal("-50"), "G", "H", LocalDateTime.now(), TransactionStatus.INITIATED) // Invalid amount
    );

    processTransactions(processor, transactions);
  }

  private static void logResult(TransactionResult result) {
    if (result.isSuccess()) {
      logger.info("Transaction processed successfully: " + result.getMessage());
    } else {
      logger.warning("Transaction processing failed: " + result.getMessage());
    }
  }

  private static void processTransactions(TransactionProcessor processor, List<Transaction> transactions) {
    for (Transaction transaction : transactions) {
      TransactionResult result = processor.processTransaction(transaction);
      logResult(result);
    }
  }
}
