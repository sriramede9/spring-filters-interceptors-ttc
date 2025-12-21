package com.sri.springrevise.dto.request;

import com.sri.springrevise.annotations.field.ValidBoardingStop;
import com.sri.springrevise.annotations.field.ValidRouteName;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRequest {
    @NotBlank(message = "Passenger name is required")
    private String name;
    
    @NotBlank(message = "Ticket ID is required")
    private String ticketId;

    @ValidBoardingStop
    private String boardingStop; // Optional: e.g., "Union Station"
}