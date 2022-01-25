package com.doublekit.pipeline.common;

import com.doublekit.eam.common.Ticket;
import com.doublekit.eam.common.TicketContext;
import com.doublekit.eam.common.TicketHolder;


/*
* 查询当前登录用户
* */
public class CurrentRegUser {

    private static CurrentRegUser  instance = new CurrentRegUser();
    private CurrentRegUser(){};


    //当前登录用户id
    public static CurrentRegUser getInstace() {

        return instance;
    }

    //当前登录用户id
    public String findCreatUser() {

        String ticketId = TicketHolder.get();
        Ticket ticket = TicketContext.get(ticketId);
        return ticket.getUserId();
    }
}
