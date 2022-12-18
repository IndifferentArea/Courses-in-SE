package cn.edu.xjtu.server;
          
    import cn.edu.xjtu.server.mapper.DriversMapper;
  	import cn.edu.xjtu.server.mapper.OrderMapper;
  	import cn.edu.xjtu.server.mapper.ProductMapper;
  	import cn.edu.xjtu.server.mapper.TransprtMapper;
  	import cn.edu.xjtu.server.pojo.*;
  	import cn.edu.xjtu.server.pojo.Order;
  	import cn.edu.xjtu.server.service.*;
  	import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
  	import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
  	import lombok.RequiredArgsConstructor;
  	import org.junit.Assert;
  	import org.junit.jupiter.api.*;
  	import org.junit.jupiter.params.ParameterizedTest;
  	import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
  	import org.junit.jupiter.params.provider.CsvSource;
  	import org.springframework.beans.factory.annotation.Autowired;
  	import org.springframework.boot.test.context.SpringBootTest;
    import javax.sql.DataSource;
  	import java.math.BigDecimal;
  	import java.sql.DriverManager;
  	import java.util.Arrays;
  	import java.util.Date;
  	import java.util.List;
    import static org.junit.jupiter.api.Assertions.assertEquals;

    @SpringBootTest
  	@RequiredArgsConstructor
  	public class serviceTest {

        @Autowired
        private OrderMapper orderMapper;
        @Autowired
        private TransprtMapper transprtMapper;
        @Autowired
        private ProductMapper productMapper;
        @Autowired
        private OrderService orderService;
        @Autowired
        private ProductService productService;
        @Autowired
        private AdminService adminService;
        @Autowired
        private DataSource dataSource;
        @Autowired
        private UserService userService;

        @BeforeEach
        void before() {
            System.out.println("开始该单元测试！");
        }

        @AfterEach
        void after() {
            System.out.println("该单元测试完成，请查看结果！");
        }

        @BeforeAll
        public static void ball() {
            System.out.println("测试开始");
        }

        @AfterAll
        public static void aall() {
            System.out.println("全部测试结束");
        }

        //用户登陆成功测试
        @Test
        void userLogTest1() {
            User u = userService.getOne(new QueryWrapper<User>().eq("user_id", "123456")
                    .eq("user_pwd", "123456"));
            if (u == null) {
                System.out.println("账号或密码输入错误");
            } else
                System.out.println("登陆成功！当前账户为" + u);
        }

        //用户登陆失败测试
        @Test
        void userLogTest2() {
            User u = userService.getOne(new QueryWrapper<User>().eq("user_id", "123456")
                    .eq("user_pwd", "123456"));
            if (u == null) {
                System.out.println("账号或密码输入错误");
            } else
                System.out.println("登陆成功！当前账户为" + u);
        }

        void dataSource() {
            System.out.println(dataSource.getClass().getName());
        }

        //用户创建订单测试
        @Test
        void createOrderTest() {
            Order order = new Order(null, "西安", "123457",
                    null, null, new BigDecimal(8), null,
                    null, null, null, null, null, null);
            OrderItem item1 = new OrderItem(null, 6, 2, new BigDecimal(46), null, null);//购买6号物品两件
            OrderItem item2 = new OrderItem(null, 7, 2, new BigDecimal(8), null, null);//购买7号物品两件
            List<OrderItem> orderItems = Arrays.asList(item1, item2); //添加信息至商品-订单表
            order.setItems(orderItems);
            System.out.println(orderService.createOrder(order));
        }

        //计算运费，更新应付价格测试
        @Test
        void eval() {
            orderService.eval(60);
        }

        //根据id查询订单
        @ParameterizedTest
        @CsvSource({"3", "100"})
        void queryDetailsByID(ArgumentsAccessor args) {
            Order order = orderService.queryDetailsByID(args.getInteger(0));
            if (order == null) {
                System.out.println("订单不存在！");
            } else
                System.out.println("查询成功，订单为" + order);
        }

        //用户支付测试
        @ParameterizedTest
        @CsvSource({"61,  0", "60,  0"})
        void advanceCharge(ArgumentsAccessor args) {
            Boolean aBoolean = orderService.advanceCharge(args.getInteger(0), args.getDouble(1));
            assertEquals(true, aBoolean);
        }

        //签收测试，改变订单状态和用户余额
        @ParameterizedTest
        @CsvSource({"61", "80"})
        void provinceSign(ArgumentsAccessor args) {
            Boolean aBoolean = orderService.provinceSign(args.getInteger(0));
            assertEquals(true, aBoolean);
        }

        //检查运费，改变运费
        @Test
        void spotSignAndRaisePrice() {
            orderService.spotSignAndRaisePrice(61);
        }

        //确认收货，完成订单，改变订单状态
        @Test
        void checkAndFinish() {
            Integer integer = orderService.checkAndFinish(61);
            System.out.println(integer);
        }

        //退货测试，改变订单状态
        @Test
        void orderReturn() {
            Boolean aBoolean = orderService.orderReturn(61);
            assertEquals(true, aBoolean);
        }

    }

