import java.awt.*;
import java.util.*;

public class eg3 {
    static class MyEventObject extends EventObject {
        public MyEventObject(Object source) {
            super(source);    // 通过source构造event
        }
    }

    static class MyListener implements EventListener {
        public void onMyEvent(EventObject e) {
            if (e.getSource() instanceof EventSource) {
                EventSource temp = (EventSource) e.getSource();
                System.out.println("收到来自" + temp.getActioner() + "的事件!");
                CaughtCont ++ ;
            }
        }
    }

    public static MyListener listener = null;
    static int CaughtCont = 0; // 记录监听到的次数

    static class EventSource {
        private String who;
        Vector<MyListener> listeners = new Vector<MyListener>();

        public EventSource(String s) {
            this.who = s;
        }

        public String getActioner() {
            return who;
        }

        public void addMyEventListener(MyListener listener) {
            listeners.add(listener);
        }

        public void say(String words) {
            System.out.println(this.getActioner() + ": " + words);
            for (int i = 0; i < listeners.size(); i++) {
                MyListener listener = (MyListener) listeners.elementAt(i);
                listener.onMyEvent(new MyEventObject(this));
            }
        }
    }

    public static void main(String[] args) {
        listener = new MyListener();
        EventSource A = new EventSource("Alex");
        EventSource B = new EventSource("Bobby");
        A.addMyEventListener(listener);
        A.say("今天天气不错");
        B.say("适合出去走走");
        System.out.println();
        System.out.println("当前共收到" + CaughtCont + "次事件");
        B.addMyEventListener(listener);
        System.out.println();
        A.say("今天天气不错");
        B.say("适合出去走走");
        System.out.println();
        System.out.println("当前共收到" + CaughtCont + "次事件");
    }
}

