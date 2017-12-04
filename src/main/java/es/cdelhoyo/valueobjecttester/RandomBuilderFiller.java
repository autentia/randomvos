package es.cdelhoyo.valueobjecttester;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomBuilderFiller {

	private final RandomObjectFiller randomObjectFiller;
	
	public RandomBuilderFiller(RandomObjectFiller randomObjectFiller) {
		this.randomObjectFiller = randomObjectFiller;
	}
	
	protected <T, B> T createObjectWithBuilder(Class<T> objectClass, Class<B> builderClass) throws Exception {
		B builder = builderClass.newInstance();
		List<Method> withMethods = getWithBuilderMethods(builderClass);
		for(Method withMethod : withMethods) {
			Object withObject = createWithObject(withMethod);
			withMethod.invoke(builder, withObject);
		}
		Method buildMethod = getBuildMethod(objectClass, builderClass);
        return (T) buildMethod.invoke(builder);
    }

	protected Object createWithObject(Method withMethod) throws Exception {
		Object object;
		Class<?> parameterClass = withMethod.getParameterTypes()[0];
		if(parameterClass.equals(List.class)) {
			List list = new ArrayList<>();
			parameterClass = (Class<?>)((ParameterizedType) withMethod.getGenericParameterTypes()[0]).getActualTypeArguments()[0];
			list.add(randomObjectFiller.createAndFillObject(parameterClass));
			object = list;
		}else{
			object = randomObjectFiller.createAndFillObject(parameterClass);
		}
		return object;
	}

	protected <T, B> Method getBuildMethod(Class<T> objectClass, Class<B> builderClass) {
		Method methodBuild = null;
		List<Method> methods = Arrays.asList(builderClass.getDeclaredMethods());
        for(Method method : methods) {
        		if(method.getReturnType().equals(objectClass)) {
        			methodBuild = method;
        			break;
        		}
        }
        return methodBuild;
	}

	protected <T> List<Method> getWithBuilderMethods(Class builderClass) {
		List<Method> methods = new ArrayList<>();
        while(builderClass != Object.class) {
        		for(Method method : builderClass.getDeclaredMethods()) {
            		if(method.getReturnType().equals(builderClass)) {
            			methods.add(method);
            		}
        		}
        		builderClass = builderClass.getSuperclass();
        }
        return methods;
	}
	
}
