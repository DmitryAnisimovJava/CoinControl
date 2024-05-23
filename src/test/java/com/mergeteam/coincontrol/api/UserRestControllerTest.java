package com.mergeteam.coincontrol.api;

import com.mergeteam.coincontrol.dto.wallet.WalletDto;
import com.mergeteam.coincontrol.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
class UserRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    UserRestController userRestController;


    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .alwaysDo(result -> {
                    // Print detailed logs for each request/response
                    System.out.println("Response Status: " + result.getResponse()
                            .getStatus());
                    System.out.println("Response Content: " + result.getResponse()
                            .getContentAsString());
                })
                .build();
    }

    @Test
    void getAllWalletsByUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id")
                .descending());
        WalletDto walletDto = WalletDto.builder()
                .build();
        Page<WalletDto> walletPage = new PageImpl<>(Collections.singletonList(walletDto));

        when(userService.getWalletsForUser(userId, pageable)).thenReturn(walletPage);

        mockMvc.perform(get("/api/v1/" + userId + "/wallets")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) content().json("{\"content\":[{}],\"pageable\":{\"sort\":{\"sorted\":true," +
                        "\"unsorted\":false,\"empty\":false},\"pageNumber\":0,\"pageSize\":10,\"offset\":0," +
                        "\"paged\":true,\"unpaged\":false},\"last\":true,\"totalPages\":1,\"totalElements\":1," +
                        "\"size\":10,\"number\":0,\"sort\":{\"sorted\":true,\"unsorted\":false,\"empty\":false}," +
                        "\"first\":true,\"numberOfElements\":1,\"empty\":false}"));
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void saveUser() {
    }
}