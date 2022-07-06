package org.example;

import java.util.Map;

public interface ApplicationContext {
    /**
     * it should throw NoSuchBeanException if nothing is found
     * it should throw NoUniqueBeanException if more than one bean is found
     */
    public <T> T getBean (Class<T> beanType);

    /**
     * it should throw NoSuchBeanException if nothing is found
     */
    public <T> T getBean (String name, Class<T> type);

    public <T> Map<String, T> getAllBeans(Class<T> beanType);
}
