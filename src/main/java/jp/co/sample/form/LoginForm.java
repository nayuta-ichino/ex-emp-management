package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class LoginForm {
	/** メールアドレス */
	@Email(message = "Emailの形式が不正です。")
	private String mailAddress;
	/** パスワード */
	@Size(min = 1, max = 127, message = "パスワードは1文字以上、127文字以内で入力してください。")
	private String password;

	// getter,setter
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// toString
	@Override
	public String toString() {
		return "LoginForm [mailAddress=" + mailAddress + ", password=" + password + "]";
	}
}
