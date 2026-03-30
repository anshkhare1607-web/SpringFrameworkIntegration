package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.dto.QuantityDto;
import com.app.quantitymeasurement.model.dto.QuantityResponseDto;
import com.app.quantitymeasurement.service.QuantityService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/quantity")
public class QuantityController {

    private final QuantityService service;
   // private static final Logger logger = LoggerFactory.getLogger(QuantityController.class);

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(QuantityController.class);
    public QuantityController(QuantityService service) {
        this.service = service;
    }

    @PostMapping("/convert")
    public ResponseEntity<QuantityResponseDto> convert(
            @Valid @RequestBody QuantityDto dto) {
        QuantityResponseDto response = service.convert(dto);
        if (response.isError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        logger.info("Conversion performed: {} {} {} to {}", dto.getValue1(), dto.getUnit1(), dto.getMeasurementType(), response.getResultUnit());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/compare")
    public ResponseEntity<QuantityResponseDto> compare(
            @Valid @RequestBody QuantityDto dto) {
        QuantityResponseDto response = service.compare(dto);
        if (response.isError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/calculate")
    public ResponseEntity<QuantityResponseDto> calculate(
            @Valid @RequestBody QuantityDto dto) {
        QuantityResponseDto response = service.calculate(dto);
        if (response.isError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/history")
    public ResponseEntity<List<QuantityResponseDto>> getHistory(
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String type) {

        List<QuantityResponseDto> result;
        if (operation != null) {
            result = service.getByOperation(operation);
        } else if (type != null) {
            result = service.getByMeasurementType(type);
        } else {
            result = service.getAll();
        }
        return ResponseEntity.ok(result);
    }
}