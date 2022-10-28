package hw4;

interface Animal {
    default void greet(Animal a) {
        System.out.println("hello animal");
    }

    default void sniff(Animal a) {
        System.out.println("sniff animal");
    }

    default void praise(Animal a) {
        System.out.println("u r cool animal");
    }
}

public class Dog implements Animal {
    @Override
    public void sniff(Animal a) {
        System.out.println("dog sniff animal");
    }

    void praise(Dog a) {
        System.out.println("u r cool dog");
    }

    public static void main(String[] args) {
        Animal a = new Dog();
        Dog d = new Dog();
        a.greet(d);
        a.sniff(d);
        d.praise(d);
        ((Dog) a).praise(d);
    }
}
