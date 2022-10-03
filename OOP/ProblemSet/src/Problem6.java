import java.util.Random;

public class Problem6 {
    public static void main(String[] args) {
        Random r = new Random();
        int num = 0;
        for (int i = 0; i < 100000; i++) {
            double x = r.nextDouble(-100,100);
            double y = r.nextDouble(-100, 100);
            if (x < 0) num++;
            else if (x > 0 && y > 0 && x + y < 100) num++;
        }
        System.out.print((double) num / 100000);
    }
}
