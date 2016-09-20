package zttc.itat.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zttc.itat.model.User;
import zttc.itat.model.UserException;

@Controller //采用@Controller方式注入
@RequestMapping("/user") //访问时地址链接"/user"
public class UserController {
    
	private Map<String, User> users=new HashMap<String,User>();
	
	//向User类型的users对象传入参数
	public UserController() {
		users.put("sdy", new User("sdy","123","宋冬野","sdy@sina.com"));
		users.put("lyu", new User("lyu","123","李云迪","lyd@sina.com"));
		users.put("klo", new User("klo","123","葵咯筽","klo@sina.com"));
		users.put("angel",new User("angel","123","杨颖","angel@sina.com"));
	}
	
	//访问时通过“/user/users”访问到此段代码
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("users", users);  //将users对象加入视图
		return "user/list"; //返回到"user/list"视图
	}
	
	//链接到"/user/add"页面时,是GET请求，会访问此段代码
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(@ModelAttribute("user")User user){
		//开启modelDriven
		//model.addAttribute(new User());
		return "user/add";
	}
	
	//具体添加用户时是POST请求，访问此段代码
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Validated User user,BindingResult br,@RequestParam("attachs")MultipartFile [] attachs,HttpServletRequest req) throws IOException{//BindingResult一定要紧跟着@Validated来写
		//如果有错误，则跳转到add视图
		if(br.hasErrors()){
			return "user/add";
		}
		String realpath=req.getSession().getServletContext().getRealPath("/resources/upload");
		System.out.println(realpath);
		for(MultipartFile attach:attachs){
		 if(attach.isEmpty()) continue;  //如果上传文件为空，则跳过文件上传
		  File f=new File(realpath+"/"+attach.getOriginalFilename());
		  FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
		}
		users.put(user.getUsername(),user);//将user对象添加到users页面
		return "redirect:/user/users";  //客户端跳转
	}
	
	@RequestMapping(value="/{username}",method=RequestMethod.GET)
	public String show(@PathVariable String username,Model model){  //“user/users”页面下，点击username可跳转到链接“username”
		model.addAttribute(users.get(username));  //添加username对象信息到页面
		return "user/show";//返回到“show”页面
	}
	
	@RequestMapping(value="/{username}",method=RequestMethod.GET,params="jason")  //通过jason来访问页面
	@ResponseBody
	public User show(@PathVariable String username){  //“user/users”页面下，点击username可跳转到链接“username”
		return users.get(username);
	}
	
	@RequestMapping(value="/{username}/update",method=RequestMethod.GET)
	public String update(@PathVariable String username,Model model){
		model.addAttribute(users.get(username));//添加username信息到页面
		return "user/update";
	}
	
	@RequestMapping(value="/{username}/update",method=RequestMethod.POST)
	public String update(@PathVariable String username,@Validated User user,BindingResult br){//@Validated User user对输入信息进行验证
		//如果有错误，则跳转到update视图
	    if(br.hasErrors()){
	       return "user/update";
		 }
		users.put(username, user);//将username对象信息添加到user中
		return "redirect:/user/users";//客户端跳转，跳转到“/user/users”视图
	}
	
	@RequestMapping(value="{username}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String username){
		users.remove(username);
		return "redirect:/user/users";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,HttpSession session){
		if(!users.containsKey(username)){
			throw new UserException("用户名不正确");
		}
		User u=users.get(username);
		if(!u.getPassword().equals(password)){
			throw new UserException("用户密码不正确，请重新输入");
		}
		session.setAttribute("loginUser", u); //添加页面信息（将loginUser的nickname传到显示界面）
		return "redirect:/user/users"; //客户端跳转，跳转至“/user/users"页面
	}
	
	/**
	 * 局部异常处理
	 * @author hangyang
	 */
	
	/*@ExceptionHandler(value=UserException.class)
	public String handlerException(UserException e,HttpServletRequest req){
		req.setAttribute("e", e);
		return "error";
	}*/
	
}
