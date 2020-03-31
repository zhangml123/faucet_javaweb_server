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


/**
 * @Auther: 
 * @Date: 
 * @Description: 发送转账交易,查询余额
 */
public class TransactionSender {
	private static String chainId = "101";
	private static String nodeAddress = "NODE_ADDRESS";
	private static String privateKey = "PRIVATEKEY";
	private static String faucetAddress = "FAUCETADDRESS";
    private Web3j currentValidWeb3j = Web3j.build(new HttpService(nodeAddress));
    private Credentials credentials = Credentials.create(privateKey);
    private final Logger logger = LogManager.getLogger(TransactionSender.class.getName());
    public TransactionSender (){
    	super();
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
