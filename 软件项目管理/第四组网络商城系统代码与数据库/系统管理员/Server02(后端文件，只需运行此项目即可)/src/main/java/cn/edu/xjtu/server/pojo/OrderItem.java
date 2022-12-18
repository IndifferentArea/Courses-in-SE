package cn.edu.xjtu.server.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

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
@ApiModel(value="OrderItem对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "item_id", type = IdType.AUTO)
    private Integer itemId;

    private Integer product;
    @TableField("`count`")
    private Integer count;

    private BigDecimal price;

    private Integer orderId;

    @TableField(exist = false)
    private Order order;

    public OrderItem(Object o, int i, int i1, BigDecimal bigDecimal, Object o1, Object o2) {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getProduct() {
        return product;
    }

    public Integer getCount() {
        return count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Order getOrder() {
        return order;
    }
}
