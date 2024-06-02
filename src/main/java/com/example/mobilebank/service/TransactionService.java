package com.example.mobilebank.service;

import com.example.mobilebank.entity.Transaction;
import com.example.mobilebank.entity.Wallet;
import com.example.mobilebank.repository.TransactionRepository;
import com.example.mobilebank.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing transactions.
 */
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    /**
     * Get transactions by wallet id.
     * @param walletId the id of the wallet
     * @return list of transactions
     */
    public List<Transaction> getTransactionsByWalletId(Long walletId) {
        return transactionRepository.findByFromWalletIdOrToWalletId(walletId, walletId);
    }

    /**
     * Process a transaction from one wallet to another.
     * @param fromWalletId the id of the sender wallet
     * @param toWalletId the id of the receiver wallet
     * @param amount the amount to be transferred
     */
    public void processTransaction(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findById(fromWalletId)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));
        Wallet toWallet = walletRepository.findById(toWalletId)
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        Transaction transaction = new Transaction();
        transaction.setFromWallet(fromWallet);
        transaction.setToWallet(toWallet);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}