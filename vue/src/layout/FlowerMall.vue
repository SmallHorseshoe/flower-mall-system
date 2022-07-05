<template>
  <div style="margin:auto;width: 100%; min-height: 100vh">
    <!--    头部-->
    <MallHeader/>

    <!--      侧边栏-->
    <!--    <MallAside/>-->

    <!--      内容区域-->
    <router-view style="flex: 1"/>

  </div>
</template>

<script>
import Aside from "@/components/Aside";
import MallHeader from "@/components/MallHeader";
import MallAside from "@/components/MallAside";

export default {
  name: "FlowerMall",
  components: {
    MallAside,
    MallHeader
  }
}
</script>

<style scoped>

</style>