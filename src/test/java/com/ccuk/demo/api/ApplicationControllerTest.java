package com.ccuk.demo.api;

import com.ccuk.demo.entity.MaintenanceInstruction;
import com.ccuk.demo.entity.MaintenanceInstructionType;
import com.ccuk.demo.exception.ValidationException;
import com.ccuk.demo.feature.FeatureFlag;
import com.ccuk.demo.feature.FeatureFlagService;
import com.ccuk.demo.service.MaintenanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;

import static com.ccuk.demo.api.ApplicationController.FEATURE_NOT_ENABLED_FOR_USER;
import static com.ccuk.demo.api.ApplicationController.INSTRUCTION_RESOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MaintenanceService maintenanceService;

    @MockBean
    private FeatureFlagService featureFlagService;

    private Date futureCompletionDate;

    @Before
    public void setUp() throws Exception {
        Long nowMs = new Date().getTime();
        futureCompletionDate = new Date(nowMs + 600_000L);
    }

    @Test
    public void foo() {
        assertTrue(true);
    }

    private void assertInstructionResponseMatchesExpected(MaintenanceInstructionResponse expectedResponse, MaintenanceInstructionResponse actualResponse) {
        assertEquals(expectedResponse.getId(), expectedResponse.getId());
        assertEquals(expectedResponse.getAddress(), actualResponse.getAddress());
        assertEquals(expectedResponse.getAssignee(), actualResponse.getAssignee());
        assertEquals(expectedResponse.getCompletionByDate(), actualResponse.getCompletionByDate());
        assertEquals(expectedResponse.getType(), actualResponse.getType());
    }
}
