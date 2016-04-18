package org.smart4j.framework.bean;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.InputStream;

/**
 * 封装文件上传参数
 */
public class FileParam {
    private String fieldName;//表单字段名
    private String fileName;//文件名
    private long fileSize;//文件大小
    private String contentType;//文件类型
    private InputStream inputStream;//文件输入流

    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() { return fileSize; }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
