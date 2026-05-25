package com.example.demo.dto;

import java.time.LocalDateTime;

import org.springframework.boot.json.JsonWriter.Members;

import com.example.demo.entity.Mst_Place;
import com.example.demo.entity.Mst_Position;
import com.example.demo.entity.Tbl_Member;
import com.example.demo.form.MemberForm;

/** 
 * ユーザーDTO.
 * 
 * @see MemberForm
 * @see Members
 * */
public class MemberDto {
	
	/** ID */
	private String memberId;
	
	/** 名前 */
	private String name;
	
	/** 年齢 */
	private Integer age;
	
	/** 性別 */
	private Integer sex;

	/** 住所 */
	private String address;
	
	/** 電話番号 */
	private String tel;
	
	/** mail */
	private String mail;
	
	/** 役職id */
	private String positionId;
	
	/** 役職 */
	private Mst_Position mstPosition;
	
	/** 事業所id */
	private String placeId;
	
	/** 事業所 */
	private Mst_Place mstPlace;
	
	/** 登録日 */
	private LocalDateTime registDate;
	
	/** 更新日 */
	private LocalDateTime updateDate;
	
	/** 削除フラグ */
	private Integer deleteFlg;
	

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	/*
	 * タイムリーフの仕組み
	 * HTMLに ${member.sexName} を書く
	 *タイムリーフはsexNameフィールドを探すわけじゃなくて自動的に getSexName() というgetterを裏側で呼び出す。
     *メソッドが実行され、その場で sex の中身（0か1か）が判定されて、「男」や「女」という文字がHTMLに返される。
	 * */
	
	public String getSexName() {
		//性別が0なら男、それ以外は女を返す
        if (this.sex == 0) {
        	return "男";
        } else {
        	return "女";
        }
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public Mst_Position getMstPosition() {
		return mstPosition;
	}

	public void setMstPosition(Mst_Position position) {
		this.mstPosition = position;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public Mst_Place getMstPlace() {
		return mstPlace;
	}

	public void setMstPlace(Mst_Place place) {
		this.mstPlace = place;
	}

	public LocalDateTime getRegistDate() {
		return registDate;
	}

	public void setRegistDate(LocalDateTime registDate) {
		this.registDate = registDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Integer deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	
	
	/** Form→Dtoへの変換 */
	public static final MemberDto convertFormToDto(MemberForm form) {
		//各値をセットする
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberId(form.getMemberId());
		memberDto.setName(form.getName());
		memberDto.setAge(form.getAge());
		memberDto.setSex(form.getSex());
		memberDto.setAddress(form.getAddress());
		memberDto.setTel(form.getTel());
		memberDto.setMail(form.getMail());
		memberDto.setPositionId(form.getPositionId());
		memberDto.setMstPosition(form.getPosition());
		memberDto.setPlaceId(form.getPlaceId());
		memberDto.setMstPlace(form.getPlace());
		
		//DB登録用に現在日時を取得して登録日と更新日にセットする
		LocalDateTime nowDate = LocalDateTime.now();
		memberDto.setRegistDate(nowDate);
		memberDto.setUpdateDate(nowDate);
		
		//削除フラグは初期値としては0をセットする
		memberDto.setDeleteFlg(0);
		
		return memberDto;
	}
	
	/** Dto→Formへの変換 */
	public static final MemberForm convertDtoToForm(MemberDto dto) {
		//各値をセットする
		MemberForm memberForm = new MemberForm();
		memberForm.setMemberId(dto.getMemberId());
		memberForm.setName(dto.getName());
		memberForm.setAge(dto.getAge());
		memberForm.setSex(dto.getSex());
		memberForm.setAddress(dto.getAddress());
		memberForm.setTel(dto.getTel());
		memberForm.setMail(dto.getMail());
		memberForm.setPositionId(dto.getPositionId());
		memberForm.setPosition(dto.getMstPosition());
		memberForm.setPlaceId(dto.getPlaceId());
		memberForm.setPlace(dto.getMstPlace());
		
		return memberForm;
	}
	
	/** Dto→Entityへの変換 */
	public static final Tbl_Member convertDtoToEntity(MemberDto dto) {
		//各値をセットする
		Tbl_Member tblMem = new Tbl_Member();
		tblMem.setMemberId(dto.getMemberId());
		tblMem.setName(dto.getName());
		tblMem.setAge(dto.getAge());
		tblMem.setSex(dto.getSex());
		tblMem.setAddress(dto.getAddress());
		tblMem.setTel(dto.getTel());
		tblMem.setMail(dto.getMail());
		tblMem.setPositionId(dto.getPositionId());
		tblMem.setMstPosition(dto.getMstPosition());
		tblMem.setPlaceId(dto.getPlaceId());
		tblMem.setMstPlace(dto.getMstPlace());
		tblMem.setRegistDate(dto.getRegistDate());
		tblMem.setUpdateDate(dto.getUpdateDate());
		tblMem.setDeleteFlg(dto.getDeleteFlg());
		
		return tblMem;
	}
	
	/** Entity→Dtoへの変換 */
	public static final MemberDto convertEntityToDto(Tbl_Member entity) {
		//各値をセットする
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberId(entity.getMemberId());
		memberDto.setName(entity.getName());
		memberDto.setAge(entity.getAge());
		memberDto.setSex(entity.getSex());
		memberDto.setAddress(entity.getAddress());
		memberDto.setTel(entity.getTel());
		memberDto.setMail(entity.getMail());
		memberDto.setPositionId(entity.getPositionId());
		memberDto.setMstPosition(entity.getMstPosition());
		memberDto.setPlaceId(entity.getPlaceId());
		memberDto.setMstPlace(entity.getMstPlace());
		memberDto.setRegistDate(entity.getRegistDate());
		memberDto.setUpdateDate(entity.getUpdateDate());
		memberDto.setDeleteFlg(entity.getDeleteFlg());
		
		return memberDto;
	}
	
	/**更新前Dtoに更新した箇所だけ値をセットする*/
	public static final MemberDto memberDto(MemberDto memberDto, MemberDto oldMemberDto) {
		//各値をセットする
		oldMemberDto.setMemberId(memberDto.getMemberId());
		oldMemberDto.setName(memberDto.getName());
		oldMemberDto.setAge(memberDto.getAge());
		oldMemberDto.setSex(memberDto.getSex());
		oldMemberDto.setAddress(memberDto.getAddress());
		oldMemberDto.setTel(memberDto.getTel());
		oldMemberDto.setMail(memberDto.getMail());
		oldMemberDto.setPositionId(memberDto.getPositionId());
		oldMemberDto.setPlaceId(memberDto.getPlaceId());
		
		//更新用の変換なので更新日時のみセットする
		memberDto.setUpdateDate(LocalDateTime.now());
		
		return memberDto;
	}
	

}
