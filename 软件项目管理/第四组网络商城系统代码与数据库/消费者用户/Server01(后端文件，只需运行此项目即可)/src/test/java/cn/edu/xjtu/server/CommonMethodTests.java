package cn.edu.xjtu.server;

import cn.edu.xjtu.server.util.ProjectUtils;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonMethodTests {
    @Test
    public void doTest(){
        String[] split = "a".split(",");
        System.out.println(split.length);
        System.out.println(Arrays.toString(split));

    }
    public void change(String s){
        s = "b";
    }
}
