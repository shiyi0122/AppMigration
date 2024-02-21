package com.jxzy.AppMigration.NavigationApp.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class IDBuilder {

	public static String getDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getRandomNum(int num) {
		String numStr = "";
		for (int i = 0; i < num; i++) {
			numStr += (int) (10 * (Math.random()));
		}
		return numStr;
	}

	public static Long getSeqId() {
		String sformat = "MMddhhmmssSSS";
		int num = 2;
		String idStr = getDate(sformat) + getRandomNum(num);
		Long id = Long.valueOf(idStr);
		return id;
	}

	/***
	 * 获取招商小程序订单编号
	 * @param phone
	 * @return
	 */
	public static String getOrderNumber(String phone){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderNumber = "HHRDD"+phone+formatter.format(date);// 订单编号
		return orderNumber;
	}

	/***
	 * 获取机器人押金订单编号
	 * @param phone
	 * @return
	 */
	public static String getOrderNumberYJ(String phone){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderNumber = "YJ"+phone+formatter.format(date)+"YJ";// 订单编号
		return orderNumber;
	}

	public static void main(String[] args) {
		System.out.println((int)(10 * Math.random()));
	}
}
