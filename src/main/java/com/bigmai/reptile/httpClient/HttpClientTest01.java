package com.bigmai.reptile.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

// GET请求
public class HttpClientTest01 {

    public static void main(String[] args) {
        // 获取默认的client客户端请求对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 客户端请求结果响应对象
        CloseableHttpResponse response = null;
        try{
            // 指定请求网站的路径地址
            // 当前网址是一个普通的图片网
            String url = "https://www.mmonly.cc/mmtp/swmn/";
            // 创建HttpGet对象，封装url地址，httpGet中可以进行基本请求配置
            HttpGet get = new HttpGet(url);
            // 设置请求头, 请求头可以设置多个
            get.setHeader("ceshi","1");
            get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36");//模拟真机
            // 使用客户端请求对象，发起请求，获取响应
            response = client.execute(get);
            // 获取请求状态码，状态码为200，表示网页请求成功
            if (response.getStatusLine().getStatusCode() == 200){
                // 获取response中保存的entity对象
                HttpEntity entity = response.getEntity();
                // 将entity对象转换成字符串，就是HTML页面
                String htmlPage = EntityUtils.toString(entity);
                System.out.println("获取成功！ html页面如下 ------- >>>>>>> ");
                System.out.println(htmlPage);
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
