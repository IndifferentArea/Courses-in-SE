<template>
  <div>
    <h1>退货详情</h1>
    <el-table
    :data="returns"
    style="width: 100%"
    >
    <el-table-column
      prop="orderId"
      label="退货订单号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="finishOrNot"
      label="退货状态"
      :formatter="toCNFun"
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
        returns:[],
    }
  },
  components: {
  },
  
  methods: {
    toCNFun(val){
        if(val.finishOrNot==true){
            return "退货到达仓库";
        }else{
            return "退货尚在运输";
        }
    },
    findAll(){
      this.$http.get(this.$dynamicHref+"/return-registy/list").then((res)=>{
      this.returns = res.data;
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
