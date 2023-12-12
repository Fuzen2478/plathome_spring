package com.example.plathome.real_estate.dto.response;

import com.example.plathome.global.domain.estate.common.Floor;
import com.example.plathome.global.domain.estate.common.RentalType;
import com.example.plathome.global.domain.estate.common.RoomType;
import com.example.plathome.real_estate.domain.Estate;
import lombok.Builder;

@Builder
public record MapInfoEstateResponse(
        long estateId,
        double lng,
        double lat,
        RoomType roomType,
        RentalType rentalType,
        String thumbNailUrl,
        Floor floor,
        double squareFeet,
        int deposit,
        int maintenanceFee,
        int monthlyRent,
        String location
) {

    public static MapInfoEstateResponseBuilder of() {
        return MapInfoEstateResponse.builder();
    }

    public static MapInfoEstateResponse from(Estate entity) {
        return MapInfoEstateResponse.of()
                .estateId(entity.getId())
                .lng(entity.getLng())
                .lat(entity.getLat())
                .roomType(entity.getRoomType())
                .rentalType(entity.getRentalType())
                .thumbNailUrl(entity.getThumbNailUrl())
                .floor(entity.getFloor())
                .squareFeet(entity.getSquareFeet())
                .deposit(entity.getDeposit())
                .maintenanceFee(entity.getMaintenanceFee())
                .monthlyRent(entity.getMonthlyRent())
                .location(entity.getLocation())
                .build();
    }
}
