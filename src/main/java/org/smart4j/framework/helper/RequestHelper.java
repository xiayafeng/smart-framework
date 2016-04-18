package org.smart4j.framework.helper;

import org.smart4j.framework.bean.FormPrama;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CodecUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求助手类
 */
public final class RequestHelper {
    /**
     * 创建请求对象
     */
    public static Param createParam (HttpServletRequest request) throws IOException {
        List<FormPrama> formPramaList = new ArrayList<FormPrama>();
        formPramaList.addAll(parseParameterNames(request));
        formPramaList.addAll(parseInputStream(request));
        return new Param(formPramaList);
    }

    private static List<FormPrama> parseParameterNames (HttpServletRequest request) {
        List<FormPrama> formPramaList = new ArrayList<FormPrama>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if (ArrayUtil.isNotEmpty(fieldValues)) {
                Object fieldValue;
                if (fieldValues.length == 1) {
                    fieldValue = fieldValues[0];
                } else {
                    StringBuilder sb = new StringBuilder("");
                    for (int i = 0; i < fieldValues.length; i++) {
                        sb.append(fieldValues[i]);
                        if (i != fieldValues.length -1) {
                            sb.append(StringUtil.SPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formPramaList.add(new FormPrama(fieldName, fieldValue));
            }
        }

        return  formPramaList;
    }

    private static List<FormPrama> parseInputStream (HttpServletRequest request) throws IOException {
        List<FormPrama> formPramaList = new ArrayList<FormPrama>();
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtil.isNotEmpty(body)) {
            String[] kvs = body.split("&");
            if (ArrayUtil.isNotEmpty(kvs)) {
                for (String kv : kvs) {
                    String[] array = kv.split("=");
                    if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                        String fieldName = array[0];
                        String fieldValue = array[1];
                        formPramaList.add(new FormPrama(fieldName, fieldValue));
                    }
                }
            }
        }
        return formPramaList;
    }

}
