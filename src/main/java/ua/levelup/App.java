//1. Создайте 2 компонента, 1 из которых публикует свои доступы по шаблону, 2-й через BeanInfo
package ua.levelup;

import java.beans.*;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println("==========Introspection with BeanInfo========");
            Class<?> c = Class.forName("ua.levelup.JSPBean");
            BeanInfo beanInfo = Introspector.getBeanInfo(c);

            System.out.println("=============PropertyDescriptors=============");
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor item: propertyDescriptors) {
                System.out.println(item.getName());
            }

            System.out.println("=============MethodDescriptors===============");
            MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
            for (MethodDescriptor item: methodDescriptors) {
                System.out.println(item.getName());
            }

            System.out.println("=============EventSetDescriptors=============");
            EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
            for (EventSetDescriptor item: eventSetDescriptors) {
                System.out.println(item.getName());
            }

            System.out.println("=======Introspection with reflection API====");
            System.out.println("=============PropertyDescriptors=============");
            propertyDescriptors = MyIntrospector.getPropertyDescriptors(c);
            for (PropertyDescriptor item: propertyDescriptors) {
                System.out.println(item.getName());
            }

            System.out.println("=============MethodDescriptors===============");
            methodDescriptors = MyIntrospector.getMethodDescriptors(c);
            for (MethodDescriptor item: methodDescriptors) {
                System.out.println(item.getName());
            }

            System.out.println("=============EventSetDescriptors=============");
            eventSetDescriptors = MyIntrospector.getEventSetDescriptors(c);
            for (EventSetDescriptor item: eventSetDescriptors) {
                System.out.println(item);
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
