package com.zq.blog.util;

import com.zq.blog.po.Blog;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper=new BeanWrapperImpl(source);
        PropertyDescriptor[] pds=beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames=new ArrayList<>();
        for(PropertyDescriptor pd:pds){
            String prpertyName=pd.getName();
            if(beanWrapper.getPropertyValue(prpertyName)==null){
                nullPropertyNames.add(prpertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
