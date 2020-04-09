package com.ey.controllers;

import com.google.gson.Gson;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.connectivity.ErpHttpDestination;
import com.sap.cloud.sdk.s4hana.connectivity.ErpHttpDestinationUtils;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.productmaster.Product;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.productmaster.ProductDescription;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultProductMasterService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<Product>> getProducts() {
    ErpHttpDestination erpHttpDestination = ErpHttpDestinationUtils
        .getErpHttpDestination("EYErpSystem");
    try {
      final List<Product> products = new DefaultProductMasterService().getAllProduct().top(200)
          .execute(erpHttpDestination);
      return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (ODataException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public ResponseEntity<Product> createProduct() {
    ErpHttpDestination erpHttpDestination = ErpHttpDestinationUtils
        .getErpHttpDestination("EYErpSystem");
    try {
      Product product = new Product();
      ProductDescription a = new ProductDescription();
      a.setLanguage("EN");
      a.setProductDescription("Nate的物料拉");
      List<ProductDescription> b = new ArrayList<>();
      b.add(a);
      product.setDescription(b);
      product.setProductType("HAWA");
      product.setProductGroup("L001");
      product.setBaseUnit("EA");
      product.setItemCategoryGroup("NORM");
      Product resProduct = new DefaultProductMasterService().createProduct(product)
          .execute(erpHttpDestination);
      return new ResponseEntity<>(resProduct, HttpStatus.OK);
    } catch (ODataException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
