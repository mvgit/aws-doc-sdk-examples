package com.amazon.elasticache;

import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
// Import the &AWS;-provided library with Auto Discovery support 
import net.spy.memcached.MemcachedClient;

public class AutoDiscoveryDemo {

	
	 public static void main(String[] args) throws Exception {
	  
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
        MemcachedClient client = new MemcachedClient(connectionFactoryBuilder.build(), AddrUtil.getAddresses("memcachecluster.zq1ksm.cfg.use1.cache.amazonaws.com:11211"));
      
        // Store a data item for an hour.
        client.set("theKey", 3600, "Finally I am Successful.");
     
        System.out.println("Value of the key is : " + client.get("theKey"));
        
	  }

}