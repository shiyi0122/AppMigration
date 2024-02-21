package com.jxzy.AppMigration.NavigationApp.util.jwt;
import java.util.UUID;

public class JwtConstant {
	
	public static final String JWT_ID = UUID.randomUUID().toString();
    public static final int JWT_TTL = 60*60*1000;  //millisecond
    /**
     * 加密密文
     */
    public static final String JWT_SECRET = "FHZM";

}
