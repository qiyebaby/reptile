package com.bigmai.reptile.httpClient;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// 工具类
public class HttpClientTest04Pool {

    public static final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

    static {
        // 每隔一秒检查一次连接有效期
        cm.setValidateAfterInactivity(1000);
        // 设置连接池最大连接数
        cm.setMaxTotal(10);
        // 设置路由最大连接数
        cm.setDefaultMaxPerRoute(5);
    }

    /**
     * 从连接池中获取client对象
     * @return
     */
    public static CloseableHttpClient getClient(){
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * 构建参数，发起get请求
     * @param url 请求URL资源
     * @param headerMap 请求头
     * @param hostIp IP地址
     * @param port 端口号
     * @param client 请求对象
     * @return 响应对象
     */
    public static CloseableHttpResponse getCall(String url,Map<String, String> headerMap,
                                                String hostIp,String port,CloseableHttpClient client){
        CloseableHttpResponse response = null;
        try{
            HttpGet get = new HttpGet(url);
            if (MapUtils.isNotEmpty(headerMap)){
                for (String key : headerMap.keySet()) {
                    get.setHeader(key, headerMap.get(key));
                }
            }
            RequestConfig requestConfig;
            if (StringUtils.isNotBlank(hostIp) && StringUtils.isNotBlank(port)) {
                HttpHost httoHost = new HttpHost(hostIp, Integer.parseInt(port));
                requestConfig = RequestConfig.custom()
                        //设置连接超时时间,单位毫秒
                        .setConnectTimeout(5000)
                        //设置读取超时时间,单位毫秒
                        .setSocketTimeout(5000)
                        //设置代理
                        .setProxy(httoHost)
                        .build();

            } else {
                requestConfig = RequestConfig.custom()
                        //设置连接超时时间,单位毫秒
                        .setConnectTimeout(5000)
                        //设置读取超时时间,单位毫秒
                        .setSocketTimeout(5000)
                        .build();
            }
            get.setConfig(requestConfig);
            response = client.execute(get);
        }catch (Exception e){
            e.printStackTrace();
            return response;
        }
        return response;
    }

    /**
     *  构建参数，发起POST请求
     * @param url 请求URL资源
     * @param headerMap 请求头
     * @param paramsMap 请求参数
     * @param bodyData 请求表单
     * @param hostIp 代理IP
     * @param port 代理端口
     * @param httpClient 请求对象
     * @return 响应对象
     */
    public static CloseableHttpResponse postCall(String url, Map<String, String> headerMap,
                                                 Map<String, String> paramsMap, JSONObject bodyData, String hostIp, String port, CloseableHttpClient httpClient) {
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            if (MapUtils.isNotEmpty(headerMap)) {
                for (String key : headerMap.keySet()) {
                    post.setHeader(key, headerMap.get(key));
                }
            }
            if (MapUtils.isNotEmpty(paramsMap)) {
                for (String key : paramsMap.keySet()) {
                    List<BasicNameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair(key, paramsMap.get(key)));
                    post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                }
            }
            if (!Objects.equals(bodyData, null)) {
                post.setEntity(new StringEntity(bodyData.toString(), "UTF-8"));
            }
            RequestConfig requestConfig;
            if (StringUtils.isNotBlank(hostIp) && StringUtils.isNotBlank(port)) {
                HttpHost httoHost = new HttpHost(hostIp, Integer.parseInt(port));
                requestConfig = RequestConfig.custom()
                        //设置连接超时时间,单位毫秒
                        .setConnectTimeout(5000)
                        //设置读取超时时间,单位毫秒
                        .setSocketTimeout(5000)
                        //设置代理
                        .setProxy(httoHost)
                        .build();

            } else {
                requestConfig = RequestConfig.custom()
                        //设置连接超时时间,单位毫秒
                        .setConnectTimeout(5000)
                        //设置读取超时时间,单位毫秒
                        .setSocketTimeout(5000)
                        .build();
            }
            post.setConfig(requestConfig);
            response = httpClient.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }
        return response;
    }

    /***
     * 获取响应体信息
     * @param response
     * @return
     */
    public static String getEntityStr(CloseableHttpResponse response) {
        try {
            String s = EntityUtils.toString(response.getEntity());
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
