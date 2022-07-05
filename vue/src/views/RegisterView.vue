<template>
  <div style="width: 100%; height: 100vh; background-color: #e2f1ff; overflow: hidden">
    <div style="width: 1000px; margin:100px auto">
      <div style=" font-size: 30px; text-align: center; padding: 30px 0"> 注册账号</div>
      <div style="display: flex">
        <el-form :model="registerForm" ref="registerForm" status-icon :rules="registerFormRules" :label-width="'100px'">
          <div style="display: flex">
            <el-form-item label="手机号码" prop="userPhone">
              <el-input v-model.number="registerForm.userPhone" class="dialog-el-input"
                        autocomplete="off" maxlength="11" clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="账号昵称" prop="nickname">
              <el-input v-model="registerForm.nickname" class="dialog-el-input" autocomplete="off" maxlength="16"
                        clearable>
              </el-input>
            </el-form-item>
            <!--        <el-form-item label="账号密码" prop="pass" autocomplete="off" maxlength="16" show-password>-->
            <!--          <el-input v-model="addForm.pass" type="password" clearable style="width: 80%"></el-input>-->
            <!--        </el-form-item>-->
            <el-form-item label="账号密码" prop="password" show-password>
              <el-input v-model="registerForm.password" class="dialog-el-input"
                        autocomplete="off" maxlength="16" clearable></el-input>
            </el-form-item>
          </div>
          <div style="display: flex">
            <el-form-item label="用户姓名" prop="userName">
              <el-input v-model="registerForm.userName" class="dialog-el-input" autocomplete="off" maxlength="16"
                        clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="用户性别" prop="sex" style="width: 300px">
              <el-radio v-model="registerForm.sex" label="男">男</el-radio>
              <el-radio v-model="registerForm.sex" label="女">女</el-radio>
            </el-form-item>
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="registerForm.idCard" class="dialog-el-input" autocomplete="off" maxlength="18"
                        clearable>
              </el-input>
            </el-form-item>
          </div>
          <el-form-item label="用户住址" prop="userAddress">
            <el-input v-model="registerForm.userAddress" autocomplete="off" maxlength="50" clearable>
            </el-input>
          </el-form-item>
          <el-form-item label="个性签名" prop="signature">
            <el-input v-model="registerForm.signature" autocomplete="off" maxlength="50" clearable>
            </el-input>
          </el-form-item>
        </el-form>
        <!--头像-->
        <div style="margin-left: 10px">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/image/upload/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <el-image :src="registerForm.avatar" style="display: block; width: 150px; height: 150px;"
                      @error="registerForm.avatar='http://localhost:9090/image/defaultUser.png'"></el-image>
            <el-button size="small" type="primary">点击更换头像</el-button>
          </el-upload>

        </div>
      </div>
      <div style="text-align: center;">
        <el-button @click="$router.push('/login')">取&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp消</el-button>
        <el-button type="primary" style="margin-left: 50px" @click="submitForm('userForm')">
          确&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp定
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "RegisterView",
  data() {
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机号码不能为空'));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入数字值'));
        } else {
          if (value < 10000000000) {
            callback(new Error('请输入11位手机号码'));
          } else {
            callback();
          }
        }
      }, 500);
    };
    var validatePass = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'));
      } else {
        if (this.registerForm.password !== '') {
          this.$refs.addForm.validateField('password');
        }
        callback();
      }
    };
    var validateConfirm = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'));
      } else if (value !== this.registerForm.confirm) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    var validateEmpty = (rule, value, callback) => {
      if (!value) {
        callback(new Error('数据不能为空'));
      } else {
        callback();
      }
    };
    return {
      registerForm: {
        userPhone: '',
        nickname: '',
        // pass: '',
        password: '',
        userName: '',
        idCard: '',
        sex: '',
        userAddress: '',
        signature: '',
        avatar: ''
      },
      registerFormRules: {
        userPhone: [{validator: checkPhone, trigger: 'blur'}],
        nickname: [{validator: validateEmpty, trigger: 'blur'}],
        password: [{validator: validateEmpty, trigger: 'blur'}],
        userName: [{validator: validateEmpty, trigger: 'blur'}],
        idCard: [{validator: validateEmpty, trigger: 'blur'}],
        sex: [{validator: validateEmpty, trigger: 'change'}]
      },
    };

  },
  methods: {
    handleAvatarSuccess(res) {
      this.registerForm.avatar = res.data
    },
    submitForm() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          request.post("/user", this.registerForm).then(res => {
            // console.log(res);
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册账号成功！"
              });
              this.$router.push("/login")
            } else {
              this.$message({
                type: "error",
                message: res.msg
              });
            }
          })
        } else {
          console.log('error register!!');
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>

</style>