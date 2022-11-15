package com.amazon.elasticache;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
// Import the &AWS;-provided library with Auto Discovery support 
import net.spy.memcached.MemcachedClient;

public class MemcachedClientFactory {
	
	private MemcachedClient memcachedClient;


	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	/**
	 * Constructor
	 */
	public MemcachedClientFactory() {
		super();
		initMemcachedClient();
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	 public static void main(String[] args) throws Exception {
		 
		MemcachedClientFactory mcFactory = new MemcachedClientFactory();
		//Get TLS enabled memcached client.
		MemcachedClient client = mcFactory.getMemcachedClient();
		
		if (client!=null) {
	        // Store a data item for an hour.
	        client.set("theKey", 3600, "Finally I am Successful.");
	        System.out.println("Value of the key is : " + client.get("theKey"));
	        //Work is done shutdown the client.
	        client.shutdown();
		}else {
			 System.out.println("Cloud not create memcached client.");
		}
        
	  }
	 
	/**
	 * 
	 * @return TLS enabled memcached client. 
	 */
	 private void initMemcachedClient() {
		MemcachedClient client;
	 	try {
			ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
			// Build SSLContext
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init((KeyStore) null);
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, tmf.getTrustManagers(), null);
			// Create the client in TLS mode
			connectionFactoryBuilder.setSSLContext(sslContext);
			// TLS mode enables hostname verification by default. It is always recommended to do that.
			// To disable hostname verification, do the following:
			connectionFactoryBuilder.setSkipTlsHostnameVerification(true);
			client = new MemcachedClient(connectionFactoryBuilder.build(), AddrUtil.getAddresses("<clusterconfighost:port"));
			if (client.isConfigurationInitialized())
				setMemcachedClient(client);
			else
				client.shutdown();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	 }

}