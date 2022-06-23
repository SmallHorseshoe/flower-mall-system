<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-size: large; font-weight: bold; color: dodgerblue">后台管理</div>
    <div style="flex: 1"></div>
    <div style="width: 120px">
      <el-dropdown>
        <span class="el-dropdown-link">
          管理员<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="$router.push('/login')">退出系统</el-dropdown-item>
<!--          <el-dropdown-item>狮子头</el-dropdown-item>-->
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!--  </div>-->
  </div>
</template>

<script>
export default {
  name: "Header"
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
  font-size: larger;
}

.el-icon-arrow-down {
  font-size: 18px;
}

</style>