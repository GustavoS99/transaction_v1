package com.emazon.transaction_v1.infrastructure.input.rest;

import com.emazon.transaction_v1.domain.exception.*;
import com.emazon.transaction_v1.domain.model.ItemUpdateQuantity;
import com.emazon.transaction_v1.domain.spi.IStockClientPort;
import com.emazon.transaction_v1.infrastructure.out.jpa.entity.SupplyEntity;
import com.emazon.transaction_v1.infrastructure.out.jpa.repository.ISupplyRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static com.emazon.transaction_v1.infrastructure.input.rest.util.PathDefinition.*;
import static com.emazon.transaction_v1.util.GlobalConstants.WAREHOUSE_WORKER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SupplyRestControllerTest {

    public static final String WORKER_USERNAME = "3";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ISupplyRepository supplyRepository;

    @MockBean
    private IStockClientPort stockClientPort;

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
    void when_addSupply_then_createdStatus() throws Exception {
        String addSupplyJson = """
                {
                    "itemId" : 1,
                    "quantity" : 30
                }
                """;

        when(supplyRepository.save(any(SupplyEntity.class))).thenReturn(new SupplyEntity());

        doNothing().when(stockClientPort).increaseQuantity(any(ItemUpdateQuantity.class));

        mockMvc.perform(post(SUPPLY.concat(ADD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addSupplyJson))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",",
            "-1,",
            "2,",
            "2,-2"
    }, nullValues = {"null"})
    @WithMockUser(username = WORKER_USERNAME, roles = WAREHOUSE_WORKER)
    void expect_badRequest_when_requestIsWrong(Long id, Long quantity) throws Exception {
        Map<String, Long> addSupplyJson = new HashMap<>();

        addSupplyJson.put("itemId", id);
        addSupplyJson.put("quantity", quantity);

        mockMvc.perform(post(SUPPLY.concat(ADD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(addSupplyJson).toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = WORKER_USERNAME, roles = WAREHOUSE_WORKER)
    void expect_badRequest_when_stockClientReturnsBadRequest() throws Exception {
        String addSupplyJson = """
                {
                    "itemId" : 1,
                    "quantity" : 30
                }
                """;

        when(supplyRepository.save(any(SupplyEntity.class))).thenReturn(new SupplyEntity());

        doThrow(StockClientBadRequestException.class).when(stockClientPort).increaseQuantity(any(ItemUpdateQuantity.class));

        mockMvc.perform(post(SUPPLY.concat(ADD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(addSupplyJson).toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = WORKER_USERNAME, roles = WAREHOUSE_WORKER)
    void expect_notFound_when_stockClientReturnsNotFound() throws Exception {
        String addSupplyJson = """
                {
                    "itemId" : 1,
                    "quantity" : 30
                }
                """;

        when(supplyRepository.save(any(SupplyEntity.class))).thenReturn(new SupplyEntity());

        doThrow(StockClientNotFoundException.class).when(stockClientPort).increaseQuantity(any(ItemUpdateQuantity.class));

        mockMvc.perform(post(SUPPLY.concat(ADD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(addSupplyJson).toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = WORKER_USERNAME, roles = WAREHOUSE_WORKER)
    void expect_unauthorized_when_stockClientReturnsUnauthorized() throws Exception {
        String addSupplyJson = """
                {
                    "itemId" : 1,
                    "quantity" : 30
                }
                """;

        when(supplyRepository.save(any(SupplyEntity.class))).thenReturn(new SupplyEntity());

        doThrow(StockClientUnauthorizedException.class).when(stockClientPort).increaseQuantity(any(ItemUpdateQuantity.class));

        mockMvc.perform(post(SUPPLY.concat(ADD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(addSupplyJson).toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = WORKER_USERNAME, roles = WAREHOUSE_WORKER)
    void expect_forbidden_when_stockClientReturnsForbidden() throws Exception {
        String addSupplyJson = """
                {
                    "itemId" : 1,
                    "quantity" : 30
                }
                """;

        when(supplyRepository.save(any(SupplyEntity.class))).thenReturn(new SupplyEntity());

        doThrow(StockClientForbiddenException.class).when(stockClientPort).increaseQuantity(any(ItemUpdateQuantity.class));

        mockMvc.perform(post(SUPPLY.concat(ADD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(addSupplyJson).toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = WORKER_USERNAME, roles = WAREHOUSE_WORKER)
    void expect_internalServerError_when_stockClientFails() throws Exception {
        String addSupplyJson = """
                {
                    "itemId" : 1,
                    "quantity" : 30
                }
                """;

        when(supplyRepository.save(any(SupplyEntity.class))).thenReturn(new SupplyEntity());

        doThrow(StockClientException.class).when(stockClientPort).increaseQuantity(any(ItemUpdateQuantity.class));

        mockMvc.perform(post(SUPPLY.concat(ADD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(addSupplyJson).toString()))
                .andExpect(status().isInternalServerError());
    }
}