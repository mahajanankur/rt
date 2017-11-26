package com.sb.rt.dao;

import java.util.List;

import com.sb.rt.model.Product;

/**
 * @author amahajan
 * 
 */
public interface ProductDao {

	String uploadProductData(Product product);

	Product getProductByEpc(String epc);

	List<Product> getProductsByStyleCode(int styleCode);

	List<Product> getSuggestionsBySku(String sku);

}
