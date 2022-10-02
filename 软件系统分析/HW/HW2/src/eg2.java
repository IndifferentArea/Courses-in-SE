public class eg2 {
    public interface CallBack {
        public void receiveNotice(String Message);
    }

    public static class Caller implements eg2.CallBack {
        String name;

        Caller(String s) {
            name = s;
        }

        public void receiveNotice(String Message) {
            System.out.println("Caller " + name + " received a Notice: " + Message);
        }

        public void call(Callee b) {
            System.out.println("Caller " + name + " will assign Callee " + b.name + " a task.");
            b.getcall(this);
        }
    }

    public static class Callee {
        String name;

        Callee(String s) {
            name = s;
        }

        public void getcall(CallBack cb) {
            System.out.println("This is Callee " + name + ", I was called so I need to finish my task");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cb.receiveNotice("Callee " + name + " has done his task.");
        }
    }

    public static void main(String[] args) {
        Caller boss = new Caller("boss");
        Callee worker = new Callee("worker");
        boss.call(worker);
    }
}


