package com.sam.secondKill.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "t_sec_kill_details")
//@Table(name = "t_sec_kill_details", uniqueConstraints = {
//		@UniqueConstraint(columnNames = { "killProductId", "telephone" }) })
public class SecondKillDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer killProductId;
//	private String telephone;
	private String status;
	private Timestamp createTime = new Timestamp(new Date().getTime());

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getKillProductId() {
		return killProductId;
	}

	public void setKillProductId(Integer killProductId) {
		this.killProductId = killProductId;
	}

//	public String getTelephone() {
//		return telephone;
//	}
//
//	public void setTelephone(String telephone) {
//		this.telephone = telephone;
//	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public SecondKillDetails() {
		super();
	}

}
