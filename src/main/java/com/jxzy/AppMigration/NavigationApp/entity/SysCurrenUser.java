package com.jxzy.AppMigration.NavigationApp.entity;


import lombok.Data;

/**
* @ClassName: SysCurrenUser  
* @Description: 前台用户
* @author Mars
* @date 2018年4月3日  
*
 */
@Data
public class SysCurrenUser  {

	/**
	* @Fields serialVersionUID : serialVersionUID
	*/
	private static final long serialVersionUID = -2164214646864659825L;

	public Long currentUserId;

	public String currentUserPhone;

	public String currentOpenId;

	public String currentSessionKey;

	public String currentThdSession;

	public String depositPayState;

	public String depositPayAmount;

	public String creditArrearsState;
	
	public String payUserId;
	
	public String paymentChannels;

	public String createDate;

	public String updateDate;

	private Long currentUserBlackId;
	
	private String gpsCoordinates;
	
	private String scenicSpotId;
	
    private String scenicSpotName;
    
    private String depositPayTime;
    
    private String returnDepositPayTime;
    
    private String smallAppMonitorState;
    
    private String smallPolling;
    
    private String continuationStatus;
    
	

	
	
}
