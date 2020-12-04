package es.springframework.springrestwebflux.controllers;

import es.springframework.springrestwebflux.domain.Vendor;
import es.springframework.springrestwebflux.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorRepository vendorRepository;


    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping(BASE_URL)
    Flux<Vendor> getAllVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping(BASE_URL + "/{id}")
    Mono<Vendor> getVendorById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(BASE_URL)
    Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping(BASE_URL + "/{id}")
    Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor){
        return vendorRepository.findById(id).flatMap(vendorFound -> {
            vendor.setId(vendorFound.getId());
            return vendorRepository.save(vendor);
        }).switchIfEmpty(Mono.error(new Exception("Vendor not found")));
    }

}
