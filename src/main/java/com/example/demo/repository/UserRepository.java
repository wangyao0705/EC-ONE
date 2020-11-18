package com.example.demo.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

	/*
	 * useridで取り出す
	 */
	@Query("select u from User u where userId=?1")
	public User findbyuserId(String userId);

	/*
	 * idで取り出す
	 */
	@Query("select u from User u where id=?1")
	public User findbyid(Integer id);
	/*
	 * 检索
	 */

	@Query("select u from User u where u.userId like %?1%")
	public List<User> selectByuserId(String userId);

	@Query("select u from User u where u.userName like %?1%")
	public List<User> selectByuserName(String userName);

	@Query("select u from User u where u.tel like %?1%")
	public List<User> selectBytel(String tel);

	@Query("select u from User u where u.email like %?1%")
	public List<User> selectByemail(String email);

	@Query("select u from User u where u.sex like %?1%")
	public List<User> selectBysex(String sex);

	@Query("select u from User u where u.userStatus like %?1%")
	public List<User> selectByuserStatus(String userStatus);

	@Query("select u from User u where u.address like %?1%")
	public List<User> selectByaddress(String address);

	@Query("select u from User u where u.birth like %?1%")
	public List<User> selectBybirth(Date birth);

	/*
	 *更新
	 */
	@Modifying
	@Query("update User u set u.userId=?1,u.tel=?2,u.email=?3,u.sex=?4,u.address=?5,u.photo=?6,u.dateModified=?7,u.passWord=?8 where u.userId=?1")
	public void useredit(String userId, String tel, String email, String sex, String address, String photo,
			Timestamp dateModified, String passWord);
}
