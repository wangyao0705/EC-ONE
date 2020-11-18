package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Control;

@Repository
public interface ControlLoginRepository extends JpaRepository<Control, Integer> {
	/*
	 * name and passwordを取り出し
	 */
	//c为一行数据的别名
	@Query(value = "select c from Control c where c.controlName=?1 and c.passWord=?2")
	List<Control> findBycontrolNameAndpassWord(String controlName, String passWord);

	/*
	 * 管理者nameで職位を取り出す
	 */
	List<Control> findByControlName(String controlName);

	/*
	 * 管理者nameで取り出す
	 */
	@Query("select c from Control c where c.controlName=?1")
	Control findBycontrolName(String controlName);

	//	//更新最后登录时间和登录回数
	//	@Modifying
	//	@Query("update Control c set c.lastLogin=?2,set c.loginTimes=?3, where c.controlName=?1")
	//	Control updateCotroldate(String controlName, Timestamp lastLogin, Integer loginTimes);

	//	//取出登录回数
	//	@Query("select c from Control c where c.controlName=?1")
	//	Control findByLoginTimes(String controlName);

}
