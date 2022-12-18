<template>
  <div>
    <el-descriptions title="订单详情">
    <el-descriptions-item label="orderId"><el-tag size="small">{{order.orderId}}</el-tag></el-descriptions-item>
    <el-descriptions-item label="destProvince">{{order.destProvince}}</el-descriptions-item>
    <el-descriptions-item label="user">{{order.user}}</el-descriptions-item>
    <el-descriptions-item label="carId">{{order.carId!=null?order.carId:"暂未配车"}}</el-descriptions-item>
    <el-descriptions-item label="endStatus">{{endStatusToCN(order.endStatus)}}</el-descriptions-item>
    <el-descriptions-item label="shouldPay"><el-tag size="small">{{order.shouldPay}}</el-tag></el-descriptions-item>
    <el-descriptions-item label="actuallyPay">{{order.actuallyPay}}</el-descriptions-item>
    <el-descriptions-item label="completeSatus"><el-tag size="small">{{toCN(order.completeSatus)}}</el-tag></el-descriptions-item>
    <el-descriptions-item label="createTime">{{order.createTime}}</el-descriptions-item>
    <el-descriptions-item label="provinceSignTime">{{order.provinceSignTime!=null?order.provinceSignTime:"暂未省级签收"}}</el-descriptions-item>
    <el-descriptions-item label="spotSignTime">{{order.spotSignTime!=null?order.spotSignTime:"暂未站点签收"}}</el-descriptions-item>
    <el-descriptions-item label="finishTime">{{order.finishTime!=null?order.finishTime:"暂未完成订单"}}</el-descriptions-item>
</el-descriptions>
<el-table
    :data="order.items"
    stripe
    style="width: 100%">
    <el-table-column
      prop="product"
      label="产品编号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="count"
      label="产品数量"
      width="180">
    </el-table-column>
    <el-table-column
      prop="price"
      label="总价"
      width="180">
    </el-table-column>
  </el-table>

  <el-row>
  <el-button @click="exitBack()" type="success" round>返回</el-button>
</el-row>
  </div>
</template>

<script type="text/javascript">

export default {
  name: "Details",
  data() {
    return {
        order: {
            
        },
        orderId: {},
        pathURL: "",
        pageSize: null,
        pageNo: null,
        
    }
  },
  components: {
  },
  methods: {
        findOne(){
            this.$http.get(this.$dynamicHref+"/order/queryDetailsByID/"+this.orderId).then((res)=>{
            this.order = res.data;
    });
        },
        
        exitBack(){
            this.$router.push("/user?pageNo="+this.pageNo+"&pageSize="+this.pageSize);
        },
        toCN(completeSatus){
        var action = "";
        switch (completeSatus) {
        case 0 :
        action = "已经创建订单";
        break; 
        case 1 :
        action = "已估价";
        break; 
        case 2 :
        action = "已完成预付";
        break;
        case 3 :
        action = "省级配送已到货";
        break; 
        case 4 :
        action = "已到达签收站点";
        break; 
        case 5 :
        action = "已完成";
        break;  
        }
        return action;
    },
    endStatusToCN(status){
        var action = "";
        switch (status) {
        case 0 :
        action = "正常配送";
        break; 
        case 1 :
        action = "有缺损";
        break; 
        case 2 :
        action = "申请退货";
        break;
        }
        return action;
    },
  },
  created() {
    this.orderId = this.$route.query.id;
    this.pathURL=this.$route.query.pathURL;
    this.pageNo=this.$route.query.pageNo;
    this.pageSize=this.$route.query.pageSize;
    this.findOne();
  },
}
</script>

<style scoped>
</style>
