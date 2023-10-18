package com.jobscout.domain.offer;

import com.jobscout.domain.offer.dto.OfferRequestDto;
import com.jobscout.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OfferFacade {
    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAll().stream()
                .map(OfferMapper::mapFromEntity)
                .toList();

    }

    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        return offerService.fetchAllOffersAndSaveAllIfNotExists().stream()
                .map(OfferMapper::mapFromEntity)
                .toList();
    }

    public OfferResponseDto saveOffer(OfferRequestDto offerResponseDto) {
        Offer savedOffer = offerRepository.save(OfferMapper.mapToEntityOfferRequest(offerResponseDto));
        return OfferMapper.mapFromEntity(savedOffer);

    }

    public OfferResponseDto findOfferById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapFromEntity)
                .orElseThrow(() -> new NotFoundException("Offer not found"));
    }
}
