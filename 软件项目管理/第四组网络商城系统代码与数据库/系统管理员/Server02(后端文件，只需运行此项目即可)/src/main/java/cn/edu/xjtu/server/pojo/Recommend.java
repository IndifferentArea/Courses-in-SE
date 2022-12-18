package cn.edu.xjtu.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张剑歆
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Recommend对象", description="")
public class Recommend implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.ID_WORKER_STR)
    private String userId;

    private Integer pointer;

    private String products;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProducts() {
        return products;
    }
}
