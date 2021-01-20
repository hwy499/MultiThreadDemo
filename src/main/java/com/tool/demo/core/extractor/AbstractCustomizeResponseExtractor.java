package com.tool.demo.core.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


public abstract class AbstractCustomizeResponseExtractor<T> implements DisplayDownLoadSpeed, ResponseExtractor<T> {


    /**
     * 获取已经下载的字节数量
     *
     * @return
     */
    protected abstract long obtainAlreadyDownloadLength();

    /**
     * 从响应结果中解析数据
     *
     * @param response
     * @return
     * @throws IOException
     */
    protected abstract T doExtractData(ClientHttpResponse response) throws IOException;


    @Override
    public void displaySpeed(String task, long contentLength) {
        long totalSize = contentLength / 1024;
        CompletableFuture.runAsync(() -> {
            long temp = 0, speed;
            while (contentLength - temp > 0) {
                speed = obtainAlreadyDownloadLength() - temp;
                temp = obtainAlreadyDownloadLength();
                log(task, totalSize, temp, speed);
                sleep(1000);
            }
        });
    }


    @Override
    public T extractData(ClientHttpResponse response) throws IOException {
        long contentLength = response.getHeaders().getContentLength();
        this.displaySpeed(Thread.currentThread().getName(), contentLength);
        return this.doExtractData(response);
    }

    protected void log(String task, long totalSize, long tmp, long speed) {
        System.out.println(String.format("%s,文件总大小%dKB,已下载%dKB,下载速度%dKB/S", task, totalSize, (tmp / 1024), (speed / 1000)));
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}