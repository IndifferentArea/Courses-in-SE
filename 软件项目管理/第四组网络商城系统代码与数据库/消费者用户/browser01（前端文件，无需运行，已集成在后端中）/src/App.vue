<template>
  <div id="app">
  <el-container>
  <el-header style="background-color: rgb(255, 255, 255);height: 60px">
			<img class="logo_img" width="60px" height="60px" alt="" src="./assets/logo.png" >
   <div class="tag-group">
  <span class="tag-group__title">学生商城——用户系统</span>
  <template v-if="$logUser">
  <el-tag type="" effect="dark">用户：{{ $logUser.userId }}</el-tag>
  <el-button @click="displayBalance()" type="succcess" round>显示余额</el-button>
  <el-tag type="warning" effect="dark">所在地：{{ $logUser.province }}</el-tag>
</template>
   </div>


	</el-header>
  <el-container style="height: 750px; border: 1px solid #eee">

    <el-header style="background-color: rgb(255, 255, 255)">
      <el-menu
      :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          >
          <el-menu-item index="1"><router-link active-class="active" to="/display">商品橱窗</router-link></el-menu-item>
          <el-menu-item index="2"><router-link active-class="active" to="/userLog">用户登录</router-link></el-menu-item>
          <el-menu-item index="3" ><router-link :style="styleObj"  active-class="active" to="/cart" >购物车</router-link></el-menu-item>
          <el-menu-item index="4" ><router-link :style="styleObj"  active-class="active" to="/userOrder">用户订单</router-link></el-menu-item>
          
</el-menu>

    
    
    </el-header>
    <el-main><router-view/></el-main>
  

</el-container>
  <el-footer><div id="bottom">
		<span>
			网络商城.Copyright &copy;2022
		</span>
	</div></el-footer>
  </el-container>

  </div>
</template>
<script>
export default {
  name: 'App',
  data(){
    return{
      activeIndex: '1',
      styleObj:{
        'pointer-events':'none',
      },
    }
  },
  created() {
    this.$globalEventBus.$on('styleChange', this.styleChange)
    this.$globalEventBus.$on('indexChange', this.changeActiveIndex);
  },
  methods: {
    changeActiveIndex(index){
        this.activeIndex = index;
    },
    styleChange(val){
      this.styleObj['pointer-events']=val
    },
    displayBalance(){
      alert(this.$logUser.userBalance);
    },
      handleSelect(key, keyPath) {
        
      }
    }
}
</script>

<style>
#bottom {
	height: 30px;
	width: 1200px;
	text-align: center;
}
.el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  
  .el-aside {
    color: #333;
  }
  #header {
	height: 82px;
	width: 1200px;
}
#header div a {
	text-decoration: none;
	font-size: 20px;
}

#header div{
	float: right;
	margin-top: 55px;
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
.logo_img{
	float: left;
}
.wel_word{
	font-size: 60px;
	float: left;
}
</style>
