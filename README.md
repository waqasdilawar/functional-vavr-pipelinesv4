# Functional Pipelines v4

This project demonstrates the use of functional programming paradigms in Java to process transactions. It includes two main approaches: one using Vavr, a functional library for Java, and another using standard Java functional interfaces.

## Project Structure

The project is organized into the following packages:

- `org.devguru.functionalpipelinesv4.controller`: Contains the controllers for handling HTTP requests.
- `org.devguru.functionalpipelinesv4.vavr`: Contains the Vavr-based transaction processing logic.
- `org.devguru.functionalpipelinesv4.standard`: Contains the standard Java-based transaction processing logic.

## Controllers

### TransactionController

Handles HTTP requests for transaction processing.

#### Endpoints

- `GET /transactions`: Processes transactions using the Vavr library.
- `GET /transactions/standard`: Processes transactions using standard Java functional interfaces.

#### Methods

```java
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
        processor.executeTransactions((io.vavr.collection.List<VavrTransaction>) transactions);
        return ResponseEntity.ok("Vavr! Hello World!");
    } catch (Exception e) {
        logger.warning("Error processing transactions: " + e.getMessage());
        return ResponseEntity.internalServerError().body("Error processing transactions");
    }
}