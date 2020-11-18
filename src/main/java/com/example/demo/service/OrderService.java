package com.example.demo.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;

@Service
@Transactional
public class OrderService {
	@Autowired
	OrderRepository orderRepository;
	/*
	 * 増加orders
	 */

	public String findorderAll(Order order) {
		orderRepository.save(order);
		return "";
	}
	/*
	 * 全てを取り出し
	 */

	public List<Order> findall() {
		return orderRepository.findAll();
	}

	/*
	 * IDで削除
	 */
	public void deleteid(Integer id) {
		orderRepository.deleteById(id);

	}

	/*
	 * IDで取り出し
	 */
	public Order findids(Integer id) {

		return orderRepository.findid(id);
	}
	/*
	 * 出荷時間を更新
	 */

	public void updatetime(Integer id, Timestamp ConfirmationTime) {
		orderRepository.updateConfirmationTime(id, ConfirmationTime);
	}
	/*
	 * 检索
	 */

	public List<Order> selectorderId(Integer orderId) {
		return orderRepository.selectByorderId(orderId);
	}

	public List<Order> selectproductId(Integer productId) {
		return orderRepository.selectByproductId(productId);
	}

	public List<Order> selectproductInfo(String productInfo) {
		return orderRepository.selectByproductname(productInfo);
	}

	public List<Order> selectuserName(String userName) {
		return orderRepository.selectByusername(userName);
	}

	public List<Order> selectstatusByOrder(String statusByOrder) {
		return orderRepository.selectBystatusByOrder(statusByOrder);
	}

	public List<Order> selectstatusByBuy(String statusByBuy) {
		return orderRepository.selectBystatusByBuy(statusByBuy);
	}

	public List<Order> selecttotal(Double total) {
		return orderRepository.selectBytotal(total);
	}

	public List<Order> selectpayment(Double payment) {
		return orderRepository.selectBypayment(payment);
	}

	public List<Order> selectaddress(String address) {
		return orderRepository.selectByaddress(address);
	}

	public List<Order> selectorderNumber(Integer orderNumber) {
		return orderRepository.selectByorderNumber(orderNumber);
	}

	/*
	 *更新
	 */
	public void updateOrderAll(Integer id, Integer orderId, Integer productId, String productInfo, String userName,
			String statusByOrder, String statusByBuy,
			Double total, Double payment, String address, Integer orderNumber) {
		orderRepository.updateOrderByid(id, orderId, productId, productInfo, userName, statusByOrder, statusByBuy,
				total, payment, address, orderNumber);
	}

	public Order orderidByid(Integer id) {
		return orderRepository.findid(id);

	}
}
