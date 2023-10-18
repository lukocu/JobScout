package com.jobscout.domain.offer;

import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOfferDatabase implements OfferRepository{

    Map<String,Offer> offerMap=new ConcurrentHashMap<>();

    @Override
    public List<Offer> findAll() {

        return offerMap.values().stream().toList();
    }

    @Override
    public Offer save(Offer entity) {
        if (offerMap.values().stream()
                .anyMatch(offer -> offer.offerUrl().equals(entity.offerUrl()))) {
            throw new DuplicateKeyException(String.format("Offer with offerUrl [%s] already exists", entity.offerUrl()));
        }
        UUID id = UUID.randomUUID();
        Offer offer = new Offer(
                id.toString(),
                entity.companyName(),
                entity.position(),
                entity.salary(),
                entity.offerUrl()
        );
        offerMap.put(id.toString(), offer);

        return offer;
    }

    @Override
    public boolean existsByOfferUrl(String offerUrl) {

        return offerMap.values().stream()
                .anyMatch(value-> value.offerUrl().equals(offerUrl));
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(offerMap.get(id));
    }
}
