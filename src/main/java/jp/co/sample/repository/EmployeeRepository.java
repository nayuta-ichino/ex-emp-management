package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author nayuta
 */
@Repository
public class EmployeeRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	// RowMapperをラムダ式で定義
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();

		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacterstics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));

		return employee;
	};

	/**
	 * 従業員全員の情報を検索
	 *
	 * @return 従業員情報一覧
	 */
	public List<Employee> findAll() {
		// sql文作成
		String findAllSql = "SELECT "
				+ " id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count"
				+ " FROM employees ORDER BY hire_date DESC;";

		// 実行
		List<Employee> employeeList = template.query(findAllSql, EMPLOYEE_ROW_MAPPER);

		return employeeList;
	}

	/**
	 * 主キーで従業員を検索.
	 * 
	 * @param id 従業員ID 主キー
	 * @return 1人分の従業員の情報
	 */
	public Employee load(Integer id) {
		// sql文作成
		String loadSql = "SELECT "
				+ " id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count"
				+ " FROM employees WHERE id = :id;";

		// :id にデータを格納
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		// 実行
		Employee employee = template.queryForObject(loadSql, param, EMPLOYEE_ROW_MAPPER);

		return employee;
	}

	/**
	 * 従業員情報を更新.
	 * 
	 * @param employee 従業員情報
	 */
	public void update(Employee employee) {
		// sql文の作成
		String updateSql = "UPDATE employees SET"
				+ " name=:name, image=:image, gender=:gender, hire_date=:hireDate, mail_address=:mailAddress, zip_code=:zipCode,"
				+ " address=:address, telephone=:telephone, salary=:salary, characteristics=:characterstics, dependents_count=:dependentsCount"
				+ " WHERE id = :id;";

		// :idにデータ格納
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		// 実行
		template.update(updateSql, param);
	}

}
