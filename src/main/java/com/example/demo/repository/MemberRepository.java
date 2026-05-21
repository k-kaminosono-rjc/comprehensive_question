package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Tbl_Member;

public interface MemberRepository extends JpaRepository<Tbl_Member, String> {
	
	

}
