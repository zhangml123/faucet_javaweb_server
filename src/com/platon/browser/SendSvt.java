package com.platon.browser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = "/send")
public class SendSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TransactionSender transactionSender;
	private final Logger logger = LogManager.getLogger(SendSvt.class.getName());
	public SendSvt() {
        super();
        this.transactionSender = new TransactionSender();
    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
    {
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String address = request.getParameter("address");
		String amount = request.getParameter("amount");
		String status = "";
		try {
			status = transactionSender.transfer(address,amount);
			response.getOutputStream().write(status.getBytes());
		}catch(Exception e) {
			logger.error("{\"msg\":\"SendSvt error\",\"err\":"+ e +"}");
			response.getOutputStream().write(e.toString().getBytes());
		}
		
	}
}