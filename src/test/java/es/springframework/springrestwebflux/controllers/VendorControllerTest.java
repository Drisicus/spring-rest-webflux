package es.springframework.springrestwebflux.controllers;

import es.springframework.springrestwebflux.domain.Vendor;
import es.springframework.springrestwebflux.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getAllVendors() {
        BDDMockito.given(vendorRepository.findAll()).willReturn(Flux.just(
                Vendor.builder().firstName("Bob").lastName("Bobinson").build(),
                Vendor.builder().firstName("Danny").lastName("Darko").build()
        ));

        webTestClient.get().uri(VendorController.BASE_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        BDDMockito.given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().firstName("Bob").lastName("Bobbins").build()));

        webTestClient.get().uri(VendorController.BASE_URL + "/someid")
                .exchange().expectBody(Vendor.class);
    }
}