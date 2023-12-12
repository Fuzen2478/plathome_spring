package com.example.plathome.real_estate.repository;

import com.example.plathome.real_estate.domain.Estate;
import com.example.plathome.real_estate.domain.types.Area;
import com.example.plathome.real_estate.dto.search.Filter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.plathome.global.domain.estate.common.Floor.*;
import static com.example.plathome.global.domain.estate.common.RentalType.JEONSE;
import static com.example.plathome.global.domain.estate.common.RentalType.MONTHLY;
import static com.example.plathome.global.domain.estate.common.RoomType.*;
import static com.example.plathome.real_estate.domain.QEstate.estate;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Repository
public class EstateRepositoryImpl implements EstateRepositoryCustom{
    private final JPAQueryFactory queryFactory;



    @Override
    public List<Estate> filterSearch(Filter filter) {
         BooleanExpression areaFilter = null;
         BooleanExpression depositFilter = null;
         BooleanExpression floorFilter = null;
         BooleanExpression monthlyFeeFilter = null;
         BooleanExpression maintenanceFeeFilter = null;
         BooleanExpression rentTypeFilter = null;
         BooleanExpression roomSizeFilter = null;
         BooleanExpression roomTypeFilter = null;
         BooleanExpression optionFilter = null;

        areaFilter = chainBooleanExpression(areaFilter, gwanggyo(filter.Area().gwanggyo()));
        areaFilter = chainBooleanExpression(areaFilter, ingyedong(filter.Area().gwanggyo()));
        areaFilter = chainBooleanExpression(areaFilter, uman(filter.Area().uman()));
        areaFilter = chainBooleanExpression(areaFilter, woncheon(filter.Area().woncheon()));
        areaFilter = chainBooleanExpression(areaFilter, maetan(filter.Area().maetan()));

        System.out.println(areaFilter);

        depositFilter = chainBooleanExpression(depositFilter,
                depositBetween(filter.Deposit().min(), filter.Deposit().max()));

        floorFilter = chainBooleanExpression(floorFilter, first(filter.floor().first()));
        floorFilter = chainBooleanExpression(floorFilter, second(filter.floor().second()));
        floorFilter = chainBooleanExpression(floorFilter, third(filter.floor().third()));
        floorFilter = chainBooleanExpression(floorFilter, fourth(filter.floor().fourth()));
        floorFilter = chainBooleanExpression(floorFilter, fifth(filter.floor().fifth()));
        floorFilter = chainBooleanExpression(floorFilter, sixth(filter.floor().sixth()));
        floorFilter = chainBooleanExpression(floorFilter, seventhUpper(filter.floor().seventhUpper()));
        floorFilter = chainBooleanExpression(floorFilter, top(filter.floor().top()));
        floorFilter = chainBooleanExpression(floorFilter, under(filter.floor().under()));

        monthlyFeeFilter = chainBooleanExpression(monthlyFeeFilter,
                monthlyFeeBetween(filter.MonthlyFee().min(), filter.MonthlyFee().max()));

        maintenanceFeeFilter = chainBooleanExpression(maintenanceFeeFilter, maintenanceFeeBetween(filter.MaintenanceFee().min(), filter.MaintenanceFee().max()));

        rentTypeFilter = chainBooleanExpression(rentTypeFilter, monthly(filter.RentType().monthly()));
        rentTypeFilter = chainBooleanExpression(rentTypeFilter, jeonse(filter.RentType().jeonse()));

        roomSizeFilter = chainBooleanExpression(roomSizeFilter,
                roomSizeBetween(filter.RoomSize().min(), filter.RoomSize().max()));

        roomTypeFilter = chainBooleanExpression(roomTypeFilter, studio(filter.RoomType().studio()));
        roomTypeFilter = chainBooleanExpression(roomTypeFilter, twoThreeRoom(filter.RoomType().two_threeRoom()));
        roomTypeFilter = chainBooleanExpression(roomTypeFilter, officetel(filter.RoomType().officetel()));
        roomTypeFilter = chainBooleanExpression(roomTypeFilter, apartment(filter.RoomType().apartment()));

        optionFilter = chainBooleanExpression(optionFilter, elevator(filter.option().elevator()));
        optionFilter = chainBooleanExpression(optionFilter, park(filter.option().park()));
        optionFilter = chainBooleanExpression(optionFilter, cctv(filter.option().cctv()));
        optionFilter = chainBooleanExpression(optionFilter, doorLock(filter.option().doorLock()));
        optionFilter = chainBooleanExpression(optionFilter, pet(filter.option().pet()));
        optionFilter = chainBooleanExpression(optionFilter, veranda(filter.option().veranda()));
        optionFilter = chainBooleanExpression(optionFilter, bunner(filter.option().bunner()));
        optionFilter = chainBooleanExpression(optionFilter, airConditioner(filter.option().airConditioner()));
        optionFilter = chainBooleanExpression(optionFilter, refrigerator(filter.option().refrigerator()));
        optionFilter = chainBooleanExpression(optionFilter, sink(filter.option().sink()));
        optionFilter = chainBooleanExpression(optionFilter, tv(filter.option().tv()));
        optionFilter = chainBooleanExpression(optionFilter, internet(filter.option().internet()));
        optionFilter = chainBooleanExpression(optionFilter, bed(filter.option().bed()));
        optionFilter = chainBooleanExpression(optionFilter, desk(filter.option().desk()));
        optionFilter = chainBooleanExpression(optionFilter, microwave(filter.option().microwave()));
        optionFilter = chainBooleanExpression(optionFilter, closet(filter.option().closet()));
        optionFilter = chainBooleanExpression(optionFilter, shoeRack(filter.option().shoeRack()));
        optionFilter = chainBooleanExpression(optionFilter, bidet(filter.option().bidet()));
        optionFilter = chainBooleanExpression(optionFilter, interPhone(filter.option().interPhone()));
        optionFilter = chainBooleanExpression(optionFilter, parking(filter.option().parking()));
        optionFilter = chainBooleanExpression(optionFilter, security(filter.option().security()));
        optionFilter = chainBooleanExpression(optionFilter, deliveryBox(filter.option().deliveryBox()));
        optionFilter = chainBooleanExpression(optionFilter, buildingEntrance(filter.option().buildingEntrance()));
        optionFilter = chainBooleanExpression(optionFilter, washingMachine(filter.option().washingMachine()));
        optionFilter = chainBooleanExpression(optionFilter, elevator(filter.option().elevator()));
        optionFilter = chainBooleanExpression(optionFilter, elevator(filter.option().elevator()));

        return queryFactory
                .select(estate)
                .from(estate)
                .where(
                        areaFilter,
                        depositFilter,
                        floorFilter,
                        monthlyFeeFilter,
                        maintenanceFeeFilter,
                        rentTypeFilter,
                        roomSizeFilter,
                        roomTypeFilter,
                        optionFilter
                )
                .fetch();
    }

