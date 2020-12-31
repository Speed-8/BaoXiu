package com.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//设备报修
@Entity
@Table(name="t_Baoxiu")
public class Baoxiu {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	@ManyToOne
	@JoinColumn(name="shebeiid")
	private Shebei shebei;//关联的设备，外键
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;//报修的用户，外键
	
	private String bianhao;//报修编号
	
	
	@Column(name="miaoshu", columnDefinition="TEXT")
	private String miaoshu;//故障描述
	
	private String didian;//报修地址
	
	private String chuli;//处理情况 未受理  处理中 维修完成  建议更换 取消申请  已申请更换
	
	private String shijian;//报修时间
	
	private String shijian2;//完成时间
	
	@Column(name="fankui", columnDefinition="TEXT")
	private String fankui;//处理反馈
	
	
	@ManyToOne
	@JoinColumn(name="user2id")
	private User user2;//处理的报修管理员，外键

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
	
	
	
	
	
	
}
