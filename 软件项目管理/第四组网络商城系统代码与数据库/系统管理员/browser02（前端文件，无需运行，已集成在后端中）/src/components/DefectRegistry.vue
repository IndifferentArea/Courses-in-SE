<template>
  <div>
    <h1>缺损详情</h1>
    <el-table
    :data="defect"
    style="width: 100%"
    >
    <el-table-column
      prop="orderId"
      label="缺损订单号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="product"
      label="缺损产品编号"
      width="180">
    </el-table-column>
  <el-table-column
      prop="count"
      label="缺损数量"
      width="180">
    </el-table-column>
  </el-table>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "DefectRegistry",
  data() {
    return {
        defect:[],
    }
  },
  components: {
  },
  
  methods: {
    findAll(){
      this.$http.get(this.$dynamicHref+"/defect-registy/list").then((res)=>{
      this.defect = res.data;
    });
    },
  },
  created(){
    this.findAll();
  },
}
</script>

<style scoped>
</style>
