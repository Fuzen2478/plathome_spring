package com.example.plathome.real_estate.domain;

import com.example.plathome.global.domain.estate.common.Floor;
import com.example.plathome.global.domain.estate.common.Option;
import com.example.plathome.global.domain.estate.common.RentalType;
import com.example.plathome.global.domain.estate.common.RoomType;
import com.example.plathome.real_estate.domain.types.Area;
import com.example.plathome.global.domain.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Estate extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estate_id")
    private Long id;

    private Long memberId;

    private String location;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RentalType rentalType;

    @Enumerated(EnumType.STRING)
    private Floor floor;

    @Column(length = 100)
    private String contractUrl;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;
    private String thumbNailUrl;

    private LocalDate contractTerm;

    @Embedded
    private Option option;

    private double squareFeet;
    private double lng;
    private double lat;

    private int deposit;
    private int maintenanceFee;
    private int monthlyRent;

    @Builder
    public Estate(Long estateId, Long memberId, String location, Area area, RoomType roomType, RentalType rentalType, Floor floor, String contractUrl, String context, String thumbNailUrl, LocalDate contractTerm, Option option, double squareFeet, double lng, double lat, int deposit, int maintenanceFee, int monthlyRent) {
        this.memberId = memberId;
        this.location = location;
        this.area = area;
        this.roomType = roomType;
        this.rentalType = rentalType;
        this.floor = floor;
        this.contractUrl = contractUrl;
        this.context = context;
        this.thumbNailUrl = thumbNailUrl;
        this.contractTerm = contractTerm;
        this.option = option;
        this.squareFeet = squareFeet;
        this.lng = lng;
        this.lat = lat;
        this.deposit = deposit;
        this.maintenanceFee = maintenanceFee;
        this.monthlyRent = monthlyRent;
        this.id = estateId;
    }

    public void update(String context, LocalDate contractTerm, Option option, double squareFeet, int deposit, int maintenanceFee, int monthlyRent) {
        this.context = context;
        this.contractTerm = contractTerm;
        this.option = option;
        this.squareFeet = squareFeet;
        this.deposit = deposit;
        this.maintenanceFee = maintenanceFee;
        this.monthlyRent = monthlyRent;
    }
}
