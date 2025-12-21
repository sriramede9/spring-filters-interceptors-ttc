package com.sri.springrevise.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusCreateRequest {
    @NotBlank(message = "Route is mandatory")
    private String routeName;
    @NotBlank(message = "Driver Name is mandatory")
    private String driverName;
    @NotBlank(message = "Depot Code is mandatory")
    private String internalDepotCode;
    @PastOrPresent(message = "Service date cannot be in the future")
    private LocalDateTime lastServiceDate;
    @NotNull(message = "Active is mandatory")
    private Boolean active;
}