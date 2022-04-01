package com.example.authservice.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.authservice.config.CorsConfig;
import com.example.authservice.config.SecurityConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = {CorsConfig.class, SecurityConfig.class}) // 시큐리티 컨텍스트를 등록해주어야 한다
class HttpApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 권한_체크_403_테스트_그리고_404_테스트() throws Exception {
        // 권한없음 (304)
        mockMvc.perform(get("/api/v1/user/"))
                .andDo(print())
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/v1/admin/"))
                .andDo(print())
                .andExpect(status().isForbidden());

        // Not Found (404)
        mockMvc.perform(get("/api/vv/vvvv"))
                .andExpect(status().isNotFound());
    }

}