package com.example.plathome.real_estate.dto.search;

import com.example.plathome.real_estate.dto.search.filter.*;
import lombok.Builder;

@Builder
public record Filter(
        AreaFilter Area,
        RoomTypeFilter RoomType,
        RentTypeFilter RentType,
        DepositFilter Deposit,
        MonthlyFeeFilter MonthlyFee,
        MaintenanceFeeFilter MaintenanceFee,
        RoomSizeFilter RoomSize,
        FloorFilter floor,
        OptionFilter option
) {

}
