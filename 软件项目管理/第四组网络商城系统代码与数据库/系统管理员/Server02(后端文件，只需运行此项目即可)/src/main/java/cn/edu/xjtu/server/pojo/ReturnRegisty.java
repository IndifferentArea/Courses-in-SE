package cn.edu.xjtu.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@ApiModel(value="ReturnRegisty对象", description="")
@NoArgsConstructor
@AllArgsConstructor
public class ReturnRegisty implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.ID_WORKER_STR)
    private Integer orderId;

    @ApiModelProperty(value = "true为未发货直接退款false为收获后退款，后续可扩展。")
    private Boolean finishOrNot;
}
