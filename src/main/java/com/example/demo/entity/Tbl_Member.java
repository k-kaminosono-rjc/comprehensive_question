package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "tbl_member")
@SQLRestriction("delete_flg = '0'")
public class Tbl_Member {
	
	/** ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private String memberId;
	
	/** 名前 */
	@Column(name = "member_name")
	private String name;
	
	/** 年齢 */
	@Column(name = "age")
	private Integer age;
	
	/** 性別 */
	@Column(name = "sex_flg")
	private Integer sex;
	
	/** 住所 */
	@Column(name = "address")
	private String address;
	
	/** 電話番号 */
	@Column(name = "telephone")
	private String tel;
	
	/** mail */
	@Column(name = "mail")
	private String mail;
	
	/** 役職id */
	@Column(name = "position_id")
	private String positionId;
	
	/** 役職 */
	@ManyToOne
	@JoinColumn(name = "position_id", insertable = false, updatable = false)
	private Mst_Position mstPosition;
	
	/** 事業所id */
	@Column(name = "place_id")
	private String placeId;
	
	/** 事務所 */
	@ManyToOne
	@JoinColumn(name = "place_id", insertable = false, updatable = false)
	private Mst_Place mstPlace;
	
	/** 登録日 */
	@Column(name = "regist_date")
	private LocalDateTime registDate;
	
	/** 更新日 */
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	
	/** 削除フラグ */
	@Column(name = "delete_flg")
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

	public void setMstPosition(Mst_Position mstPosition) {
		this.mstPosition = mstPosition;
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

	public void setMstPlace(Mst_Place mstPlace) {
		this.mstPlace = mstPlace;
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
	
}
