package com.example.mobilebank.controller;

import com.example.mobilebank.entity.Transaction;
import com.example.mobilebank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for managing transactions.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Get transactions by wallet id.
     * @param walletId the id of the wallet
     * @return list of transactions
     */
    @GetMapping("/wallet/{walletId}")
    public List<Transaction> getTransactionsByWalletId(@PathVariable Long walletId) {
        return transactionService.getTransactionsByWalletId(walletId);
    }

    /**
     * Process a transaction from one wallet to another.
     * @param fromWalletId the id of the sender wallet
     * @param toWalletId the id of the receiver wallet
     * @param amount the amount to be transferred
     */
    @PostMapping("/transfer")
    public void processTransaction(@RequestParam Long fromWalletId, @RequestParam Long toWalletId, @RequestParam BigDecimal amount) {
        transactionService.processTransaction(fromWalletId, toWalletId, amount);
    }
}