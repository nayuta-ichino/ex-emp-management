package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブルを操作するリポジトリ.
 * 
 * @author nayuta
 */
@Repository
public class AdministratorRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	// ラムダ式でRowMapperを定義
	private static final RowMapper<Administrator> ADMIN_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();

		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));

		return administrator;
	};

	/**
	 * 管理者情報を登録.
	 * 
	 * @param administrator 管理者情報.
	 */
	public void insert(Administrator administrator) {
		// プレースホルダーに値を代入
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);

		// SQLを作成
		String insertSql = "INSERT INTO administrators (name, mail_address, password) VALUES(:name, :mailAddress, :password);";

		// 実行
		template.update(insertSql, param);
	}

	/**
	 * メールアドレスとパスワードから管理者を検索.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報
	 */
	public Administrator findByMailaddressAndPassword(String mailAddress, String password) {
		// SQLを作成
		String findSql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address = :mail_address AND password = :password;";

		// プレースホルダーに値を代入
		SqlParameterSource param = new MapSqlParameterSource().addValue("mail_address", mailAddress)
				.addValue("password", password);

		// 実行
		List<Administrator> administratorList = template.query(findSql, param, ADMIN_ROW_MAPPER);

		// 検索結果がなければnullを返す
		if (administratorList.size() == 0) {
			return null;
		}

		// 0番目(一番上)のデータを出力
		return administratorList.get(0);

	}

}
