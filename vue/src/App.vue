<template>
  <div>
    <router-view/>
  </div>
</template>

<script>
//全局变量
const userName = '登录';
const userPhone = '';
const userStatus = 'false'
const avatar = ''
const address = ''
export default {
  name: "App",
  userName,
  userPhone,
  userStatus, // 用户登录状态
  avatar,
  address,

  // watch: {
  //   "userPhone": {
  //     handler() {
  //       // this.userName = this.GLOBAL.userName
  //       console.log("监听成功")
  //     },
  //     deep: true
  //   },
  //   userName: {
  //     handler() {
  //       console.log("userName监听成功")
  //     },
  //   }
  // }
}

</script>