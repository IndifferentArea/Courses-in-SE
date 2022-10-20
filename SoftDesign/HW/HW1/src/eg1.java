public class eg1 {
    public static class Caller {
        String name;
        Caller(String s) {
            name = s;
        }
        public void call(Callee Y) {
            System.out.println("This is Caller " + name + ", I will call Callee " + Y.name + " .");
            Y.getcall();
        }
    }
    public static class Callee{
        String name;
        Callee(String s){
            name = s;
        }
        public void getcall(){
            System.out.println("This is Callee " + name + ", I was called.");
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args)
    {
        Caller x = new Caller("X");
        Callee y = new Callee("Y");
        long t = System.currentTimeMillis();
        x.call(y);
        t -= System.currentTimeMillis();
        System.out.println("Time cost is "+ (-t) + " ms");
    }
}
