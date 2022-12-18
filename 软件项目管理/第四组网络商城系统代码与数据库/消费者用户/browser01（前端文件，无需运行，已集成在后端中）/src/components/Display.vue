<template>
  <div>
    
	<div id="main">
		<el-select v-model="price" placeholder="请选择">
    <el-option
      v-for="item in priceOptions"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>

  <el-select v-model="categories" placeholder="请选择">
    <el-option
      v-for="item in categoryOptions"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>

  <el-button type="success" @click="getProducts(1,8,price,categories)" icon="el-icon-check" circle></el-button>
  <el-button type="primary" :disabled="$logUser==null" @click="doRecommend($logUser.userId)" round>系统推荐</el-button>
		<div id="book">
			<div v-for="product in products" class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" :src="$dynamicHref+'/images/'+product.id+'.png'" />
				</div>
				<div class="book_info">
					<div class="book_name">
						<span class="sp1">编号</span>
						<span class="sp2">{{product.id}}</span>
					</div>
					<div class="book_name">
						<span class="sp1">产品名</span>
						<span class="sp2">{{product.name}}</span>
					</div>
					
					<div class="book_price">
						<span class="sp1">价格</span>
						<span class="sp2">{{product.price}}</span>
					</div>
				
					<div class="book_amount">
						<span class="sp1">库存</span>
						<span class="sp2">{{product.stock}}</span>
					</div>
					<div class="book_name">
						<span class="sp1">种类</span>
						<span class="sp2">{{mapCategory(product.category)}}</span>
					</div>
				</div>
				<div class="book_get">
					<el-button @click="add_filter()" type="success"  circle>加入购物车</el-button>
				</div>
			</div>
			
			
		</div>
		
		
	
	</div>
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
  name: "Display",
  data() {
    return {
        products: [],
		
		total: 0,
		pageSize: 8,
		pageNo: 1,
		priceOptions: [{
          value: -1,
          label: '所有'
        }, {
          value: 1,
          label: '1-30'
        }, {
          value: 31,
          label: '31-60'
        }, {
          value: 61,
          label: '61-90'
        }, {
          value: 91,
          label: '91-120'
        },],
		categoryOptions: [{
          value: -1,
          label: '所有'
        },{
          value: 0,
          label: '书籍'
        }, {
          value: 1,
          label: '电子产品'
        }, {
          value: 2,
          label: '课程'
        },],
		price: -1,
		categories: -1,
		goodArr:[],
		//product.id: null,
        addId: null, 
        order: {"items":[]},
    }
  },

  created(){
	if(this.$logUser==null)
		this.getProducts(this.pageNo,this.pageSize,this.price,this.categories);
	else
		this.doRecommend(this.$logUser.userId);
    
	 
  },
  methods: {
	add_filter(){
		this.$router.push('/Cart')
	},
	mapCategory(no){
		var action = "";
        switch (no) {
        case 0 :
        action = "书籍";
        break; 
        case 1 :
        action = "电子产品";
        break; 
        case 2 :
        action = "课程";
        break;
        }
        return action;

	},
	doRecommend(userID){
		this.$http.get(this.$dynamicHref+"/recommend/"+userID).then((res)=>{
			this.price = res.data.price;
			this.categories = res.data.categories;
			this.getProducts(1,8,this.price,this.categories);
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
    getProducts(pageNo,size,price,categories){
      this.$http.get(this.$dynamicHref+"/product/"+pageNo+"/"+size+"/"+price+"/"+categories).then((res)=>{
      this.products = res.data.records;
	  this.total=res.data.total;
	  this.pageNo=res.data.current;
	  this.pageSize=res.data.size;
	  if(this.products.length==0 && this.total!=0){
		this.pageNo=1;
		this.getProducts(this.pageNo,this.pageSize,this.price,this.categories);
	  }
    });
    },
	handleSizeChange(val) {
        this.getProducts(this.pageNo,val,this.price,this.categories);
      },
      handleCurrentChange(val) {
        this.getProducts(val,this.pageSize,this.price,this.categories);
      },
  }

}
</script>

<style scoped lang="css">

body {
	overflow: hidden;
}

* {
	margin: 0;
	font-family:"Microsoft Yahei";
	color: #666;
}

div{
	margin: auto;
	margin-bottom: 10px;
	margin-top: 10px;
	
}

#header {
	height: 82px;
	width: 1200px;
}

#main {
	height: 460px;
	width: 1200px;
	border: 1px black solid;
	overflow: auto;
}

#bottom {
	height: 30px;
	width: 1200px;
	text-align: center;
}

#book{
	width: 100%;
	height: 90%;
	margin: auto;
	
}

.b_list{
	height:320px;
	width:250px;
	margin: 20px;
	float: left;
	margin-top:0px;
	margin-bottom:0px;
	border: 1px #e3e3e3 solid;
}

#page_nav{
	width: 100%;
	height: 10px;
	margin: auto;
	
	text-align: center;
}

#pn_input {
	width: 30px;
	text-align: center;
}

.img_div{
	height: 150px;
	text-align: center;
}

.book_img {
	height:150px;
	width:150px;
}

.book_info {
	
	text-align: center;
}

.book_get{
	
	text-align:right;
}



.book_info div{
	height: 10px;
	width: 300px;
	text-align: left;
}

.wel_word{
	font-size: 60px;
	float: left;
}

.logo_img{
	float: left;
}

#header div a {
	text-decoration: none;
	font-size: 20px;
}

#header div{
	float: right;
	margin-top: 55px;
}

.book_cond{
	margin-left: 500px;
}

.book_cond input{
	width: 50px;
	text-align: center;
}


/*登录页面CSS样式  */

#login_header{
	height: 82px;
	width: 1200px;
}

.login_banner{
	height:475px;
	background-color: #39987c;
}

.login_form{
	height:310px;
	width:406px;
	float: right;
	margin-right:50px;
	margin-top: 50px;
	background-color: #fff;
}

#content {
	height: 475px;
	width: 1200px;
}

.login_box{
	margin: 20px;
	height: 260px;
	width: 366px;
}

h1 {
	font-size: 20px;
}



#sub_btn{
	background-color: #39987c;
	border: none;
	color: #fff;
	width: 360px;
	height: 40px;
}

#l_content {
	float: left;
	margin-top: 150px;
	margin-left: 300px;
}

#l_content span {
	font-size: 60px;
	color: white;
}

.tit h1 {
	float: left;
	margin-top: 5px;
}

.tit a {
	float: right;
	margin-left: 10px;
	margin-top: 10px;
	color: red;
	text-decoration: none;
}

.tit .errorMsg {
	float: right;
	margin-left: 10px;
	margin-top: 10px;
	color: red;
}

.tit {
	height: 30px;
}
/*购物车*/
#main table{
	margin: auto;
	margin-top: 80px; 
	border-collapse: collapse;
}

#main table td{
	width: 120px;
	text-align:center;
	border-bottom: 1px #e3e3e3 solid;
	padding: 10px;
}

.cart_info{
	width: 700px;
	text-align: right;
}

.cart_span {
	margin-left: 20px;
}

.cart_span span{
	color: red;
	font-size: 20px;
	margin: 10px;
}

.cart_span a , td a{
	font-size: 20px;
	color: blue;
}

#header div span {
	margin: 10px;
}

#header div .um_span{
	color: red;
	font-size: 25px;
	margin: 10px;
}

#header div a {
	color: blue;
}

</style>
