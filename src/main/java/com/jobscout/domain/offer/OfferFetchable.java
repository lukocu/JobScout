package com.jobscout.domain.offer;

import com.jobscout.domain.offer.dto.JobOfferResponse;

import java.util.List;

interface OfferFetchable {
    List<JobOfferResponse> fetchOffers();
}
