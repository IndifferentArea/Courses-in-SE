<template>
  <div>
    <h1>{{$logUser.userId}}用户订单列表</h1>
    
    
    <el-table
    :data="orders"
    style="width: 100%"
  >
    <el-table-column
      prop="orderId"
      label="订单编号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="destProvince"
      label="目的省份"
      width="180">
    </el-table-column>
    <el-table-column
      prop="user"
      label="下单用户">
    </el-table-column>
    <el-table-column
      prop="carId"
      label="分配车辆"
      :formatter="onLineStatus"
      width="180">
    </el-table-column>
    <el-table-column
      prop="endStatus"
      label="订单状态"
      :formatter="endStatusFun"
      width="180">
    </el-table-column>
    <el-table-column
      prop="shouldPay"
      label="应该支付">
    </el-table-column>
    <el-table-column
      prop="actuallyPay"
      label="实际支付">
    </el-table-column>
    <el-table-column
      prop="completeSatus"
      :formatter="toCNFun"
      label="运送状态">
    </el-table-column>
  <el-table-column label="查询订单细节" min-width="150px">
	<template slot-scope="{row}">
       <el-link :href="'/#/details?id='+row.orderId+'&pathURL=detail&pageNo='+pageNo+'&pageSize='+pageSize" class="buttonText"  type="primary" :underline="false">{{ row.orderId }}号订单详情</el-link>
  </template>
</el-table-column>
<el-table-column label="用户操作" min-width="150px">
	<template slot-scope="{row}">
    <div v-if="row.completeSatus==5">已完结</div>
              <div v-else-if="twoToFour(row.completeSatus)">在运送途中</div>
                <div v-else-if="row.completeSatus==0"><a href="javascript:;" @click="doChange(row.orderId,toMethod(row.completeSatus))">{{toMethodCN(row.completeSatus)}}</a></div>
                <div v-else="row.completeSatus==1">
                    <el-link :href="'/#/details?id='+row.orderId+'&pathURL=pay&pageNo='+pageNo+'&pageSize='+pageSize" class="buttonText"  type="primary" :underline="false">{{ row.orderId }}号订单付款</el-link>
                </div>
                
	</template>
</el-table-column>
<el-table-column label="退货申请" min-width="150px">
    <template slot-scope="{row}">
        <div v-if="row.completeSatus==5">您的订单已完成</div>
        <div v-else><a href="javascript:;" @click="doChange(row.orderId,'orderReturn')">{{ row.orderId }}号订单退货</a></div>
        
    </template>
</el-table-column>
  </el-table>

  <div class="block">
    <span class="demonstration">分页调整</span>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNo"
      :page-sizes="[2, 4, 6, 8]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>

  </div>
</template>

<script type="text/javascript">


