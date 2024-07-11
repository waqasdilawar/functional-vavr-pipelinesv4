package org.devguru.functionalpipelinesv4.controller;

import org.devguru.functionalpipelinesv4.standard.Transaction;
import org.devguru.functionalpipelinesv4.standard.TransactionProcessor;
import org.devguru.functionalpipelinesv4.standard.TransactionResult;
import org.devguru.functionalpipelinesv4.standard.TransactionStatus;
import org.devguru.functionalpipelinesv4.vavr.VavrTransaction;
import org.devguru.functionalpipelinesv4.vavr.VavrTransactionProcessor;
import org.devguru.functionalpipelinesv4.vavr.VavrTransactionStatus;
import org.springframework.http.ResponseEntity;
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
  private static final Logger logger = Logger.getLogger(TransactionController.class.getName());

  /**
   * Handles GET requests for transactions.
   *
   * This method initializes a list of VavrTransaction objects and processes them using the VavrTransactionProcessor.
   * If the transactions are processed successfully, it returns a response with a success message.
   * If an error occurs during processing, it logs the error and returns an internal server error response.
   *
   * @return ResponseEntity<String> - A response entity containing either a success message or an error message.
   */
  @GetMapping
  public ResponseEntity<String> transaction() {
    try {
      VavrTransactionProcessor processor = new VavrTransactionProcessor();
      List<VavrTransaction> transactions = List.of(
              new VavrTransaction("1", new BigDecimal("100"), "A", "B", LocalDateTime.now(), VavrTransactionStatus.INITIATED),
              new VavrTransaction("2", new BigDecimal("200"), "C", "D", LocalDateTime.now(), VavrTransactionStatus.INITIATED)
      );
      processor.executeTransactions((io.vavr.collection.List.ofAll(transactions)));
      return ResponseEntity.ok("Vavr! Hello World!");
    } catch (Exception e) {
      logger.warning("Error processing transactions: " + e.getMessage());
      return ResponseEntity.internalServerError().body("Error processing transactions");
    }
  }

  /**
   * Handles GET requests for standard transactions.
   *
   * This method calls the standardJava method to process standard transactions.
   * If the transactions are processed successfully, it returns a response with a success message.
   * If an error occurs during processing, it logs the error and returns an internal server error response.
   *
   * @return ResponseEntity<String> - A response entity containing either a success message or an error message.
   */
  @GetMapping("/standard")
  public ResponseEntity<String> standardTransaction() {
    try {
      standardJava();
      return ResponseEntity.ok("Standard! Hello World!");
    } catch (Exception e) {
      logger.warning("Error processing standard transactions: " + e.getMessage());
      return ResponseEntity.internalServerError().body("Error processing standard transactions");
    }
  }

  private void standardJava() {
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
