package cn.edu.xjtu.server.pojo;

import java.math.BigDecimal;
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
@ApiModel(value="Transprt对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class Transprt implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "province", type = IdType.ID_WORKER_STR)
    private String province;

    private BigDecimal cost;


}
