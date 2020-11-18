package com.example.demo.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
@Transactional
public class ProductService {
	@Autowired
	ProductRepository productRepository;

	/*
	 * 新規
	 */
	public String productAdd(Product product) {
		productRepository.save(product);
		return "";
	}

	/*
	 * すべてのデータを取り出す
	 */
	public List<Product> productfindAll() {
		return productRepository.findAll();

	}

	/*
	 * IDで削除
	 */
	public Product dropProduct(Integer id) {
		productRepository.deleteById(id);
		return null;
	}

	/*
	 *編集
	 */
	public Product editProduct(Integer id, Integer productId, String productInfo, Double sales, Double cost,
			Integer stock, String productType, Timestamp dateModified) {
		productRepository.updateproductByid(id, productId, productInfo, sales, cost, stock, productType, dateModified);
		return null;
	}

	/*
	 * IDで取り出す
	 */
	public Product productById(Integer id) {

		return productRepository.findprobyid(id);

	}

	/*
	 * productIDで取り出す
	 */
	public Product findproid(Integer productId) {
		return productRepository.findproductId(productId);

	}

	/*
	 * 在庫を更新
	 */
	public void updatestock(Integer productId, Integer stock) {
		productRepository.updateproductStock(productId, stock);
	}
	/*
	 * 检索
	 */

	public List<Product> selectproductId(Integer productId) {
		return productRepository.selectByproductId(productId);
	}

	public List<Product> selectproductType(String productType) {
		return productRepository.selectByproductType(productType);
	}

	public List<Product> selectproductInfo(String productInfo) {
		return productRepository.selectByproductInfo(productInfo);
	}

	public List<Product> selectsales(Double sales) {
		return productRepository.selectBysales(sales);
	}

	public List<Product> selectcost(Double cost) {
		return productRepository.selectBycost(cost);
	}

	public List<Product> selectstock(Integer stock) {
		return productRepository.selectBystock(stock);
	}

	public List<Product> selectaccessNumber(Integer accessNumber) {
		return productRepository.selectByaccessNumber(accessNumber);
	}
}
