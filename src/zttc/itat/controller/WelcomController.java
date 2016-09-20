package zttc.itat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class WelcomController extends AbstractController {
    
    //handleRequestInternal()为抽象方法，处理	request并给出response
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("welcome");
		return new ModelAndView("welcome");  //返回ModelAndView类型的视图“welcome”
	}

}
