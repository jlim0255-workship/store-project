package com.jlim.store.catalogservice.domain;

import com.jlim.store.catalogservice.ApplicationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional // auto roll back if something failed
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties applicationProperties;

    ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties){
        this.productRepository = productRepository;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Instead or returning ProductEntity, we map it to Product type
     * */
    public PagedResult<Product> getProducts(int pageNo){
        // pagination always comes with sorting (to be consistent)
        Sort sort = Sort.by("name").ascending();

        // cast it to 0, ensure no negative faulty value
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;

        Pageable pageable = PageRequest.of(pageNo, applicationProperties.pageSize(), sort);

        // convert the ProductEntity to Product type
        Page<Product> productsPage = productRepository.findAll(pageable).map(ProductMapper :: toProduct);

        // specified pagedResult
        return new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1, // add it back
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious()
        );
    }
}
