package com.tool.demo.core.service.impl;

import com.tool.demo.core.extractor.FileResponseExtractor;
import com.tool.demo.core.service.FileDownloader;
import com.tool.demo.core.util.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

/**
 * 通过将
 */
public class FileDownloaderByFileExtractor implements FileDownloader {


    @Override
    public void downloadFile(String fileUrl, String savePath) throws IOException {
        long start = System.currentTimeMillis();
        FileResponseExtractor extractor = new FileResponseExtractor(savePath + ".download");
        RestTemplate restTemplate = RestTemplateBuilder.builder().build();
        // 先下载程临时文件
        File tempFile = restTemplate.execute(fileUrl, HttpMethod.GET, null, extractor);
        // 改名
        tempFile.renameTo(new File(savePath));
        System.out.println(String.format("下载文件耗时:%d+ s", (System.currentTimeMillis() - start) / 1000));
    }

}
