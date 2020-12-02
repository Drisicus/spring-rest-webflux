package es.springframework.springrestwebflux.repositories;

import es.springframework.springrestwebflux.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
