package com.app.quantitymeasurement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.app.quantitymeasurement.model.entity.QuantityMeasurementEntity;
import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuantityResponseDto {

    private Long id;
    private String operation;

    private Double result;
    private String resultUnit;

    private String comparisonResult;

    private boolean isError;
    private String errorMessage;

    private LocalDateTime createdAt;

    public static QuantityResponseDto fromEntity(QuantityMeasurementEntity entity) {
        QuantityResponseDto dto = new QuantityResponseDto();
        dto.setId(entity.getId());
        dto.setOperation(entity.getOperation());
        dto.setResult(entity.getResult());
        dto.setComparisonResult(entity.getComparisonResult());
        dto.setError(entity.isError());
        dto.setErrorMessage(entity.getErrorMessage());
        dto.setCreatedAt(entity.getCreatedAt());


        String op = entity.getOperation();
        if (op == null) {
            dto.setResultUnit(null);
        } else if (op.equals("CONVERT")) {
            dto.setResultUnit(entity.getUnit2());
        } else if (op.equals("COMPARE")) {
            dto.setResultUnit(null);
        } else {
            dto.setResultUnit(entity.getUnit1());
        }

        return dto;
    }

    public Long getId() { 
    	return id; 
    }
    public void setId(Long id) { 
    	this.id = id; 
    }

    public String getOperation() { 
    	return operation; 
    }
    public void setOperation(String operation) { 
    	this.operation = operation; 
    }

    public Double getResult() { 
    	return result; 
    }
    public void setResult(Double result) { 
    	this.result = result; 
    }

    public String getResultUnit() { 
    	return resultUnit; 
    }
    public void setResultUnit(String resultUnit) { 
    	this.resultUnit = resultUnit; 
    }

    public String getComparisonResult() { 
    	return comparisonResult; 
    }
    public void setComparisonResult(String comparisonResult) { 
    	this.comparisonResult = comparisonResult; 
    }

    public boolean isError() { 
    	return isError; 
    }
    public void setError(boolean error) { 
    	isError = error; 
    }

    public String getErrorMessage() { 
    	return errorMessage; 
    }
    public void setErrorMessage(String errorMessage) { 
    	this.errorMessage = errorMessage; 
    }

    public LocalDateTime getCreatedAt() { 
    	return createdAt; 
    }
    public void setCreatedAt(LocalDateTime createdAt) { 
    	this.createdAt = createdAt; 
    }
}