package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer productId;
	@Column(unique=true)
	private Integer orderId;
	private String productInfo;
	private String userName;
	private String statusByOrder;
	private String statusByBuy;
	private Double total;
	private Double payment;
	private String address;
	private Integer orderNumber;
	private Timestamp payTime;
	private Timestamp ConfirmationTime;
}