    private BooleanExpression chainBooleanExpression(BooleanExpression filter, BooleanExpression booleanExpression) {
        if (filter != null) {
            if (booleanExpression != null) return filter.or(booleanExpression);
            else return filter;
        } else {
            if(booleanExpression != null) return booleanExpression;
            else return null;
        }
    }

    /** Area */
    private BooleanExpression gwanggyo(boolean gwanggyo) {
        return gwanggyo ? estate.area.eq(Area.GWANGGYO) : null;
    }

    private BooleanExpression ingyedong(boolean ingyedong) {
        return ingyedong ? estate.area.eq(Area.INGYEDONG) : null;
    }

    private BooleanExpression uman(boolean uman) {
        return uman ? estate.area.eq(Area.UMAN) : null;
    }

    private BooleanExpression woncheon(boolean woncheon) {
        return woncheon ? estate.area.eq(Area.WONCHEON) : null;
    }

    private BooleanExpression maetan(boolean maetan) {
        return maetan ? estate.area.eq(Area.MAETAN) : null;
    }

    /** deposit */
    private BooleanExpression depositBetween(Integer depositGoe, Integer depositLoe) {
        return depositLoe(depositLoe).and(depositGoe(depositGoe));
    }

    private BooleanExpression depositLoe(Integer depositLoe) {
        return depositLoe != null ?  estate.deposit.loe(depositLoe) : null;
    }

