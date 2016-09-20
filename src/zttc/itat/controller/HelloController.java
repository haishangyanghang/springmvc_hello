package zttc.itat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  //@Controller方式注入
public class HelloController {
    
	@RequestMapping({"/hello","/"})  //访问地址链接为"/hello"时访问此段代码
	public String hello(String username,Model model){
		System.out.println("hello");
		model.addAttribute("username",username);//把“username”参数传到视图
		model.addAttribute(username);
		System.out.println(username);
		return "hello"; //返回hello视图
	}
	
	@RequestMapping({"/welcome","/"})
	public String welcome(){
		return "welcome";
	}
	
}
