package com.jobscout.domain.offer;

import com.jobscout.domain.offer.dto.OfferResponseDto;

import java.util.List;
import java.util.Optional;

interface OfferRepository {

    List<Offer> findAll();

    Offer save(Offer offer);

    boolean existsByOfferUrl(String offerUrl);

    List<Offer> saveAll(List<Offer> offers);

    Optional<Offer> findById(String id);
}
