package hw2.p4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SupplierTest {

    static int Add(int a, int b) {
        return a+b;
    }

    @org.junit.jupiter.api.Test
    public void testAdd(){
        assertEquals(2,Add(1,1));
    }
}