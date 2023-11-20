package com.sourcery.oirs.database.entity;

import com.sourcery.oirs.model.UserUpdateDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AddressEntity {
    private UUID id;
    private String street;
    private String city;
    private String state;
    private String postcode;
    private UUID countryId;
    private UUID employeeId;
}
