<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-card class="box-card">
          <el-row :gutter="20" style="margin-bottom: 15px">
            <el-col :span="4">
              <el-input
                size="small"
                placeholder="请输入商品名称"
                v-model="query.name"
                clearable
              >
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-select
                style="width: 100%"
                size="small"
                v-model="query.category"
                placeholder="请选择商品类别"
                clearable
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="2">
              <el-button
                size="small"
                type="primary"
                icon="el-icon-search"
                @click="querySearch(query.name)"
                >搜索</el-button
              >
            </el-col>
          </el-row>
          <el-row style="margin-bottom: 10px">
            <el-button
              size="small"
              type="primary"
              icon="el-icon-plus"
              @click="addDialogVisible = true"
              >新 增</el-button
            >
            <el-button
              size="small"
              type="warning"
              icon="el-icon-edit"
              @click="updateBtn"
              >编 辑</el-button
            >
            <el-button
              size="small"
              type="danger"
              icon="el-icon-delete"
              @click="deleteBtn"
              >删 除</el-button
            >
          </el-row>
          <el-table
            :data="productList"
            border
            stripe
            ref="userTable"
            style="width: 100%"
          >
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="id" label="商品ID" align="center">
            </el-table-column>
            <el-table-column prop="name" label="商品名" align="center">
            </el-table-column>
            <el-table-column prop="price" label="商品单价" align="center">
            </el-table-column>
            <el-table-column prop="stock" label="商品库存" align="center">
            </el-table-column>
            <el-table-column prop="category" label="商品类别" align="center">
            </el-table-column>
            
            
            
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-pagination
        style="margin-top: 20px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-row>
    <!--新增弹窗-->
    <el-dialog
      center
      title="新 增"
      :visible.sync="addDialogVisible"
      width="30%"
    >
      <el-form
        :rules="rules"
        :model="addProductForm"
        ref="addRuleForm"
        label-width="90px"
        @close="addFormClose"
      >
        <el-form-item prop="id" label="商品ID">
          <el-input
            v-model="addProductForm.id"
            placeholder="请输入商品ID"
          ></el-input>
        </el-form-item>
        <el-form-item prop="category" label="商品类别">
          <el-select
            style="width: 100%"
            v-model="addProductForm.category"
            placeholder="请选择商品类别"
            clearable
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="name" label="商品名称">
          <el-input
            v-model="addProductForm.name"
            placeholder="请输入商品名称"
          ></el-input>
        </el-form-item>
        <el-form-item prop="name" label="商品库存">
          <el-input
            v-model="addProductForm.stock"
            placeholder="请输入商品库存"
          ></el-input>
        </el-form-item>
        <el-form-item prop="name" label="商品单价">
          <el-input
            v-model="addProductForm.price"
            placeholder="请输入商品单价"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
      </span>
    </el-dialog>
    <!--编辑弹窗-->
    <el-dialog
      center
      title="编 辑"
      :visible.sync="updateDialogVisible"
      width="30%"
    >
      <el-form
        :model="updateProductForm"
        ref="updateRuleForm"
        :rules="rules"
        label-width="90px"
        @close="updateFormClose"
      >
        <el-form-item prop="id" label="商品ID">
          <el-input v-model="updateProductForm.id"></el-input>
        </el-form-item>
        <el-form-item prop="category" label="商品类别">
          <el-select
            style="width: 100%"
            v-model="updateProductForm.category"
            placeholder="请选择商品类别"
            clearable
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="name" label="商品名称">
          <el-input v-model="updateProductForm.name"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  created() {
    this.list(this.current,this.limit);
  },
  data() {
    return {
      size:10,
      product:{
        id:"",
      },
      current: 1,
      limit: 10,
      query:{
        name:"",
        category:"",
      },
      total: 0,
      productList: [],
      rules: {
        id: [{ required: true, message: "请输入商品Id", trigger: "blur" }],
        category: [
          { required: true, message: "请输入商品类别", trigger: "blur" },
        ],
        name: [
          { required: true, message: "请输入商品名称", trigger: "blur" },
        ],
        stock: [
          { required: true, message: "请输入商品库存", trigger: "blur" },
        ],
        price: [
          { required: true, message: "请输入商品单价", trigger: "blur" },
        ],
      },
      options: [
        {
          label: "书籍",
          value: "0",
        },
        {
          label: "电子产品",
          value: "1",
    
        },
        {
          label: "课程",
          value: "2"
        }
      ],
      addDialogVisible: false,
      addProductForm: {
        id: "", 
        name: "",
        price:"",
        stock:"",
        category: "",

      },
      updateDialogVisible: false,
      updateProductForm: {
        id: "", 
        name: "",
        price:"",
        stock:"",
        category: "",

      },
    };
  },

  methods: {
    handleSizeChange(newSize){
      this.limit = newSize;
      this.list(this.current,this.limit);
    },
    handleCurrentChange(current){
      this.current = current;
      this.list(this.current,this.limit);
    },
    // 获取商品列表数据
    
    list(current,limit){
      this.$http({
        method: "get",
        url: this.$dynamicHref+`/product/page/${current}/${limit}`,
        data: this.product,
      }).then((res) => {
        console.log(res)
        this.total = res.data.total;
        this.productList = res.data.rows;
        this.limit = res.data.limit;
        this.current = res.data.current;
      });
    },
    
    querySearch() {
      this.$http({
          method: "get",
          url: this.$dynamicHref+`/product/getByName/`+this.query.name,
        }).then((res) => {
        this.total = 1;
        this.limit = 1;
        this.current = 1;
        this.productList=[];
        this.productList.push(res.data.data.product)
          if (res.data.code == 200) {
            this.$message({
              type: "success",
              message: "查询成功!",
            });
          }
        });
    },
    
    // 新增
    add() {
      
        this.$http({
        method: "post",
        url: this.$dynamicHref+"/product/add",
        data: this.addProductForm,
      })
        .then((response) => {
          this.$message({
            type: "success",
            message: "添加成功!",
          });
        })
        .catch((error) => {
          this.$message({
            type: "error",
            message: "添加失败!",
          });
        });
          this.addDialogVisible = false;
          this.list();
        
      },
    
    // 点击编辑
    updateBtn() {
      // 判断是否勾选行数据
      const _selectData = this.$refs.userTable.selection;
      if (_selectData.length === 0) {
        this.$message({
          message: "请选择一行数据",
          type: "warning",
        });
        return false;
      } else if (_selectData.length > 1) {
        this.$message({
          message: "只能选中一行数据!",
          type: "warning",
        });
        return false;
      }
      // 显示弹窗
      this.updateDialogVisible = true;
      // 将选中的数据进行赋值
      this.updateProductForm = _selectData[0];
    },

    // 确认编辑
    updateSubmit() {
      // 表单验证
      this.$http({
        method: "post",
        url: "product/update",
        data: this.updateProductForm,
      })
        .then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
        })
        .catch((error) => {
          this.$message({
            type: "error",
            message: "修改失败!",
          });
        });
          this.updateDialogVisible = false;
          this.list();
      },
    
    // 删除
    deleteBtn(id) {
      const ids = [];
      const _selectData = this.$refs.userTable.selection;
      if (_selectData.length === 0) {
        this.$message({
          message: "请至少选择一行数据",
          type: "warning",
        });
        return false;
      }
      else if (_selectData.length > 1) {
        this.$message({
          message: "只能选中一行数据!",
          type: "warning",
        });
        return false;
      }
      id=_selectData.id;
      this.$confirm("是否删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$http({
          method: "delete",
          url: `product/delete/${id}`,
        }).then((res) => {
          if (res.data.code == 200) {
            this.$message({
              type: "success",
              message: "删除成功!",
            });
          }
        this.list();
      });
      });
    },
    // 新增弹窗关闭回调事件
    addFormClose() {
      this.$refs.addRuleForm.resetFields();
    },
    // 修改弹窗关闭回调事件
    updateFormClose() {
      this.$refs.updateRuleForm.resetFields();
    },
  },
};
</script>
<style scoped>
</style>