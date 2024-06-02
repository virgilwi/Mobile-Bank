package com.example.mobilebank.repository;

import com.example.mobilebank.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Wallet entity.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
