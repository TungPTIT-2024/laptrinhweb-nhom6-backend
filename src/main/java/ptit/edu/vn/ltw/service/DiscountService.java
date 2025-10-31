package ptit.edu.vn.ltw.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ptit.edu.vn.ltw.dto.common.GenericResponse;
import ptit.edu.vn.ltw.dto.discount.DiscountRequest;
import ptit.edu.vn.ltw.entity.Discount;
import ptit.edu.vn.ltw.exception.ErrorDetail;
import ptit.edu.vn.ltw.exception.HttpStatusException;
import ptit.edu.vn.ltw.repository.DiscountRepository;
import ptit.edu.vn.ltw.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    private static final String RESOURCES_NOT_FOUND = "Resources not found";
    private static final String ERROR_PRODUCT_NOT_FOUND_TEMPLATE = "Product with id %s not found";


    public GenericResponse applyDiscountToProduct(String productId, @Valid DiscountRequest request) {
        this.checkProductExistById(productId);
        Discount discount = new Discount();
        discount.setProductId(productId);
        BeanUtils.copyProperties(request, discount);

        discountRepository.save(discount);

        return new GenericResponse().setHttpStatus(200).setMessage("Discount applied successfully");
    }

    public GenericResponse updateDiscount(String id, @Valid DiscountRequest request) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> {
                    ErrorDetail errorDetail = new ErrorDetail().setField("discount_id").setIssue(String.format("Discount with id %s not found", id));
                    return HttpStatusException.badRequest(RESOURCES_NOT_FOUND, List.of(errorDetail));
                });

        if (request.getDescription() != null) {
            existingDiscount.setDescription(request.getDescription());
        }
        if (request.getPercentage() != null) {
            existingDiscount.setPercentage(request.getPercentage());
        }
        if (request.getStartDate() != null) {
            existingDiscount.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            existingDiscount.setEndDate(request.getEndDate());
        }
        discountRepository.save(existingDiscount);

        return new GenericResponse().setHttpStatus(200).setMessage("Discount updated successfully");
    }

    public GenericResponse removeDiscount(String id) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> {
                    ErrorDetail errorDetail = new ErrorDetail().setField("discount_id").setIssue(String.format("Discount with id %s not found", id));
                    return HttpStatusException.badRequest(RESOURCES_NOT_FOUND, List.of(errorDetail));
                });

        discountRepository.delete(existingDiscount);
        return new GenericResponse().setHttpStatus(200).setMessage("Discount removed successfully");
    }

    public Optional<Discount> getBestActiveDiscountForProduct(String productId) {
        List<Discount> activeDiscounts = discountRepository.findActiveDiscountByProductId(productId, Instant.now());
        if (!CollectionUtils.isEmpty(activeDiscounts)) {
            return activeDiscounts.stream()
                    .max(Comparator.comparing(d -> Optional.ofNullable(d.getPercentage()).orElse(BigDecimal.ZERO)));
        } else {
            return Optional.empty();
        }
    }

    public Map<String, Discount> findBestActiveDiscountPerProduct(List<String> productIds) {
        Instant now = Instant.now();
        if (CollectionUtils.isEmpty(productIds)) {
            return Collections.emptyMap();
        }

        List<Discount> active = discountRepository.findActiveByProductIds(productIds, now);

        // group by productId -> choose Discount with max percentage
        return active.stream()
                .collect(Collectors.groupingBy(
                        Discount::getProductId,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(d -> Optional.ofNullable(d.getPercentage()).orElse(BigDecimal.ZERO))),
                                optional -> optional.orElse(null)
                        )
                ));
    }

    private void checkProductExistById(String id) {
        if (!productRepository.existsById(id)){
            ErrorDetail errorDetail = new ErrorDetail().setField("product_id").setIssue(String.format(ERROR_PRODUCT_NOT_FOUND_TEMPLATE, id));
            throw HttpStatusException.badRequest(RESOURCES_NOT_FOUND, List.of(errorDetail));

        }
    }
}
