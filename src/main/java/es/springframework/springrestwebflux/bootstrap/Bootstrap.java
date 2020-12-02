package es.springframework.springrestwebflux.bootstrap;

import es.springframework.springrestwebflux.domain.Category;
import es.springframework.springrestwebflux.domain.Vendor;
import es.springframework.springrestwebflux.repositories.CategoryRepository;
import es.springframework.springrestwebflux.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;
    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override public void run(String...args) {
        loadCategories();
        loadVendors();
    }

    private void loadCategories() {
        categoryRepository.deleteAll().thenMany(Flux.just("Fruits", "Nuts", "Breads", "Meats", "Eggs")
                .map(name ->new Category(null, name))
                .flatMap(categoryRepository::save))
                .then(categoryRepository.count())
                .subscribe(categories ->System.out.println(categories + " categories saved"));
    }

    private void loadVendors() {
        vendorRepository.deleteAll()
                .thenMany(Flux.just(Vendor.builder().firstName("Alan").lastName("Dark").build(),
                        Vendor.builder().firstName("Bob").lastName("Light").build(),
                        Vendor.builder().firstName("Cat").lastName("Catto").build(),
                        Vendor.builder().firstName("Dog").lastName("Doggo").build())
                        .flatMap(vendorRepository::save))
                .then(vendorRepository.count()).subscribe(vendors ->System.out.println(vendors + " vendors saved"));
    }
}