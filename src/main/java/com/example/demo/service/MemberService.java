package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Tbl_Member;
import com.example.demo.repository.MemberRepository;

@Service
public class MemberService  {
	
	//リポジトリをAutowiredする
	@Autowired
	private MemberRepository memberRepository;
	
	/** 
	 * メンバー一覧を返す
	 * */
	public List<MemberDto> getAll() {
		//データベースから対象のメンバーを取得する
		List<Tbl_Member> member = memberRepository.findAll();
		//entityからDtoに変換する
		List<MemberDto> userDtoList = member.stream()
				//メソッド参照。以下を省略している
				//.map(entity -> UserDto.convertEntityToDto(entity))
				.map(MemberDto::convertEntityToDto)
				.collect(Collectors.toList());
		
		//一覧をリターンする
		return userDtoList;
	}
	
}
