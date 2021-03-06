package com.fmq.common.demo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.websocket.server.PathParam;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UrlPathHelper;

import com.fmq.common.base.BaseController;
import com.fmq.common.controller.vo.CommonVO;
import com.fmq.common.controller.vo.TestDemoVO;


/**
 * 测试Controller 和Controller 类注解的使用
 * 
 * @author ljg
 *
 *
 * =@RestController等于@Controller+@ResponseBody组合，相当于在每个方法都加上@ResponseBody。  
 *
 */

// @Controller
@RestController
@SpringBootApplication
public class DemoController extends BaseController {
	
	
	// http://localhost:8080/echo/message
	@RequestMapping(value = "/echo/{message}", method = RequestMethod.GET)
	public String echo1(@PathVariable("message") String msg) {
		return "echo  " + msg;
	}

	/*
	 * 
	 * 默认传值
	 */
	// @RequestMapping(value = "/say", method = RequestMethod.GET)
	@GetMapping("/say")
	public String say(@RequestParam(value = "say", required = false, defaultValue = "0") String msg) {
		return "echo  " + msg;
	}

	// http://localhost:8089/mess?msg=1
	// http://localhost:8089/ec?msg=1
	@RequestMapping(value = { "/mess", "/ec" }, method = RequestMethod.GET)
	public String echo2(String msg) {
		return "echo  " + msg;
	}

	// http://localhost:8080/esa?num=2
	@RequestMapping("/esa")
	public Object nul(int num) {
		return "" + num * 2;
	}

	/**
	 * ip地址0:0:0:0:0:0:0:1 客户端编码ISO-8859-1
	 * 真实路径/private/var/folders/vg/6_3h_1c935j2qn7s5ck5lmmm0000gn/T/tomcat-docbase.5835212489978454804.8080/
	 * session0E81293D06C16FDD4798771E76EDB4E6
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/objet")
	public String objet(HttpServletRequest req, HttpServletResponse res) {
		try {
			System.out.println("ip地址" + req.getRemoteAddr() + "获取机器和ip" + InetAddress.getLocalHost());
			System.out.println("客户端编码 " + res.getCharacterEncoding());
			System.out.println("真实路径 " + req.getServletContext().getRealPath("/"));// Users/ljg/myapp/workspace/common/
			System.out.println("session " + req.getSession().getId());
			System.out.println("Cookies " + req.getCookies().toString());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		//获取本机IP
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				System.out.println("netInterface.getName:"+netInterface.getName());
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						System.out.println("本机的IP = " + ip.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		UrlPathHelper urlPathHelper=new UrlPathHelper();
		System.out.println("ContextPath "+urlPathHelper.getContextPath(req));
		System.out.println("RequestUri "+urlPathHelper.getRequestUri(req));
		System.out.println("LookupPathForRequest "+urlPathHelper.getLookupPathForRequest(req));
		
		
		return "objet";
	}

	// http://localhost:8080/echo?msg=echo
	@RequestMapping("/echo")
	public String echo(String msg) {
		return "echo  " + msg;
	}

	// http://localhost:8080/
	@RequestMapping("/")
	// @ResponseBody
	public String home() {
		return "home";
	}


}
