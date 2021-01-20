package com.tool.demo.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 文件下载器接口
 */
public interface FileDownloader {


    /**
     * 下载文件
     *
     * @param fileURL
     * @param savePath
     */
    void downloadFile(String fileURL, String savePath) throws IOException;



}
