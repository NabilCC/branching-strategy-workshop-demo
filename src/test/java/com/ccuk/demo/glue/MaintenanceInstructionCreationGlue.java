package com.ccuk.demo.glue;

import com.ccuk.demo.entity.MaintenanceInstruction;
import com.ccuk.demo.entity.MaintenanceInstructionType;
import com.ccuk.demo.exception.ValidationException;
import com.ccuk.demo.repository.MaintenanceRepository;
import com.ccuk.demo.service.MaintenanceService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MaintenanceInstructionCreationGlue {

    public static final String THE_ASSIGNEE = "the_assignee";
    public static final String THE_ADDRESS = "the_address";
    public static final MaintenanceInstructionType CLEANING_TYPE = MaintenanceInstructionType.CLEANING;

    private Date futureDate, pastDate;
    private MaintenanceService service;
    private MaintenanceInstruction originalInstruction, returnedInstruction;
    private MaintenanceRepository repository;
    private Exception caughtException;

    @Before
    public void setUp() {
        repository = mock(MaintenanceRepository.class);
        service = new MaintenanceService(repository);
        originalInstruction = null;
        returnedInstruction = null;
        caughtException = null;

        Long nowEpochMs = new Date().getTime();
        pastDate = new Date(nowEpochMs - 60_000L);
        futureDate = new Date(nowEpochMs + 60_000L);
    }

    // =========================================================== GIVEN ==============================================================

    @Given("I create a work instruction with a completion by date in the past")
    public void i_create_a_work_instruction_with_a_completion_by_date_in_the_past() {
        originalInstruction = new MaintenanceInstruction(THE_ASSIGNEE, THE_ADDRESS, CLEANING_TYPE, pastDate);
    }

    @Given("I create a work instruction and do not specify the completion by date")
    public void i_create_a_work_instruction_and_do_not_specify_the_completion_by_date() {
        originalInstruction = new MaintenanceInstruction(THE_ASSIGNEE, THE_ADDRESS, CLEANING_TYPE, null);
    }

    @Given("I create a work instruction and do not specify the instruction type")
    public void i_create_a_work_instruction_and_do_not_specify_the_instruction_type() {
        originalInstruction = new MaintenanceInstruction(THE_ASSIGNEE, THE_ADDRESS, null, futureDate);
    }

    @Given("I create a work instruction and do not specify the address")
    public void i_create_a_work_instruction_and_do_not_specify_the_address() {
        originalInstruction = new MaintenanceInstruction(THE_ASSIGNEE, null, CLEANING_TYPE, futureDate);
    }

    @Given("I create a work instruction and do not specify the assignee")
    public void i_create_a_work_instruction_and_do_not_specify_the_assignee() {
        originalInstruction = new MaintenanceInstruction(null, THE_ADDRESS, CLEANING_TYPE, futureDate);
    }

    @Given("I create a valid work instruction")
    public void i_create_a_valid_work_instruction() {
        originalInstruction = new MaintenanceInstruction(THE_ASSIGNEE, THE_ADDRESS, CLEANING_TYPE, futureDate);
    }

    // =========================================================== WHEN ===============================================================

    @When("I submit the work instruction")
    public void i_submit_the_work_instruction() {
        try {
            this.returnedInstruction = service.saveMaintenanceInstruction(originalInstruction);
        } catch (ValidationException e) {
            this.caughtException = e;
        }
    }

    // =========================================================== THEN ===============================================================

    @Then("I should receive a validation error, indicating the completion by date in the past error")
    public void i_should_receive_a_validation_error_indicating_the_completion_by_date_in_the_past_error() {
        assertValidationExceptionWasCaught(MaintenanceService.COMPLETION_NOT_IN_PAST);
    }

    @Then("I should receive a validation error, indicating the completion by missing error")
    public void i_should_receive_a_validation_error_indicating_the_completion_by_missing_error() {
        assertValidationExceptionWasCaught(MaintenanceService.COMPLETION_BY_DATE_IS_MANDATORY);
    }

    @Then("I should receive a validation error, indicating the instruction type error")
    public void i_should_receive_a_validation_error_indicating_the_instruction_type_error() {
        assertValidationExceptionWasCaught(MaintenanceService.MAINTENANCE_TYPE_IS_MANDATORY);
    }

    @Then("I should receive a validation error, indicating the address error")
    public void i_should_receive_a_validation_error_indicating_the_address_error() {
        assertValidationExceptionWasCaught(MaintenanceService.ADDRESS_IS_MANDATORY);
    }

    @Then("I should receive a validation error, indicating the assignee error")
    public void i_should_receive_a_validation_error_indicating_the_assignee_error() {
        assertValidationExceptionWasCaught(MaintenanceService.ASSIGNEE_IS_MANDATORY);
    }

    @Then("The work instruction is created")
    public void the_work_instruction_is_created() {
        verify(repository).create(eq(originalInstruction));
    }

    // =========================================================== PRIVATE =============================================================
    private void assertValidationExceptionWasCaught(String message) {
        assertNotNull(caughtException);
        assertEquals(ValidationException.class, caughtException.getClass());
        assertEquals(message, caughtException.getMessage());
    }

}
