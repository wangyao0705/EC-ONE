package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Control;

@Repository
public interface ControlRepository extends JpaRepository<Control, Integer> {
	/*
	 * 管理者nameで取り出す
	 */
	//	@Query("select c from Control c where c.controlName=?1")
	List<Control> findByControlName(String controlName);

	/*
	 *  IDで取り出す
	 */

	@Query("select c from Control c where c.id=?1")
	Control findcontrolId(Integer id);

	//除了select都要用@Modifying
	//	@Modifying
	//用自增长id更新 修改查询只能使用void或int / Integer作为返回类型！ Modifying queries can only use void or int/Integer as return type!
	//update 使用void
	/*
	 * control更新
	 */
	@Modifying
	@Query("update Control c set c.controlName=?2,c.characterName=?3,c.statusbycontrol=?4,c.sex=?5,c.tel=?6 where c.id=?1")
	void updateCotrolByid(Integer id, String controlName, String characterName, String statusbycontrol, String sex,
			String tel);

	/*
	 * controlNameでpasswordを更新
	 */
	@Modifying
	@Query("update Control c set c.passWord=?2 where c.controlName=?1")
	void updatePassWord(String controlName, String passWord);

	/*
	 * 曖昧検索
	 */
	@Query("select c from Control c where c.controlName like %?1%")
	List<Control> selectControlName(String controlName);

	@Query("select c from Control c where c.characterName like %?1%")
	List<Control> selectCharacterName(String characterName);

	@Query("select c from Control c where c.statusbycontrol like %?1%")
	List<Control> selectStatus(String statusbycontrol);

	@Query("select c from Control c where c.sex like %?1%")
	List<Control> selectSex(String sex);

	@Query("select c from Control c where c.tel like %?1%")
	List<Control> selectTel(String tel);

	@Query("select c from Control c where c.loginTimes like %?1%")
	List<Control> selectLoginTime(String loginTimes);

}
