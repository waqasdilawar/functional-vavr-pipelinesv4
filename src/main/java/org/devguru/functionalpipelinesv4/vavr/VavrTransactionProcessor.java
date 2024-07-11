package org.devguru.functionalpipelinesv4.vavr;

import io.vavr.control.Try;
import io.vavr.collection.List;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class VavrTransactionProcessor {
  private static final Logger logger = Logger.getLogger(VavrTransactionProcessor.class.getName());

  public Try<VavrTransactionResult> processTransaction(VavrTransaction transaction) {
    return validate(transaction)
            .flatMap(this::enrichTransaction)
            .flatMap(this::processPayment)
            .flatMap(this::notifyParties)
            .flatMap(this::updateDatabase);
  }

  private Try<VavrTransactionResult> validate(VavrTransaction transaction) {
    return Try.of(() -> {
      if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Invalid amount");
      }
      transaction.setStatus(VavrTransactionStatus.VALIDATED);
      return new VavrTransactionResult(transaction, "", true);
    });
  }

  private Try<VavrTransactionResult> enrichTransaction(VavrTransactionResult result) {
    return Try.of(() -> {
      Thread.sleep(100); // Simulate enriching transaction
      return result;
    }).recover(e -> {
      logger.warning("Enrichment failed, but continuing pipeline");
      return result; // Continue pipeline even if enrichment fails
    });
  }

  private Try<VavrTransactionResult> processPayment(VavrTransactionResult result) {
    return Try.of(() -> {
      Thread.sleep(200); // Simulate payment processing
      result.getTransaction().setStatus(VavrTransactionStatus.PROCESSED);
      return result;
    });
  }

  private Try<VavrTransactionResult> notifyParties(VavrTransactionResult result) {
    return Try.run(() -> {
              Thread.sleep(50); // Simulate sending notifications
            }).map(__ -> result)
            .recover(e -> {
              logger.warning("Notification failed, but continuing pipeline");
              return result; // Continue pipeline even if notification fails
            });
  }

  private Try<VavrTransactionResult> updateDatabase(VavrTransactionResult result) {
    return Try.of(() -> {
      Thread.sleep(100); // Simulate database update
      result.getTransaction().setStatus(VavrTransactionStatus.COMPLETED);
      return new VavrTransactionResult(result.getTransaction(), "Transaction completed successfully", true);
    });
  }

  public void executeTransactions(List<VavrTransaction> transactions) {
    transactions.forEach(transaction ->
            processTransaction(transaction)
                    .onSuccess(result -> logger.info("Transaction processed: " + result.getMessage()))
                    .onFailure(error -> logger.severe("Transaction failed: " + error.getMessage()))
    );
  }
}
