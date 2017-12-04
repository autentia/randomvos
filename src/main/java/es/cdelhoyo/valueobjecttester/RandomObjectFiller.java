package es.cdelhoyo.valueobjecttester;

import java.lang.reflect.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class RandomObjectFiller {

	private final Random random = new Random();
	private final ObjectFieldsFiller objectFilledFields;
	
	public RandomObjectFiller(ObjectFieldsFiller objectFilledFields) {
		this.objectFilledFields = objectFilledFields;
	}
	
	protected <T> List<T> createAndFillListWithTwoEqualsObjectsAndOthersWithOnlyOneDiferentField(Class<T> clazz) throws Exception {
		T originalInstance = createAndFillObject(clazz);
		List<T> list = new ArrayList<T>();
		list.add(originalInstance);
		list.add(createOtherInstanceOfSameObject(clazz, originalInstance));
        list.addAll(createListOfObjectsWithOneDifferentProperty(clazz, originalInstance));
        return list;
    }

	protected <T> T createOtherInstanceOfSameObject(Class<T> clazz, T originalInstance) throws Exception {
        T newInstance = clazz.newInstance();
        for(Field field: objectFilledFields.getAllFilledFields(originalInstance.getClass())) {
            Object value = field.get(originalInstance);
            field.set(newInstance, value);
        }
        return newInstance;
    }

	protected <T> List<T> createListOfObjectsWithOneDifferentProperty(Class<T> clazz, T originalInstance)
			throws InstantiationException, IllegalAccessException, Exception {
		List<T> listOfObjectsWithOneDifferentProperty  = new ArrayList<T>();
		List<Field> fields = objectFilledFields.getAllFilledFields(clazz);
        for(int i = 0; i<fields.size(); i++) {
        		Map<Class, Integer> repetedClasses = new HashMap();
            T instance = clazz.newInstance();
            for(int j = 0; j<fields.size(); j++) {
				Field field = fields.get(j);
				Object value;
				Object originalValue = field.get(originalInstance);
        			if(i == j) {
        	            value = getDifferentRandomValueForField(field, originalValue, repetedClasses);
        			}else {
        				value = originalValue;
        			}
    	            field.set(instance, value);
            }
            listOfObjectsWithOneDifferentProperty.add(instance);
        }
		return listOfObjectsWithOneDifferentProperty;
	}

	protected Object getDifferentRandomValueForField(Field field, Object originalValue, Map<Class, Integer> repetedClasses) throws Exception {
		Object value = getRandomValueForField(field, repetedClasses);
		int attemps = 0;
		while(value.equals(originalValue)) { // while by chance a value is repeated.
		    if(5<attemps++) { // Avoid fields that can have a single value (En: enum with only one option)
		    		value = null;
		    }
		    value = getRandomValueForField(field, repetedClasses);
		}
		return value;
	}
	
	protected <T> T createAndFillObject(Class<T> clazz) throws Exception {
		Map<Class, Integer> repetedClasses = new HashMap();
        return createAndFill(clazz, repetedClasses);
    }
	
	protected <T> T createAndFill(Class<T> clazz, Map<Class, Integer> repetedClasses) throws Exception {
    		if(checkRepetedClassAndAddOne(clazz, repetedClasses)) { // Evitamos objetos anidados unos dentro de otros
    			return null;
    		}
    		if(isSimpleObject(clazz)) {
    			return (T) getRandomValueForClass(clazz, repetedClasses);
    		}
        T instance = clazz.newInstance();
        for(Field field: objectFilledFields.getAllFilledFields(clazz)) {
            Object value = getRandomValueForField(field, repetedClasses);
            field.set(instance, value);
        }
        return instance;
    }
	 
    protected Object getRandomValueForField(Field field, Map<Class, Integer> repetedClasses) throws Exception {
       Class<?> type = field.getType();
       if(type.equals(List.class)) {
       		Type typeOfList = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
       		List randomList = new ArrayList<>();
       		randomList.add(getRandomValueForClass((Class<?>)typeOfList, repetedClasses));
       		return randomList;
       }
       return getRandomValueForClass(type, repetedClasses);
   }
 
	protected Object getRandomValueForClass(Class clazz, Map<Class, Integer> repetedClasses) throws Exception {
        if(clazz.isEnum()) {
            Object[] enumValues = clazz.getEnumConstants();
            return enumValues[random.nextInt(enumValues.length)];
        } else if(clazz.equals(Integer.TYPE) || clazz.equals(Integer.class)) {
            return random.nextInt();
        } else if(clazz.equals(Long.TYPE) || clazz.equals(Long.class)) {
            return random.nextLong();
        } else if(clazz.equals(Double.TYPE) || clazz.equals(Double.class)) {
            return random.nextDouble()*100;
        } else if(clazz.equals(Float.TYPE) || clazz.equals(Float.class)) {
            return random.nextFloat()*100;
        } else if(clazz.equals(Boolean.TYPE) || clazz.equals(Boolean.class)) {
            return random.nextBoolean();
        } else if(clazz.equals(String.class)) {
            return UUID.randomUUID().toString();
        } else if(clazz.equals(BigInteger.class)){
            return BigInteger.valueOf(random.nextInt());
        }
        return createAndFill(clazz, repetedClasses);
    }

    private boolean isSimpleObject(Class clazz) {
    		return  clazz.equals(Integer.TYPE) || clazz.equals(Integer.class) || 
	    		clazz.equals(Long.TYPE) || clazz.equals(Long.class) || clazz.equals(Double.TYPE) || 
	    		clazz.equals(Double.class) || clazz.equals(Float.TYPE) || clazz.equals(Float.class) || 
	    		clazz.equals(Boolean.TYPE) || clazz.equals(Boolean.class) || clazz.equals(String.class) || 
	    		clazz.equals(BigInteger.class);
    }
    
	protected boolean checkRepetedClassAndAddOne(Class clazz, Map<Class, Integer> repetedClasses) {
		if(isSimpleObject(clazz) || clazz.equals(List.class) || clazz.isEnum()){
            return false;
        }
		if(!repetedClasses.containsKey(clazz)) {
			repetedClasses.put(clazz, 1);
		}else {
			if(repetedClasses.get(clazz)>3) {
		        return true;
			}
			repetedClasses.put(clazz, repetedClasses.get(clazz) + 1);
		}
        return false;
		
	}

}
