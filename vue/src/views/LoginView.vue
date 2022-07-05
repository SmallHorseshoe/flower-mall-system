<template>
  <div style="width: 100%; height: 100vh; background-color: #e2f1ff; overflow: hidden">
    <div style="width: 400px; margin:150px auto">
      <div style="font-size: 30px; text-align: center; padding: 30px 0"> 欢迎登录</div>
      <el-form ref="loginForm" :model="loginForm" :rules="loginFormRules" size="normal">
        <el-form-item prop="userPhone">
          <el-input prefix-icon="el-icon-mobile-phone" v-model.number="loginForm.userPhone" maxlength="11"
                    clearable></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="loginForm.password" maxlength="16" show-password></el-input>
        </el-form-item>
        <el-form-item style="height: 25px;">
          <el-button style="width: 100%;margin-bottom: 1px" type="primary" @click="login">
            登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录
          </el-button>
        </el-form-item>
      </el-form>
      <div style="margin-top: 1px;  height: 30px;">
        <!--        <a style="font-size: 14px">注 册 账 号</a>-->
        <!--        <a style="font-size: 14px">注 册 账 号</a>-->
        <el-button size="small" style="background-color: #e2f1ff; border: none" @click="$router.push('/register')">注 册 账
          号
        </el-button>
        <el-button size="small" style="float: right; background-color: #e2f1ff; border: none">忘 记 密 码</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "LoginView",
  data() {
    return {
      loginForm: {userPhone: '', password: ''},
      loginFormRules: {
        userPhone: [{required: true, message: "账号不能为空", trigger: 'blur'}],
        password: [{required: true, message: "密码不能为空", trigger: 'blur'}],
      }
    }
  },
  methods: {
    login() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          request.post("/user/login", this.loginForm).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登陆成功"
              })
              // this.GLOBAL.userPhone = this.userPhone
              // this.GLOBAL.userStatuse = "true"
              // this.$global.userPhone = this.loginForm.userPhone
              this.$router.push({
                name: "Index",
                query: {
                  userPhone: this.loginForm.userPhone
                }
              })  //登录成功后跳转到系统主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        } else {
          console.log('error login!!');
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>

</style>