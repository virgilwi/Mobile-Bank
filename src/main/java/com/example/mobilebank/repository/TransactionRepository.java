package com.example.mobilebank.repository;

import com.example.mobilebank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromWalletIdOrToWalletId(Long fromWalletId, Long toWalletId);
}
