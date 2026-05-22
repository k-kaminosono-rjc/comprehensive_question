package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "mst_place")
@SQLRestriction("delete_flg = '0'")
public class Mst_Place {
	
	/** 事業所ID*/
	@Id
	@Column(name = "place_id")
	private String placeId;
	
	/** 事業所名*/
	@Column(name = "place_name")
	private String placeName;
	
	/** 住所*/
	@Column(name = "address")
	private String address;
	
	/** 電話番号*/
	@Column(name = "telephone")
	private String tel;
	
	/** メールアドレス*/
	@Column(name = "mail")
	private String mail;
	
	/** 登録日 */
	@Column(name = "regist_date")
	private LocalDateTime registDate;
	
	/** 更新日 */
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	
	/** 削除フラグ*/
	@Column(name = "delete_flg")
	private Integer deleteFlg;

	
	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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
