package com.example.demo.form;

import com.example.demo.entity.Mst_Place;
import com.example.demo.entity.Mst_Position;

/** 
 * メンバー一覧表示用フォーム
 * */
public class MemberForm {
	
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
	
	/** 役職名 */
	private Mst_Position position;
	
	/** 事業所id */
	private String placeId;
	
	/** 事業所名 */
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
