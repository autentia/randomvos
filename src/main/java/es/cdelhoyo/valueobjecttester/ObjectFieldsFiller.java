package es.cdelhoyo.valueobjecttester;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectFieldsFiller {

	protected <T> List<Field> getAllFilledFields(Class<T> clazz) {
		List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class superClass = clazz.getSuperclass();
        while(superClass != Object.class) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }
        List<Field> filteredAndAccesibleList = new ArrayList<>();
        for(Field field : fields) {
			if(Modifier.isFinal(field.getModifiers())){
				continue;
			}
			field.setAccessible(true);
			filteredAndAccesibleList.add(field);
        }
        return filteredAndAccesibleList;
	}
	
	protected <T> boolean objectHasAllFieldsFilled(T object) throws IllegalArgumentException, IllegalAccessException {
		Class objectClass = object.getClass();
		List<Field> fields = getAllFilledFields(objectClass);
		boolean allFieldsFilled = true;
		for(Field field: fields) {
			if(field.get(object) == null) {
				allFieldsFilled = false;
				break;
			}
		}
		return allFieldsFilled;
	}
	
}
