package com.cydeo.javahedgehogsproject;

import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;
import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.service.implementation.InvoiceProductServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockConstruction;

@ExtendWith(MockitoExtension.class)
public class InvoiceProductServiceImplTest {
    @Mock
    private InvoiceProductRepository invoiceProductRepository;
    @Mock
    private MapperUtil mapperUtil;
    @InjectMocks
private InvoiceProductServiceImpl invoiceProductServiceImpl;
    @Test
    public void testFindAllInvoiceProducts (){
        List<InvoiceProduct> invoiceProductList = new ArrayList<>();
        InvoiceProduct ipl= new InvoiceProduct();
        ipl.setPrice(new BigDecimal(20));
        ipl.setTax(new BigDecimal(2));
        invoiceProductList.add(ipl);
        given(invoiceProductRepository.findAllByInvoiceId(2L))

                .willReturn(invoiceProductList);
        InvoiceProductDto invoiceProductDto =new InvoiceProductDto();
        invoiceProductDto.setPrice(new BigDecimal(20));
        invoiceProductDto.setTax(new BigDecimal(2));
        invoiceProductDto.setQuantity(2);
        given(mapperUtil.convert(Mockito.any(InvoiceProduct.class), Mockito.any(InvoiceProductDto.class))).willReturn(invoiceProductDto);
        List<InvoiceProductDto> invoiceProductDtos=invoiceProductServiceImpl.findAllInvoiceProducts(2L);
        assertThat(invoiceProductDtos).isNotNull();
        BigDecimal expectedPrice = (new BigDecimal(20));
        BigDecimal actualPrice= invoiceProductDtos.get(0).getPrice();

        assertEquals(expectedPrice, actualPrice);

    }

}
