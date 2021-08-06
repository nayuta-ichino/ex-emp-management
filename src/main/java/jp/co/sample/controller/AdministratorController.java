package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラー.
 * 
 * @author nayuta
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	@ModelAttribute
	private InsertAdministratorForm setUpForm() {
		return new InsertAdministratorForm();
	}

	@ModelAttribute
	private LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	@Autowired
	private AdministratorService service;

	@Autowired
	private HttpSession session;

	/**
	 * ログイン用の画面へフォワード.
	 * 
	 * @return ログイン用画面
	 */
	@RequestMapping("")
	public String index() {
		return "administrator/login";
	}

	/**
	 * 管理者情報登録画面へフォワード.
	 * 
	 * @return 管理者情報登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	//
	/**
	 * 管理者情報を登録した後、トップ画面へリダイレクト.
	 *
	 * @param form 入力画面で受け取った登録情報
	 * @return トップページ
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator admiAdministrator = new Administrator();

		BeanUtils.copyProperties(form, admiAdministrator);

		service.insert(admiAdministrator);

		return "redirect:/";
	}

	/**
	 * ログイン機能実装.
	 * 
	 * @param form  ログインユーザーのメールアドレスとパスワード
	 * @param model sessionスコープ
	 * @return ログインするユーザー
	 */
	@RequestMapping("/login")
	public String login(@Validated LoginForm form, BindingResult result, Model model) {

		// エラー発生の確認
		if (result.hasErrors()) {
			model.addAttribute("error", "メールアドレスまたはパスワードが不正です。");
			return index();
		}

		// ログイン機能実行
		Administrator administrator = service.login(form.getMailAddress(), form.getPassword());

		if (administrator == null) {
			return index();
		}
		session.setAttribute("administratorName", administrator.getName());

		return "forward:/employee/showList";
	}

}
