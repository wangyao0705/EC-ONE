package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository

public interface ProductRepository extends JpaRepository<Product, Integer> {
	/*
	 *  更新
	 */
	@Modifying
	@Query("update Product p set p.productId=?2,p.productInfo=?3,p.sales=?4,p.cost=?5,p.stock=?6,p.productType=?7,p.dateModified=?8 where p.id=?1")
	void updateproductByid(Integer id, Integer productId, String productInfo, Double sales, Double cost, Integer stock,
			String productType, Timestamp dateModified);

	/*
	 * 更新在庫
	 */
	@Modifying
	@Query("update Product p set p.stock=?2 where p.productId=?1")
	void updateproductStock(Integer productId, Integer stock);

	/*
	 * IDで取り出す、return object(Product)
	 */
	@Query("select p from Product p where p.id=?1")
	Product findprobyid(Integer id);

	/*
	 * productIDで取り出す
	 */
	@Query("select p from Product p where p.productId=?1")
	Product findproductId(Integer productId);
	/*
	 *暧昧检索
	 */

	@Query("select p from Product p where p.productId like concat(:productId,'%')")
	List<Product> selectByproductId(@Param("productId") Integer productId);

	@Query("select p from Product p where p.productType like %?1%")
	List<Product> selectByproductType(String productType);

	@Query("select p from Product p where p.productInfo like %?1%")
	List<Product> selectByproductInfo(String productInfo);

	@Query("select p from Product p where p.sales like concat(:sales,'%')")
	List<Product> selectBysales(@Param("sales") Double sales);

	@Query("select p from Product p where p.cost like concat(:cost,'%')")
	List<Product> selectBycost(@Param("cost") Double cost);

	@Query("select p from Product p where p.stock like concat(:stock,'%')")
	List<Product> selectBystock(@Param("stock") Integer stock);

	@Query("select p from Product p where p.accessNumber like concat(:accessNumber,'%')")
	List<Product> selectByaccessNumber(@Param("accessNumber") Integer accessNumber);

}
