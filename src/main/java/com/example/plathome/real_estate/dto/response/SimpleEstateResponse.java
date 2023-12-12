package com.example.plathome.real_estate.dto.response;

import com.example.plathome.global.domain.estate.common.RentalType;
import com.example.plathome.global.domain.estate.common.RoomType;
import com.example.plathome.real_estate.domain.Estate;
import lombok.Builder;

@Builder
public record SimpleEstateResponse(
        long estateId,
        RoomType roomType,
        RentalType rentalType,
        String thumbNailUrl,
        String context
) {

    public static SimpleEstateResponseBuilder of() {
        return SimpleEstateResponse.builder();
    }

    public static SimpleEstateResponse from(Estate entity) {
        return SimpleEstateResponse.of()
                .estateId(entity.getId())
                .roomType(entity.getRoomType())
                .rentalType(entity.getRentalType())
                .thumbNailUrl(entity.getThumbNailUrl())
                .context(entity.getContext()).build();
    }
}
