package com.example.mobilebank.service;

import com.example.mobilebank.entity.Wallet;
import com.example.mobilebank.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing wallets.
 */
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Get all wallets.
     * @return list of wallets
     */
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    /**
     * Get wallet by id.
     * @param id the id of the wallet
     * @return the wallet if found, else Optional.empty()
     */
    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    }

    /**
     * Add a new wallet.
     * @param wallet the wallet to be added
     * @return the added wallet
     */
    public Wallet addWallet(Wallet wallet) {
        wallet.setBalance(BigDecimal.ZERO);  // initialize wallet with zero balance
        return walletRepository.save(wallet);
    }

    /**
     * Update an existing wallet.
     * @param id the id of the wallet to be updated
     * @param wallet the wallet details to be updated
     * @return the updated wallet
     */
    public Wallet updateWallet(Long id, Wallet wallet) {
        return walletRepository.findById(id).map(existingWallet -> {
            existingWallet.setBalance(wallet.getBalance());
            return walletRepository.save(existingWallet);
        }).orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    /**
     * Delete a wallet.
     * @param id the id of the wallet to be deleted
     */
    public void deleteWallet(Long id) {
        walletRepository.deleteById(id);
    }
}
