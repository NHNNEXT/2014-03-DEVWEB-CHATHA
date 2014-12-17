package realrank.controller;

import java.io.IOException;

import realrank.Http;
import realrank.annotation.Controller;
import realrank.annotation.Get;
import realrank.annotation.Post;

@Controller
public class HomeController {

	@Get("/home.realrank")
	public String home(Http http){
		return "home.jsp"; //리턴패스로 연결
	}
	
	@Get("/index.realrank")
	public String ss(Http http){
		return "index.jsp";
	}
	
	@Get("/test.realrank")
	public String sss(Http http) throws IOException{
		http.getResp().getWriter().write("ssssss"); //http객체에서 리스폰스나 리큐를 꺼내와서 사용
		return null; //리턴널이면 연결 종료 
	}
		
	@Post("/home.realrank")
	public String homes(Http http){
		return null;
	}
}
