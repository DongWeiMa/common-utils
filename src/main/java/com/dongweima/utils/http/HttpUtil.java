package com.dongweima.utils.http;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

/**
 * @author dongweima
 */
@SuppressWarnings("unused")
public class HttpUtil {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
  private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

  public static JSONObject httpPost(String url, JSONObject jsonParam) {
    if (jsonParam == null) {
      jsonParam = new JSONObject();
    }
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpPost httppost = new HttpPost(url);
    JSONObject json = null;
    CloseableHttpResponse response = null;
    try {
      StringEntity entity0 = new StringEntity(jsonParam.toString(), "utf-8");
      entity0.setContentEncoding("UTF-8");
      entity0.setContentType("application/json");
      String nbugs = threadLocal.get();
      if (nbugs != null) {
        httppost.setHeader("Cookie", "nbugs=" + nbugs);
      }
      httppost.setEntity(entity0);
      response = httpclient.execute(httppost);
      getCookie(response);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        json = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    } finally {
      try {
        httpclient.close();
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
      }
      if (response != null) {
        try {
          response.close();
        } catch (Exception e) {
          LOGGER.error(e.getMessage(), e);
        }
      }
    }
    return json;
  }

  public static JSONObject httpGet(String url) {
    CloseableHttpClient httpClient = getHttpClient();
    JSONObject json = null;
    try {
      HttpGet get = new HttpGet(url);
      CloseableHttpResponse httpResponse;
      String nbugs = threadLocal.get();
      if (nbugs != null) {
        get.setHeader("Cookie", "nbugs=" + nbugs);
      }
      httpResponse = httpClient.execute(get);
      getCookie(httpResponse);
      try {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
          json = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
        }
      } finally {
        httpResponse.close();
      }
    } catch (Exception e) {
      LOGGER.warn(e.getMessage(), e);
    } finally {
      try {
        closeHttpClient(httpClient);
      } catch (IOException e) {
        LOGGER.warn(e.getMessage(), e);
      }
    }
    return json;
  }

  private static CloseableHttpClient getHttpClient() {
    return HttpClients.createDefault();
  }

  private static void closeHttpClient(CloseableHttpClient client) throws IOException {
    if (client != null) {
      client.close();
    }
  }

  private static void getCookie(HttpResponse httpResponse) {
    Header[] headers = httpResponse.getHeaders("Set-Cookie");
    if (headers == null || headers.length == 0) {
      return;
    }
    StringBuilder cookie = new StringBuilder();
    for (int i = 0; i < headers.length; i++) {
      cookie.append(headers[i].getValue());
      if (i != headers.length - 1) {
        cookie.append(";");
      }
    }

    String[] cookies = cookie.toString().split(";");
    for (String c : cookies) {
      c = c.trim();
      String[] ck = c.split("=");
      if (ck[0] != null && "nbugs".equals(ck[0])) {
        threadLocal.remove();
        threadLocal.set(ck[1]);
      }
    }
  }
}
