package zttc.itat.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class User {
   
	private String username;
	private String password;
	private String nickname;
	private String email;
    public User() {
    }
    
    @NotEmpty(message="用户名不能为空")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Size(min=1,max=10,message="密码长度在1到10位之间")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Email(message="邮箱输入格式不正确")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public User(String username, String password, String nickname, String email) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
	}
	
}
