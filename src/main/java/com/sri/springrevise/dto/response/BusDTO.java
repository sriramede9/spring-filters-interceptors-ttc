package com.sri.springrevise.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {
private String id;
private String routeName;
private String driverName;
private Boolean active;
}
