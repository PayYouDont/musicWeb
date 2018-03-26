package music.user.entity;

import java.io.Serializable;

public class User implements Serializable{
	/** 
	* @Fields serialVersionUID : TODO(     ) 
	*/ 
	private static final long serialVersionUID = -3920365974958289059L;
	private Integer id;
	private String account;
	private String password;
	private String nick;
	private String name;
	private String phone;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", password=" + password + ", nick=" + nick + ", name="
				+ name + ", phone=" + phone + ", status=" + status + "]";
	}
	
}
