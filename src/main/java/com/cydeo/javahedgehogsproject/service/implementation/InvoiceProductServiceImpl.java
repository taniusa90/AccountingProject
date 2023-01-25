package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.entity.*;
import com.cydeo.javahedgehogsproject.dto.*;
import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, SecurityService securityService, @Lazy InvoiceService invoiceService, ProductService productService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    @Override
    public List<InvoiceProductDto> findAllById(Long id) {
        CompanyDto loggedInCompany = securityService.getLoggedInCompany();
        Company company = mapperUtil.convert(loggedInCompany, new Company());

        return invoiceProductRepository.findAllByInvoice_CompanyAndInvoiceId(company, id).stream()
                .filter(invoiceProduct -> !invoiceProduct.getIsDeleted())
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .peek(invoiceProductDto -> invoiceProductDto.setTotal(calculate(invoiceProductDto)))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDto> findAllInvoiceProducts(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);
        List<InvoiceProductDto> DtoList = invoiceProductList.stream().map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto())).collect(Collectors.toList());
        for (InvoiceProductDto each : DtoList) {
            BigDecimal x = BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice());
            BigDecimal y = BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice()).multiply(each.getTax()).divide(BigDecimal.valueOf(100));
            each.setTotal(x.add(y).setScale(2, RoundingMode.CEILING));

        }
        return DtoList;
    }

    @Override
    public BigDecimal totalTax(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);//from entity

        BigDecimal totalTax = BigDecimal.valueOf(0);
        for (InvoiceProduct each : invoiceProductList) {
            totalTax = totalTax.add(BigDecimal.valueOf(each.getQuantity()).multiply(each.getTax()).divide(BigDecimal.valueOf(100)).multiply(each.getPrice()));
        }
        return totalTax.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal totalPriceWithoutTax(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);//from entity

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (InvoiceProduct each : invoiceProductList) {
            totalPrice = totalPrice.add(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())));
        }
        return totalPrice.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public void savePurchaseProductByInvoiceId(InvoiceProductDto invoiceProduct, Long id) {
        InvoiceDto invoice = invoiceService.findById(id);
        InvoiceProduct invProduct = mapperUtil.convert(invoiceProduct, new InvoiceProduct());
        invProduct.setProfitLoss(new BigDecimal(0));
        invProduct.setInvoice(mapperUtil.convert(invoice, new Invoice()));
        invoiceProductRepository.save(invProduct);
    }

    @Override
    public void deletePurchaseProduct(Long productId) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(productId).get();
        invoiceProduct.setIsDeleted(true);

        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public void approvePurchaseInvoice(Long purchaseInvoiceId) {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoiceId(purchaseInvoiceId);
        if (invoiceProducts.size() > 0) {
            for (InvoiceProduct eachInvoiceProduct : invoiceProducts) {
                Product product = eachInvoiceProduct.getProduct();
                product.setQuantityInStock(product.getQuantityInStock() + eachInvoiceProduct.getQuantity());
                eachInvoiceProduct.setRemainingQty(eachInvoiceProduct.getQuantity()); // it's needed to calculate profit/loss
                InvoiceProductDto invoiceProductDto = mapperUtil.convert(eachInvoiceProduct, new InvoiceProductDto());

                invoiceProductRepository.save(mapperUtil.convert(invoiceProductDto, new InvoiceProduct()));
            }
        } else {
            throw new RuntimeException("Purchase invoice has no products.");
        }
    }

    @Override
    public void saveProduct(InvoiceProductDto invoiceProductDto, Long id) {
        InvoiceDto invoice = invoiceService.findById(id);
        InvoiceProduct invProduct = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());
        invProduct.setProfitLoss(new BigDecimal(0));
        invProduct.setInvoice(mapperUtil.convert(invoice, new Invoice()));
        invoiceProductRepository.save(invProduct);
    }

    @Override
    public void deleteSalesInvoiceProduct(Long invoiceProductId) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductId).get();
        invoiceProduct.setIsDeleted(true);
        invoiceProduct.setPrice(new BigDecimal(0));
        invoiceProduct.setTax(new BigDecimal(0));
        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public boolean hasEnoughProductQuantityInStock(Long invoiceId) {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoiceId);
        boolean enoughStock = true;
        for (InvoiceProduct each : invoiceProducts) {
            if (each.getProduct().getQuantityInStock() < each.getQuantity()) {
                enoughStock = false;
            }
            reduceAmountOfProductQuantityInStock(each.getProduct(), each.getQuantity());
        }
        return enoughStock;
    }

    @Override
    public void calculateProfitLossForSoldInvoiceProduct(Long invoiceId) {
        List<InvoiceProduct> salesInvoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoiceId);
        for (InvoiceProduct saleProduct : salesInvoiceProducts) {
            saleProduct.setRemainingQty(saleProduct.getQuantity());
            BigDecimal salesInvoiceProductPrice = calculatePriceOfProductWithTax(saleProduct.getPrice(), saleProduct.getQuantity(), saleProduct.getTax());

            List<InvoiceProduct> purchaseInvoiceProducts = getAllPurchasedInvoiceProductsByProduct(saleProduct.getProduct().getId());
            BigDecimal purchaseInvoiceProductPrice = BigDecimal.ZERO;
            for (InvoiceProduct purchasedProduct : purchaseInvoiceProducts) {
                if(saleProduct.getRemainingQty() > 0) {
                    if (purchasedProduct.getRemainingQty() == 0) {
                        continue;
                    }
                    if (purchasedProduct.getRemainingQty() > saleProduct.getRemainingQty()) {
                        purchaseInvoiceProductPrice = purchaseInvoiceProductPrice.add(calculatePriceOfProductWithTax(purchasedProduct.getPrice(), saleProduct.getRemainingQty(), purchasedProduct.getTax()));
                        purchasedProduct.setRemainingQty(purchasedProduct.getRemainingQty() - saleProduct.getRemainingQty());
                        saleProduct.setRemainingQty(0);
                    } else if (purchasedProduct.getRemainingQty() < saleProduct.getRemainingQty()) {
                        purchaseInvoiceProductPrice = purchaseInvoiceProductPrice.add(calculatePriceOfProductWithTax(purchasedProduct.getPrice(), purchasedProduct.getRemainingQty(), purchasedProduct.getTax()));
                        saleProduct.setRemainingQty(saleProduct.getRemainingQty() - purchasedProduct.getRemainingQty());
                        purchasedProduct.setRemainingQty(0);
                    } else {
                        purchaseInvoiceProductPrice = purchaseInvoiceProductPrice.add(calculatePriceOfProductWithTax(purchasedProduct.getPrice(), purchasedProduct.getRemainingQty(), purchasedProduct.getTax()));
                        purchasedProduct.setRemainingQty(0);
                        saleProduct.setRemainingQty(0);
                    }
                }
            }
            BigDecimal profitLoss = salesInvoiceProductPrice.subtract(purchaseInvoiceProductPrice);
            saleProduct.setProfitLoss(profitLoss);
            invoiceProductRepository.save(saleProduct);
        }
    }

    @Override
    public List<InvoiceProduct> getAllApprovedInvoiceProductsByCompany(CompanyDto company) {
        return invoiceProductRepository
                .findAllByInvoice_InvoiceStatusAndInvoice_CompanyOrderByInvoice_LastUpdateDateTimeDesc(
                        InvoiceStatus.APPROVED, mapperUtil.convert(company, new Company()));
    }

    private BigDecimal calculate(InvoiceProductDto invoiceProductDto) {
        BigDecimal totalWithOutTax = invoiceProductDto.getPrice().multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity()));
        BigDecimal taxAmount = invoiceProductDto.getPrice().multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity())).multiply(invoiceProductDto.getTax()).divide(new BigDecimal(100));

        return totalWithOutTax.add(taxAmount).setScale(2, RoundingMode.CEILING);
    }

    @Override
    public List<InvoiceProduct> findAllMonthlyProfitLoss() {
        Company company = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());
        return invoiceProductRepository.findAllByInvoice_InvoiceStatusAndInvoice_InvoiceTypeAndInvoice_CompanyOrderByInvoice_DateDesc(InvoiceStatus.APPROVED, InvoiceType.SALES, company);
    }

    @Override
    public void deleteByInvoice(InvoiceType invoiceType, InvoiceDto invoiceDto) {
        //go to DB find that invoice:
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());
        //find all invoiceProducts belongs to that invoice:
        List<InvoiceProduct> listInvoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoice.getId());
        //delete one by one all invoiceProducts that we found base on the id:
        listInvoiceProducts.forEach(invoiceProduct -> {
            Optional<InvoiceProduct> foundInvoiceProduct = invoiceProductRepository.findById(invoiceProduct.getId());
            if (foundInvoiceProduct.isPresent()) {
                foundInvoiceProduct.get().setIsDeleted(true);
                invoiceProductRepository.save(foundInvoiceProduct.get());
            }
        });
    }

    @Override
    public InvoiceProductDto findById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id), new InvoiceProductDto());
    }

    private BigDecimal calculatePriceOfProductWithTax(BigDecimal price, Integer quantity, BigDecimal tax) {
        BigDecimal totalPriceOfProduct = price.multiply(BigDecimal.valueOf(quantity)).multiply(tax).divide(BigDecimal.valueOf(100)).add(price.multiply(BigDecimal.valueOf(quantity)));
        return totalPriceOfProduct.setScale(2, RoundingMode.CEILING);
    }

    private void reduceAmountOfProductQuantityInStock(Product product, Integer quantityAmount) {
        product.setQuantityInStock(product.getQuantityInStock() - quantityAmount);
    }

    private List<InvoiceProduct> getAllPurchasedInvoiceProductsByProduct(Long product_id) {
        Company currentCompany = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());
        return invoiceProductRepository.findAllByInvoice_InvoiceStatusAndInvoice_InvoiceTypeAndInvoice_CompanyAndProduct_IdOrderByInvoice_IdAscIdAsc(
                InvoiceStatus.APPROVED, InvoiceType.PURCHASE, currentCompany, product_id);
    }

}
