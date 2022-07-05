<template>
<!-- 该文件未使用 -->
  <div>
    <el-col style="width:200px">
      <h3 style="margin-top: 20px; color: #409EFF; text-align: center">花卉种类</h3>
      <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>导航一</span>
          </template>
          <!--          <el-checkbox-group v-model="checkList">-->
          <el-menu-item v-for="flower in flowers" :index="flower">{{ flower }}</el-menu-item>
          <!--          <el-menu-item index="1-1">-->
          <!--            <template>-->
          <!--              <el-checkbox-group v-model="checkList">-->
          <!--                <el-checkbox label="复选框 A"></el-checkbox>-->
          <!--                <el-checkbox label="复选框 B"></el-checkbox>-->
          <!--                <el-checkbox label="复选框 C"></el-checkbox>-->
          <!--                <el-checkbox label="禁用" disabled></el-checkbox>-->
          <!--                <el-checkbox label="选中且禁用" disabled></el-checkbox>-->
          <!--              </el-checkbox-group>-->
          <!--            </template>-->

          <!--          </el-menu-item>-->
          <!--          <el-menu-item index="1-2">选项2</el-menu-item>-->
          <!--          <el-menu-item index="1-3">选项3</el-menu-item>-->
          <!--          <el-menu-item index="1-4-1">选项1</el-menu-item>-->
        </el-submenu>
        <el-menu-item index="2">
          <i class="el-icon-menu"></i>
          <span slot="title">导航二</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </div>
</template>

<script>
export default {
  name: "MallAside",
  data() {
    return {
      flowers: [
        '梅花', '桃花', '牡丹', '海棠', '玉兰', '风信子', '郁金香', '紫罗兰', '金鱼草', '长春菊', '瓜叶菊'
      ]
    }
  },
  methods: {
    handleOpen() {

    },
    handleClose() {

    }
  }
}
</script>

<style scoped>

</style>