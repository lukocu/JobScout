package com.jobscout.domain.offer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOfferDatabase {

    Map<String,Offer> offerMap=new ConcurrentHashMap<>();
}