export default {
  name: "User",
  data() {
    return {
        orders: [],
        
        total: 0,
        pageSize: 4,
        pageNo: 1,
    }
  },
  components: {},
  created(){
    if(this.$route.query.pageNo)
      this.pageNo=parseInt(this.$route.query.pageNo);
    if(this.$route.query.pageSize)
      this.pageSize=parseInt(this.$route.query.pageSize);
    this.findAll();
  },
  methods: {
    handleSizeChange(val) {
      this.pageSize=val;
      this.findAll();
      },
      handleCurrentChange(val) {
        this.pageNo = val;
        this.findAll();
      },
    payMoney(id){
        this.$http.get(this.$dynamicHref+"/order/pay/"+id+"/"+this.money).then((res)=>{
        if(res.data==true){
            alert("已付款"+this.money+"元");
        }else{
            alert("账户余额不足，请充值");
        }
        
      this.money = 0.0;
      this.findAll();
    });
    },
    findAll(){
      this.$http.get(this.$dynamicHref+"/order/findall/"+this.$logUser.userId+"/"+this.pageNo+"/"+this.pageSize).then((res)=>{
      this.orders = res.data.records;
      this.total = res.data.total;
      this.$refreshBalance();
    });
    },
    onLineStatus(row, column) {
      return row.carId == null ? "尚未配送" : row.carId;
    },
    endStatusFun(row, column){
      return this.endStatusToCN(row.endStatus);
    },
    toCNFun(row, column){
      return this.toCN(row.completeSatus);
    },
    twoToFour(val){
      if(val>=2 && val<=4)
        return true;
      else
        return false;
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
    toMethodCN(completeSatus){
        var action = "";
        switch (completeSatus) {
        case 0 :
        action = "开始估价";
        break; 
        case 1 :
        action = "进行预支付";
        break; 
        case 2 :
        action = "省级签收确认";
        break;
        case 3 :
        action = "站点签收确认";
        break; 
        case 4 :
        action = "订单完成确认";
        break; 
        case 5 :
        action = "";
        break;  
        }
        return action;
    },
    evalReturn(msg){
      return "估计成功，请查看订单";
    },
    advanceChargeReturn(msg){
      if(msg==true)
        return "预付款成功，并已扣除相应余额";
      else{
          return "账户余额不足，请充值";
      }
    },
    returnApplyReturn(msg){
      if(msg==true)
        return "已退货成功，您尚未付款";
      else{
          return "货物配送中，已申请退货，到达站点后，工作人员将为您退货还款";
      }
    },
    provinceSignReturn(msg){
      if(msg==true){
        return "省级签收成功！！！";
      }
      
    },
    spotSignAndRaisePriceReturn(msg){
        if(msg==true)
        return "站点接受成功，并且由于道路原因，运费增加10元,很抱歉！";
      else{
          return "站点接受成功!!!";
      }
    },
    checkAndFinishReturn(msg){
        var returnMSG;
        switch (msg) {
        case 0 :
        returnMSG = "订单正常完成";
        break; 
        case 1 :
        returnMSG = "货物出现缺损，已登记在缺损表并赔偿用户金额，订单完成";
        break; 
        case 2 :
        returnMSG = "完成用户退货申请，货物已发回";
        break;
        }
        return returnMSG;
    },
    returnMsgDistribute(methodAction){
        var invokeFun;
        switch (methodAction) {
        case "eval" :
        invokeFun = this.evalReturn;
        break; 
        case "advanceCharge" :
        invokeFun = this.advanceChargeReturn;
        break; 
        case "provinceSign" :
        invokeFun = this.provinceSignReturn;
        break;
        case "spotSignAndRaisePrice" :
        invokeFun = this.spotSignAndRaisePriceReturn;
        break; 
        case "checkAndFinish" :
        invokeFun = this.checkAndFinishReturn;
        break;  
        case "orderReturn" :
        invokeFun = this.returnApplyReturn;
        break; 
        }
        return invokeFun;
    },
    toMethod(completeSatus){
        var action = "";
        switch (completeSatus) {
        case 0 :
        action = "eval";
        break; 
        case 1 :
        action = "advanceCharge";
        break; 
        case 2 :
        action = "provinceSign";
        break;
        case 3 :
        action = "spotSignAndRaisePrice";
        break; 
        case 4 :
        action = "checkAndFinish";
        break; 
        case 5 :
        action = "";
        break;  
        }
        return action;
    },
    
    doChange(orderId,methodAction){
      this.$http.get(this.$dynamicHref+"/order/"+methodAction+"/"+orderId).then(res=>{
        var returnFun=this.returnMsgDistribute(methodAction);
        alert(returnFun(res.data));
        this.findAll();
        this.$refreshBalance();
        });

    },
    toReturnMsg(completeSatus){
        var action = "";
        switch (completeSatus) {
        case 0 :
        action = "";
        break; 
        case 1 :
        action = "估价";
        break; 
        case 2 :
        action = "完成预付";
        break;
        case 3 :
        action = "省级配送到货确认";
        break; 
        case 4 :
        action = "到达签收站点";
        break; 
        case 5 :
        action = "检查缺损或者退货并完成付款";
        break;  
        default : 
        action = "已经创建订单";
        }
        return action;
    },
    tableRowClassName({row, rowIndex}) {
        if (rowIndex%4 === 1) {
          return 'warning-row';
        } else if (rowIndex%4 === 3) {
          return 'success-row';
        }
        return '';
      },

  },

}
</script>

<style scoped>
.el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
