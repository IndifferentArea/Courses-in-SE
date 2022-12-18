<template>
  <div id="ui">
    <p>购物车</p>
    
    <el-table
      :data="goodArr"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="编号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="产品名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="price"
        label="单价">
      </el-table-column>
      <el-table-column
        label="数量"
        min-width="100"
        >
        <template slot-scope="scope">
            <input @click="reduceCount(scope.$index)" id="reduceBut" type="button" value="-">
                {{scope.row.count}}
                <input @click="addCount(scope.$index)" id="addBut" type="button" value="+">
              
        </template>
    </el-table-column>
    <el-table-column
        label="小计"
        min-width="100"
        >
        <template slot-scope="{row}">
            {{(row.price)*(row.count)}}
        </template>
    </el-table-column>
    <el-table-column
        label="删除"
        min-width="100"
        >
        <template slot-scope="scope">
             <input @click="delGood(scope.$index)" id="delBut" type="button" value="删除">
        </template>
    </el-table-column>
    </el-table>
    <p>总价：{{countTotalPrice()}}</p><br>
 
    <div>
        
        <el-input v-model="addId" placeholder="请输入商品id">加入购物车商品的编号</el-input>
        <el-row>
            <el-button @click="getProduct()" type="success" icon="el-icon-check" circle>确认添加</el-button>
        </el-row>
    </div>
    <div>
        <el-row>
            <el-button @click="doCreate()" type="primary" round>创建订单</el-button>
        </el-row>
    </div>
 
 
</div>

</template>

<script type="text/javascript">
export default {
  name: "Cart",
        data() {return{
            goodArr:[],
            addId: null,
            
            order: {"items":[]},
            
            
        }
    },
    created(){
        this.order["destProvince"] = this.$logUser.province,
        this.order["user"] = this.$logUser.userId
    },
    methods:{
            countTotalPrice(){
                let totalPrice=0;
                for(let i=0;i<this.goodArr.length;i++){
                    totalPrice+=this.goodArr[i].count*this.goodArr[i].price;
                }
                return totalPrice;
            },
            doCreate(){
            for (let i = 0; i < this.goodArr.length; i++) {
                var toAdd={};      
                toAdd["product"] = this.goodArr[i].id;
                toAdd["price"] = this.goodArr[i].price*this.goodArr[i].count;
                if(this.goodArr[i].count === 0){
                    alert("购物车中存在数量为0的商品项，请修改");
                    return;
                }
                toAdd["count"] = this.goodArr[i].count;  
                this.order.items.push(toAdd);       
            }
            this.order["shouldPay"]=this.countTotalPrice();
            this.$http.post(this.$dynamicHref+"/order/create",this.order).then((res)=>{
                alert(res.data);
                this.goodArr=[];
                this.addId=null;
                this.order.items=[];
                });
            
            },
            getProduct(){
                for (let i = 0; i < this.goodArr.length; i++){
                    if(this.addId==this.goodArr[i].id){
                        alert("该商品已添加，请在购物车中修改数量");
                        return;
                    }
                }
                this.$http.get(this.$dynamicHref+"/product/get/"+this.addId).then((res)=>{
                var val =res.data;
                if(!val){
                    alert("该商品编号不存在，请去商品橱窗查询");
                    return;
                }
                if(val.stock==0){
                    this.$http.get(this.$dynamicHref+"/lack-registy/"+this.addId+"/"+this.$logUser.userId).then((res)=>{
                        if(res.data)
                            alert("商品缺货，已经为您催货");
                        else
                            alert("请勿重复催货");
                    });
                    return;
                }
                val["count"]=1;
                this.goodArr.push(val);
                alert("添加成功");
    });
            },
            reduceCount(index){
                if(this.goodArr[index].count>0){
                    this.goodArr[index].count--;
                }else {
                    alert("数量不能为负!")
                }
            },
            addCount(index){
                this.goodArr[index].count++;
 
            },
            
            delGood(index){
                this.goodArr.splice(index,1);
 
            },
        
        },
        
  components: {
  }
}
</script>

<style scoped>
    table{
           height: 300px;
           width: 500px;
           border: black solid 2px;
       }

    td{
            height: 30px;
           border: black solid 2px;
       }
</style>
