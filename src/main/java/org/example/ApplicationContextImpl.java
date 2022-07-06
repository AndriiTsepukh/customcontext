package org.example;

import org.example.exceptions.NoUniqueBeanException;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ApplicationContextImpl implements ApplicationContext{
    private Map<Class<?>, Map<String, Object>> context = new HashMap<>();
    public ApplicationContextImpl(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(org.example.Bobobean.class);

        for (Class<?> clazz : annotated) {
            try {
                final Object newInstance = clazz.getDeclaredConstructor().newInstance();
                String beanName = clazz.getAnnotation(org.example.Bobobean.class).value();
                if (beanName.equals("")) {
                    beanName = clazz.getSimpleName();
                    beanName = beanName.substring(0,1).toLowerCase() + beanName.substring(1);
                }

                if (context.containsKey(clazz)) {
                    Map<String, Object> entry = context.get(clazz);
                    entry.put(beanName, newInstance);
                } else {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put(beanName, newInstance);
                    context.put(clazz, entry);
                }

            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        var beans = getAllBeans(beanType);
        if (beans.size() == 0) throw new NoSuchElementException();
        if (beans.size() > 1) throw new NoUniqueBeanException();
        return beans.values().iterator().next();
    }

    @Override
    public <T> T getBean(String name, Class<T> type) {
        var beans = getAllBeans(type);
        if (!beans.containsKey(name)) throw  new NoSuchElementException();
        return beans.get(name);
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        Map <String, T> resultMap = new HashMap<>();

        for (var entrySet : context.entrySet()) {
            if (beanType.isAssignableFrom(entrySet.getKey())) {
                for (Map.Entry<String, Object> entry : entrySet.getValue().entrySet()) {
                    resultMap.put(entry.getKey(), beanType.cast(entry.getValue()));
                }
            }
        }

        return resultMap;
    }
}
