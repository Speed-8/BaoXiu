package com.action;




import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.dao.BaoxiuDao;
import com.dao.ChukuDao;
import com.dao.FenleiDao;
import com.dao.GenghuanDao;
import com.dao.KucunDao;
import com.dao.RukuDao;
import com.dao.ShebeiDao;
import com.dao.UserDao;
import com.model.Baoxiu;
import com.model.Chuku;
import com.model.Fenlei;
import com.model.Genghuan;
import com.model.Kucun;
import com.model.Ruku;
import com.model.Shebei;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.util.Pager;
import com.util.Util;

public class ManageAction extends ActionSupport {

	private static final long serialVersionUID = -4304509122548259589L;

	private UserDao userDao;

	private String url = "./";

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	
	
	
//登入请求
	public String login() throws IOException {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String  role = request.getParameter("role");
		User user = userDao.selectBean(" where username = '" + username
				+ "' and password= '" + password + "' and role= "+role +" and deletestatus=0 ");
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			this.setUrl("index.jsp");
			return "redirect";
		} else {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");
			response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('用户名或者密码错误');window.location.href='login.jsp';</script>");
		}
		return null;
	}
//用户退出
	public String loginout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		this.setUrl("login.jsp");
		return SUCCESS;
	}
//跳转到修改密码页面
	public String changepwd() {
		this.setUrl("password2.jsp");
		return SUCCESS;
	}
//修改密码操作
	public void changepwd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		
		User bean = userDao.selectBean(" where username= '"+u.getUsername()+"' and password= '"+password1+"' and deletestatus=0");
		if(bean!=null){
			String dianhua = request.getParameter("dianhua");
			if(!bean.getDianhua().equals(dianhua)){
				response
				.getWriter()
				.print(
						"<script language=javascript>alert('修改失败，手机号不正确');window.location.href='method!changepwd';</script>");
				return;
			}
			
			bean.setPassword(password2);
			userDao.updateBean(bean);
			
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('修改成功');window.location.href='method!changepwd';</script>");
		}else{
		
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('原密码错误');window.location.href='method!changepwd';</script>");
		}
	}
	
	

	//跳转到修改密码页面
		public String changepwd3() {
			this.setUrl("password2.jsp");
			return SUCCESS;
		}
	//修改密码操作
		public void changepwd4() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			HttpSession session = request.getSession();
			User u = (User)session.getAttribute("user");
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");

			
			User bean = userDao.selectBean(" where username= '"+u.getUsername()+"' and password= '"+password1+"' and deletestatus=0");
			if(bean!=null){
				
				
				bean.setPassword(password2);
				userDao.updateBean(bean);
				
				response
						.getWriter()
						.print(
								"<script language=javascript>alert('修改成功');window.location.href='method!changepwd3';</script>");
			}else{
			
				response
						.getWriter()
						.print(
								"<script language=javascript>alert('原密码错误');window.location.href='method!changepwd3';</script>");
			}
		}
	
	//库存管理员信息列表
	public String userlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String username = request.getParameter("username");
		
		String xingming = request.getParameter("xingming");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (username != null && !"".equals(username)) {

			sb.append("username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}
		
		if (xingming != null && !"".equals(xingming)) {

			sb.append("xingming like '%" + xingming + "%'");
			sb.append(" and ");
			request.setAttribute("xingming", xingming);
		}
		
		sb.append("   deletestatus=0 and role=1 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist?menu="+menu);
		request.setAttribute("url2", "method!user");
		request.setAttribute("title", "库存管理员信息管理");
		this.setUrl("user/userlist.jsp");
		return SUCCESS;

	}
//跳转到添加库存管理员信息页面
	public String useradd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		

		
		request.setAttribute("url", "method!useradd2?menu="+menu);
		request.setAttribute("title", "添加新库存管理员");
		this.setUrl("user/useradd.jsp");
		return SUCCESS;
	}
	


//添加库存管理员信息操作
	public void useradd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String menu = request.getParameter("menu");
		

		String xingming = request.getParameter("xingming");
		String username = request.getParameter("username");
		String dianhua = request.getParameter("dianhua");

		User bean = userDao.selectBean(" where deletestatus=0  and username='"+username+"' ");
		if(bean==null){
			bean = new User();
			
			bean.setCreatetime(Util.getRiqi());
		
			bean.setPassword("111111");
			bean.setRole(1);
			bean.setDianhua(dianhua);
			bean.setUsername(username);
			bean.setXingming(xingming);
			
			
			userDao.insertBean(bean);
			response
			.getWriter()
			.print("<script language=javascript>alert('操作成功');window.location.href='method!userlist?menu="+menu+"';</script>");
		}else{
			response
			.getWriter()
			.print("<script language=javascript>alert('操作失败，该用户名已经存在');window.location.href='method!userlist?menu="+menu+"';</script>");
		}
	
		
		
		
	}
//跳转到更新库存管理员信息页面
	public String userupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!userupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "库存管理员信息修改");
		this.setUrl("user/userupdate.jsp");
		return SUCCESS;
	}
//更新库存管理员信息操作
	public void userupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();


		String dianhua = request.getParameter("dianhua");
		String xingming = request.getParameter("xingming");

		
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		

		bean.setDianhua(dianhua);
	

		bean.setXingming(xingming);
		
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String menu = request.getParameter("menu");
		response
			.getWriter()
			.print("<script language=javascript>alert('操作成功');window.location.href='method!userlist?menu="+menu+"';</script>");
	}
//删除库存管理员信息操作
	public void userdelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "+ request.getParameter("id"));
		bean.setDeletestatus(1);
		userDao.updateBean(bean);
	
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String menu = request.getParameter("menu");
		response
			.getWriter()
			.print("<script language=javascript>alert('操作成功');window.location.href='method!userlist?menu="+menu+"';</script>");
	}
	
	//跳转到查看库存管理员信息页面
	public String userupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "库存管理员信息查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("user/userupdate3.jsp");
		return SUCCESS;
	}
	
	
	private FenleiDao fenleiDao;

	public FenleiDao getFenleiDao() {
		return fenleiDao;
	}

	public void setFenleiDao(FenleiDao fenleiDao) {
		this.fenleiDao = fenleiDao;
	}
	
	
	//设备分类信息列表
	public String fenleilist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fname = request.getParameter("fname");
		
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (fname != null && !"".equals(fname)) {

			sb.append("fname like '%" + fname + "%'");
			sb.append(" and ");
			request.setAttribute("fname", fname);
		}
		
		
	
		sb.append("   deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = fenleiDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", fenleiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!fenleilist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!fenleilist?menu="+menu);
		request.setAttribute("url2", "method!fenlei");
		request.setAttribute("title", "设备分类信息管理");
		this.setUrl("fenlei/fenleilist.jsp");
		return SUCCESS;

	}
