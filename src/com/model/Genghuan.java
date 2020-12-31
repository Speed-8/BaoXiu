package com.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//设备更换
@Entity
@Table(name="t_Genghuan")
public class Genghuan {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	@ManyToOne
	@JoinColumn(name="shebeiid")
	private Shebei shebei;//更换的设备，外键
	
	@ManyToOne
	@JoinColumn(name="shebei2id")
	private Shebei shebei2;//更换后的设备，外键
	
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;//更换的用户，外键
	
	private String bianhao;//更换编号
	
	
	@Column(name="miaoshu", columnDefinition="TEXT")
	private String miaoshu;//情况描述
	
	private String didian;//更换地点
	
	private String chuli;//处理情况 未受理  处理中 更换完成  不同意更换  取消申请  
	
	private String shijian;//申请时间
	
	private String shijian2;//完成时间
	
	@Column(name="fankui", columnDefinition="TEXT")
	private String fankui;//处理反馈
	
	
	@ManyToOne
	@JoinColumn(name="user2id")
	private User user2;//处理的库存管理员，外键

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMiaoshu() {
		return miaoshu;
	}

	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}

	public String getChuli() {
		return chuli;
	}

	public void setChuli(String chuli) {
		this.chuli = chuli;
	}

	public String getShijian() {
		return shijian;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	public String getShijian2() {
		return shijian2;
	}

	public void setShijian2(String shijian2) {
		this.shijian2 = shijian2;
	}

	public String getDidian() {
		return didian;
	}

	public void setDidian(String didian) {
		this.didian = didian;
	}

	public String getBianhao() {
		return bianhao;
	}

	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}

	public String getFankui() {
		return fankui;
	}

	public void setFankui(String fankui) {
		this.fankui = fankui;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public Shebei getShebei2() {
		return shebei2;
	}

	public void setShebei2(Shebei shebei2) {
		this.shebei2 = shebei2;
	}
	
	
	
	
	
	
}
