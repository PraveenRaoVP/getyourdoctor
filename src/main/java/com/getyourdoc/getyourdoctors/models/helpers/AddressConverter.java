package com.getyourdoc.getyourdoctors.models.helpers;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AddressConverter implements AttributeConverter<Address, String> {
    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Address address) {
        if (address == null) {
            return null;
        }

        // Convert Address object to a comma-separated string representation
        StringBuilder sb = new StringBuilder();
        sb.append(address.getStreet()).append(DELIMITER);
        sb.append(address.getCity()).append(DELIMITER);
        sb.append(address.getState()).append(DELIMITER);
        sb.append(address.getPincode());
        return sb.toString();
    }

    @Override
    public Address convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        // Convert comma-separated string representation to an Address object
        String[] addressParts = dbData.split(DELIMITER);
        if (addressParts.length != 4) {
            throw new IllegalArgumentException("Invalid address format: " + dbData);
        }

        Address address = new Address();
        address.setStreet(addressParts[0]);
        address.setCity(addressParts[1]);
        address.setState(addressParts[2]);
        address.setPincode(addressParts[3]);
        return address;
    }

}
