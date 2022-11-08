package hw2.p4;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class BirdTest {

    @Test
    public void add() {
        Random r = new Random();
        int a = r.nextInt(1000);
        int b = r.nextInt(1000);
        assertEquals(a+b, Bird.add(a,b));
    }
}