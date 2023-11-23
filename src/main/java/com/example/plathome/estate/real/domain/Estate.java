package com.example.plathome.estate.real.domain;

import com.example.plathome.estate.Option;
import com.example.plathome.estate.constant.RentalType;
import com.example.plathome.estate.converter.OptionsConverter;
import com.example.plathome.global.domain.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private RentalType rentalType;

    @Column(length = 100)
    private String contractPath;

    private LocalDate contractDate;

    @Convert(converter = OptionsConverter.class)
    private Set<Option> options = new LinkedHashSet<>();

    private double squareFeet;
    private double lng;
    private double lat;

    private int maintenanceFee;

    private int monthlyRent;

    @Builder
    public Estate(Long memberId, String location, RentalType rentalType, String contractPath, Set<Option> options, int maintenanceFee, int monthlyRent) {
        this.memberId = memberId;
        this.location = location;
        this.rentalType = rentalType;
        this.contractPath = contractPath;
        this.options = options;
        this.maintenanceFee = maintenanceFee;
        this.monthlyRent = monthlyRent;
    }
}