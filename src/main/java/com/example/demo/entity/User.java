package com.example.demo.entity;

import java.sql.Date;
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
@Table(name="userinfo")
public class User {
	//自增长
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String photo;
	//key
	@Column(unique=true)
	private String userId;
	private String passWord;
	private String userName;
	private String tel;
	private String email;
	private String sex;
	private String userStatus;
	private String address;
	private Date birth;
	private Timestamp dateCreated;
	private Timestamp dateModified;
}
