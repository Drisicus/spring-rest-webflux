package es.springframework.springrestwebflux.repositories;

import es.springframework.springrestwebflux.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