//跳转到添加设备分类信息页面
	public String fenleiadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		request.setAttribute("url", "method!fenleiadd2?menu="+menu);
		request.setAttribute("title", "添加新设备分类");
		this.setUrl("fenlei/fenleiadd.jsp");
		return SUCCESS;
	}
	

//添加设备分类信息操作
	public void fenleiadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		
		
		String fname = request.getParameter("fname");
		
		Fenlei bean = new Fenlei();
		bean.setFname(fname);
		fenleiDao.insertBean(bean);
		
		String menu = request.getParameter("menu");
		response
		.getWriter()
		.print("<script language=javascript>alert('操作成功');window.location.href='method!fenleilist?menu="+menu+"';</script>");
		
		
	}
//跳转到更新设备分类信息页面
	public String fenleiupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		Fenlei bean = fenleiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!fenleiupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "设备分类信息修改");
		this.setUrl("fenlei/fenleiupdate.jsp");
		return SUCCESS;
	}
//更新设备分类信息操作
	public void fenleiupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();

		String fname = request.getParameter("fname");

		
		Fenlei bean = fenleiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setFname(fname);
		
		fenleiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String menu = request.getParameter("menu");
		response
			.getWriter()
			.print("<script language=javascript>alert('操作成功');window.location.href='method!fenleilist?menu="+menu+"';</script>");
	}
//删除设备分类信息操作
	public void fenleidelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Fenlei bean = fenleiDao.selectBean(" where id= "+ request.getParameter("id"));
		bean.setDeletestatus(1);
		fenleiDao.updateBean(bean);
	
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String menu = request.getParameter("menu");
		response
			.getWriter()
			.print("<script language=javascript>alert('操作成功');window.location.href='method!fenleilist?menu="+menu+"';</script>");
	}
	
	//跳转到查看设备分类信息页面
	public String fenleiupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Fenlei bean = fenleiDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "设备分类信息查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("fenlei/fenleiupdate3.jsp");
		return SUCCESS;
	}
	
	
	private RukuDao rukuDao;

	public RukuDao getRukuDao() {
		return rukuDao;
	}

	public void setRukuDao(RukuDao rukuDao) {
		this.rukuDao = rukuDao;
	}
	
	
	private ShebeiDao shebeiDao;

	public ShebeiDao getShebeiDao() {
		return shebeiDao;
	}

	public void setShebeiDao(ShebeiDao shebeiDao) {
		this.shebeiDao = shebeiDao;
	}
	
	
	
	//入库列表
	public String rukulist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String bianhao = request.getParameter("bianhao");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (bianhao != null && !"".equals(bianhao)) {

			sb.append(" shebei.bianhao like '%" + bianhao + "%'");
			sb.append(" and ");
			request.setAttribute("bianhao", bianhao);
		}
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
	
		sb.append(" shebei.user.id="+user.getId()+" and    deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = rukuDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", rukuDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!rukulist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!rukulist?menu="+menu);
		request.setAttribute("url2", "method!ruku");
		request.setAttribute("title", "入库管理");
		this.setUrl("ruku/rukulist.jsp");
		return SUCCESS;

	}
	
//跳转到设备入库页面
	public String rukuadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		request.setAttribute("fenleilist", fenleiDao.selectBeanList(0, 9999, " where deletestatus=0 "));
		
		request.setAttribute("url", "method!rukuadd2?menu="+menu);
		request.setAttribute("title", "设备入库");
		this.setUrl("ruku/rukuadd.jsp");
		return SUCCESS;
	}
	

//设备入库操作
	public void rukuadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		
		
		String jiage = request.getParameter("jiage");
		String beizhu = request.getParameter("beizhu");
		String bianhao = request.getParameter("bianhao");
		String fenleiid = request.getParameter("fenleiid");
		String mingchen = request.getParameter("mingchen");
		String shuoming = request.getParameter("shuoming");

		String rukushijian = Util.getTime();

		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		Shebei bean = shebeiDao.selectBean(" where deletestatus=0 and bianhao='"+bianhao+"'");
		if(bean==null){
			Fenlei fenlei = fenleiDao.selectBean(" where id= "+fenleiid);
			bean = new Shebei();
			bean.setShuoming(shuoming);
			bean.setBeizhu(beizhu);
			bean.setBianhao(bianhao);
			bean.setFenlei(fenlei);
			bean.setJiage(jiage);
			bean.setMingchen(mingchen);
			bean.setRukushijian(rukushijian);
			bean.setUser(user);
			bean.setZhuangtai("库存");
			
			shebeiDao.insertBean(bean);
			
			Ruku ruku = new Ruku();
			ruku.setShebei(bean);
			rukuDao.insertBean(ruku);
			
			Kucun kucun = kucunDao.selectBean(" where deletestatus=0 and fenlei.id= "+fenlei.getId());
			if(kucun==null){
				kucun = new Kucun();
				kucun.setFenlei(fenlei);
				kucun.setSl(1);
				kucunDao.insertBean(kucun);
			}else{
				kucun.setSl(kucun.getSl()+1);
				kucunDao.updateBean(kucun);
			}
			
			String menu = request.getParameter("menu");
			response
			.getWriter()
			.print("<script language=javascript>alert('操作成功');window.location.href='method!rukulist?menu="+menu+"';</script>");
		}else{
			String menu = request.getParameter("menu");
			response
			.getWriter()
			.print("<script language=javascript>alert('操作失败，该设备编号已经存在！');window.location.href='method!rukulist?menu="+menu+"';</script>");
		}

	}
	
	
//跳转到修改入库信息页面
	public String rukuupdate() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		Ruku bean = rukuDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		Shebei shebei = shebeiDao.selectBean(" where id= "+bean.getShebei().getId());
		if(!"库存".equals(shebei.getZhuangtai())){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
				.getWriter()
				.print("<script language=javascript>alert('操作失败，该设备不是库存状态！');window.location.href='method!rukulist?menu="+menu+"';</script>");
			return null;
		}
		
		
		
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!rukuupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "修改入库信息");
		this.setUrl("ruku/rukuupdate.jsp");
		return SUCCESS;
	}
//修改入库信息操作
	public void rukuupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();

		String beizhu = request.getParameter("beizhu");
		String jiage = request.getParameter("jiage");
		String mingchen = request.getParameter("mingchen");
		String shuoming = request.getParameter("shuoming");

		
		Ruku ruku = rukuDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		Shebei bean = shebeiDao.selectBean(" where id= "+ruku.getShebei().getId());
		
		bean.setBeizhu(beizhu);
		bean.setJiage(jiage);
		bean.setMingchen(mingchen);
		bean.setShuoming(shuoming);
		shebeiDao.updateBean(bean);
		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String menu = request.getParameter("menu");
		response
			.getWriter()
			.print("<script language=javascript>alert('操作成功');window.location.href='method!rukulist?menu="+menu+"';</script>");
	}
	
