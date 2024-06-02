package com.example.mobilebank.controller;

import com.example.mobilebank.entity.Wallet;
import com.example.mobilebank.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing wallets.
 */
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Get all wallets.
     * @return list of wallets
     */
    @GetMapping
    public List<Wallet> getAllWallets() {
        return walletService.getAllWallets();
    }

    /**
     * Get a wallet by id.
     * @param id the id of the wallet
     * @return the wallet if found
     */
    @GetMapping("/{id}")
    public Optional<Wallet> getWalletById(@PathVariable Long id) {
        return walletService.getWalletById(id);
    }

    /**
     * Add a new wallet.
     * @param wallet the wallet to be added
     * @return the added wallet
     */
    @PostMapping
    public Wallet addWallet(@RequestBody Wallet wallet) {
        return walletService.addWallet(wallet);
    }

    /**
     * Update an existing wallet.
     * @param id the id of the wallet to be updated
     * @param wallet the wallet details to be updated
     * @return the updated wallet
     */
    @PutMapping("/{id}")
    public Wallet updateWallet(@PathVariable Long id, @RequestBody Wallet wallet) {
        return walletService.updateWallet(id, wallet);
    }

    /**
     * Delete a wallet.
     * @param id the id of the wallet to be deleted
     */
    @DeleteMapping("/{id}")
    public void deleteWallet(@PathVariable Long id) {
        walletService.deleteWallet(id);
    }

    /**
     * Get the balance of a wallet by id.
     * @param id the id of the wallet
     * @return the balance of the wallet
     */
    @GetMapping("/{id}/balance")
    public BigDecimal getWalletBalance(@PathVariable Long id) {
        return walletService.getWalletById(id)
                .map(Wallet::getBalance)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }
}
