package com.jobscout.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferFacadeConfiguration {

    @Bean
    public OfferFacade offerFacade(OfferFetchable offerFetchable,OfferRepository offerRepository){

        return new OfferFacade(offerRepository,new OfferService(offerFetchable,offerRepository));
    }

}
