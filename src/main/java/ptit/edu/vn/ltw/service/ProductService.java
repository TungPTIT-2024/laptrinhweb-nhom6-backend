package ptit.edu.vn.ltw.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ptit.edu.vn.ltw.dto.common.GenericResponse;
import ptit.edu.vn.ltw.dto.product.ProductDetailResponse;
import ptit.edu.vn.ltw.dto.product.ProductListResponse;
import ptit.edu.vn.ltw.dto.product.ProductRequest;
import ptit.edu.vn.ltw.entity.Discount;
import ptit.edu.vn.ltw.entity.Product;
import ptit.edu.vn.ltw.exception.ErrorDetail;
import ptit.edu.vn.ltw.exception.HttpStatusException;
import ptit.edu.vn.ltw.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final DiscountService discountService;

    private static final String RESOURCES_NOT_FOUND = "Resources not found";
    private static final String ERROR_PRODUCT_NOT_FOUND_TEMPLATE = "Product with id %s not found";

    public ProductListResponse getProducts(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);

        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductDetailResponse> productList = productPage.stream().map(product -> {
            ProductDetailResponse detailResponse = new ProductDetailResponse();
            BeanUtils.copyProperties(product, detailResponse);
            return detailResponse;
        }).toList();

        ProductListResponse response = new ProductListResponse();

        if (!productList.isEmpty()) {
            Map<String, Discount> discountMap = discountService.findBestActiveDiscountPerProduct(
                    productList.stream().map(ProductDetailResponse::getId).toList()
            );

            productList.forEach(product -> {
                if (discountMap.containsKey(product.getId())) {
                    Discount discount = discountMap.get(product.getId());
                    product.setDiscountPercentage(discount.getPercentage());
                    product.setDiscountDescription(discount.getDescription());
                }
            });
        }

        response.setProductList(productList);
        response.setPage(page);
        response.setSize(size);
        response.setTotalPage(productPage.getTotalPages());

        return response;
    }

    public ProductDetailResponse getProductById(String id) {
        Product foundProduct = productRepository.findById(id).orElseThrow(() -> {
            ErrorDetail errorDetail = new ErrorDetail().setField("id").setIssue(String.format(ERROR_PRODUCT_NOT_FOUND_TEMPLATE, id));
            return HttpStatusException.badRequest(RESOURCES_NOT_FOUND, List.of(errorDetail));
        });

        ProductDetailResponse response = new ProductDetailResponse();
        BeanUtils.copyProperties(foundProduct, response);

        Optional<Discount> activeDiscount = discountService.getBestActiveDiscountForProduct(id);

        if (activeDiscount.isPresent()){
            Discount discount = activeDiscount.get();
            response.setDiscountPercentage(discount.getPercentage());
            response.setDiscountDescription(discount.getDescription());
        }

        return response;
    }

    public ProductDetailResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);
        productRepository.save(product);

        ProductDetailResponse response = new ProductDetailResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    public ProductDetailResponse updateProduct(String productId, @Valid ProductRequest productRequest) {
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            ErrorDetail errorDetail = new ErrorDetail().setField("id").setIssue(String.format(ERROR_PRODUCT_NOT_FOUND_TEMPLATE, productId));
            return HttpStatusException.badRequest(RESOURCES_NOT_FOUND, List.of(errorDetail));
        });

        if (Objects.nonNull(productRequest.getName())){
            product.setName(productRequest.getName());
        }

        if (Objects.nonNull(productRequest.getPrice())){
            product.setPrice(productRequest.getPrice());
        }

        if (Objects.nonNull(productRequest.getImage())){
            product.setImage(productRequest.getImage());
        }

        if (Objects.nonNull(productRequest.getDescription())){
            product.setDescription(productRequest.getDescription());
        }

        if (Objects.nonNull(productRequest.getSku())){
            product.setSku(productRequest.getSku());
        }

        productRepository.save(product);
        ProductDetailResponse response = new ProductDetailResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    public GenericResponse deleteProductById(String id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()){
            ErrorDetail errorDetail = new ErrorDetail().setField("id").setIssue(String.format(ERROR_PRODUCT_NOT_FOUND_TEMPLATE, id));
            throw HttpStatusException.badRequest(RESOURCES_NOT_FOUND, List.of(errorDetail));
        } else {
            productRepository.deleteById(id);
            return new GenericResponse().setHttpStatus(200).setMessage("Product deleted successfully");
        }
    }

    public void checkProductExistById(String id) {
        if (!productRepository.existsById(id)){
            ErrorDetail errorDetail = new ErrorDetail().setField("product_id").setIssue(String.format(ERROR_PRODUCT_NOT_FOUND_TEMPLATE, id));
            throw HttpStatusException.badRequest(RESOURCES_NOT_FOUND, List.of(errorDetail));

        }
    }
}
