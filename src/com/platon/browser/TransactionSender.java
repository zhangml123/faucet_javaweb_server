package com.platon.browser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties; 

import java.io.BufferedInputStream;
import java.io.InputStream;

import java.io.FileInputStream;
/**
 * @Auther: 
 * @Date: 
 * @Description: 发送转账交易,查询余额
 */
public class TransactionSender {
	Properties prop = new Properties();
	private static String chainId;
	private static String nodeAddress;
	private static String privateKey;
	private static String faucetAddress;
    private Web3j currentValidWeb3j = Web3j.build(new HttpService(nodeAddress));
    private Credentials credentials = Credentials.create(privateKey);
    private final Logger logger = LogManager.getLogger(TransactionSender.class.getName());
    public TransactionSender (){
    	super();
    	try{
	    	InputStream in = new BufferedInputStream (new FileInputStream("a.properties"));
	        prop.load(in);
	        chainId = prop.getProperty("chainId");
	        nodeAddress = prop.getProperty("nodeAddress");
	        privateKey = prop.getProperty("privateKey");
	        faucetAddress = prop.getProperty("faucetAddress");
	    }
        catch(Exception e){
            System.out.println(e);
        } 
    	
    }
    // 发送转账交易
    @Test
    public String transfer(String address, String amount) throws Exception {
		TransactionReceipt receipt = Transfer.sendFunds(
		        currentValidWeb3j,
		        credentials,
		        chainId,
		        address,
		        new BigDecimal(amount),
		        Unit.LAT
		).send();
		logger.info("{\"mag\":\"send lat\",\"address\":"+address+",\"amount\":"+amount+",\"status\":"+receipt.getStatus()+"}");
		String status = receipt.getStatus();
		String txid = receipt.getTransactionHash();
		return "{\"status\":\""+status+"\",\"address\":\""+address+"\",\"txid\":\""+txid+"\"}";
    }
    @Test
    //public String getBalance(String address) throws Exception {
    public String getBalance() throws Exception {
    	BigInteger balance = currentValidWeb3j.platonGetBalance(faucetAddress, DefaultBlockParameterName.LATEST).send().getBalance();
    	logger.info("{\"mag\":\"check balance\",\"address\":"+faucetAddress+",\"balance\":"+balance.toString()+"}");
    	return balance.toString();
    }

}
