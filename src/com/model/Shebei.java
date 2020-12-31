package com.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//设备
@Entity
@Table(name="t_Shebei")
public class Shebei {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	private String bianhao;//设备编号
	
	private String mingchen;//设备名称
	
	@Column(name="beizhu", columnDefinition="TEXT")
	private String beizhu;//备注
	
	@ManyToOne
	@JoinColumn(name="fenleiid")
	private Fenlei fenlei;//设备分类
	
	private String zhuangtai;//使用  库存  报修  更换  待报废 报废
	
	private String jiage;//采购价格
	
	private String rukushijian;//入库时间
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;//入库的库存管理员，外键
	
	private String chukushijian;//出库时间
	
	private String didian;//使用地点
	
	@Column(name="shuoming", columnDefinition="TEXT")
	private String shuoming;//使用说明
	
	
	private int cishu;//维修次数
	
	private String baofeishijian;//报废时间


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

	public String getBianhao() {
		return bianhao;
	}

	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}

	public Fenlei getFenlei() {
		return fenlei;
	}

	public void setFenlei(Fenlei fenlei) {
		this.fenlei = fenlei;
	}

	public String getZhuangtai() {
		return zhuangtai;
	}

	public void setZhuangtai(String zhuangtai) {
		this.zhuangtai = zhuangtai;
	}

	public String getJiage() {
		return jiage;
	}

	public void setJiage(String jiage) {
		this.jiage = jiage;
	}

	public String getRukushijian() {
		return rukushijian;
	}

	public void setRukushijian(String rukushijian) {
		this.rukushijian = rukushijian;
	}

	public String getChukushijian() {
		return chukushijian;
	}

	public void setChukushijian(String chukushijian) {
		this.chukushijian = chukushijian;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMingchen() {
		return mingchen;
	}

	public void setMingchen(String mingchen) {
		this.mingchen = mingchen;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getDidian() {
		return didian;
	}

	public void setDidian(String didian) {
		this.didian = didian;
	}

	public String getShuoming() {
		return shuoming;
	}

	public void setShuoming(String shuoming) {
		this.shuoming = shuoming;
	}

	public int getCishu() {
		return cishu;
	}

	public void setCishu(int cishu) {
		this.cishu = cishu;
	}

	public String getBaofeishijian() {
		return baofeishijian;
	}

	public void setBaofeishijian(String baofeishijian) {
		this.baofeishijian = baofeishijian;
	}
	
	
	
	
	
	
	
}
