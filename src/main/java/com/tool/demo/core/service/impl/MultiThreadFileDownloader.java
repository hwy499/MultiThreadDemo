package com.tool.demo.core.service.impl;

import com.tool.demo.core.extractor.FileResponseExtractor;
import com.tool.demo.core.service.FileDownloader;
import com.tool.demo.core.util.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程文件下载器
 */
public class MultiThreadFileDownloader implements FileDownloader {


    private int threadNum = 3;

    public MultiThreadFileDownloader(int threadNum) {
        this.threadNum = threadNum;
    }

    @Override
    public void downloadFile(String fileUrl, String savePath) throws IOException {
        RestTemplate restTemplate = RestTemplateBuilder.builder().build();
        // 开始时间
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        System.out.println(String.format("%d start download file .....", startTime));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        // 獲取响应实体
        ResponseEntity<String> responseEntity = restTemplate.exchange(fileUrl, HttpMethod.HEAD, requestEntity, String.class);
        // 获取文件大小
        long contentLength = responseEntity.getHeaders().getContentLength();
        // 将文件长度分块
        long step = contentLength / threadNum;
        List<CompletableFuture<File>> futures = new ArrayList<>();

        for (int i = 1; i <= threadNum; i++) {
            String start = step * (i - 1) + "";
            String end = (i == threadNum) ? "" : (step * i - 1) + "";
            FileResponseExtractor extractor = new FileResponseExtractor(savePath + ".download." + i);
            CompletableFuture<File> future = CompletableFuture.supplyAsync(() -> {
                RequestCallback callback = request -> {
                    request.getHeaders().set(HttpHeaders.RANGE, "bytes=" + start + "-" + end);
                };
                return restTemplate.execute(fileUrl, HttpMethod.GET, callback, extractor);
            }, executorService).exceptionally(e -> {
                e.printStackTrace();
                return null;
            });
            futures.add(future);
        }

        FileChannel channel = new FileOutputStream(new File(savePath)).getChannel();
        futures.forEach(future -> {
            try {
                File temp = future.get();
                FileChannel in = new FileInputStream(temp).getChannel();
                channel.transferFrom(in, channel.size(), in.size());
                in.close();
                temp.delete(); // 删除临时文件
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        channel.close();
        executorService.shutdown();
        System.out.println(String.format("download success ,total cast time :%d S", (System.currentTimeMillis() - startTime) / 1000));
    }

}
