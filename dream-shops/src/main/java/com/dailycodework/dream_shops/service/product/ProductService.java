package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.dto.ImageDTO;
import com.dailycodework.dream_shops.dto.ProductDTO;
import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Image;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.CategoryRepository;
import com.dailycodework.dream_shops.repository.ImageRepository;
import com.dailycodework.dream_shops.repository.ProductRepository;
import com.dailycodework.dream_shops.request.AddProductRequest;
import com.dailycodework.dream_shops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {

        if(productExists(request.getName(), request.getBrand())){
            throw new AlreadyExistsException(request.getBrand() + " " + request.getName() + " already exists, you may update this product instead");
        }
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseGet(()-> {
                    Category newCategory = Category.builder().name(request.getCategory()).build();
                    return  categoryRepository.save(newCategory);
                });
        return productRepository.save(createProduct(request, category));
    }

    private boolean productExists(String name, String brand){
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductRequest request, Category category){
        return Product.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .price(request.getPrice())
                .inventory(request.getInventory())
                .description(request.getDescription())
                .category(category)
                .build();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()-> {throw new  ProductNotFoundException("Product not found");});
    }

    @Override
    public Product updateProductById(ProductUpdateRequest request, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(()-> new  ProductNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());

        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDTO convertToDto(Product product){
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDTOS = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();
        productDTO.setImages(imageDTOS);
        return productDTO;
    }
}
