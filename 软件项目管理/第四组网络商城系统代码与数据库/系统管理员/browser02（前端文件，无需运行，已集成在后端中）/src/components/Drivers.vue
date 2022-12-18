<template>
  <div>
    <el-table
    :data="drivers"
    stripe
    style="width: 100%">
    <el-table-column
      prop="id"
      label="车辆编号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="carId"
      label="车牌号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="priority"
      label="运送优先级（数值大优先配送）"
      width="180">
    </el-table-column>
    <el-table-column
      prop="busy"
      label="运送状态"
      :formatter="toCNFun"
      width="180">
    </el-table-column>
  </el-table>
  <el-row>
  <el-button @click="exitBack()" type="success" round>已阅，退出</el-button>
</el-row>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "Drivers",
  data() {
    return {
        drivers:[],
        province:"",
    }
  },
  components: {
  },
  methods:{
    find(){
            this.$http.get(this.$dynamicHref+"/transprt/drivers/"+this.province).then((res)=>{
            this.drivers = res.data;
            
    });
        },
        toCNFun(busy){
            if(busy=0){
                return "空闲";
            }else{
                return "在路上";
            }
        },
        exitBack(){
            this.$router.push("/transport");
        },
  },
  created() {
    this.province = this.$route.query.province;
    this.find();
  },
}
</script>

<style scoped>
</style>
