package com.tool.demo.core.extractor;

public interface DisplayDownLoadSpeed {

    /**
     * 显示下载的速度
     *
     * @param task
     * @param contentLength
     */
    void displaySpeed(String task, long contentLength);
}
