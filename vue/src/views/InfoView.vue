<template>
  <div style="width: 100%; min-height:calc(100vh - 50px); background-color: #f1f1f1; overflow: hidden">
    <div style="width: 900px; margin: auto; overflow: hidden">
      <!--      <el-form :model="infoData">-->
      <!--    顶部信息区域    -->
      <div class="div-radius" style="height: 90px; display: flex; margin-top: 5px; background-color: #f5f5f5">
        <el-avatar style="width: 80px;height: 80px; margin: 5px 35px; background-color: #ffffff"
                   :src="this.avatar"></el-avatar>
        <div>
          <h4 style="margin-top: 25px;">{{ userName }}</h4>
          <h5 style="margin-top: 8px">{{ this.GLOBAL.userPhone }}</h5>
        </div>
      </div>
      <div style="margin-top: 5px; display: flex; min-height:calc(100vh - 160px);">
        <!--    左侧Aside区域    -->
        <el-aside width="150px" style="background-color: #f5f5f5;">

          <el-button class="button-list"
                     @click="$router.push({path: '/info/infoBase'},onComplete => {},onAbort => {})">
            <i class="el-icon-user"></i>&nbsp;&nbsp;&nbsp;基本信息 <i class="el-icon-arrow-right el-icon--right"></i>
          </el-button>
          <el-button class="button-list" style="margin-left: 0"
                     @click="$router.push({path: '/info/infoGoods'},onComplete => {},onAbort => {})">
            <i class="el-icon-goods"></i>&nbsp;&nbsp;&nbsp;我的商品 <i class="el-icon-arrow-right el-icon--right"></i>
          </el-button>
          <el-button class="button-list" style="margin-left: 0"
                     @click="$router.push({path: '/info/infoOrder'},onComplete => {},onAbort => {})">
            <i class="el-icon-document"></i>&nbsp;&nbsp;&nbsp;我的订单 <i class="el-icon-arrow-right el-icon--right"></i>
          </el-button>

        </el-aside>
        <el-main style="margin-left:5px; background-color: white">
          <router-view/>
        </el-main>
      </div>

      <!--      </el-form>-->
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "InfoView",
  data() {
    return {
      userName: '',
      userPhone: '',
      avatar: ''
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
          this.GLOBAL.avatar = this.infoData.avatar;
          this.GLOBAL.address = this.infoData.userAddress;
        } else {
          console.log(res)
        }
      })
    },
    dataChange() {
      this.userName = this.GLOBAL.userName
      this.userPhone = this.GLOBAL.userPhone
      this.avatar = this.GLOBAL.avatar
    }
  },
  mounted() {
    // 每隔一秒就监听全局变量
    setInterval(() => {
      setTimeout(this.dataChange, 0)
    }, 1000)
  },
  // created() {
  //   this.load()
  //   if (this.GLOBAL.userStatus === "false"){
  //     console.log(this.$global.userPhone)
  //     this.$router.push({path:'/index'},onComplete => {},onAbort => {})
  //   }
  // }
  created() {
    //重新将sessionStorage中的值存储到Store中，判断是否是初次进入的方法视自己而定
    if (JSON.parse(sessionStorage.getItem("userPhone"))) {
      this.GLOBAL.userPhone = JSON.parse(sessionStorage.getItem("userPhone"))
    }
    this.load()
    if (this.GLOBAL.userStatus === "") {
      // console.log(this.$global.userPhone)
      this.$router.push({path: '/index'}, onComplete => {
      }, onAbort => {
      })
    }
    //监听页面刷新
    window.addEventListener("beforeunload", () => {
      sessionStorage.setItem("userPhone", JSON.stringify(this.GLOBAL.userPhone));
    })
  }
}
</script>

<style scoped>
.div-radius {
  /*overflow: hidden;*/
  /*border-bottom-left-radius: 40px 40px;*/
  /*border-top-left-radius: 40px 40px;*/
  border-radius: 10px;
}

.button-list {
  width: 150px;
  margin: auto;
  background-color: #f1f1f1;
  border-radius: 0;
}

/deep/ .el-tabs__item {
  font-size: 20px;
  width: 150px;
}
</style>