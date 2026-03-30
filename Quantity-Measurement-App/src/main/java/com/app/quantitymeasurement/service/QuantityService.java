package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.dto.QuantityDto;
import com.app.quantitymeasurement.model.dto.QuantityResponseDto;
import com.app.quantitymeasurement.model.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.enums.OperationType;
import com.app.quantitymeasurement.model.enums.Unit;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuantityService {

    private final QuantityMeasurementRepository repository;

    public QuantityService(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    public QuantityResponseDto convert(QuantityDto dto) {
        QuantityMeasurementEntity entity = buildEntity(dto, "CONVERT");
        try {
            Unit u1 = parseUnit(dto.getUnit1());
            Unit u2 = parseUnit(dto.getUnit2());
            validateUnitsMatchType(u1, u2, dto.getMeasurementType());

            double result = u2.fromBase(u1.toBase(dto.getValue1()));
            entity.setResult(result);
            entity.setError(false);

        } catch (Exception e) {
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
        }
        return QuantityResponseDto.fromEntity(repository.save(entity));
    }


    public QuantityResponseDto compare(QuantityDto dto) {
        QuantityMeasurementEntity entity = buildEntity(dto, "COMPARE");
        try {
            requireValue2(dto);
            Unit u1 = parseUnit(dto.getUnit1());
            Unit u2 = parseUnit(dto.getUnit2());
            validateUnitsMatchType(u1, u2, dto.getMeasurementType());

            double base1 = u1.toBase(dto.getValue1());
            double base2 = u2.toBase(dto.getValue2());

            String comparison;
            if (Math.abs(base1 - base2) < 0.0001) {
                comparison = "EQUAL";
            } else if (base1 > base2) {
                comparison = "GREATER";
            } else {
                comparison = "LESSER";
            }
            entity.setComparisonResult(comparison);
            entity.setError(false);

        } catch (Exception e) {
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
        }
        return QuantityResponseDto.fromEntity(repository.save(entity));
    }



    public QuantityResponseDto calculate(QuantityDto dto) {
        if (dto.getOperation() == null || dto.getOperation().isBlank()) {
            throw new IllegalArgumentException(
                "operation is required for /calculate. Allowed: ADD, SUBTRACT, MULTIPLY, DIVIDE"
            );
        }

        OperationType op = OperationType.fromString(dto.getOperation());

        if (op == OperationType.CONVERT || op == OperationType.COMPARE) {
            throw new IllegalArgumentException(
                "Use /convert for CONVERT and /compare for COMPARE operations."
            );
        }

        QuantityMeasurementEntity entity = buildEntity(dto, op.name());
        try {
            requireValue2(dto);
            Unit u1 = parseUnit(dto.getUnit1());
            Unit u2 = parseUnit(dto.getUnit2());
            validateUnitsMatchType(u1, u2, dto.getMeasurementType());

            double base1 = u1.toBase(dto.getValue1());
            double base2 = u2.toBase(dto.getValue2());

            switch (op) {
                case ADD:
                    entity.setResult(u1.fromBase(base1 + base2));
                    break;

                case SUBTRACT:
                    entity.setResult(u1.fromBase(base1 - base2));
                    break;

                case MULTIPLY:
                    entity.setResult(dto.getValue1() * dto.getValue2());
                    break;

                case DIVIDE:
                    if (dto.getValue2() == 0) {
                        throw new IllegalArgumentException("Cannot divide by zero.");
                    }
                    entity.setResult(dto.getValue1() / dto.getValue2());
                    break;

                default:
                    throw new IllegalArgumentException("Unhandled operation: " + op);
            }

            entity.setError(false);

        } catch (Exception e) {
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
        }
        return QuantityResponseDto.fromEntity(repository.save(entity));
    }

    public List<QuantityResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(QuantityResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<QuantityResponseDto> getByOperation(String operation) {
        return repository.findByOperation(operation.toUpperCase())
                .stream()
                .map(QuantityResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<QuantityResponseDto> getByMeasurementType(String type) {
        return repository.findByMeasurementType(type.toUpperCase())
                .stream()
                .map(QuantityResponseDto::fromEntity)
                .collect(Collectors.toList());
    }


    private QuantityMeasurementEntity buildEntity(QuantityDto dto, String operation) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation(operation);
        entity.setMeasurementType(dto.getMeasurementType().toUpperCase());
        entity.setValue1(dto.getValue1());
        entity.setValue2(dto.getValue2());
        entity.setUnit1(dto.getUnit1().toUpperCase());
        entity.setUnit2(dto.getUnit2().toUpperCase());
        return entity;
    }

    private void validateUnitsMatchType(Unit u1, Unit u2, String measurementType) {
        if (!u1.getType().equalsIgnoreCase(measurementType)) {
            throw new IllegalArgumentException(
                "Unit '" + u1.name() + "' does not belong to measurement type '" + measurementType + "'"
            );
        }
        if (!u2.getType().equalsIgnoreCase(measurementType)) {
            throw new IllegalArgumentException(
                "Unit '" + u2.name() + "' does not belong to measurement type '" + measurementType + "'"
            );
        }
    }

    private Unit parseUnit(String unitStr) {
        try {
            return Unit.valueOf(unitStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Unknown unit: '" + unitStr + "'. Allowed: KM, M, MILE, TON, KG, G, L, ML, GALLON, C, F, K"
            );
        }
    }

    private void requireValue2(QuantityDto dto) {
        if (dto.getValue2() == null) {
            throw new IllegalArgumentException(
                "value2 is required for this operation."
            );
        }
    }
}