package org.smart4j.framework.bean;

import org.smart4j.framework.util.CastUtil;
import org.smart4j.framework.util.CollectionUtil;

import java.util.*;

/**
 *请求参数对象
 */
public class Param {
    private List<FormPrama> formPramaList;//表单参数
    private List<FileParam> fileParamList;//文件参数

    public Param(List<FormPrama> formPramaList) {
        this.formPramaList = formPramaList;
    }
    public Param(List<FormPrama> formPramaList, List<FileParam> fileParamList) {
        this.formPramaList = formPramaList;
        this.fileParamList = fileParamList;
    }
    /**
     * 获取请求参数映射
     */
    public Map<String, Object> getFieldMap () {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        if (CollectionUtil.isNotEmpty(formPramaList)) {
            for (FormPrama formPrama : formPramaList) {
                String fieldName = formPrama.getFieldName();
                Object fieldValue = formPrama.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + "/" + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }


    /**
     * 获取上传文件映射
     */
    public Map<String, List<FileParam>> getFileMap () {
        Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
        if (CollectionUtil.isNotEmpty(fileMap)) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParamList;
                if (fileMap.containsKey(fieldName)) {
                    fileParamList = fileMap.get(fieldName);
                } else {
                    fileParamList = new ArrayList<FileParam>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName, fileParamList);
            }
        }
        return fileMap;
    }
    /**
     * 获取所有上传文件
     */
    public List<FileParam> getFileList (String fieldName) {
        return getFileMap().get(fieldName);
    }
    /**
     * 获取唯一上传文件
     */
   public FileParam getFile (String fieldName) {
       List<FileParam> fileParamList = getFileList(fieldName);
       if (CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
           return fileParamList.get(0);
       }
       return null;
   }
    /**
     * 验证参数是否为空
     */
    public boolean isEmpty () {
        return CollectionUtil.isEmpty(formPramaList) && CollectionUtil.isEmpty(fileParamList);
    }
    /**
     * 根据参数值获取String型参数值
     */
    public String getString (String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }
    /**
     * 根据参数值获取Double型参数值
     */
    public Double getDouble (Double name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }
    /**
     * 根据参数值获取Long型参数值
     */
    public Long getLong (Long name) {
        return CastUtil.castLong(getFieldMap().get(name));
    }
    /**
     * 根据参数值获取Int型参数值
     */
    public int getInt (int name) {
        return CastUtil.castInt(getFieldMap().get(name));
    }
    /**
     * 根据参数值获取string型参数值
     */
    public Boolean getBoolean (String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }

















}
