package com.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//设备入库
@Entity
@Table(name="t_Ruku")
public class Ruku {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	@ManyToOne
	@JoinColumn(name="shebeiid")
	private Shebei  shebei;//关联的设备，外键


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeletestatus() {
		return deletestatus;
	}

	public void setDeletestatus(int deletestatus) {
		this.deletestatus = deletestatus;
	}

	public Shebei getShebei() {
		return shebei;
	}

	public void setShebei(Shebei shebei) {
		this.shebei = shebei;
	}


	
	
	
	
}
