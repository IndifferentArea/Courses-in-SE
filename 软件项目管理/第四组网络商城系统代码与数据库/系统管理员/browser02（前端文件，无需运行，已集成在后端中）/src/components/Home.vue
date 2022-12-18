<template>
  <el-container>
    <el-header style="background-color: rgb(255, 255, 255); height: 60px">
      <div class="tag-group">
        <span class="tag-group__title">学生商城——管理员系统</span>
        <template v-if="$logUser">
          <el-tag type="" effect="dark">管理员：{{ $logUser.id }}</el-tag>
          <el-tag type="success" effect="dark"
            >类型：{{ switchType($logUser.type) }}</el-tag
          >

          <el-tag type="warning" effect="dark"
            >所在地：{{ $logUser.province }}</el-tag
          >
        </template>
      </div></el-header
    >
    <el-container style="height: 750px; border: 1px solid #eee">
      <el-header style="padding: 0; background-color: rgb(255, 255, 255)">
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-menu-item index="1"
            >
              商品模块
              
            </el-menu-item
          >
          <el-menu-item index="2"
            >
            管理员订单</el-menu-item
          >
          <el-menu-item index="3"
            >省级运输</el-menu-item
          >
          <el-menu-item index="4"
            >退货查询</el-menu-item
          >
          <el-menu-item index="5"
            >缺损查询</el-menu-item
          >
          <el-menu-item index="6"
            >催货查询</el-menu-item
          >
        </el-menu>
      </el-header>
      <el-main>
        <!-- <router-view></router-view> -->
        <div v-if="activeIndex == 1">
          <Goods />
        </div>
        <div v-if="activeIndex == 2">
          <User />
        </div>
        <div v-if="activeIndex == 3">
          <Transport />
        </div>
        <div v-if="activeIndex == 4">
          <ReturnRegistry />
        </div>
        <div v-if="activeIndex == 5">
          <DefectRegistry />
        </div>
        <div v-if="activeIndex == 6">
          <Lack />
        </div>
        
      </el-main>
    </el-container>
    <el-footer
      ><div id="bottom">
        <span> 在线商城.Copyright &copy;2022 </span>
      </div></el-footer
    >
  </el-container>
</template>

<script>
import Goods from "@/components/Goods";
import User from "@/components/User";
import Transport from "@/components/Transport";
import ReturnRegistry from "@/components/ReturnRegistry";
import DefectRegistry from "@/components/DefectRegistry";
import Lack from "@/components/Lack";
export default {
  components: { Goods,User,Transport,ReturnRegistry,DefectRegistry,Lack },
  data() {
    return {
      activeIndex: "1",
      logged: {
        "pointer-events": "none",
      },
      superAdmin: {
        "pointer-events": "none",
      },
    };
  },
  created() {
    this.$globalEventBus.$on("styleChange", this.styleChange);
    this.$globalEventBus.$on("indexChange", this.changeActiveIndex);
  },

  methods: {
    changeActiveIndex(index) {
      this.activeIndex = index;
    },
    switchType(type) {
      var action = "";
      switch (type) {
        case 0:
          action = "超级管理员";
          break;
        case 1:
          action = "省级签收员";
          break;
        case 2:
          action = "站点签收员";
          break;
        case 3:
          action = "站点管理员";
          break;
      }
      return action;
    },
    styleChange(val, type) {
      this.logged["pointer-events"] = "none";
      this.superAdmin["pointer-events"] = "none";
      this.logged["pointer-events"] = val;
      if (type == 0) this.superAdmin["pointer-events"] = val;
    },
    displayBalance() {
      alert(this.$logUser.userBalance);
    },
    handleSelect(key, keyPath) {
      this.activeIndex = key
    },
  },
};
</script>

<style>
#bottom {
  height: 30px;
  width: 1200px;
  text-align: center;
}
.el-header {
  background-color: #b3c0d1;
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

#header div {
  float: right;
  margin-top: 55px;
}
#header div span {
  margin: 10px;
}

#header div .um_span {
  color: red;
  font-size: 25px;
  margin: 10px;
}

#header div a {
  color: blue;
}
.logo_img {
  float: left;
}
.wel_word {
  font-size: 60px;
  float: left;
}
</style>
