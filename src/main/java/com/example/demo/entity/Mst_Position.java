package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "mst_position")
@SQLRestriction("delete_flg = '0'")
public class Mst_Position {
	
	/** 役職ID*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_id")
	private String positionId;
	
	/** 役職名*/
	@Column(name = "position_name")
	private String positionName;
	
	/** アクセス権限*/
	@Column(name = "access_authority")
	private Integer accessAuthority;
	
	/** 登録日*/
	@Column(name = "regist_date")
	private LocalDateTime registDate;
	
	
	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getAccessAuthority() {
		return accessAuthority;
	}

	public void setAccessAuthority(Integer accessAuthority) {
		this.accessAuthority = accessAuthority;
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

	/** 更新日*/
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	
	/** 削除フラグ*/
	@Column(name = "delete_flg")
	private Integer deleteFlg;
}
