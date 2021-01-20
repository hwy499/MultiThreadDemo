package com.tool.demo.core.service.impl;

import com.tool.demo.core.extractor.ByteArrayResponseExtractor;
import com.tool.demo.core.service.FileDownloader;
import com.tool.demo.core.util.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class SingleThreadFileDownloader implements FileDownloader {

    @Override
    public void downloadFile(String fileUrl, String savePath) throws IOException {
        long start = System.currentTimeMillis();
        RestTemplate restTemplate = RestTemplateBuilder.builder().build();
        byte[] body = restTemplate.execute(fileUrl, HttpMethod.GET, null, new ByteArrayResponseExtractor());
        Files.write(Paths.get(savePath), Objects.requireNonNull(body));
        System.out.println(String.format("下载文件耗时:%d+ s", (System.currentTimeMillis() - start) / 1000));
    }


}
