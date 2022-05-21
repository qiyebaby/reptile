package com.bigmai.reptile.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2022/5/21 10:44
 * @Author:何磊
 */
public class HttpClientTest02 {

    public static void main(String[] args) {
        // 获取默认的client客户端请求对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 客户端请求结果响应对象
        CloseableHttpResponse response = null;
        try{
            // 指定请求网站的路径地址
            String url = "https://www.bfzywz.com";
            // 创建HttpPost对象
            HttpPost post = new HttpPost(url);
            // 设置请求头
//            post.setHeader("","");
            // 设置post请求表单数据
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            // 封装请求数据
            params.add(new BasicNameValuePair("username","admin"));
            params.add(new BasicNameValuePair("password","123456"));
            // 将表单数据封装到post请求中
            post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
            // 设置请求的超时时间等，链接时间，等待时间，如果超时则断开链接。
            // 因为有的网站可能访问不到，或者服务器宕机，不在花太长时间等待。
            RequestConfig requestConfig;
            // 使用IP代理，也就是更换IP进行访问  这里一般不适用，除非网站会黑IP，写在这里是演示
            HttpHost httpHost = new HttpHost("192.168.0.1",8080);
            requestConfig = RequestConfig.custom()
//                    .setProxy(httpHost)         // 模拟IP代理 运行时注释掉，不用代理
                    .setSocketTimeout(5000)     // 设置读取的超时时间 单位是毫秒
                    .setConnectTimeout(3000)    // 设置连接的超时时间 单位是毫秒
                    .build();
            // 将配置加入POST对象中
            post.setConfig(requestConfig);
            // 发起请求，获取响应结果
            response = client.execute(post);
            // 如果响应状态码是200，则请求成功
            if ( response.getStatusLine().getStatusCode() == 200 ) {
                HttpEntity entity = response.getEntity();
                String htmlPage = EntityUtils.toString(entity);
                System.out.println("请求成功！！ -------- >>>> " + htmlPage);
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            // 响应对象 以及 请求对象，都会打开通道消耗资源，使用完毕必须关闭。
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
