package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 */
public final class IocHelper {
    static {
        //1.获取所有bean类和bean实例之间的映射关系(beanmap)
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        //2.遍历beanmap,分别取出bean类和bean实例
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()){
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //3.通过反射获取类中的所有成员变量
                Field[] beanFields = beanClass.getFields();
                //4.遍历成员变量，判断该变量是否有inject注解
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            //5.在beanmap中根据bean类取出bean实例
                             Class<?> beanFieldClass = beanField.getType();
                             Object beanFieldInstance = beanMap.get(beanFieldClass);
                            //6.通过反射工具类的setField方法设置该成员变量的值
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance ,beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }



    }
}
