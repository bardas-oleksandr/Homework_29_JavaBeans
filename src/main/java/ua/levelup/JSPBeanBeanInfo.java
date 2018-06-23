package ua.levelup;

import java.beans.*;

public class JSPBeanBeanInfo extends SimpleBeanInfo {
    private static final Class jspBeanClass = JSPBean.class;
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor[] arr = new PropertyDescriptor[1];
            arr[0] = new PropertyDescriptor("message", jspBeanClass);
            return arr;
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    //При переопределении метода указываем какие именно методы из класса JSPBean нужно выводить
    //при интроспекции
    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        MethodDescriptor[] arr = new MethodDescriptor[4];
        try {
            //Набор параметров метода getMessage пустой, поэтому передаем в качестве аргумента (Class[])null
            arr[0] = new MethodDescriptor(jspBeanClass.getMethod("getMessage",(Class[])null));
            Class[] paramSetMassage = {String.class};   //Набор параметров метода setMessage
            arr[1] = new MethodDescriptor(jspBeanClass.getMethod("setMessage",paramSetMassage));
            arr[2] = new MethodDescriptor(jspBeanClass.getMethod("getParam",(Class[])null));
            Class[] paramSetParam = {int.class};
            arr[3] = new MethodDescriptor(jspBeanClass.getMethod("setParam",paramSetParam));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return arr;
    }

    //Переопределяем метод, возвращающий дескрипторы всех событий
    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        EventSetDescriptor[] arr = new EventSetDescriptor[1];
        try {
            arr[0] = new EventSetDescriptor(jspBeanClass,"messageChange",
                    MessageChangeListener.class,new String[]{"messageChange"},
                    "addMessageChangeListener",
                    "removeMessageChangeListener");
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
