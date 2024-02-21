package com.jxzy.AppMigration.NavigationApp.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeatherReport {
	/**
	 * 
	 * @param: city 
	 * @description: TODO
	 * @return: java.lang.String
	 * @author: qushaobei
	 * @date: 2022/7/27 0027
	 */
	private static String getSoapRequest(String city) {
		StringBuilder sb = new StringBuilder();
		sb
				.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
						+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
						+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
						+ "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+ "<soap:Body>    <getWeatherbyCityName xmlns=\"http://WebXml.com.cn/\">"
						+ "<theCityName>" + city
						+ "</theCityName>    </getWeatherbyCityName>"
						+ "</soap:Body></soap:Envelope>");
		return sb.toString();
	}

	/**
	 * 
	 * @param: city 
	 * @description: TODO
	 * @return: java.io.InputStream
	 * @author: qushaobei
	 * @date: 2022/7/27 0027
	 */
	private static InputStream getSoapInputStream(String city) throws Exception {
		try {
			String soap = getSoapRequest(city);
			if (soap == null) {
				return null;
			}
			URL url = new URL(
					"http://www.webxml.com.cn/WebServices/WeatherWebService.asmx");
			URLConnection conn = url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestProperty("Content-Length", Integer.toString(soap
					.length()));
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.setRequestProperty("SOAPAction",
					"http://WebXml.com.cn/getWeatherbyCityName");

			OutputStream os = conn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			osw.write(soap);
			osw.flush();
			osw.close();

			InputStream is = conn.getInputStream();
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param: city 
	 * @description: TODO
	 * @return: java.lang.String
	 * @author: qushaobei
	 * @date: 2022/7/27 0027
	 */
	public static String getWeather(String city) {
		try {
			Document doc;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = getSoapInputStream(city);
			doc = db.parse(is);
			NodeList nl = doc.getElementsByTagName("string");
			StringBuffer sb = new StringBuffer();
			for (int count = 0; count < nl.getLength(); count++) {
				Node n = nl.item(count);
				if(n.getFirstChild().getNodeValue().equals("查询结果为空")) {
					sb = new StringBuffer("#") ;
					break ;
				}
				sb.append(n.getFirstChild().getNodeValue() + "#\n");
			}
			is.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @param: args 
	 * @description: TODO
	 * @return: void
	 * @author: qushaobei
	 * @date: 2022/7/27 0027
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(getWeather("丰台"));
		//System.out.println("po&oi".split("&").length) ;
		//System.out.println("##".split("#").length) ;
	}
}
