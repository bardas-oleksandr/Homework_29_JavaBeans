package ua.levelup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Одно из типичных свойств, которым может обладать бин - свойство сохраняемости (Persistance).
//Для этого он должен быть сериализуемым
public class JSPBean implements Serializable {
    private String message;
    transient private int param;
    //Еще одно из типичных свойств бина - поддержка событий.
    //Эта подержка обеспечивается:
    //0. Наличием списка подписанных слушателей (private List<MessageChangeListener> listeners)
    //1. Методами добавления и удаления слушателей:
    //          public void addMessageChangeListener(MessageChangeListener listener)
    //          public void removeMessageChangeListener(MessageChangeListener listener)
    //2. Непосредственным извещением слушателей о событиях:
    //          public void setMessage(String message)
    private List<MessageChangeListener> listeners;

    //Бин должен иметь конструктор по умолчанию. Предполагается, что визуальная среда будет
    //создавать экземпляры бинов и использовать для этого конструкторы по умолчанию
    public JSPBean(){
        this.message = null;
        this.param = 0;
        this.listeners = new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        for (MessageChangeListener listener : this.listeners) {
            listener.messageChange(new MessageChangeEvent(this,this.message, message));
        }
        this.message = message;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public void addMessageChangeListener(MessageChangeListener listener) {
        this.listeners.add(listener);
    }

    public void removeMessageChangeListener(MessageChangeListener listener) {
        this.listeners.remove(listener);
    }
}