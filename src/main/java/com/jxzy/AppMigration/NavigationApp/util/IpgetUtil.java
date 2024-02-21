package com.jxzy.AppMigration.NavigationApp.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Slf4j
public class IpgetUtil {

    private  int serverPort;
    
    private static String hostaddr ="0.0.0.1";


    public int getPort() {
        return this.serverPort;
    }
  //ip 的 获取 如下 

	public static String getHostaddr() {
		try {
			hostaddr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.error(e.getMessage(),e);
		}
		return hostaddr;
	}

	public static String getHostIp(){
		return getHostaddr();
	}

	  //获得访问者IP方法
	public static String getIp(HttpServletRequest request){

		  String ip = request.getHeader("x-forwarded-for");
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			   ip = request.getHeader("X-real-ip");
		   }
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			   ip = request.getHeader("Proxy-Client-IP");
		   }
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			   ip = request.getHeader("WL-Proxy-Client-IP");
		   }
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			   ip = request.getRemoteAddr();
		   }
		   if(StringUtils.isNotBlank(ip)) {
			   ip = ip.split(",")[0];
		   }

		return ip;
	}







}
