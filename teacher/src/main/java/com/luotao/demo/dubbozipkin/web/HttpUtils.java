package com.luotao.demo.dubbozipkin.web;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * User: luotao
 * Date: 12-4-19
 * Time: 12:43:00
 */
public class HttpUtils {

    private static final String METHOD_POST = "POST";

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String CONTENT_TYPE_TEXT = "application/x-www-form-urlencoded";

    public static byte[] doGet(String str_url) {
        try {

            URL url = new URL(str_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("requestid",String.valueOf(System.currentTimeMillis()));
            conn.setDoOutput(true);

            InputStream input = conn.getInputStream();
            return parseFromInputStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String doGetStr(String str_url, String encoding) {
        byte[] res_bytes = doGet(str_url);
        try {
            String res_str = new String(res_bytes, encoding);
            return res_str;
        } catch (UnsupportedEncodingException e) {
            return new String(res_bytes);
        }
    }


    public static String doPostMapStr(String str_url, String params
            , int conn_timeout, int read_timeout, String encoding) {
        byte[] res_bytes = doPost(str_url, params, conn_timeout, read_timeout);
        try {
            String res_str = new String(res_bytes, encoding);
            return res_str;
        } catch (UnsupportedEncodingException e) {
            return new String(res_bytes);
        }
    }

    public static String doPostStr(String str_url, Map<String, String> params
            , int conn_timeout, int read_timeout, String encoding) {
        byte[] res_bytes = doPost(str_url, params, conn_timeout, read_timeout);
        try {
            String res_str = new String(res_bytes, encoding);
            return res_str;
        } catch (UnsupportedEncodingException e) {
            return new String(res_bytes);
        }
    }

    public static byte[] doPost(String str_url, Map<String, String> paramsMap, int conn_timeout, int read_timeout) {
        StringBuilder sb = new StringBuilder();
        if (paramsMap != null && paramsMap.size() > 0) {
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return doPost(str_url, sb.toString(), conn_timeout, read_timeout);
    }

    public static byte[] doPost(String str_url, String params, int conn_timeout, int read_timeout) {
        try {
            URL url = new URL(str_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            // Read from the connection. Default is true.
            connection.setDoInput(true);
            // Set the post method. Default is GET
            connection.setRequestMethod(METHOD_POST);
            // Post cannot use caches
            // Post 请求不能使用缓存
            connection.setUseCaches(false);

            connection.setConnectTimeout(conn_timeout);

            connection.setReadTimeout(read_timeout);
            // This method takes effects to
            // every instances of this class.
            // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
            // connection.setFollowRedirects(true);

            // This methods only
            // takes effacts to this
            // instance.
            // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            connection.setInstanceFollowRedirects(false);

            // Set the content type to urlencoded,
            // because we will write
            // some URL-encoded content to the
            // connection. Settings above must be set before connect!
            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
            // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
            // 进行编码
            connection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_TEXT);
            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
            // 要注意的是connection.getOutputStream会隐含的进行connect。
            connection.setRequestProperty("connection","close");
            connection.setRequestProperty("requestid",String.valueOf(System.currentTimeMillis()));


            connection.connect();


            DataOutputStream out = new DataOutputStream(connection.getOutputStream());


            out.writeBytes(params);
            out.flush();
            out.close();
            InputStream input = connection.getInputStream();
            return parseFromInputStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static byte[] parseFromInputStream(InputStream input) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(input);
        byte[] by_result = new byte[dataInputStream.available()];
        dataInputStream.readFully(by_result);
        input.close();
        return by_result;
    }

    public static void main(String[] args) throws Exception {

        byte[] res = doGet("http://localhost:8080/index?id=105");
        String str11 = new String(res);
        System.out.println("str11 = " + str11);
    }
}