package com.example.mobilebank.controller;

import com.example.mobilebank.entity.User;
import com.example.mobilebank.entity.Wallet;
import com.example.mobilebank.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class WalletControllerTest {
    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = standaloneSetup(walletController).build();
    }

    @Test
    void testGetAllWallets() throws Exception{
        User user = new User(1L, "user1@example.com", "User One");
        Wallet wallet1 = new Wallet(1L, BigDecimal.valueOf(100.00), user);
        Wallet wallet2 = new Wallet(2L, BigDecimal.valueOf(200.00), user);

        when(walletService.getAllWallets()).thenReturn(Arrays.asList(wallet1, wallet2));

        mockMvc.perform(get("/api/wallets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(100.00))
                .andExpect(jsonPath("$[1].balance").value(200.00));
    }

    @Test
    void testGetWalletById() throws Exception{
        User user = new User(1L, "user@example.com", "User One");
        Wallet wallet = new Wallet(1L, BigDecimal.valueOf(100.00), user);

        when(walletService.getWalletById(1L)).thenReturn(Optional.of(wallet));

        mockMvc.perform(get("/api/wallets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    void testAddWallet() throws Exception{
        User user = new User(1L, "user@example.com", "User One");
        Wallet wallet = new Wallet(null, BigDecimal.valueOf(100.00), user);

        when(walletService.addWallet(any(Wallet.class))).thenReturn(wallet);

        mockMvc.perform(post("/api/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wallet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    void testUpdateWallet() throws Exception{
        User user = new User(1L, "user@example.com", "User One");
        Wallet wallet = new Wallet(null, BigDecimal.valueOf(150.00), user);

        when(walletService.updateWallet(eq(1L), any(Wallet.class))).thenReturn(wallet);

        mockMvc.perform(put("/api/wallets/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(wallet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(150.00));
    }

    @Test
    void testDeleteWallet() throws Exception{
        doNothing().when(walletService).deleteWallet(1L);

        mockMvc.perform(delete("/api/wallets/1")).andExpect(status().isOk());

        verify(walletService).deleteWallet(1L);
    }

    @Test
    void testGetWalletBalance() throws Exception{
        User user = new User(1L, "user@example.com", "User One");
        Wallet wallet = new Wallet(1L, BigDecimal.valueOf(100.00), user);

        when(walletService.getWalletById(1L)).thenReturn(Optional.of(wallet));

        mockMvc.perform(get("/api/wallets/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(100.00));
    }

}
