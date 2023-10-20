package com.jobscout.infrastructure.offer.http;

import com.jobscout.domain.offer.OfferFetchable;
import com.jobscout.domain.offer.dto.JobOfferResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;





@AllArgsConstructor
public class OfferRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;
    private final int port;
    private final String uri;
    @Override
    public List<JobOfferResponse> fetchOffers() {

        String urlForService = (uri + ":" + port + "/offers");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();

        ResponseEntity<List<JobOfferResponse>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                });

        final List<JobOfferResponse> body = response.getBody();
        if (body == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return body;
    }
}
