<template>
  <div>
    <h1>催货详情</h1>
    <el-table
    :data="lack"
    style="width: 100%"
    >
    <el-table-column
      prop="product"
      label="脱销商品"
      width="180">
    </el-table-column>
    <el-table-column
      prop="count"
      label="催货数量"
      width="180">
    </el-table-column>
  <el-table-column
      prop="userId"
      label="催货用户"
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
        lack:[],
    }
  },
  components: {
  },
  
  methods: {
    findAll(){
      this.$http.get(this.$dynamicHref+"/lack-registy/list").then((res)=>{
      this.lack = res.data;
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
