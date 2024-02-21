package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrderUserInformation;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/5 14:38
 */
public interface BusinessOrderUserInformationService {
    int addBusinessBookingTickets(BusinessOrderUserInformation businessOrderUserInformation);


    PageDataResult getBusinessBookingTicketsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    BusinessOrderUserInformation getBusinessBookingTicketsDetails(Map<String, Object> search);


    int cancellationReservation(String id);

    void getAdmissionfeeTime();



}