//取消入库操作
	public void rukudelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menu = request.getParameter("menu");
		Ruku bean = rukuDao.selectBean(" where id= "+ request.getParameter("id"));
		
		Shebei shebei = shebeiDao.selectBean(" where id= "+bean.getShebei().getId());
		
		if(!"库存".equals(shebei.getZhuangtai())){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
				.getWriter()
				.print("<script language=javascript>alert('操作失败，该设备不是库存状态！');window.location.href='method!rukulist?menu="+menu+"';</script>");
			return ;
		}
		
		bean.setDeletestatus(1);
		rukuDao.updateBean(bean);
		
	
		
		shebei.setDeletestatus(1);
		
		shebeiDao.updateBean(shebei);
		
		
		Kucun kucun = kucunDao.selectBean(" where deletestatus=0 and fenlei.id= "+shebei.getFenlei().getId());
		kucun.setSl(kucun.getSl()-1);
		kucunDao.updateBean(kucun);
		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		
		response
			.getWriter()
			.print("<script language=javascript>alert('操作成功');window.location.href='method!rukulist?menu="+menu+"';</script>");
	}
	
	//跳转到查看入库页面
	public String rukuupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Ruku bean = rukuDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "入库查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("ruku/rukuupdate3.jsp");
		return SUCCESS;
	}
	
	private KucunDao kucunDao;

	public KucunDao getKucunDao() {
		return kucunDao;
	}

	public void setKucunDao(KucunDao kucunDao) {
		this.kucunDao = kucunDao;
	}
	
	
	//库存列表
	public String kucunlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String fname = request.getParameter("fname");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (fname != null && !"".equals(fname)) {

			sb.append(" fenlei.fname like '%" + fname + "%'");
			sb.append(" and ");
			request.setAttribute("fname", fname);
		}
		
		
	
	
		sb.append("    deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = kucunDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", kucunDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!kucunlist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!kucunlist?menu="+menu);
		request.setAttribute("url2", "method!kucun");
		request.setAttribute("title", "库存查询");
		this.setUrl("kucun/kucunlist.jsp");
		return SUCCESS;

	}
	
	
	//库存设备查询
	public String shebeilist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String fenleiid = request.getParameter("fenleiid");
		
		request.setAttribute("fenleiid",fenleiid );
		
		
		String bianhao = request.getParameter("bianhao");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (bianhao != null && !"".equals(bianhao)) {

			sb.append(" bianhao like '%" + bianhao + "%'");
			sb.append(" and ");
			request.setAttribute("bianhao", bianhao);
		}
		
		
	
	
		sb.append("  fenlei.id="+fenleiid+"  and zhuangtai='库存' and  deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shebeiDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", shebeiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shebeilist?menu="+menu+"&fenleiid="+fenleiid, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shebeilist?menu="+menu+"&fenleiid="+fenleiid);
		request.setAttribute("url2", "method!shebei");
		request.setAttribute("title", "库存设备查询");
		this.setUrl("shebei/shebeilist.jsp");
		return SUCCESS;

	}
	
	
	//跳转到查看设备页面
	public String shebeiupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shebei bean = shebeiDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "设备查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("shebei/shebeiupdate3.jsp");
		return SUCCESS;
	}
	
	
	private ChukuDao chukuDao;

	public ChukuDao getChukuDao() {
		return chukuDao;
	}

	public void setChukuDao(ChukuDao chukuDao) {
		this.chukuDao = chukuDao;
	}
	
	
	//库存列表
	public String kucunlist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String fname = request.getParameter("fname");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (fname != null && !"".equals(fname)) {

			sb.append(" fenlei.fname like '%" + fname + "%'");
			sb.append(" and ");
			request.setAttribute("fname", fname);
		}
		
		
	
	
		sb.append("    deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = kucunDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", kucunDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!kucunlist2?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!kucunlist2?menu="+menu);
		request.setAttribute("url2", "method!kucun");
		request.setAttribute("title", "设备出库");
		this.setUrl("kucun/kucunlist2.jsp");
		return SUCCESS;

	}
	
	
	//设备出库
	public String shebeilist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String fenleiid = request.getParameter("fenleiid");
		
		request.setAttribute("fenleiid",fenleiid );
		
		
		String bianhao = request.getParameter("bianhao");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (bianhao != null && !"".equals(bianhao)) {

			sb.append(" bianhao like '%" + bianhao + "%'");
			sb.append(" and ");
			request.setAttribute("bianhao", bianhao);
		}
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
	
		sb.append("  fenlei.id="+fenleiid+"  and zhuangtai='库存' and  user.id="+user.getId()+" and  deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shebeiDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", shebeiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shebeilist2?menu="+menu+"&fenleiid="+fenleiid, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shebeilist2?menu="+menu+"&fenleiid="+fenleiid);
		request.setAttribute("url2", "method!shebei");
		request.setAttribute("title", "设备出库");
		this.setUrl("shebei/shebeilist2.jsp");
		return SUCCESS;

	}
	
	
	//出库列表
	public String chukulist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String bianhao = request.getParameter("bianhao");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (bianhao != null && !"".equals(bianhao)) {

			sb.append(" shebei.bianhao like '%" + bianhao + "%'");
			sb.append(" and ");
			request.setAttribute("bianhao", bianhao);
		}
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
	
		sb.append(" shebei.user.id="+user.getId()+" and    deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = chukuDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", chukuDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!chukulist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!chukulist?menu="+menu);
		request.setAttribute("url2", "method!chuku");
		request.setAttribute("title", "出库记录查询");
		this.setUrl("chuku/chukulist.jsp");
		return SUCCESS;

	}
	
//跳转到设备出库页面
	public String chukuadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		String shebeiid = request.getParameter("shebeiid");
		Shebei shebei = shebeiDao.selectBean(" where id= "+shebeiid);
		request.setAttribute("shebei", shebei);
		
		request.setAttribute("url", "method!chukuadd2?menu="+menu+"&shebeiid="+shebeiid);
		request.setAttribute("title", "设备出库");
		this.setUrl("chuku/chukuadd.jsp");
		return SUCCESS;
	}
	

