package music.user.entity;

import java.util.Date;

public class User {
    private Integer id;

    private String account;

    private String password;

    private String nick;

    private String name;

    private String phone;

    private Byte status;

    private String email;

    private String token;

    private Date activatetime;

    private Date createdate;

    private Date updatedate;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getActivatetime() {
        return activatetime;
    }

    public void setActivatetime(Date activatetime) {
        this.activatetime = activatetime;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", password=" + password + ", nick=" + nick + ", name="
				+ name + ", phone=" + phone + ", status=" + status + ", email=" + email + ", token=" + token
				+ ", activatetime=" + activatetime + ", createdate=" + createdate + ", updatedate=" + updatedate + "]";
	}
    
}