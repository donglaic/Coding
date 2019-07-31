package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller		//配置为一个控制器
@RequestMapping("/")	//声明该方法要处理的请求路径
public class HomeController {

    @RequestMapping(method = GET)	//声明该方法需要处理的请求方法
    public String home(Model model){
        return "home";			//返回view name
    }

}
