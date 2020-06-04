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

    @Test
    @WithMockUser(username = "admin", authorities = { "application_access" })
    public void createNewInstruction_feature_flag_off_expect_notEnabledMessage() throws Exception {
        when(featureFlagService.isFeatureEnabledForUser(any(Principal.class), eq(FeatureFlag.CREATE_INSTRUCTION))).thenReturn(false);

        MaintenanceInstructionRequest request = new MaintenanceInstructionRequest("the_assignee", "the_address", MaintenanceInstructionType.CLEANING, futureCompletionDate);
        byte[] requestBytes = objectMapper.writeValueAsBytes(request);

        this.mockMvc.perform(post(INSTRUCTION_RESOURCE).contentType(MediaType.APPLICATION_JSON).content(requestBytes))
                .andExpect(status().isForbidden())
                .andExpect(content().string(ApplicationController.FEATURE_NOT_ENABLED_FOR_USER));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "application_access" })
    public void createNewInstruction_validationException_expectBadRequestStatus() throws Exception {
        when(featureFlagService.isFeatureEnabledForUser(any(Principal.class), eq(FeatureFlag.CREATE_INSTRUCTION))).thenReturn(true);
        ValidationException toThrow = new ValidationException("expected_message");
        when(maintenanceService.createMaintenanceInstruction(any(MaintenanceInstructionRequest.class))).thenThrow(toThrow);

        MaintenanceInstructionRequest request = new MaintenanceInstructionRequest("the_assignee", "the_address", MaintenanceInstructionType.CLEANING, futureCompletionDate);
        byte[] requestBytes = objectMapper.writeValueAsBytes(request);

        this.mockMvc.perform(post(INSTRUCTION_RESOURCE).contentType(MediaType.APPLICATION_JSON).content(requestBytes))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ApplicationController.invalidRequestMessage(toThrow)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "application_access" })
    public void createNewInstruction_validRequest_expectCreatedStatusWithHeader() throws Exception {
        when(featureFlagService.isFeatureEnabledForUser(any(Principal.class), eq(FeatureFlag.CREATE_INSTRUCTION))).thenReturn(true);
        MaintenanceInstruction createdInstruction = new MaintenanceInstruction();
        createdInstruction.setId(1L);
        when(maintenanceService.createMaintenanceInstruction(any(MaintenanceInstructionRequest.class))).thenReturn(createdInstruction);

        MaintenanceInstructionRequest request = new MaintenanceInstructionRequest("the_assignee", "the_address", MaintenanceInstructionType.CLEANING, futureCompletionDate);
        byte[] requestBytes = objectMapper.writeValueAsBytes(request);

        this.mockMvc.perform(post(INSTRUCTION_RESOURCE).contentType(MediaType.APPLICATION_JSON).content(requestBytes))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "http://localhost" + INSTRUCTION_RESOURCE + "/1"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "application_access" })
    public void getInstructionById_featureDisabled_expectCorrectMessage() throws Exception {
        when(featureFlagService.isFeatureEnabledForUser(any(Principal.class), eq(FeatureFlag.GET_INSTRUCTION_BY_ID))).thenReturn(false);
        this.mockMvc.perform(get(INSTRUCTION_RESOURCE + "/1"))
                .andExpect(status().isForbidden()).andExpect(content().string(FEATURE_NOT_ENABLED_FOR_USER));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "application_access" })
    public void getInstructionById_featureEnabled_expectCorrectResponse() throws Exception {
        when(featureFlagService.isFeatureEnabledForUser(any(Principal.class), eq(FeatureFlag.GET_INSTRUCTION_BY_ID))).thenReturn(true);
        MaintenanceInstructionResponse givenResponse = new MaintenanceInstructionResponse(1L, "the_assignee", "the_address", MaintenanceInstructionType.CLEANING, futureCompletionDate);
        when(maintenanceService.findById(anyLong())).thenReturn(givenResponse);

        MvcResult mvcResult = this.mockMvc.perform(get(INSTRUCTION_RESOURCE + "/1"))
                .andExpect(status().isOk()).andReturn();

        byte[] responseBodyBytes = mvcResult.getResponse().getContentAsByteArray();
        MaintenanceInstructionResponse actualResponse = objectMapper.readValue(responseBodyBytes, MaintenanceInstructionResponse.class);
        assertInstructionResponseMatchesExpected(givenResponse, actualResponse);
    }

    private void assertInstructionResponseMatchesExpected(MaintenanceInstructionResponse expectedResponse, MaintenanceInstructionResponse actualResponse) {
        assertEquals(expectedResponse.getId(), expectedResponse.getId());
        assertEquals(expectedResponse.getAddress(), actualResponse.getAddress());
        assertEquals(expectedResponse.getAssignee(), actualResponse.getAssignee());
        assertEquals(expectedResponse.getCompletionByDate(), actualResponse.getCompletionByDate());
        assertEquals(expectedResponse.getType(), actualResponse.getType());
    }
}
