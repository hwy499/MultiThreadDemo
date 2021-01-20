package com.tool.demo.core.extractor;

import org.springframework.http.client.ClientHttpResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hwy20
 * @version 版本
 * @date 2021/1/20
 * @description 简单描述一下类的功能
 */
public class FileResponseExtractor extends AbstractCustomizeResponseExtractor<File> {

    // 已经下载的字节数
    private long byteCount;
    /**
     * 文件路径
     */
    private String savePath;

    public FileResponseExtractor(String savePath) {
        this.savePath = savePath;
    }

    @Override
    protected long obtainAlreadyDownloadLength() {
        return this.byteCount;
    }

    @Override
    protected File doExtractData(ClientHttpResponse response) throws IOException {
        InputStream in = response.getBody();
        File file = new File(savePath);
        FileOutputStream out = new FileOutputStream(file);
        int byteRead;
        byte[] buffer = new byte[4096];
        while ((byteRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, byteRead);
            byteCount += byteRead;
        }
        out.flush();
        out.close();
        return file;
    }
}
