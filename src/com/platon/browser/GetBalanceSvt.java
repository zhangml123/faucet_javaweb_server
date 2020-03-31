package com.platon.browser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = "/getBalance")
public class GetBalanceSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TransactionSender transactionSender;
	private final Logger logger = LogManager.getLogger(GetBalanceSvt.class.getName());
	public GetBalanceSvt() {
        super();
        this.transactionSender = new TransactionSender();
    }
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
    {
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		//String address = request.getParameter("address");
		String balance = "";
		try {
			//balance = transactionSender.getBalance(address);
			balance = transactionSender.getBalance();
			response.getOutputStream().write(("{\"status\":true,\"balance\":"+ balance +"}").getBytes());
		    
		}catch(Exception e) {
			logger.error("{\"msg\":\"getBalance error\",\"err\":"+ e +"}");
			response.getOutputStream().write(("{\"status\":false,\"err\":"+ e.toString() +"}").getBytes());
		}
	}
}