    private BooleanExpression depositGoe(Integer depositGoe) {
        return depositGoe != null ? estate.deposit.goe(depositGoe) : null;
    }

    /** floor */
    private BooleanExpression first(boolean first) {
        return first ? estate.floor.eq(FIRST) : null;
    }

    private BooleanExpression second(boolean second) {
        return second ? estate.floor.eq(SECOND) : null;
    }

    private BooleanExpression third(boolean third) {
        return third ? estate.floor.eq(THIRD) : null;
    }

    private BooleanExpression fourth(boolean fourth) {
        return fourth ? estate.floor.eq(FOURTH) : null;
    }

    private BooleanExpression fifth(boolean fifth) {
        return fifth ? estate.floor.eq(FIFTH) : null;
    }

    private BooleanExpression sixth(boolean sixth) {
        return sixth ? estate.floor.eq(SIXTH) : null;
    }

    private BooleanExpression seventhUpper(boolean seventhUpper) {
        return seventhUpper ? estate.floor.eq(SEVENTH_UPPER) : null;
    }

    private BooleanExpression top(boolean top) {
        return top ? estate.floor.eq(TOP) : null;
    }

    private BooleanExpression under(boolean under) {
        return under ? estate.floor.eq(UNDER) : null;
    }

    /** monthlyFee */
    private BooleanExpression monthlyFeeBetween(Integer monthlyFeeGoe, Integer monthlyFeeLoe) {
        return monthlyFeeLoe(monthlyFeeLoe).and(monthlyFeeGoe(monthlyFeeGoe));
    }

    private BooleanExpression monthlyFeeLoe(Integer monthlyFeeLoe) {
        return monthlyFeeLoe != null ?  estate.monthlyRent.loe(monthlyFeeLoe): null;
    }

    private BooleanExpression monthlyFeeGoe(Integer monthlyFeeGoe) {
        return monthlyFeeGoe != null ? estate.monthlyRent.goe(monthlyFeeGoe) : null;
    }

    /** maintenanaceFee */
    private BooleanExpression maintenanceFeeBetween(Integer maintenanceFeeGoe, Integer maintenanceFeeLoe) {
        return maintenanceFeeLoe(maintenanceFeeLoe).and(maintenanceFeeGoe(maintenanceFeeGoe));
    }

    private BooleanExpression maintenanceFeeLoe(Integer maintenanceFeeLoe) {
        return maintenanceFeeLoe != null ?  estate.maintenanceFee.loe(maintenanceFeeLoe): null;
    }

    private BooleanExpression maintenanceFeeGoe(Integer maintenanceFeeGoe) {
        return maintenanceFeeGoe != null ? estate.maintenanceFee.goe(maintenanceFeeGoe) : null;
    }

    /** renType */
    private BooleanExpression monthly(boolean monthly) {
        return monthly ? estate.rentalType.eq(MONTHLY) : null;
    }

    private BooleanExpression jeonse(boolean jeonse) {
        return jeonse ? estate.rentalType.eq(JEONSE) : null;
    }

    /** roomSize */
    private BooleanExpression roomSizeBetween(Double roomSizeGoe, Double roomSizeLoe) {
        return roomSizeLoe(roomSizeLoe).and(roomSizeGoe(roomSizeGoe));
    }

    private BooleanExpression roomSizeLoe(Double roomSizeLoe) {
        return roomSizeLoe != null ? estate.squareFeet.loe(roomSizeLoe): null;
    }

