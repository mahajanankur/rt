/**
 * 
 */
package com.sb.rt.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.sb.rt.dao.AbstractDao;
import com.sb.rt.dao.ProductDao;
import com.sb.rt.model.Product;

/**
 * @author ankur.mahajan
 *
 */
@Transactional
@Repository("productDao")
public class ProductDaoImpl extends AbstractDao implements ProductDao {

	@Value("${app.suggestions.limit}")
	private int SUGGESTION_LIMIT;

	@Override
	public String uploadProductData(Product product) {
		getSession().saveOrUpdate(product);
		return "ok";
	}

	@Override
	public Product getProductByEpc(String epc) {
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.eq("epc", epc));
		Product product = (Product) criteria.uniqueResult();
		return product;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductsByStyleCode(int styleCode) {
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.eq("styleCode", styleCode));
		List<Product> products = criteria.list();
		return products;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getSuggestionsBySku(String sku) {
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.eq("sku", sku));
		criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
		criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
		criteria.setMaxResults(SUGGESTION_LIMIT);
		List<Product> suggestions = criteria.list();
		return suggestions;
	}

}
