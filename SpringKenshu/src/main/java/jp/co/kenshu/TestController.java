package jp.co.kenshu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.kenshu.form.TestForm;

@Controller
public class TestController {
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showMessage(Model model) {
	    TestForm form = new TestForm();
	    form.setId(0);
	    form.setName("ここに名前を書いてね");
	    //↓showMessageのform:formタグのtestFormと関連付けられている
	    model.addAttribute("testForm", form);
	    model.addAttribute("message", "FORMの練習");
	    return "showMessage";
	}

	@RequestMapping(value = "/show", method = RequestMethod.POST)
	public String getFormInfo(@ModelAttribute TestForm form, Model model) {
	    model.addAttribute("message", "ID : " + form.getId() + " & name : " + form.getName());
	    return "showMessage";
	}
}
