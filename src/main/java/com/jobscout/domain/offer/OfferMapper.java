package com.jobscout.domain.offer;

import com.jobscout.domain.offer.dto.OfferRequestDto;
import com.jobscout.domain.offer.dto.OfferResponseDto;
import com.jobscout.domain.offer.dto.JobOfferResponse;

class OfferMapper {

    public static OfferResponseDto mapFromEntity(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .position(offer.position())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }

    public static Offer mapToEntityOfferRequest(OfferRequestDto offerRequestDto) {
        return Offer.builder()
                .companyName(offerRequestDto.companyName())
                .position(offerRequestDto.position())
                .salary(offerRequestDto.salary())
                .offerUrl(offerRequestDto.offerUrl())
                .build();
    }

    public static Offer  mapFromOfferResponseToEntity(JobOfferResponse jobOfferResponse) {
       return Offer.builder()
                .companyName(jobOfferResponse.company())
                .salary(jobOfferResponse.salary())
                .offerUrl(jobOfferResponse.offerUrl())
                .build();

    }
}
