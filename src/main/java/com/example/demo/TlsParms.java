package com.example.demo;

public class TlsParms {

    private String testEndpoint = "https://www.howsmyssl.com/a/check";
    private String tlsVersion = "TLSv1.2";
    private String result = "{\"value\": \"test\"}";

    public String getTestEndpoint() {
        return testEndpoint;
    }

    public void setTestEndpoint(String testEndpoint) {
        this.testEndpoint = testEndpoint;
    }

    public String getTlsVersion() {
        return tlsVersion;
    }

    public void setTlsVersion(String tlsVersion) {
        this.tlsVersion = tlsVersion;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}