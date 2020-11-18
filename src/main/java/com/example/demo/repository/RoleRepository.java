package com.example.demo.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Role;

@Repository

public interface RoleRepository extends JpaRepository<Role, Integer> {
	/*
	 *characterNameで取り出す
	 */
	@Query("select r from Role r where r.characterName=?1")
	Role findByCharacterNames(String characterName);

	/*
	 * 更新 状態 時間
	 */
	@Modifying
	@Query("update Role r set r.statusbycharacter=?2,r.dateModified=?3 where r.characterName=?1")
	void updateRole(String characterName, String statusbycharacter, Timestamp dateModifie);
}
