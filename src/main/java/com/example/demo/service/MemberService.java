package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Mst_Place;
import com.example.demo.entity.Mst_Position;
import com.example.demo.entity.Tbl_Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.Mst_PlaceRepository;
import com.example.demo.repository.Mst_PositionRepository;

@Service
public class MemberService  {
	
	//リポジトリをAutowiredする
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private Mst_PositionRepository positionRepository;
	
	@Autowired
	private Mst_PlaceRepository placeRepository;
	
	/** 
	 * メンバー一覧を返す
	 * */
	public List<MemberDto> getAll() {
		/*
		 * データベースから対象のメンバーを取得する
		 * entityからDtoに変換する
		 * 一覧をリターンする
		 * */
		List<Tbl_Member> member = memberRepository.findAll();
		List<MemberDto> userDtoList = member.stream()
				//メソッド参照。以下を省略している
				//.map(entity -> UserDto.convertEntityToDto(entity))
				.map(MemberDto::convertEntityToDto)
				.collect(Collectors.toList());
		return userDtoList;
	}
	
	
	/** 
	 * メンバーを返す
	 * @param memberId メンバーID
	 * @return member メンバー情報
	 * */
	public MemberDto getMember(String memberId) throws NotFoundException {
		/*
		 * memberIdを使用してDBから対象のデータを取得し、Optional型で受け取る
		 * isEmptyで値が存在しない場合、エラー画面を表示する
		 * 値が存在する場合、EntityからDtoに変換する
		 * Dtoをリターンする
		 * */
		Optional<Tbl_Member> member = memberRepository.findById(memberId);
		if(member.isEmpty()) {
			throw new NotFoundException();
		} 
		
		MemberDto memberDto = MemberDto.convertEntityToDto(member.get());
		return memberDto;
	}
	
	
	
	/** 
	 * 事業所一覧リストを返す
	 * */
	public List<Mst_Place> getPlace() {
		List<Mst_Place> placeList = placeRepository.findAll();
		return placeList;
	}
	
	
	/** 
	 * 役職一覧リストを返す
	 * */
	public List<Mst_Position> getPosition() {
		List<Mst_Position> positionList = positionRepository.findAll();
		return positionList;
	}
	
	
	/** 
	 * idを使って指定の事業所を取得
	 * @param id 事業所ID
	 * @return place 事業所情報
	 * */
	public Mst_Place placeById(String id) throws NotFoundException {
		/*
		 * 引数のidを使ってMst_Placeからデータを取得する
		 * Optionl型で受け取り、値が存在しな場合例外をthrowする
		 * 値が存在する場合その値を返す
		 * */
		Optional<Mst_Place> place = placeRepository.findById(id);
		if(place.isEmpty()) {
			throw new NotFoundException();
		} else {
			return place.get() ;
		}
	}
	
	
	/** 
	 * idを使って指定の役職を取得
	 * @param id 役職ID
	 * @return position 役職情報
	 * */
	public Mst_Position positionById(String id) throws NotFoundException {
		/*
		 * 引数のidを使ってMst_Positionからデータを取得する
		 * Optionl型で受け取り、値が存在しな場合例外をthrowする
		 * 値が存在する場合その値を返す
		 * */
		Optional<Mst_Position> position = positionRepository.findById(id);
		if(position.isEmpty()) {
			throw new NotFoundException();
		} else {
			return position.get() ;
		}
	}
	
	
	/** 
	 * メンバー登録処理
	 * @param dto メンバーDto
	 * */
	public Tbl_Member insert(MemberDto dto) {
		/*
		 * DtoからEntityに変換する
		 * Repositoryのsaveメソッドでデータを登録する
		 * */
		Tbl_Member tblMember = MemberDto.convertDtoToEntity(dto);
		return memberRepository.save(tblMember);
		
	}
	
	
	/** 
	 * メンバー削除処理
	 * @parma id メンバーID
	 * */
	public void delete(String id) {
		memberRepository.deleteById(id);
	}
	
	
	/** 
	 * データの存在有無を返す
	 * @param id メンバーID
	 * @return 存在する場合：true
	 * 			存在しない場合：false
	 * 
	 * */
	public boolean checkMember(String id) {
		//存在する場合：true
		//存在しない場合：false
		return memberRepository.existsById(id);
	}
	
	
	
	
	
	
	
	
	
	
}
