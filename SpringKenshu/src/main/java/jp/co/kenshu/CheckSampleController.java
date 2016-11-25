package jp.co.kenshu;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.kenshu.form.CheckForm;

@Controller
public class CheckSampleController {
	@RequestMapping(value = "/sample/check/disp/", method = RequestMethod.GET)
	public String disp(Model model) {
		model.addAttribute("message", "checkのサンプル");
		CheckForm form = new CheckForm();
		form.setEmployees(new String[] { "次郎" });
		model.addAttribute("checkForm", form);
		model.addAttribute("checkEmployees", getCheckEmployees());
		return "checkSample";
	}

	@RequestMapping(value = "/sample/check/info/", method = RequestMethod.POST)
	public String getcheckInfo(@ModelAttribute CheckForm form, Model model) {
		String[] checkedEmployee = form.getEmployees();
		StringBuilder builder = new StringBuilder();
		for (String employee : checkedEmployee) {
			builder.append(employee).append(System.lineSeparator());
		}
		model.addAttribute("message", builder);
		model.addAttribute("checkForm", form);
		model.addAttribute("checkEmployees", getCheckEmployees());
			return "checkSample";

	}

	private List<String> getCheckEmployees() {
		List<String> list = new LinkedList<String>();
		list.add("一太郎");
		list.add("次郎");
		list.add("三郎");
		return list;

	}
}
