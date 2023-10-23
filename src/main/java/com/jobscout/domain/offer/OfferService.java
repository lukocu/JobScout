package com.jobscout.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
class OfferService {
    private final OfferFetchable offerFetcher;
    private final OfferRepository offerRepository;

    public List<Offer> fetchAllOffersAndSaveAllIfNotExists() {
        List<Offer> jobOffers = fetchOffers();
        List<Offer> offers = filterNotExistingOffers(jobOffers);

        return offerRepository.saveAll(offers);
    }

    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers().stream()
                .map(OfferMapper::mapFromOfferResponseToEntity)
                .toList();
    }

    private List<Offer> filterNotExistingOffers(List<Offer> jobOffers) {
        return jobOffers.stream()
                .filter(offerDto -> !offerDto.offerUrl().isEmpty())
                .filter(offerDto -> !offerRepository.existsByOfferUrl(offerDto.offerUrl()))
                .toList();
    }
}
