package com.huboot.business.base_model.weixin_service.support;

import cn.binarywang.wx.miniapp.bean.AbstractWxMaQrcodeWrapper;
import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.common.util.http.apache.InputStreamResponseHandler;
import me.chanjar.weixin.common.util.http.apache.Utf8ResponseHandler;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class QrCodeRequestExecutorExt implements RequestExecutor<File, AbstractWxMaQrcodeWrapper> {
  protected RequestHttp<CloseableHttpClient, HttpHost> requestHttp;

  public QrCodeRequestExecutorExt(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  @Override
  public File execute(String uri, AbstractWxMaQrcodeWrapper ticket) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    httpPost.setHeader("Content-Type", "application/json");
    if (requestHttp.getRequestHttpProxy() != null) {
      httpPost.setConfig(
          RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build()
      );
    }
    httpPost.setEntity(new StringEntity(WxMaGsonBuilder.create().toJson(ticket)));

    try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost);
         InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);) {
      Header[] contentTypeHeader = response.getHeaders("Content-Type");
      if (contentTypeHeader != null && contentTypeHeader.length > 0
          && ContentType.APPLICATION_JSON.getMimeType()
          .equals(ContentType.parse(contentTypeHeader[0].getValue()).getMimeType())) {
        String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
        throw new WxErrorException(WxError.fromJson(responseContent));
      }

      return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
    } finally {
      httpPost.releaseConnection();
    }
  }
}
