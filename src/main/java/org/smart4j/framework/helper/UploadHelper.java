package org.smart4j.framework.helper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.FormPrama;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.FileUtil;
import org.smart4j.framework.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 文件上传助手类
 */
public final class UploadHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);
    //Apache Commons FileUpload提供的servlet文件上传对象
    private static ServletFileUpload servletFileUpload;
    /**
     * 初始化
     */
    public static void init(ServletContext servletContext) {
        //获取一个临时目录(使用tomcat的work目录)
        File repository = (File)servletContext.getAttribute("javax.servlet.context.tempdir");
        //创建Fileupload对象
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        //设置上传上限
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);//单位为M
        }
    }
    /**
     * 判断请求类型是否为mutilpart类型
     */
    public static boolean isMutilpart (HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }
    /**
     * 创建请求对象
     */
    public static Param createParam (HttpServletRequest request) throws IOException {
        //表单参数
        List<FormPrama> formPramaList = new ArrayList<FormPrama>();
        //文件参数
        List<FileParam> fileParamList = new ArrayList<FileParam>();
        try {
            //解析并遍历请求参数
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (CollectionUtil.isNotEmpty(fileItemListMap)) {
                for (Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap.entrySet()) {
                    String fieldName = fileItemListEntry.getKey();
                    List<FileItem> fileItemList = fileItemListEntry.getValue();
                    if (CollectionUtil.isNotEmpty(fileItemList)) {
                        for (FileItem fileItem : fileItemList) {
                            if (fileItem.isFormField()) {
                                //处理普通表单字段
                                String fieldValue = fileItem.getString("UTF-8");
                                //初始化表单参数对象
                                formPramaList.add(new FormPrama(fieldName, fieldValue));
                            } else {
                                //处理文件字段
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if (StringUtil.isNotEmpty(fileName)) {
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            LOGGER.error("create param failure", e);
            throw new RuntimeException(e);
        }
        return  new Param(formPramaList, fileParamList);

    }
    /**
     * 上传文件
     */
    public static void uploadFile(String basePath, FileParam filePrama) {
        if (filePrama != null) {
            String filePath = basePath + filePrama.getFieldName();

        }
    }
    /**
     * 批量上传文件
     */
    public static void uploadFile(String basePath, List<FileParam> filePramaList) {
        try {
            if (CollectionUtil.isNotEmpty(filePramaList)) {
                for (FileParam fileParam : filePramaList) {
                    uploadFile(basePath, fileParam);
                }
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

}
