<template>
  <div style="height: 50px; width: 100%; margin: auto; line-height: 50px; background-color:#e2f1ff">
    <div style="width: 1300px; margin: auto; display: flex">
      <!--    <div style="width: 200px; padding-left: 30px; font-size: xx-large; font-weight: bold; color: dodgerblue">花卉商城</div>-->
      <el-link type="primary" @click="indexClick"
               style="padding-bottom: 10px; font-size: xx-large; font-weight: revert; ">花卉商城
      </el-link>
      <div style="flex: 1"></div>
      <el-dropdown style="float: right">
        <!--        <span class="el-dropdown-link">我<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
        <span class="el-dropdown-link">{{ userName }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-if="userStatus === 'false'"
                            @click.native="$router.push({path:'/login'},onComplete => {},onAbort => {})">登录/注册
          </el-dropdown-item>
          <el-dropdown-item :disabled="userStatus==='false'"
                            @click.native="$router.push({path:'/info/infoBase'},onComplete => {},onAbort => {})">个人信息
          </el-dropdown-item>
          <el-dropdown-item :disabled="userStatus==='false'"
                            @click.native="$router.push({path:'/info/infoGoods'},onComplete => {},onAbort => {})">我的商品
          </el-dropdown-item>
          <el-dropdown-item :disabled="userStatus==='false'"
                            @click.native="$router.push({path:'/info/infoOrder'},onComplete => {},onAbort => {})">我的订单
          </el-dropdown-item>
          <el-dropdown-item v-if="userStatus === 'true'" @click.native="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <!--  </div>-->
    </div>
  </div>
</template>

<script>

// var userName = ''
import request from "@/utils/request";

export default {
  name: "MallHeader",
  data() {
    return {
      userPhone: '',
      userName: "",
      userStatus: ''
    }
  },
  created() {
    this.load()
  },
  watch: {
    userPhone: {
      handler() {
        this.load()
      }
    }
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
          this.GLOBAL.userStatus = "true";
          this.GLOBAL.avatar = this.infoData.avatar;
          this.GLOBAL.address = this.infoData.userAddress;
        } else {
          // console.log(res)
        }
      })
    },
    logout() {
      // userName=
      this.GLOBAL.userName = '登录'
      this.GLOBAL.userPhone = ''
      this.GLOBAL.userStatus = 'false'
      sessionStorage.removeItem("userPhone");
      this.$router.push({path: '/index'}, onComplete => {
      }, onAbort => {
      })
      // console.log(this.GLOBAL.userName)
      // console.log(this.GLOBAL.userPhone)
      // console.log(this.GLOBAL.userStatus)
    },
    indexClick() {
      if (this.GLOBAL.userPhone === null) {
        this.$router.push({path: '/index'}, onComplete => {
        }, onAbort => {
        })
      } else {
        this.$router.push({
          path: '/index',
          query: {
            userPhone: this.GLOBAL.userPhone
          }
        }, onComplete => {
        }, onAbort => {
        })
      }
    },
    dataChange() {
      // console.log(this.GLOBAL.userPhone)
      this.userPhone = this.GLOBAL.userPhone
      this.userName = this.GLOBAL.userName
      this.userStatus = this.GLOBAL.userStatus
    }
  },
  mounted() {
    // 每隔一秒就监听全局变量
    setInterval(() => {
      setTimeout(this.dataChange, 0)
    }, 1000)
  },

}
</script>

<style scoped>

</style>