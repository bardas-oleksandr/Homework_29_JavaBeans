package ua.levelup;

import javax.management.IntrospectionException;
import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyIntrospector {
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> c) throws java.beans.IntrospectionException{
        List<PropertyDescriptor> list = new ArrayList<>();
        Field[] fields = c.getDeclaredFields();
        for (Field field: fields) {
            //Добавляем свойство в список только в том случае если у этого свойства есть аксессоры
            if(accessorsArePresent(c,field)){
                list.add(new PropertyDescriptor(field.getName(), c));
            }
        }
        PropertyDescriptor[] arr = new PropertyDescriptor[list.size()];
        list.toArray(arr);
        return arr;
    }

    public static MethodDescriptor[] getMethodDescriptors(Class<?> c){
        List<MethodDescriptor> list = new ArrayList<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method: methods) {
            list.add(new MethodDescriptor(method));
        }
        MethodDescriptor[] arr = new MethodDescriptor[list.size()];
        list.toArray(arr);
        return arr;
    }

    public static EventSetDescriptor[] getEventSetDescriptors(Class<?> c) throws java.beans.IntrospectionException{
        List<EventSetDescriptor> list = new ArrayList<>();
        Method[] methods = c.getDeclaredMethods();
        for(int i = 0; i < methods.length; i++){
            String methodName = methods[i].getName();
            //Еси название метода подходит по шаблону под addPropertyChangeListener или removePropertyChangeListener
            if((methodName.startsWith("add") || methodName.startsWith("remove")) && methodName.endsWith("Listener")){
                int start = methodName.startsWith("add")? "add".length(): "remove".length();
                int end = methodName.length() - "Listener".length();
                String eventName = methodName.substring(start, end);    //Название события
                System.out.println(eventName);
                //Название события с первой заглавной буквой
                String firstSymbolUpper = eventName.substring(0,1).toUpperCase()
                        + eventName.substring(1, eventName.length());

                Class<?> changeListenerClass;
                try {
                    //РИТОРИЧЕСКИЙ ВОПРОС. Как найти класс, зная только имя и не зная где он лежит
                    changeListenerClass = Class.forName(firstSymbolUpper + "Listener");
                    list.add(new EventSetDescriptor(c,eventName,
                            changeListenerClass,new String[]{eventName},
                            "add" + firstSymbolUpper + "Listener",
                            "remove" + firstSymbolUpper + "Listener"));
                } catch (ClassNotFoundException e) {
                    System.out.println("Class " + firstSymbolUpper + "Listener was not found");
                }
            }
        }
        EventSetDescriptor[] arr = new EventSetDescriptor[list.size()];
        list.toArray(arr);
        return arr;
    }

    //Метод проверяет присутствуют ли в классе аксессоры для заданного поля
    private static boolean accessorsArePresent(Class<?> c, Field field){
        Method[] methods = c.getDeclaredMethods();
        String propertyName = field.getName();
        //Название свойства с первой заглавной буквой (для подстановки в навание метода)
        String firstSymbolUpper = propertyName.substring(0,1).toUpperCase()
                + propertyName.substring(1,propertyName.length());
        boolean setterIsPresent = false;
        boolean getterIsPresent = false;
        for (Method method: methods) {
            String methodName = method.getName();
            //Проверяем является ли метод сеттером
            if (methodName.equals("set" + firstSymbolUpper)){
                //Если сеттер возвращает void
                if(method.getReturnType().equals(Void.TYPE)){
                    Class<?>[] params = method.getParameterTypes(); //Список параметров метода
                    //Если первый параметр такого же типа, как и свойство field
                    if(params.length != 0 && params[0].equals(field.getType())){
                        setterIsPresent = true;
                    }
                }
            }
            //Проверяем является ли метод геттером
            if (methodName.equals("get" + firstSymbolUpper) || methodName.equals("is" + firstSymbolUpper)){
                //Если геттер возвращает тот же тип, какого типа свойство
                if(method.getReturnType().equals(field.getType())){
                    //Если первый параметр такого же типа, как и свойство field
                    if(method.getParameterTypes().length == 0){
                        getterIsPresent = true;
                    }
                }
            }
        }
        return setterIsPresent && getterIsPresent;
    }
}
