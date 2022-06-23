<template>
  <div>
    <el-menu
        :default-active="path"
        style="width: 200px; min-height: calc(710px - 50px)"
        class="el-menu-vertical-demo"
        @select="handleSelect"
        router>
      <!--      <el-submenu index="1">-->
      <!--        <template slot="title">-->
      <!--          <i class="el-icon-location"></i>-->
      <!--          <span>用户信息</span>-->
      <!--        </template>-->
      <!--        <el-menu-item index="1-1">选项1</el-menu-item>-->
      <!--        <el-menu-item index="1-2">选项2</el-menu-item>-->
      <!--      </el-submenu>-->
      <el-menu-item index="/user">
        <i class="el-icon-menu"></i>
        <span slot="title">用户信息</span>
      </el-menu-item>
      <el-menu-item index="/goods">
        <i class="el-icon-document"></i>
        <span slot="title">商品信息</span>
      </el-menu-item>
      <el-menu-item index="/order">
        <i class="el-icon-setting"></i>
        <span slot="title">订单信息</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      path: this.$route.path,   //设置默认高亮
    }
  },
  methods: {
    handleSelect(key) {
      this.currentMenuItem = key
      console.log(key)
    }
  }
}
</script>

<style scoped>

</style>