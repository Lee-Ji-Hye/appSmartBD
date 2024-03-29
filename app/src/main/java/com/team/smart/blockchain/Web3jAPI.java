package com.team.smart.blockchain;

import com.team.smart.blockchain.config.Configuration;
import com.team.smart.blockchain.config.Web3jUtil;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class Web3jAPI {

    private static Web3j web3j;

    public static Credentials getCredentials() {
        return credentials;
    }


    private static Credentials credentials;

    private static final Web3jAPI INSTANCE = new Web3jAPI();

    public static Web3jAPI getInstance(){

        if (web3j == null){
            initialize();
        }

        return INSTANCE;
    }

    private Web3jAPI(){}

    public String version(){
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();

            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            return clientVersion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private static void connect(){
        web3j = Web3jUtil.buildHttpClient();
    }

    static void initCredentials(){
        Wallet wallet = Wallet.getInstance();
        credentials = wallet.getCredentials();
    }

    static void initialize(){
        connect();
        initCredentials();
    }



    public boolean sendETH(String to, Double ETHValue) {

        try {
            TransactionReceipt transactionReceipt = Transfer.sendFunds(
                    web3j, credentials, to,
                    BigDecimal.valueOf(ETHValue), Convert.Unit.ETHER)
                    .send();
            // get tx hash and tx fees
            String txHash = transactionReceipt.getTransactionHash();
            BigInteger txFees = transactionReceipt
                    .getCumulativeGasUsed()
                    .multiply(Configuration.GAS_PRICE);

            System.out.println("hash: " + txHash);
            System.out.println("fees: " + Web3jUtil.weiToEther(txFees));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public BigDecimal getETHBalance(){

        try {
            return Web3jUtil.getBalanceEther(web3j,credentials.getAddress());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new BigDecimal("0");

    }


    public String getAddress(){
        return credentials.getAddress();
    }


    public String exportPrivateKey(){
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        return Numeric.toHexStringWithPrefix(ecKeyPair.getPrivateKey());
    }
}
