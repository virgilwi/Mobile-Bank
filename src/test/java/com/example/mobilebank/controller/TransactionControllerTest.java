package com.example.mobilebank.controller;

import com.example.mobilebank.entity.Transaction;
import com.example.mobilebank.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


class TransactionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = standaloneSetup(transactionController).build();
    }

    @Test
    void testGetTransactionsByWalletId() throws Exception{
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(BigDecimal.valueOf(100.00));

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(BigDecimal.valueOf(200.00));

        when(transactionService.getTransactionsByWalletId(1L)).thenReturn(Arrays.asList(transaction1, transaction2));

        mockMvc.perform(get("/api/transactions/wallet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(100.00))
                .andExpect(jsonPath("$[1].amount").value(200.00));
    }

    @Test
    void testProcessTransaction() throws Exception{
        BigDecimal amount = new BigDecimal("50.00");
        doNothing().when(transactionService).processTransaction(eq(1L), eq(2L), eq(amount));

        mockMvc.perform(post("/api/transactions/transfer")
                        .param("fromWalletId", "1")
                        .param("toWalletId", "2")
                        .param("amount", "50.00")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        verify(transactionService).processTransaction(1L, 2L, amount);
    }
}
