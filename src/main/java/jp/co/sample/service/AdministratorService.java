package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

/**
 * AdministratorRepositoryクラスからメソッドを呼び出す.
 * 
 * @author nayuta
 */
@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository repository;

	/**
	 * 従業員情報を登録する.
	 * 
	 * @param administrator
	 */
	public void insert(Administrator administrator) {
		repository.insert(administrator);
	}

	/**
	 * ログイン機能.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return ログインしたユーザーの情報
	 */
	public Administrator login(String mailAddress, String password) {
		return repository.findByMailaddressAndPassword(mailAddress, password);
	}
}
