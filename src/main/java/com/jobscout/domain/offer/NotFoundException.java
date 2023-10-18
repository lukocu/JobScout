package com.jobscout.domain.offer;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String offerNotFound) {
        super("message : "+offerNotFound);
    }
}
