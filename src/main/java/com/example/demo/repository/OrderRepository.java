package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;

@Repository

public interface OrderRepository extends JpaRepository<Order, Integer> {
	/*
	 * 出荷時間を更新
	 */
	@Modifying
	@Query("update Order o set o.ConfirmationTime=?2 where o.id =?1")
	public void updateConfirmationTime(Integer id, Timestamp ConfirmationTime);

	/*
	 * IDで取り出し
	 */
	@Query("select o from Order o where o.id =?1")
	public Order findid(Integer id);
	/*
	 * 検索
	 */

	@Query("select o from Order o where o.orderId like concat(:orderId,'%')")
	List<Order> selectByorderId(@Param("orderId") Integer orderId);

	@Query("select o from Order o where o.productId like concat(:productId,'%')")
	List<Order> selectByproductId(@Param("productId") Integer productId);

	@Query("select o from Order o where o.productInfo like %?1%")
	List<Order> selectByproductname(String productInfo);

	@Query("select o from Order o where o.userName like %?1%")
	List<Order> selectByusername(String userName);

	@Query("select o from Order o where o.statusByOrder like %?1%")
	List<Order> selectBystatusByOrder(String statusByOrder);

	@Query("select o from Order o where o.statusByBuy like %?1%")
	List<Order> selectBystatusByBuy(String statusByBuy);

	@Query("select o from Order o where o.total like concat(:total,'%')")
	List<Order> selectBytotal(@Param("total") Double total);

	@Query("select o from Order o where o.payment like concat(:payment,'%')")
	List<Order> selectBypayment(@Param("payment") Double payment);

	@Query("select o from Order o where o.address like %?1%")
	List<Order> selectByaddress(String address);

	@Query("select o from Order o where o.orderNumber like concat(:orderNumber,'%')")
	List<Order> selectByorderNumber(@Param("orderNumber") Integer orderNumber);

	/*
	 * 更新
	 */
	@Modifying
	@Query("update Order o set o.orderId=?2,o.productId=?3,o.productInfo=?4,o.userName=?5,o.statusByOrder=?6,o.statusByBuy=?7,o.total=?8,o.payment=?9,o.address=?10,o.orderNumber=?11 where o.id=?1")
	void updateOrderByid(Integer id, Integer orderId, Integer productId, String productInfo, String userName,
			String statusByOrder, String statusByBuy,
			Double total, Double payment, String address, Integer orderNumber);
}
