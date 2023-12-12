package com.example.plathome.real_estate.dto.response;

import com.example.plathome.global.domain.estate.common.Floor;
import com.example.plathome.global.domain.estate.common.Option;
import com.example.plathome.global.domain.estate.common.RentalType;
import com.example.plathome.global.domain.estate.common.RoomType;
import com.example.plathome.real_estate.domain.Estate;
import com.example.plathome.real_estate.domain.types.Area;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record EstateResponse(
        long memberId,
        String location,
        String context,
        Set<String> thumbNailUrls,
        Area area,
        RoomType roomType,
        RentalType rentalType,
        Floor floor,
        LocalDate contractTerm,
        Option option,
        double squareFeet,
        int deposit,
        int maintenanceFee,
        int monthlyRent,
        double lat,
        double lng,
        long estateId
) {

    public static EstateResponseBuilder of() {
        return EstateResponse.builder();
    }

    public static EstateResponse from(Estate entity, Set<String> thumbNailUrls) {
        return EstateResponse.of()
                .memberId(entity.getMemberId())
                .location(entity.getLocation())
                .context(entity.getContext())
                .thumbNailUrls(thumbNailUrls)
                .area(entity.getArea())
                .roomType(entity.getRoomType())
                .rentalType(entity.getRentalType())
                .floor(entity.getFloor())
                .contractTerm(entity.getContractTerm())
                .option(entity.getOption())
                .squareFeet(entity.getSquareFeet())
                .deposit(entity.getDeposit())
                .maintenanceFee(entity.getMaintenanceFee())
                .monthlyRent(entity.getMonthlyRent())
                .lat(entity.getLat())
                .lng(entity.getLng())
                .estateId(entity.getId())
                .build();
    }
}
