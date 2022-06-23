<template>
  <div style="width: 100%; height: 100vh; background-color: #9ecdff; overflow: hidden">
    <div style="width: 400px; margin:150px auto">
      <div style="color:white; font-size: 30px; text-align: center; padding: 30px 0"> 欢迎登录</div>
      <el-form ref="form" :model="loginForm" size="normal">
        <el-form-item>
          <el-input prefix-icon="el-icon-user-solid" v-model="loginForm.userName" maxlength="16"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input prefix-icon="el-icon-lock" v-model="loginForm.password" maxlength="16" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login">登 录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "LoginView",
  data() {
    return {
      loginForm: {userName: 'admin', password: '123456'},

    }
  },
  methods: {
    login() {
      request.post("/user/login", this.loginForm).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "登陆成功"
          })
          this.$router.push("/")  //登录成功后跳转到系统主页
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }

}
</script>

<style scoped>

</style>