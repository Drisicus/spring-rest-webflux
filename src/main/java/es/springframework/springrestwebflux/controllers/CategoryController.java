package es.springframework.springrestwebflux.controllers;

import es.springframework.springrestwebflux.domain.Category;
import es.springframework.springrestwebflux.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {
    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(BASE_URL)
    Flux<Category> getCategories(){
        return categoryRepository.findAll();
    }

    @GetMapping(BASE_URL + "/{id}")
    Mono<Category> getCategoryById(@PathVariable String id){
        return categoryRepository.findById(id);
    }
}
