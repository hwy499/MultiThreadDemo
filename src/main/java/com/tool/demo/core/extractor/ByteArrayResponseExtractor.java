package com.tool.demo.core.extractor;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayResponseExtractor extends AbstractCustomizeResponseExtractor<byte[]> {

    private long byteCount;

    @Override
    protected byte[] doExtractData(ClientHttpResponse response) throws IOException {
        long contentLength = response.getHeaders().getContentLength();
        // 创建一个字节数组的输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream( contentLength > 0 ? (int) contentLength : StreamUtils.BUFFER_SIZE);
        // 获取输入流
        InputStream in = response.getBody();
        // 用于保存实际读取字节的大小
        int byteRead;
        // 缓冲区
        byte[] buffer = new byte[4096];
        while ((byteRead = in.read(buffer)) != -1) {
            // 写入字节数组输出流
            out.write(buffer, 0, byteRead);
            byteCount += byteRead;
        }
        return out.toByteArray();
    }

    @Override
    protected long obtainAlreadyDownloadLength() {
        return byteCount;
    }



}