//设备出库操作
	public void chukuadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		
		
		String shebeiid = request.getParameter("shebeiid");
		Shebei shebei = shebeiDao.selectBean(" where id= "+shebeiid);
		
		

		String chukushijian = Util.getTime();
		String didian = request.getParameter("didian");
		String shuoming = request.getParameter("shuoming");
		
		
		Chuku chuku = new Chuku();
		
		chuku.setShebei(shebei);
		
		chukuDao.insertBean(chuku);
		
		
		shebei.setChukushijian(chukushijian);
		shebei.setDidian(didian);
		shebei.setShuoming(shuoming);
		shebei.setZhuangtai("使用");
		shebeiDao.updateBean(shebei);

		
		Kucun kucun = kucunDao.selectBean(" where deletestatus=0 and fenlei.id= "+shebei.getFenlei().getId());
		
		kucun.setSl(kucun.getSl()-1);
		kucunDao.updateBean(kucun);
		
		String menu = request.getParameter("menu");
		response
		.getWriter()
		.print("<script language=javascript>alert('操作成功');window.location.href='method!chukulist?menu="+menu+"';</script>");
		
	}
	
	
	
	
	

	//跳转到修改出库信息页面
		public String chukuupdate() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			String menu = request.getParameter("menu");
			request.setAttribute("menu",menu );
			
			
			Chuku bean = chukuDao.selectBean(" where id= "
					+ request.getParameter("id"));
			
			Shebei shebei = shebeiDao.selectBean(" where id= "+bean.getShebei().getId());
			if(!"使用".equals(shebei.getZhuangtai())){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作失败，该设备不是使用状态！');window.location.href='method!chukulist?menu="+menu+"';</script>");
				return null;
			}
			
			
			
			request.setAttribute("bean", bean);
			request.setAttribute("url", "method!chukuupdate2?id="+bean.getId()+"&menu="+menu);
			request.setAttribute("title", "修改出库信息");
			this.setUrl("chuku/chukuupdate.jsp");
			return SUCCESS;
		}
	//修改出库信息操作
		public void chukuupdate2() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();

			String didian = request.getParameter("didian");
			String shuoming = request.getParameter("shuoming");


			
			Chuku chuku = chukuDao.selectBean(" where id= "
					+ request.getParameter("id"));
			
			Shebei bean = shebeiDao.selectBean(" where id= "+chuku.getShebei().getId());
			
			bean.setDidian(didian);
			bean.setShuoming(shuoming);


			shebeiDao.updateBean(bean);
			
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			String menu = request.getParameter("menu");
			response
				.getWriter()
				.print("<script language=javascript>alert('操作成功');window.location.href='method!chukulist?menu="+menu+"';</script>");
		}
		
	//取消出库操作
		public void chukudelete() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			String menu = request.getParameter("menu");
			Chuku bean = chukuDao.selectBean(" where id= "+ request.getParameter("id"));
			
			Shebei shebei = shebeiDao.selectBean(" where id= "+bean.getShebei().getId());
			
			if(!"使用".equals(shebei.getZhuangtai())){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作失败，该设备不是使用状态！');window.location.href='method!chukulist?menu="+menu+"';</script>");
				return ;
			}
			
			bean.setDeletestatus(1);
			chukuDao.updateBean(bean);
			
		
			shebei.setZhuangtai("库存");
			shebei.setDidian(null);
			shebei.setShuoming(null);
			shebei.setChukushijian(null);
			
			shebeiDao.updateBean(shebei);
			
			
			Kucun kucun = kucunDao.selectBean(" where deletestatus=0 and fenlei.id= "+shebei.getFenlei().getId());
			kucun.setSl(kucun.getSl()+1);
			kucunDao.updateBean(kucun);
			
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			
			response
				.getWriter()
				.print("<script language=javascript>alert('操作成功');window.location.href='method!chukulist?menu="+menu+"';</script>");
		}
		
		//跳转到查看出库页面
		public String chukuupdate3() {
			HttpServletRequest request = ServletActionContext.getRequest();
			Chuku bean = chukuDao.selectBean(" where id= "+ request.getParameter("id"));
			request.setAttribute("bean", bean);
			request.setAttribute("title", "出库查看");
			String menu = request.getParameter("menu");
			request.setAttribute("menu",menu );
			this.setUrl("chuku/chukuupdate3.jsp");
			return SUCCESS;
		}
		
	
		
		

		//用户注册
			public void register() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				
			
				String dianhua = request.getParameter("dianhua");
				String password = request.getParameter("password");
				String shengri = request.getParameter("shengri");
				String username = request.getParameter("username");
				String xingbie = request.getParameter("xingbie");
				String xingming = request.getParameter("xingming");
				
				
				
				
				User bean  = userDao.selectBean(" where deletestatus=0 and username='"+username+"' ");
				if(bean==null){
					bean = new User();
					bean.setCreatetime(Util.getTime());
					bean.setDianhua(dianhua);
					bean.setPassword(password);
					bean.setRole(2);
					bean.setShengri(shengri);
					bean.setUsername(username);
					bean.setXingbie(xingbie);
					bean.setXingming(xingming);
					userDao.insertBean(bean);
					
					
					response
					.getWriter()
					.print("<script language=javascript>alert('注册成功');window.location.href='login.jsp';</script>");
					
				}else{
					response
					.getWriter()
					.print("<script language=javascript>alert('注册失败，该用户名已经存在');window.location.href='register.jsp';</script>");
				}

			}
			
			
			
			//设备信息查询
			public String shebeilist3() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				

				
				String bianhao = request.getParameter("bianhao");
				
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append(" bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				
			
			
				sb.append("  zhuangtai='使用' and  deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = shebeiDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", shebeiDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!shebeilist3?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!shebeilist3?menu="+menu);
				request.setAttribute("url2", "method!shebei");
				request.setAttribute("title", "设备信息查询");
				this.setUrl("shebei/shebeilist3.jsp");
				return SUCCESS;

			}
			
			
			//跳转到查看设备页面
			public String shebeiupdate5() {
				HttpServletRequest request = ServletActionContext.getRequest();
				Shebei bean = shebeiDao.selectBean(" where id= "+ request.getParameter("id"));
				request.setAttribute("bean", bean);
				request.setAttribute("title", "设备查看");
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				this.setUrl("shebei/shebeiupdate5.jsp");
				return SUCCESS;
			}
			
			
			//设备信息查询
			public String shebeilist4() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				

				
				String bianhao = request.getParameter("bianhao");
				
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append(" bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				
			
			
				sb.append("  zhuangtai='使用'  and deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = shebeiDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", shebeiDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!shebeilist4?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!shebeilist4?menu="+menu);
				request.setAttribute("url2", "method!shebei");
				request.setAttribute("title", "设备报修");
				this.setUrl("shebei/shebeilist4.jsp");
				return SUCCESS;

			}
			
			private BaoxiuDao baoxiuDao;

			public BaoxiuDao getBaoxiuDao() {
				return baoxiuDao;
			}

			public void setBaoxiuDao(BaoxiuDao baoxiuDao) {
				this.baoxiuDao = baoxiuDao;
			}
	
			
			//我的报修进度管理
			public String baoxiulist() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String bianhao = request.getParameter("bianhao");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
			
				sb.append("  user.id="+user.getId()+" and  deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = baoxiuDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", baoxiuDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!baoxiulist?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!baoxiulist?menu="+menu);
				request.setAttribute("url2", "method!baoxiu");
				request.setAttribute("title", "我的报修进度管理");
				this.setUrl("baoxiu/baoxiulist.jsp");
				return SUCCESS;

			}
			
		//跳转到添加报修信息页面
			public String baoxiuadd() {
				HttpServletRequest request = ServletActionContext.getRequest();
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				
				String shebeiid = request.getParameter("shebeiid");
				
				Shebei shebei = shebeiDao.selectBean(" where id= "+shebeiid);
				
				request.setAttribute("shebei", shebei);
				
				request.setAttribute("url", "method!baoxiuadd2?menu="+menu+"&shebeiid="+shebeiid);
				request.setAttribute("title", "设备报修");
				this.setUrl("baoxiu/baoxiuadd.jsp");
				return SUCCESS;
			}
			

		//添加报修信息操作
			public void baoxiuadd2() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				
				
				String didian = request.getParameter("didian");
				String miaoshu = request.getParameter("miaoshu");
				
				String shebeiid = request.getParameter("shebeiid");
				
				Shebei shebei = shebeiDao.selectBean(" where id= "+shebeiid);
				
				
				
				Baoxiu bean = new Baoxiu();
				
				
				bean.setBianhao(Util.getTime2());
				bean.setChuli("未受理");
				bean.setDidian(didian);
				bean.setMiaoshu(miaoshu);
				bean.setShebei(shebei);
				bean.setShijian(Util.getTime());
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				bean.setUser(user);
				baoxiuDao.insertBean(bean);
				
				shebei.setZhuangtai("报修");
				
				shebeiDao.updateBean(shebei);
				
				String menu = request.getParameter("menu");
				response
				.getWriter()
				.print("<script language=javascript>alert('操作成功');window.location.href='method!baoxiulist?menu="+menu+"';</script>");
				
				
			}
		//跳转到更新报修信息页面
			public String baoxiuupdate() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				
				Baoxiu bean = baoxiuDao.selectBean(" where id= "
						+ request.getParameter("id"));
				
				if(!"未受理".equals(bean.getChuli())){
					
					HttpServletResponse response = ServletActionContext.getResponse();
					response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
					response
						.getWriter()
						.print("<script language=javascript>alert('操作失败，不是未受理状态，不能修改报修信息');window.location.href='method!baoxiulist?menu="+menu+"';</script>");
					return null;
				}
				
				request.setAttribute("bean", bean);
				request.setAttribute("url", "method!baoxiuupdate2?id="+bean.getId()+"&menu="+menu);
				request.setAttribute("title", "报修信息修改");
				this.setUrl("baoxiu/baoxiuupdate.jsp");
				return SUCCESS;
			}
			
		//更新报修信息操作
			public void baoxiuupdate2() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();

				String didian = request.getParameter("didian");
				String miaoshu = request.getParameter("miaoshu");

				
				Baoxiu bean = baoxiuDao.selectBean(" where id= "
						+ request.getParameter("id"));
				
				bean.setDidian(didian);
				bean.setMiaoshu(miaoshu);
				
				baoxiuDao.updateBean(bean);
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!baoxiulist?menu="+menu+"';</script>");
			}
			
			
		//删除报修信息操作
			public void baoxiudelete() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				Baoxiu bean = baoxiuDao.selectBean(" where id= "+ request.getParameter("id"));
				if("取消申请".equals(bean.getChuli())){
					
					bean.setDeletestatus(1);
					baoxiuDao.updateBean(bean);
				}
				
				
			
				
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!baoxiulist?menu="+menu+"';</script>");
			}
			
			
			//取消申请操作
			public void baoxiudelete2() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				Baoxiu bean = baoxiuDao.selectBean(" where id= "+ request.getParameter("id"));
				if("未受理".equals(bean.getChuli())){
					
					bean.setChuli("取消申请");
					baoxiuDao.updateBean(bean);
					
					Shebei shebei = shebeiDao.selectBean(" where id= "+bean.getShebei().getId());
					
					shebei.setZhuangtai("使用");
					shebeiDao.updateBean(shebei);
				}

				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!baoxiulist?menu="+menu+"';</script>");
			}
			
			
			
			//跳转到查看报修信息页面
			public String baoxiuupdate3() {
				HttpServletRequest request = ServletActionContext.getRequest();
				Baoxiu bean = baoxiuDao.selectBean(" where id= "+ request.getParameter("id"));
				request.setAttribute("bean", bean);
				request.setAttribute("title", "报修信息查看");
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				this.setUrl("baoxiu/baoxiuupdate3.jsp");
				return SUCCESS;
			}
			
			
			
			//报修信息查询
			public String baoxiulist2() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String bianhao = request.getParameter("bianhao");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				
			
				sb.append("    deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = baoxiuDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", baoxiuDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!baoxiulist2?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!baoxiulist2?menu="+menu);
				request.setAttribute("url2", "method!baoxiu");
				request.setAttribute("title", "报修信息查询");
				this.setUrl("baoxiu/baoxiulist2.jsp");
				return SUCCESS;

			}

			
			//报修管理员信息列表
			public String userlist2() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String username = request.getParameter("username");
				
				String xingming = request.getParameter("xingming");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (username != null && !"".equals(username)) {

					sb.append("username like '%" + username + "%'");
					sb.append(" and ");
					request.setAttribute("username", username);
				}
				
				if (xingming != null && !"".equals(xingming)) {

					sb.append("xingming like '%" + xingming + "%'");
					sb.append(" and ");
					request.setAttribute("xingming", xingming);
				}
				
				sb.append("   deletestatus=0 and role=3 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = userDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!userlist2?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!userlist2?menu="+menu);
				request.setAttribute("url2", "method!user");
				request.setAttribute("title", "报修管理员信息管理");
				this.setUrl("user/userlist2.jsp");
				return SUCCESS;

			}
		//跳转到添加报修管理员信息页面
			public String useradd10() {
				HttpServletRequest request = ServletActionContext.getRequest();
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				

				
				request.setAttribute("url", "method!useradd20?menu="+menu);
				request.setAttribute("title", "添加新报修管理员");
				this.setUrl("user/useradd10.jsp");
				return SUCCESS;
			}
			


		//添加报修管理员信息操作
			public void useradd20() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				

				String xingming = request.getParameter("xingming");
				String username = request.getParameter("username");
				String dianhua = request.getParameter("dianhua");

				User bean = userDao.selectBean(" where deletestatus=0  and username='"+username+"' ");
				if(bean==null){
					bean = new User();
					
					bean.setCreatetime(Util.getRiqi());
				
					bean.setPassword("111111");
					bean.setRole(3);
					bean.setDianhua(dianhua);
					bean.setUsername(username);
					bean.setXingming(xingming);
					
					
					userDao.insertBean(bean);
					response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!userlist2?menu="+menu+"';</script>");
				}else{
					response
					.getWriter()
					.print("<script language=javascript>alert('操作失败，该用户名已经存在');window.location.href='method!userlist2?menu="+menu+"';</script>");
				}
			
				
				
				
			}
		//跳转到更新报修管理员信息页面
			public String userupdate10() {
				HttpServletRequest request = ServletActionContext.getRequest();
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				
				User bean = userDao.selectBean(" where id= "
						+ request.getParameter("id"));
				request.setAttribute("bean", bean);
				request.setAttribute("url", "method!userupdate20?id="+bean.getId()+"&menu="+menu);
				request.setAttribute("title", "报修管理员信息修改");
				this.setUrl("user/userupdate10.jsp");
				return SUCCESS;
			}
		//更新报修管理员信息操作
			public void userupdate20() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();


				String dianhua = request.getParameter("dianhua");
				String xingming = request.getParameter("xingming");

				
				User bean = userDao.selectBean(" where id= "
						+ request.getParameter("id"));
				

				bean.setDianhua(dianhua);
			

				bean.setXingming(xingming);
				
				userDao.updateBean(bean);
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!userlist2?menu="+menu+"';</script>");
			}
		//删除报修管理员信息操作
			public void userdelete10() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				User bean = userDao.selectBean(" where id= "+ request.getParameter("id"));
				bean.setDeletestatus(1);
				userDao.updateBean(bean);
			
				
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!userlist2?menu="+menu+"';</script>");
			}
			
			//跳转到查看报修管理员信息页面
			public String userupdate30() {
				HttpServletRequest request = ServletActionContext.getRequest();
				User bean = userDao.selectBean(" where id= "+ request.getParameter("id"));
				request.setAttribute("bean", bean);
				request.setAttribute("title", "报修管理员信息查看");
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				this.setUrl("user/userupdate30.jsp");
				return SUCCESS;
			}
			
			
			//报修管理
			public String baoxiulist3() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String bianhao = request.getParameter("bianhao");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
			
			
				sb.append("  chuli='未受理' and  deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = baoxiuDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", baoxiuDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!baoxiulist3?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!baoxiulist3?menu="+menu);
				request.setAttribute("url2", "method!baoxiu");
				request.setAttribute("title", "受理报修");
				this.setUrl("baoxiu/baoxiulist3.jsp");
				return SUCCESS;

			}
			
			
			//报修处理中操作
			public void baoxiudelete3() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				Baoxiu bean = baoxiuDao.selectBean(" where id= "+ request.getParameter("id"));
				if("未受理".equals(bean.getChuli())){
					
					bean.setChuli("处理中");
					HttpSession session = request.getSession();
					User user2 = (User)session.getAttribute("user");
					bean.setUser2(user2);
					baoxiuDao.updateBean(bean);
				}
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!baoxiulist4?menu="+menu+"';</script>");
			}
			
			
			
			//报修管理
			public String baoxiulist4() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String bianhao = request.getParameter("bianhao");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				HttpSession session = request.getSession();
				User user2 = (User)session.getAttribute("user");
			
				sb.append("  user2.id="+user2.getId()+" and  deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = baoxiuDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", baoxiuDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!baoxiulist4?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!baoxiulist4?menu="+menu);
				request.setAttribute("url2", "method!baoxiu");
				request.setAttribute("title", "报修管理");
				this.setUrl("baoxiu/baoxiulist4.jsp");
				return SUCCESS;

			}
			
			
			//跳转到受理报修页面
			public String baoxiuupdate5() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				
				Baoxiu bean = baoxiuDao.selectBean(" where id= "
						+ request.getParameter("id"));
				
				
				
				request.setAttribute("bean", bean);
				request.setAttribute("url", "method!baoxiuupdate6?id="+bean.getId()+"&menu="+menu);
				request.setAttribute("title", "报修反馈");
				this.setUrl("baoxiu/baoxiuupdate5.jsp");
				return SUCCESS;
			}
			
		//受理报修操作
			public void baoxiuupdate6() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();

				String chuli = request.getParameter("chuli");
				String fankui = request.getParameter("fankui");

				
				Baoxiu bean = baoxiuDao.selectBean(" where id= "
						+ request.getParameter("id"));
				
				bean.setChuli(chuli);
				bean.setFankui(fankui);
				bean.setShijian2(Util.getTime());
				baoxiuDao.updateBean(bean);
				
				Shebei shebei = shebeiDao.selectBean(" where id= "+bean.getShebei().getId());
				shebei.setZhuangtai("使用");
				shebei.setCishu(shebei.getCishu()+1);
				shebeiDao.updateBean(shebei);
				
				
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!baoxiulist4?menu="+menu+"';</script>");
			}
			
			
			//申请更换
			public String baoxiulist5() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String bianhao = request.getParameter("bianhao");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
			
				sb.append("  user.id="+user.getId()+" and chuli='建议更换' and   deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = baoxiuDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", baoxiuDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!baoxiulist5?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!baoxiulist5?menu="+menu);
				request.setAttribute("url2", "method!baoxiu");
				request.setAttribute("title", "申请更换");
				this.setUrl("baoxiu/baoxiulist5.jsp");
				return SUCCESS;

			}
			
			
			private GenghuanDao genghuanDao;

			public GenghuanDao getGenghuanDao() {
				return genghuanDao;
			}

			public void setGenghuanDao(GenghuanDao genghuanDao) {
				this.genghuanDao = genghuanDao;
			}
			
			
			
			//我的更换进度管理
			public String genghuanlist() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String bianhao = request.getParameter("bianhao");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
			
				sb.append("  user.id="+user.getId()+" and  deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = genghuanDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", genghuanDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!genghuanlist?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!genghuanlist?menu="+menu);
				request.setAttribute("url2", "method!genghuan");
				request.setAttribute("title", "我的更换进度管理");
				this.setUrl("genghuan/genghuanlist.jsp");
				return SUCCESS;

			}
			
		//跳转到添加更换信息页面
			public String genghuanadd() {
				HttpServletRequest request = ServletActionContext.getRequest();
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				
				String baoxiuid = request.getParameter("baoxiuid");
				
				Baoxiu baoxiu = baoxiuDao.selectBean(" where id= "+baoxiuid);
				
				request.setAttribute("baoxiu", baoxiu);
				
				request.setAttribute("url", "method!genghuanadd2?menu="+menu+"&baoxiuid="+baoxiuid);
				request.setAttribute("title", "设备更换");
				this.setUrl("genghuan/genghuanadd.jsp");
				return SUCCESS;
			}
			

		//添加更换信息操作
			public void genghuanadd2() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				
				
				String didian = request.getParameter("didian");
				String miaoshu = request.getParameter("miaoshu");
				
				String baoxiuid = request.getParameter("baoxiuid");
				
				Baoxiu baoxiu = baoxiuDao.selectBean(" where id= "+baoxiuid);
				
				Shebei shebei = shebeiDao.selectBean(" where id= "+baoxiu.getShebei().getId());
				
				Genghuan bean = new Genghuan();
				
				
				bean.setBianhao(Util.getTime2());
				bean.setChuli("未受理");
				bean.setDidian(didian);
				bean.setMiaoshu(miaoshu);
				bean.setShebei(shebei);
				bean.setShijian(Util.getTime());
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				bean.setUser(user);
				genghuanDao.insertBean(bean);
				
				
				shebei.setZhuangtai("更换");
				shebeiDao.updateBean(shebei);
				
				baoxiu.setChuli("已申请更换");
				
				baoxiuDao.updateBean(baoxiu);
				
				
				String menu = request.getParameter("menu");
				response
				.getWriter()
				.print("<script language=javascript>alert('操作成功');window.location.href='method!genghuanlist?menu="+menu+"';</script>");
				
				
			}
		//跳转到更新更换信息页面
			public String genghuanupdate() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				
				Genghuan bean = genghuanDao.selectBean(" where id= "
						+ request.getParameter("id"));
				
				if(!"未受理".equals(bean.getChuli())){
					
					HttpServletResponse response = ServletActionContext.getResponse();
					response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
					response
						.getWriter()
						.print("<script language=javascript>alert('操作失败，不是未受理状态，不能修改更换信息');window.location.href='method!genghuanlist?menu="+menu+"';</script>");
					return null;
				}
				
				
				request.setAttribute("bean", bean);
				request.setAttribute("url", "method!genghuanupdate2?id="+bean.getId()+"&menu="+menu);
				request.setAttribute("title", "更换信息修改");
				this.setUrl("genghuan/genghuanupdate.jsp");
				return SUCCESS;
			}
			
		//更新更换信息操作
			public void genghuanupdate2() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();

				String didian = request.getParameter("didian");
				String miaoshu = request.getParameter("miaoshu");

				
				Genghuan bean = genghuanDao.selectBean(" where id= "
						+ request.getParameter("id"));
				
				bean.setDidian(didian);
				bean.setMiaoshu(miaoshu);
				
				genghuanDao.updateBean(bean);
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!genghuanlist?menu="+menu+"';</script>");
			}
			
			
		//删除更换信息操作
			public void genghuandelete() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				Genghuan bean = genghuanDao.selectBean(" where id= "+ request.getParameter("id"));
				if("取消申请".equals(bean.getChuli())){
					
					bean.setDeletestatus(1);
					genghuanDao.updateBean(bean);
				}
				
				
			
				
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!genghuanlist?menu="+menu+"';</script>");
			}
			
			
			//取消申请操作
			public void genghuandelete2() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				
				Genghuan bean = genghuanDao.selectBean(" where id= "+ request.getParameter("id"));
				
				if("未受理".equals(bean.getChuli())){
					
					bean.setChuli("取消申请");
					genghuanDao.updateBean(bean);
					
					Shebei shebei = shebeiDao.selectBean(" where id= "+bean.getShebei().getId());
					
					shebei.setZhuangtai("使用");
					shebeiDao.updateBean(shebei);
				}

				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!genghuanlist?menu="+menu+"';</script>");
			}
			
			
			
			//跳转到查看更换信息页面
			public String genghuanupdate3() {
				HttpServletRequest request = ServletActionContext.getRequest();
				Genghuan bean = genghuanDao.selectBean(" where id= "+ request.getParameter("id"));
				request.setAttribute("bean", bean);
				request.setAttribute("title", "更换信息查看");
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				this.setUrl("genghuan/genghuanupdate3.jsp");
				return SUCCESS;
			}
			
			
			
			//更换信息查询
			public String genghuanlist2() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String bianhao = request.getParameter("bianhao");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				
			
				sb.append("    deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = genghuanDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", genghuanDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!genghuanlist2?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!genghuanlist2?menu="+menu);
				request.setAttribute("url2", "method!genghuan");
				request.setAttribute("title", "更换信息查询");
				this.setUrl("genghuan/genghuanlist2.jsp");
				return SUCCESS;

			}
			
			
			
			//更换管理
			public String genghuanlist3() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String bianhao = request.getParameter("bianhao");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
			
			
				sb.append("  chuli='未受理' and  deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = genghuanDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", genghuanDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!genghuanlist3?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!genghuanlist3?menu="+menu);
				request.setAttribute("url2", "method!genghuan");
				request.setAttribute("title", "受理更换");
				this.setUrl("genghuan/genghuanlist3.jsp");
				return SUCCESS;

			}
			
			
			//更换处理中操作
			public void genghuandelete3() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				Genghuan bean = genghuanDao.selectBean(" where id= "+ request.getParameter("id"));
				if("未受理".equals(bean.getChuli())){
					
					bean.setChuli("处理中");
					HttpSession session = request.getSession();
					User user2 = (User)session.getAttribute("user");
					bean.setUser2(user2);
					genghuanDao.updateBean(bean);
				}
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!genghuanlist4?menu="+menu+"';</script>");
			}
			
			
			
			//更换管理
			public String genghuanlist4() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String bianhao = request.getParameter("bianhao");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				HttpSession session = request.getSession();
				User user2 = (User)session.getAttribute("user");
			
				sb.append("  user2.id="+user2.getId()+" and  deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = genghuanDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", genghuanDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!genghuanlist4?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!genghuanlist4?menu="+menu);
				request.setAttribute("url2", "method!genghuan");
				request.setAttribute("title", "更换管理");
				this.setUrl("genghuan/genghuanlist4.jsp");
				return SUCCESS;

			}
			
			
			//跳转到受理更换页面
			public String genghuanupdate5() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				
				Genghuan bean = genghuanDao.selectBean(" where id= "
						+ request.getParameter("id"));
				
				
				List<Shebei> shebeilist = shebeiDao.selectBeanList(0, 9999, " where deletestatus=0 and  zhuangtai='库存' and fenlei.id="+bean.getShebei().getFenlei().getId()+" ");
				
				request.setAttribute("shebeilist", shebeilist);
				
				request.setAttribute("bean", bean);
				request.setAttribute("url", "method!genghuanupdate6?id="+bean.getId()+"&menu="+menu);
				request.setAttribute("title", "更换反馈");
				this.setUrl("genghuan/genghuanupdate5.jsp");
				return SUCCESS;
			}
			
		//受理更换操作
			public void genghuanupdate6() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();

				String shebeiid2 = request.getParameter("shebeiid2");
				String fankui = request.getParameter("fankui");

				
				Genghuan bean = genghuanDao.selectBean(" where id= "
						+ request.getParameter("id"));
				
				Shebei shebei2 = shebeiDao.selectBean(" where id= "+shebeiid2);
				
				bean.setShebei2(shebei2);
				bean.setChuli("更换完成");
				bean.setFankui(fankui);
				bean.setShijian2(Util.getTime());
				genghuanDao.updateBean(bean);
				
				Shebei shebei = shebeiDao.selectBean(" where id= "+bean.getShebei().getId());
				shebei.setZhuangtai("待报废");
				shebeiDao.updateBean(shebei);
				
				
				Chuku chuku = new Chuku();
				
				chuku.setShebei(shebei2);
				
				chukuDao.insertBean(chuku);
				
				
				shebei2.setChukushijian(Util.getTime());
				shebei2.setDidian(shebei.getDidian());
				shebei2.setShuoming(shebei.getShuoming());
				shebei2.setZhuangtai("使用");
				shebeiDao.updateBean(shebei2);

				
				Kucun kucun = kucunDao.selectBean(" where deletestatus=0 and fenlei.id= "+shebei2.getFenlei().getId());
				
				kucun.setSl(kucun.getSl()-1);
				kucunDao.updateBean(kucun);
				
				
				
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!genghuanlist4?menu="+menu+"';</script>");
			}
			
			
			//不同意更换操作
			public void genghuandelete4() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				Genghuan bean = genghuanDao.selectBean(" where id= "+ request.getParameter("id"));
				
					
				bean.setShijian2(Util.getTime());
				bean.setChuli("不同意更换");
				genghuanDao.updateBean(bean);
				
				Shebei shebei = shebeiDao.selectBean(" where id= "+bean.getShebei().getId());
				shebei.setZhuangtai("使用");
				shebeiDao.updateBean(shebei);
				
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!genghuanlist4?menu="+menu+"';</script>");
			}
			
			
			
			//设备报废管理
			public String shebeilist5() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String fenleiid = request.getParameter("fenleiid");
				
				request.setAttribute("fenleiid",fenleiid );
				
				
				String bianhao = request.getParameter("bianhao");
				
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append(" bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				
				HttpSession session = request.getSession();
				User user2 = (User)session.getAttribute("user");
			
				sb.append("  user.id="+user2.getId()+" and (zhuangtai='待报废' or zhuangtai='报废') and  deletestatus=0 order by zhuangtai desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = shebeiDao.selectBeanCount(where.replaceAll("order by zhuangtai desc", ""));
				request.setAttribute("list", shebeiDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!shebeilist5?menu="+menu+"&fenleiid="+fenleiid, "共有" + total + "条记录"));
				request.setAttribute("url", "method!shebeilist5?menu="+menu+"&fenleiid="+fenleiid);
				request.setAttribute("url2", "method!shebei");
				request.setAttribute("title", "设备报废管理");
				this.setUrl("shebei/shebeilist5.jsp");
				return SUCCESS;

			}
			
			
			//报废操作
			public void shebeidelete() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				Shebei bean = shebeiDao.selectBean(" where id= "+ request.getParameter("id"));
				
				bean.setBaofeishijian(Util.getTime());	
				bean.setZhuangtai("报废");
				shebeiDao.updateBean(bean);
				
	
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!shebeilist5?menu="+menu+"';</script>");
			}
			
			
			//注册用户管理
			public String userlist3() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
				String username = request.getParameter("username");
				
				String xingming = request.getParameter("xingming");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (username != null && !"".equals(username)) {

					sb.append("username like '%" + username + "%'");
					sb.append(" and ");
					request.setAttribute("username", username);
				}
				
				if (xingming != null && !"".equals(xingming)) {

					sb.append("xingming like '%" + xingming + "%'");
					sb.append(" and ");
					request.setAttribute("xingming", xingming);
				}
				
				sb.append("   deletestatus=0 and role=2 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = userDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!userlist3?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!userlist3?menu="+menu);
				request.setAttribute("url2", "method!user");
				request.setAttribute("title", "注册用户管理");
				this.setUrl("user/userlist3.jsp");
				return SUCCESS;

			}
			
			
			//删除注册用户操作
			public void userdelete100() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				User bean = userDao.selectBean(" where id= "+ request.getParameter("id"));
				bean.setDeletestatus(1);
				userDao.updateBean(bean);
			
				
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String menu = request.getParameter("menu");
				response
					.getWriter()
					.print("<script language=javascript>alert('操作成功');window.location.href='method!userlist3?menu="+menu+"';</script>");
			}
			
			//跳转到查看注册用户信息页面
			public String userupdate300() {
				HttpServletRequest request = ServletActionContext.getRequest();
				User bean = userDao.selectBean(" where id= "+ request.getParameter("id"));
				request.setAttribute("bean", bean);
				request.setAttribute("title", "注册用户信息查看");
				String menu = request.getParameter("menu");
				request.setAttribute("menu",menu );
				this.setUrl("user/userupdate300.jsp");
				return SUCCESS;
			}
			
			
			
			//设备查询
			public String shebeilist6() {
				HttpServletRequest request = ServletActionContext.getRequest();
				//控制左边菜单栏的展示或者闭合
				String menu = request.getParameter("menu");

				request.setAttribute("menu",menu );
				
			
				
				
				String bianhao = request.getParameter("bianhao");
				
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (bianhao != null && !"".equals(bianhao)) {

					sb.append(" bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				
			
			
				sb.append("   deletestatus=0 order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 10;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = shebeiDao.selectBeanCount(where.replaceAll("order by id desc", ""));
				request.setAttribute("list", shebeiDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!shebeilist6?menu="+menu, "共有" + total + "条记录"));
				request.setAttribute("url", "method!shebeilist6?menu="+menu);
				request.setAttribute("url2", "method!shebei");
				request.setAttribute("title", "设备查询");
				this.setUrl("shebei/shebeilist6.jsp");
				return SUCCESS;

			}
			
			
			
			
}
