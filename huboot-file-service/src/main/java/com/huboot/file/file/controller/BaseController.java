package com.huboot.file.file.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;

public class BaseController {

    protected ResponseEntity<FileSystemResource> export(File file) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().mustRevalidate());
        headers.setCacheControl(CacheControl.noStore().mustRevalidate());
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());
        headers.setPragma("no-cache");
        headers.setExpires(0);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }

    protected ResponseEntity<UrlResource> export(String url) throws MalformedURLException {
        if (url == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().mustRevalidate());
        headers.setCacheControl(CacheControl.noStore().mustRevalidate());
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("asd").build());
        headers.setPragma("no-cache");
        headers.setExpires(0);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new UrlResource(url));
    }

    protected ResponseEntity<InputStreamResource> exportFromAliyun(String url,InputStream inputStream) throws MalformedURLException {
        if (url == null) {
            return null;
        }
        String suffix = url.substring(url.lastIndexOf("/") + 1);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().mustRevalidate());
        headers.setCacheControl(CacheControl.noStore().mustRevalidate());
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(suffix).build());
        headers.setPragma("no-cache");
        headers.setExpires(0);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }

}
