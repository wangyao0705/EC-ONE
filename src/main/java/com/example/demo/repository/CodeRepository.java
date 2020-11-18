package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Code;

@Repository

public interface CodeRepository extends JpaRepository<Code, Integer> {
	@Query("select c from Code c where c.username=?1")
	List<Code> findByusername(String username);
}
