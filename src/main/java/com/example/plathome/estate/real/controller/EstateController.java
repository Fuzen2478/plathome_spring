package com.example.plathome.estate.real.controller;

import com.example.plathome.estate.real.dto.request.EstateForm;
import com.example.plathome.estate.real.dto.request.UpdateEstateForm;
import com.example.plathome.estate.real.dto.response.EstateResponse;
import com.example.plathome.estate.real.dto.response.MapInfoEstateResponse;
import com.example.plathome.estate.real.dto.response.SimpleEstateResponse;
import com.example.plathome.estate.real.service.EstateService;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Admin;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.member.domain.MemberSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/estate")
@RequiredArgsConstructor
@RestController
public class EstateController {
    private final EstateService estateService;

    @PostMapping("/auth")
    public String register(
            @Admin MemberSession memberSession,
            @RequestBody @Valid EstateForm estateForm
    ) {
        estateService.register(estateForm);
        return "success";
    }

    @GetMapping("/no-auth/map")
    public List<MapInfoEstateResponse> getMap() {
        return estateService.getAllMapInfoResponse();
    }

    @GetMapping("/no-auth/board")
    public List<SimpleEstateResponse> getSimple() {
        return estateService.getAllSimpleResponse();
    }

    @GetMapping("/no-auth/{estateId}")
    public EstateResponse getDetail(@PathVariable Long estateId) {
        return estateService.getDetail(estateId);
    }

    @DeleteMapping("/auth/{estateId}")
    public String delete(
            @Login MemberSession memberSession,
            @PathVariable Long estateId
    ) {
        estateService.delete(memberSession, estateId);
        return "success";
    }

    @PatchMapping("/auth/{estateId}")
    public String update(
            @Login MemberSession memberSession,
            @PathVariable Long estateId,
            @RequestBody @Valid UpdateEstateForm updateEstateForm
    ) {
        estateService.update(memberSession, estateId, updateEstateForm);
        return "success";
    }
}
