package com.emazon.transaction_v1.infrastructure.input.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.emazon.transaction_v1.infrastructure.input.rest.util.PathDefinition.SUPPLY;
import static com.emazon.transaction_v1.infrastructure.input.rest.util.PathDefinition.TRANSACTIONS;
import static com.emazon.transaction_v1.util.GlobalConstants.WAREHOUSE_WORKER;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TransactionRestControllerTest {

    private static final String WORKER_USERNAME = "2";
    private static final String CUSTOMER = "CUSTOMER";
    private static final String CUSTOMER_USERNAME = "3";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = WORKER_USERNAME, roles = WAREHOUSE_WORKER)
    void when_addSupplyWithWarehouseWorkerCredentials_then_statusCreated() throws Exception {
        mockMvc.perform(post(TRANSACTIONS.concat(SUPPLY)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = CUSTOMER_USERNAME, roles = CUSTOMER)
    void expect_forbidden_when_addSupplyWithACredentialDistinctToWarehouseWorker() throws Exception {
        mockMvc.perform(post(TRANSACTIONS.concat(SUPPLY)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void expect_unauthorized_when_addSupplyWithoutAWarehouseWorkerCredential() throws Exception {
        mockMvc.perform(post(TRANSACTIONS.concat(SUPPLY)))
                .andExpect(status().isUnauthorized());
    }
}