package cn.edu.xjtu.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="LackRegisty对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class LackRegisty implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "product", type = IdType.ID_WORKER_STR)
    private Integer product;
    @TableField("`count`")
    private Integer count;
    private String userId;

    public LackRegisty(Integer id, int i, String user) {

    }
}
