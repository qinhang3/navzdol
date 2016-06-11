package com.github.navzdol.util;


import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by hang on 16/5/10.
 */
public class HttpUtil {

    private static String socksAddr = "127.0.0.1";
    private static int socksPort = 1080;

    public static HttpResponse executeGet(String url, Map<String, String> param, boolean proxy, int retryTimes) throws IOException {
        HttpResponse response;
        for (int i = 1; i <= retryTimes; i++) {
            response = executeGet(url, param, proxy);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK || i == retryTimes) {
                return response;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(Math.round(Math.random() * 500));
            } catch (InterruptedException ignore) {
            }
        }
        return null;
    }


    public static HttpResponse executeGet(String url, Map<String, String> param, boolean proxy) throws IOException {
        if (param != null && !param.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(url);
            stringBuilder.append("?");
            for (Map.Entry<String, String> entry : param.entrySet()) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(entry.getValue());
            }
            url = stringBuilder.toString();
        }

        HttpUriRequest get = new HttpGet(url);
        if (proxy) {
            return executeProxy(get);
        } else {
            return execute(get);
        }
    }

    public static void main(String[] string) throws IOException {

    }

    private static HttpResponse executeProxy(HttpUriRequest request) throws IOException {
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new MyConnectionSocketFactory())
                .register("https", new MySSLConnectionSocketFactory(SSLContexts.createSystemDefault())).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg, new FakeDnsResolver());
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        InetSocketAddress socksaddr = new InetSocketAddress(socksAddr, socksPort);
        HttpClientContext context = HttpClientContext.create();
        context.setAttribute("socks.address", socksaddr);
        return httpclient.execute(request, context);
    }

    private static HttpResponse execute(HttpUriRequest request) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        return httpClient.execute(request);
    }

    private static class FakeDnsResolver implements DnsResolver {
        @Override
        public InetAddress[] resolve(String host) throws UnknownHostException {
            // Return some fake DNS record for every request, we won't be using it
            return new InetAddress[]{InetAddress.getByAddress(new byte[]{1, 1, 1, 1})};
        }
    }

    private static class MyConnectionSocketFactory extends PlainConnectionSocketFactory {
        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                    .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }
    }

    private static class MySSLConnectionSocketFactory extends SSLConnectionSocketFactory {

        public MySSLConnectionSocketFactory(final SSLContext sslContext) {
            // You may need this verifier if target site's certificate is not secure
            super(sslContext, ALLOW_ALL_HOSTNAME_VERIFIER);
        }

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                    .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }
    }
}
