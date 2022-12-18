package cn.edu.xjtu.server.pojo;

public @interface CompleteStatus {
    public static final Integer CREATE = 0;
    public static final Integer EVALUATE = 1;
    public static final Integer ADVANCE_PAY = 2;
    public static final Integer PROVINCE_SIGN = 3;
    public static final Integer SPOT_SIGN = 4;
    public static final Integer CHECK_AND_FINISH = 5;
}
