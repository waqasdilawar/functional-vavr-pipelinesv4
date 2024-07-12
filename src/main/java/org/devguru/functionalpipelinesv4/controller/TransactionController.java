package org.devguru.functionalpipelinesv4.controller;

import org.devguru.functionalpipelinesv4.standard.Transaction;
import org.devguru.functionalpipelinesv4.standard.TransactionProcessor;
import org.devguru.functionalpipelinesv4.standard.TransactionResult;
import org.devguru.functionalpipelinesv4.vavr.VavrTransaction;
import org.devguru.functionalpipelinesv4.vavr.VavrTransactionProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  @PostMapping("/vavr")
  public ResponseEntity<String> transaction(@RequestBody List<VavrTransaction> transactions) {
    try {
      new VavrTransactionProcessor().executeTransactions((io.vavr.collection.List.ofAll(transactions)));
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
  @PostMapping("/standard")
  public ResponseEntity<String> standardTransaction(@RequestBody List<Transaction> transactions) {
    try {
      var processor = new TransactionProcessor();
      transactions.stream()
              .map(processor::processTransaction)
              .peek(result -> {
                if (result.isSuccess()) {
                  logger.info("Transaction processed successfully: " + result.getMessage());
                } else {
                  logger.warning("Transaction processing failed: " + result.getMessage());
                }
              })
              .forEach(result -> {
              }); // No-op forEach to trigger the stream processing


      return ResponseEntity.ok("Standard! Hello World!");
    } catch (Exception e) {
      logger.warning("Error processing standard transactions: " + e.getMessage());
      return ResponseEntity.internalServerError().body("Error processing standard transactions");
    }
  }
}
