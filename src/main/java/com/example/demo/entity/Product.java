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
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true)
	private Integer productId;
	private String productType;
	private String productInfo;
	private Double sales;
	private Double cost;
	private Integer stock;
	private Integer accessNumber = 0;
	private Timestamp dateCreated;
	private Timestamp dateModified;

}
