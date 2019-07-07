package com.alphastar.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.ssl.PrivateKeyStrategy;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.alphastar.statics.JsonFiles;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Map;

@Component
public class HttpClientSend {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void go() {
        try {
            String CERT_ALIAS = "client-keypair", CERT_PASSWORD = "password";

            // use classpath to avoid filesystem. File should be in resources folder root dir
            InputStream clientKeystore = new ClassPathResource(
                    "client-keystore.jks").getInputStream();

            InputStream clientTruststore = new ClassPathResource(
                    "client-truststore.jks").getInputStream();

            KeyStore identityKeyStore1 = KeyStore.getInstance("jks");
            identityKeyStore1.load(clientKeystore, CERT_PASSWORD.toCharArray());

            KeyStore trustKeyStore1 = KeyStore.getInstance("jks");
            trustKeyStore1.load(clientTruststore, CERT_PASSWORD.toCharArray());


            SSLContext sslContext = null;
            try {
                sslContext = SSLContexts.custom()
                        // load identity keystore
                        .loadKeyMaterial(identityKeyStore1, CERT_PASSWORD.toCharArray(), new PrivateKeyStrategy() {
                            @Override
                            public String chooseAlias(Map<String, PrivateKeyDetails> aliases, Socket socket) {
                                return CERT_ALIAS;
                            }
                        })
                        // load trust keystore
                        .loadTrustMaterial(trustKeyStore1, null)
                        .build();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            }

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TLSv1.2", "TLSv1.1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            CloseableHttpClient client = HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .build();

            logger.info("[GOLD] MUTUAL AUTH SSL ONLY");
            String ncwurl = "https://localhost:8443/gold/person";
            HttpPost post = new HttpPost(ncwurl);
            StringEntity ncwparams = new StringEntity(JsonFiles.ONE_JSON);
            setPostAttrs(post,ncwparams);
            HttpResponse response = client.execute(post); // object reused intentionally
            printResponse(response);

            logger.info("[BRONZE] FORBIDDEN BY ANTMATCHER");
            post  = new HttpPost("https://localhost:8443/bronze/person");
            setPostAttrs(post, ncwparams);
            response = client.execute(post);
            printResponse(response);

            logger.info("[SILVER] ALLOW ALL BY ANTMATCHER");
            post  = new HttpPost("https://localhost:8443/silver/person");
            setPostAttrs(post, ncwparams);
            response = client.execute(post);
            printResponse(response);

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            e.printStackTrace();
            logger.error("Error caught", e);
        }

    }

    private void setPostAttrs(HttpPost post, StringEntity ncwparams) {
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setEntity(ncwparams);
    }

    private void printResponse(HttpResponse response) throws IOException {
        logger.info("Response Code: " + response.getStatusLine().getStatusCode());
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while (true) {
            try {
                if (!((line = rd.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(line);
        }
    }


}