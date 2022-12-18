<template>
  <div>
<el-table
    :data="transport"
    style="width: 100%"
    >
    <el-table-column
      prop="province"
      label="目的省份"
      width="180">
    </el-table-column>
    <el-table-column
      prop="cost"
      label="基础运费"
      width="180">
    </el-table-column>
  
  </el-table>
    <router-view></router-view>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "Transport",
  data() {
    return {
        transport: [],
    }
  },
  created(){
    this.findAll();
  },
  methods: {
    findAll(){
      this.$http.get(this.$dynamicHref+"/transprt/list").then((res)=>{
      this.transport = res.data;
    });
    },
  },
  components: {
  },
  watch: {
      $route: {
        handler: function(val,oldval){
            if(val.path=='/transport')
            this.findAll();
        },
        deep: true
      },
      
  }
}
</script>

<style scoped>
</style>
