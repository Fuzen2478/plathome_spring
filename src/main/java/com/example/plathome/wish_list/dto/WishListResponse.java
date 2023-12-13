package com.example.plathome.wish_list.dto;

import com.example.plathome.global.domain.estate.common.Floor;
import com.example.plathome.global.domain.estate.common.Option;
import com.example.plathome.global.domain.estate.common.RentalType;
import com.example.plathome.global.domain.estate.common.RoomType;
import com.example.plathome.real_estate.domain.Estate;
import com.example.plathome.real_estate.domain.types.Area;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WishListResponse(
        long memberId,
        String location,
        String thumbNailUrl,
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
        long estateId
) {

    public static WishListResponseBuilder of() {
        return WishListResponse.builder();
    }

    public static WishListResponse from(Estate entity) {
        return WishListResponse.of()
                .memberId(entity.getMemberId())
                .location(entity.getLocation())
                .thumbNailUrl(entity.getThumbNailUrl())
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
                .estateId(entity.getId())
                .build();
    }
}