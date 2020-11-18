package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="control")
public class Control {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true)
	private String controlName;
	private String passWord;
	private String statusbycontrol;
	private String characterName;
	private String sex;
	private String tel;
	private Integer loginTimes=0;
	private Timestamp lastLogin;
	private Timestamp dateCreated;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getControlName() {
		return controlName;
	}
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getStatusbycontrol() {
		return statusbycontrol;
	}
	public void setStatusbycontrol(String statusbycontrol) {
		this.statusbycontrol = statusbycontrol;
	}
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	@Override
	public String toString() {
		return "Control [id=" + id + ", controlName=" + controlName + ", passWord=" + passWord + ", statusbycontrol="
				+ statusbycontrol + ", characterName=" + characterName + ", sex=" + sex + ", tel=" + tel
				+ ", loginTimes=" + loginTimes + ", lastLogin=" + lastLogin + ", dateCreated=" + dateCreated + "]";
	}

}
