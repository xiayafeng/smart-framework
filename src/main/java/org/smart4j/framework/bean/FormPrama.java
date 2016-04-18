package org.smart4j.framework.bean;

/**
 * 表单参数
 */
public class FormPrama {
    private String fieldName;
    private Object fieldValue;

    public FormPrama(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
