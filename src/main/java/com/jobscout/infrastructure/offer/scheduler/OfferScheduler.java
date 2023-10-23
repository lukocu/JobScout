package com.jobscout.infrastructure.offer.scheduler;

import com.jobscout.domain.offer.OfferFacade;
import com.jobscout.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class OfferScheduler {

    private final OfferFacade offerFacade;

    @Scheduled(fixedDelayString   = "${http.offers.scheduler.request.delay}")
    public void fetchOffer() {
        log.info("offer scheduler started");
        List<OfferResponseDto> responseDtos = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        log.info(responseDtos.toString());
    }
}
