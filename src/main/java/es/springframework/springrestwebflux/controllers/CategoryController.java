package es.springframework.springrestwebflux.controllers;

import es.springframework.springrestwebflux.domain.Category;
import es.springframework.springrestwebflux.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(BASE_URL)
    Mono<Void> createCategory(@RequestBody  Publisher<Category> categoryStream){
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping(BASE_URL + "/{id}")
    Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category){
        return categoryRepository.findById(id).flatMap(foundCategory -> {
            category.setId(foundCategory.getId());
            return categoryRepository.save(category);
        }).switchIfEmpty(Mono.error(new Exception("Category Not Found")));
    }

}
