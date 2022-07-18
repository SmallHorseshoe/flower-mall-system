<template>
  <div style="">
    <el-form :model="infoData">
      <!--   编辑   -->
      <div v-if="editable === 'true'">
        <div style="height: 350px">
          <div style="float: left">
            <el-form-item label="我的昵称" style="display:flex; width: 300px">
              <el-input v-model="infoData.nickname" maxlength="15"></el-input>
            </el-form-item>
            <el-form-item label="我的密码" style="display:flex; width: 300px">
              <el-input v-model="infoData.password" show-password  maxlength="15" style="width: 207px"></el-input>
            </el-form-item>
            <el-form-item label="我的性别" style="display:flex; width: 300px">
              <el-radio v-model="infoData.sex" label="男"></el-radio>
              <el-radio v-model="infoData.sex" label="女"></el-radio>
            </el-form-item>
            <el-form-item label="真实姓名" style="display:flex; width: 300px">
              <el-input v-model="infoData.userName" maxlength="18"></el-input>
            </el-form-item>
            <el-form-item label="身份证号" style="display:flex; width: 300px">
              <el-input v-model="infoData.idCard" maxlength="18"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" style="display:flex; width: 300px" >
              <el-input v-model.number="infoData.userPhone" disabled maxlength="11"></el-input>
            </el-form-item>
          </div>
          <div style="display: block">
            <el-upload
                class="avatar-uploader"
                action="http://localhost:9090/image/upload/"
                :show-file-list="false"
                :on-success="handleAvatarSuccess">
              <el-image :src="infoData.avatar" style="display: block;height: 200px; width: 200px"
                        @error="infoData.avatar='https://baomidou.com/img/logo.svg'"></el-image>
              <el-button size="small" type="primary" style="margin-top: 5px">点击更换图片</el-button>
            </el-upload>
          </div>
        </div><br>
        <div style=" margin-top: 10px">

          <el-form-item label="居住住址" style="display:flex">
            <el-input v-model="infoData.userAddress" maxlength="50" style="width: 600px"></el-input>
          </el-form-item>
          <el-form-item label="个人简介" style="display:flex;">
            <el-input v-model="infoData.signature" type="textarea" maxlength="200" style="width: 600px;"></el-input>
          </el-form-item>
        </div>
        <el-button type="primary" style="width:120px; margin-left: 295px" @click="confirmEdit"> 确 认 修 改</el-button>
      </div>

      <!--   非编辑   -->
      <div v-else>
        <div style="height: 188px; ">
          <div style="float: left">
            <el-form-item label="我的昵称" style="display:flex; width: 250px"> {{ infoData.nickname }}</el-form-item>
            <el-form-item label="我的密码" style="display:flex; width: 250px"> ******</el-form-item>
            <el-form-item label="我的性别" style="display:flex; width: 250px"> {{ infoData.sex }}</el-form-item>
          </div>
          <div style="float: left; ">
            <el-avatar :src="infoData.avatar" shape="square" style="width: 130px;height: 130px;"></el-avatar>
          </div>
        </div>
        <div>
          <div style="display: flex">
            <el-form-item label="真实姓名" style="display:flex; width: 250px"> {{ infoData.userName }}</el-form-item>
            <el-form-item label="身份证号" style="display:flex; width: 300px"> {{ infoData.idCard }}</el-form-item>
          </div>
          <div style="display: flex">
            <el-form-item label="手机号码" style="display:flex; width: 250px"> {{ infoData.userPhone }}</el-form-item>
            <el-form-item label="居住住址" style="display:flex; "> {{ infoData.userAddress }}</el-form-item>
          </div>
          <el-form-item label="个人简介" style="display:flex; width: 300px"> {{ infoData.signature }}</el-form-item>
        </div>
        <el-button type="primary" style="width:120px; margin-left: 280px" @click="editable='true'"> 修 改 资 料</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "InfoBase",
  data() {
    return {
      editable: 'false',
      infoData: {
        nickname: '',
        password: '',
        userName: '',
        idCard: '',
        sex: '',
        userPhone: '',
        signature: '',
        userAddress: '',
        avatar: '',
      },

    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get("/user/userInfo", {
        params: {userPhone: this.GLOBAL.userPhone}
      }).then(res => {
        if (res.code === '0') {
          // console.log(res);
          this.infoData = res.data;
          this.GLOBAL.userName = this.infoData.nickname;
          this.GLOBAL.avatar = this.infoData.avatar;
          this.GLOBAL.address = this.infoData.userAddress;
        } else {
          console.log(res)
        }
      })
    },
    handleAvatarSuccess(res) {
      this.infoData.avatar = res.data
    },
    confirmEdit() {
      request.put("/user", this.infoData).then(res => {
        // console.log(res);
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "修改成功！"
          });
          // this.GLOBAL.userPhone = this.infoData.userPhone
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
        this.load();  //刷新表格
        this.editable = "false"
      });
    }
  }
}
</script>

<style scoped>

</style>