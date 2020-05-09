package com.ccuk.demo.repository;

import com.ccuk.demo.entity.MaintenanceInstruction;
import com.ccuk.demo.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MaintenanceRepository {

    private static Long sequenceNumber = 1L;

    private Map<Long,MaintenanceInstruction> instructions;

    public MaintenanceRepository() {
        this.instructions = new LinkedHashMap<>();
    }

    public MaintenanceInstruction create(MaintenanceInstruction instruction) {
        instruction.setId(sequenceNumber++);
        instructions.put(instruction.getId(), instruction);
        return instruction;
    }

    public void update(MaintenanceInstruction instruction) throws EntityNotFoundException {
        if (!instructions.containsKey(instruction.getId())) {
            throw new EntityNotFoundException("Instruction with specified ID does not exist");
        }
        instructions.put(instruction.getId(), instruction);
    }

    public List<MaintenanceInstruction> findAll() {
        return new ArrayList<>(instructions.values());
    }

}
