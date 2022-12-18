package cn.edu.xjtu.server.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张剑歆
 * @since 2022-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Order对象", description="")
@NoArgsConstructor
@AllArgsConstructor
@TableName("`order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    private String destProvince;

    @TableField(value="`user`")
    private String user;

    private Integer carId;

    @ApiModelProperty(value = "0:正常完成1：有缺损，并完成正常部分的支付2：退货")
    private Integer endStatus;

    private BigDecimal shouldPay;

    private BigDecimal actuallyPay;

    @ApiModelProperty(value = "0：创建订单；1：估价；2：完成预付；3：省级配送到货确认；4：到达签收站点；5：检查缺损或者退货并完成付款。本列的值代表列值的数字对应的事件已完成，而数字加一对应的事件还未完成。")
    private Integer completeSatus;

    private Date createTime;

    private Date provinceSignTime;

    private Date spotSignTime;

    private Date finishTime;

    @TableField(exist = false)
    private List<OrderItem> items;

    public Order(Object o, String 上海, String s, Object o1, Object o2, BigDecimal bigDecimal, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
    }

    public void setItems(List<OrderItem> orderItems) {

    }
}
