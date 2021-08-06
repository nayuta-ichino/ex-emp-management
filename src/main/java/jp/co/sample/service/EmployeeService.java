package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員情報を操作するservice.
 * 
 * @author nayuta
 */
@Service
@Transactional
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員一覧を取得.
	 * 
	 * @return 従業員一覧
	 */
	public List<Employee> showList() {
		return employeeRepository.findAll();
	}

	/**
	 * 従業員の詳細を取得.
	 * 
	 * @param id 従業員ID
	 * @return 従業員の詳細
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}

	/**
	 * 変更する従業員の情報を取得.
	 * 
	 * @param employee 更新対象の従業員
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

}