    private BooleanExpression roomSizeGoe(Double roomSizeGoe) {
        return roomSizeGoe != null ? estate.squareFeet.goe(roomSizeGoe) : null;
    }

    /** roomSize */
    private BooleanExpression studio(boolean studio) {
        return studio ? estate.roomType.eq(STUDIO) : null;
    }

    private BooleanExpression twoThreeRoom(boolean twoThreeRoom) {
        return twoThreeRoom ? estate.roomType.eq(TOW_THREEROOM) : null;
    }

    private BooleanExpression officetel(boolean officetel) {
        return officetel ? estate.roomType.eq(OFFICETEL) : null;
    }

    private BooleanExpression apartment(boolean apartment) {
        return apartment ? estate.roomType.eq(APARTMENT) : null;
    }

    /** option */
    private BooleanExpression elevator(boolean elevator) {
        return elevator ? estate.option.elevator.eq(true) : null;
    }

    private BooleanExpression park(boolean park) {
        return park ? estate.option.park.eq(true) : null;
    }

    private BooleanExpression cctv(boolean cctv) {
        return cctv ? estate.option.cctv.eq(true) : null;
    }

    private BooleanExpression doorLock(boolean doorLock) {
        return doorLock ? estate.option.doorLock.eq(true) : null;
    }

    private BooleanExpression pet(boolean pet) {
        return pet ? estate.option.pet.eq(true) : null;
    }

    private BooleanExpression veranda(boolean veranda) {
        return veranda ? estate.option.veranda.eq(true) : null;
    }

    private BooleanExpression bunner(String bunner) {
        return hasText(bunner) ? estate.option.bunner.eq(bunner) : null;
    }

    private BooleanExpression airConditioner(String airConditioner) {
        return hasText(airConditioner) ? estate.option.airConditioner.eq(airConditioner) : null;
    }

    private BooleanExpression refrigerator(boolean refrigerator) {
        return refrigerator ? estate.option.refrigerator.eq(true) : null;
    }

    private BooleanExpression sink(boolean sink) {
        return sink ? estate.option.sink.eq(true) : null;
    }

    private BooleanExpression tv(boolean tv) {
        return tv ? estate.option.tv.eq(true) : null;
    }

    private BooleanExpression internet(boolean internet) {
        return internet ? estate.option.internet.eq(true) : null;
    }

    private BooleanExpression bed(boolean bed) {
        return bed ? estate.option.bed.eq(true) : null;
    }

    private BooleanExpression desk(boolean desk) {
        return desk ? estate.option.desk.eq(true) : null;
    }

    private BooleanExpression microwave(boolean microwave) {
        return microwave ? estate.option.microwave.eq(true) : null;
    }

    private BooleanExpression closet(boolean closet) {
        return closet ? estate.option.closet.eq(true) : null;
    }

    private BooleanExpression shoeRack(boolean shoeRack) {
        return shoeRack ? estate.option.shoeRack.eq(true) : null;
    }

    private BooleanExpression bidet(boolean bidet) {
        return bidet ? estate.option.bidet.eq(true) : null;
    }

    private BooleanExpression interPhone(boolean interPhone) {
        return interPhone ? estate.option.interPhone.eq(true) : null;
    }

    private BooleanExpression parking(boolean parking) {
        return parking ? estate.option.parking.eq(true) : null;
    }

    private BooleanExpression security(boolean security) {
        return security ? estate.option.security.eq(true) : null;
    }

    private BooleanExpression deliveryBox(boolean deliveryBox) {
        return deliveryBox ? estate.option.deliveryBox.eq(true) : null;
    }

    private BooleanExpression buildingEntrance(boolean buildingEntrance) {
        return buildingEntrance ? estate.option.buildingEntrance.eq(true) : null;
    }

    private BooleanExpression washingMachine(boolean washingMachine) {
        return washingMachine ? estate.option.washingMachine.eq(true) : null;
    }
}
