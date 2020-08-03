package ru.smsoft.currencyconverter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smsoft.currencyconverter.entity.Operation;
import ru.smsoft.currencyconverter.repository.OperationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class OperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationService.class);

    @Autowired
    private OperationRepository operationRepository;

    public Operation createOperation(Operation operation) {
        if (operationRepository.existsById(operation.getId())) {
            LOGGER.error("Failed create operation. Operation with id {} already exist", operation.getId());
            return null;
        }
        try {
            Operation operationFromDb = operationRepository.save(operation);
            LOGGER.info("Operation with id {} was successfully created", operationFromDb.getId());
            return operationFromDb;
        } catch (Exception e) {
            LOGGER.error("Operation was not created", e);
        }
        return null;
    }

    public List<Operation> getOperations(String from, String to, LocalDate date) {
        return operationRepository.findAllByOriginalCurrencyAndTargetCurrencyAndDate(from, to, date);
    }

}
