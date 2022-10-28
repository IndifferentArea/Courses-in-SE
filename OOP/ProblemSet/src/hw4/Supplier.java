package hw4;


// CAST only change static type (lie to compiler)
class Contact {
    public String a;
    public String b;
    String doStuff() {
        return "howdy\n";
    }

}
class Supplier extends Contact {
    String doStuff() {
        return "send money \n";
    }

    String dome() {
        return "me\n";
    }

    public String a;
    public String c;

    public static void main(String[] args) {
        Supplier supplier = new Supplier();
        supplier.a = "supplier's Supplier :: a\n";
        ((Contact)supplier).a = "supplier's Contact :: a by supplier cast\n";
        supplier.b = "supplier's Contact :: b\n";
        supplier.c = "supplier's Contact :: b\n";
        System.out.println(supplier.doStuff() + supplier.a + supplier.b + supplier.c);
        
        Contact contact = new Contact();
        contact.a = "contact's Contact :: a\n";
        contact.b = "contact's Contact :: b\n";
        System.out.println(contact.doStuff() + contact.a + contact.b);
        
        contact = supplier;
        ((Supplier)contact).a = "supplier's Supplier :: new a by contact cast\n";
        contact.a = "supplier's Contact :: new a\n";
        contact.b = "supplier's Contact :: new b\n";
        ((Supplier)contact).c = "supplier's Supplier :: new c by contact cast\n";
        System.out.println(((Contact)supplier).doStuff() + supplier.a + supplier.b + supplier.c);
        System.out.println(contact.doStuff() + contact.a + contact.b + ((Supplier)contact).doStuff() + ((Contact)contact).doStuff());
    }

}
