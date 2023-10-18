package com.jobscout.domain.offer;

import com.jobscout.domain.offer.dto.OfferRequestDto;
import com.jobscout.domain.offer.dto.OfferResponseDto;
import com.jobscout.domain.offer.dto.JobOfferResponse;
import lombok.AllArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.anyOf;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@AllArgsConstructor
class OfferFacadeTest {

    @Test
    void should_save_4_offers_when_there_are_no_offers_in_database() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();

        // when
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "1"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "2"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "3"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "4"));

        // then
        assertThat(offerFacade.findAllOffers()).hasSize(4);
    }

    @Test
    void should_save_only_2_offers_when_repository_had_4_added_with_offer_urls() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of(
                new JobOfferResponse("id", "id", "asds", "1"),
                new JobOfferResponse("assd", "id", "asds", "2"),
                new JobOfferResponse("asddd", "id", "asds", "3"),
                new JobOfferResponse("asfd", "id", "asds", "4"),
                new JobOfferResponse("Junior", "Comarch", "1000", "https://someurl.pl/5"),
                new JobOfferResponse("Mid", "Finanteq", "2000", "https://someother.pl/6")))
                .offerFacadeForTests();


        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "positon", "1"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "positon", "2"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "positon", "3"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "positon", "4"));
        assertThat(offerFacade.findAllOffers()).hasSize(4);

        //when

        List<OfferResponseDto> response = offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        //then
        assertThat(List.of(
                        response.get(0).offerUrl(),
                        response.get(1).offerUrl()
                )
        ).containsExactlyInAnyOrder("https://someurl.pl/5", "https://someother.pl/6");
    }

    @Test
    void should_throw_duplicate_key_exception_when_with_offer_url_exists() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();

        OfferResponseDto offerResponseDto = offerFacade
                .saveOffer(new OfferRequestDto("id", "asds", "asdasd", "example.pl"));

        Throwable thrown = catchThrowable(() -> offerFacade.saveOffer(
                new OfferRequestDto("cx", "vc", "xcv", "example.pl")));

        //when, then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("Offer with offerUrl [example.pl] already exists");

    }

    @Test
    void should_throw_not_found_exception_when_offer_not_found() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        //when
        Throwable thrown = catchThrowable(() -> offerFacade.findOfferById("10"));

        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_fetch_from_jobs_from_remote_and_save_all_offers_when_repository_is_empty() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        //when
        List<OfferResponseDto> result = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        // then
        assertThat(result).hasSize(6);
    }

    @Test
    void should_find_offer_by_id_when_offer_was_saved() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        OfferResponseDto offerResponseDto = offerFacade.saveOffer(
                new OfferRequestDto("company", "position", "1000", "www.example.com"));

        //when

        OfferResponseDto offerId = offerFacade.findOfferById(offerResponseDto.id());

        //then
        assertEquals(offerId.id(),offerResponseDto.id());


    }


}