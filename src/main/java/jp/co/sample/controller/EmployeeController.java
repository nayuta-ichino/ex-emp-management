package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeFrom;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute
	private UpdateEmployeeFrom setUpdateEmployeeForm() {
		return new UpdateEmployeeFrom();
	}

	/**
	 * 従業員一覧を表示.
	 * 
	 * @param model requestスコープ
	 * @return 従業員一覧
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();

		model.addAttribute("employeeList", employeeList);

		return "employee/list.html";
	}

	/**
	 * 従業員１人分の情報を取得
	 * 
	 * @param id    ID
	 * @param model requestスコープ
	 * @return １人分の従業員情報
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		// idをint型に変更
		int intId = Integer.parseInt(id);

		Employee employee = employeeService.showDetail(intId);

		model.addAttribute("employee", employee);

		return "employee/detail";
	}

	@RequestMapping("/update")
	public String update(UpdateEmployeeFrom form) {
		Employee employee = new Employee();

		// 主キー検索で更新する従業員情報を代入
		employee = employeeService.showDetail(Integer.valueOf(form.getId()));

		// 更新
		employee.setDependentsCount(Integer.valueOf(form.getDependentsCount()));

		employeeService.update(employee);

		return "redirect:/employee/showList";
	}

}
