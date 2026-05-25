package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.example.demo.entity.Mst_Place;
import com.example.demo.entity.Mst_Position;

/** 
 * メンバー一覧表示用フォーム
 * */
public class MemberForm {
	
	/** ID */
	@NotBlank
	@Length(max = 10)
	private String memberId;
	
	/** 名前 */
	@NotBlank
	@Length(max = 40)
	private String name;
	
	/** 年齢 */
	@NotNull(message = "年齢は必ず入力してください")
	private Integer age;
	
	/** 性別 */
	private Integer sex;
	
	/** 住所 */
	@NotBlank
	@Length(max = 50)
	private String address;
	
	/** 電話番号 */
	private String tel;
	
	/** mail */
	@Length(max = 20)
	private String mail;
	
	/** 役職id */
	private String positionId;
	
	/** 役職 */
	private Mst_Position position;
	
	/** 事業所id */
	private String placeId;
	
	/** 事業所 */
	private Mst_Place place;

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

	public Mst_Position getPosition() {
		return position;
	}

	public void setPosition(Mst_Position position) {
		this.position = position;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public Mst_Place getPlace() {
		return place;
	}

	public void setPlace(Mst_Place place) {
		this.place = place;
	}
	
	
}
