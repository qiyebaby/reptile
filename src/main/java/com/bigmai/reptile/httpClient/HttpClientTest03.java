package com.bigmai.reptile.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Locale;

/**
 * Description: TODO //
 *
 * @Date:2022/5/21 11:02
 * @Author:何磊
 */
public class HttpClientTest03 {
    public static void main(String[] args) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try{
            String url = "https://www.mmonly.cc/mmtp/swmn/";
            HttpGet get = new HttpGet(url);

            response = client.execute(get);

            // 研究response响应对象的状态

            // 1、获取page页面 需要配合EntityUtils工具类将entity转成string字符串
            response.getEntity();

            // 2、statusLine中保存了请求的协议以及状态码 ---->>>  HTTP/1.1 200 OK
            StatusLine statusLine = response.getStatusLine();

            // 3、获取所有的响应头
            response.getAllHeaders();

            // 4、获取指定名称的第一个请求头
            response.getFirstHeader("set-cookie");

            // 5、获取指定名称的最后一个请求头
            response.getLastHeader("set-cookie");

            // 6、获取当前访问IP的语言，地址，类似识别IP地址
            Locale locale = response.getLocale(); //zh_CN

            // 7、获取协议
            ProtocolVersion protocolVersion = response.getProtocolVersion(); //HTTP/1.1
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if (response != null ){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
