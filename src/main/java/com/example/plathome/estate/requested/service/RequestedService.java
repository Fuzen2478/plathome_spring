package com.example.plathome.estate.requested.service;

import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.estate.requested.domain.Requested;
import com.example.plathome.estate.requested.dto.request.RequestedForm;
import com.example.plathome.estate.requested.dto.response.RequestedResponse;
import com.example.plathome.estate.requested.exception.DuplicationRequestedException;
import com.example.plathome.estate.requested.exception.NotFoundRequestedException;
import com.example.plathome.estate.requested.repository.RequestedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RequestedService {

    private final S3Service s3Service;
    private final RequestedRepository requestedRepository;

    @Transactional
    public void saveFile(MemberSession memberSession, MultipartFile file) {
        this.validDupReq(memberSession.id());
        s3Service.upload(memberSession, file);
    }

    @Transactional
    public void saveForm(MemberSession memberSession, RequestedForm requestedForm) {
        this.validDupReq(memberSession.id());
        String url = s3Service.getFile(memberSession.email());
        Requested requested = requestedForm.toEntity(memberSession.id(), url);
        requestedRepository.save(requested);
    }

    private void validDupReq(long memberId) {
        Optional<Requested> optionalRequested = requestedRepository.findByMemberId(memberId);
        if (optionalRequested.isPresent()) {
            throw new DuplicationRequestedException();
        }
    }

    public List<RequestedResponse> getAll() {
        return requestedRepository.findAll().stream()
                .map(RequestedResponse::from)
                .toList();
    }

    @Transactional
    public void updateFile(MemberSession memberSession, MultipartFile file) {
        s3Service.upload(memberSession, file);
    }

    @Transactional
    public void updateForm(MemberSession memberSession, RequestedForm requestedForm) {
        Requested requested = requestedRepository.findByMemberId(memberSession.id())
                .orElseThrow(NotFoundRequestedException::new);

        requested.updateForm(
                requestedForm.location(),
                requestedForm.roomType(),
                requestedForm.rentalType(),
                requestedForm.floor(),
                requestedForm.contractTerm(),
                requestedForm.option(),
                requestedForm.squareFeet(),
                requestedForm.deposit(),
                requestedForm.maintenanceFee(),
                requestedForm.monthlyRent());
    }

    @Transactional
    public void delete(long memberId) {
        requestedRepository.deleteByMemberId(memberId);
    }
}
