package com.example.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;

@Controller
public class TlsTestController {

    public static final String[] DEFAULT_CIPHERS = {
            "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384"
    };

    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("tlsParms", new TlsParms());
        return "tlstest";
    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute TlsParms tlsParms) throws Exception{
        SSLContext sslContext = SSLContexts.custom()
                .build();

        String[] cipherSuites = null;
        if(tlsParms.getTlsVersion().equals("TLSv1.3")){
            cipherSuites = new String[]{"TLS_AES_128_GCM_SHA256"};
        }

        SSLConnectionSocketFactory f = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{tlsParms.getTlsVersion()},
                cipherSuites,
                new DefaultHostnameVerifier());

        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(f)
                .build();

        HttpGet request = new HttpGet(tlsParms.getTestEndpoint());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpResponse response = httpClient.execute(request);
        response.getEntity().writeTo(baos);
        tlsParms.setResult(new String(baos.toByteArray()));

        return "result";
    }

}