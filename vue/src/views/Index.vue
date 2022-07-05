<template>
  <div style="width: 100%; min-height:calc(100vh - 50px); margin: auto; background-color: #f1f1f1 ">
    <el-container style="width:1300px; margin: auto">
      <el-aside width="250px" style=" background-color: #fafafa;">
        <el-col :span="21" style="margin: 10px 10px">
          <div style="color: #31a4fd; text-align: center; margin-top: 10px; font-size:large">选择花卉种类</div>
          <el-autocomplete
              class="inline-input" ref="autocomplete" placeholder="请输入内容" v-model="state1" clearable
              :fetch-suggestions="querySearch" @select="handleSelect"
              style="margin-top: 5px;"
          ></el-autocomplete>
        </el-col>
        <div style="margin-left: 10px; height: auto">
          <el-tag v-for="tag in flowerSpecies" closable type="success" @close="handleClose(tag)">
            {{ tag }}
          </el-tag>
        </div>
        <!--                <el-divider style="margin-top: 1px"></el-divider>-->
        <div>
          <!--                  <div style="color: #f85cd4; text-align: center">选择花卉种类</div>-->
        </div>
      </el-aside>
      <el-main style="padding: 0 0; min-height:calc(100vh - 50px); background-color: #ffffff">
        <!--    商品列表页面    -->
        <div ref="goodsList">
          <div style="display: flex">
            <el-input placeholder="请输入内容" v-model="inputSearch" class="input-with-select" clearable @blur="load"
                      style="margin: 20px 10px; width: 400px">
              <el-select v-model="selectSearch" slot="prepend" placeholder="商品名称" style="width: 110px">
                <el-option label="商品名称" value="商品名称"></el-option>
                <el-option label="商家名称" value="商家名称"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search" @click="load"></el-button>
            </el-input>
          </div>
          <div style="margin-left: 5px; width: 99%">
            <template>
              <el-row :gutter="1">
                <el-col v-for="goods in goodsData" style=" width:250px; margin: 10px 5px">
                  <el-card :body-style="{ padding: '1px', width:'250px' }">
                    <el-carousel :autoplay='false' style="height: 250px">
                      <el-carousel-item v-for="goodImage in ([goods.image1,goods.image2,goods.image3])"
                                        style="height: 250px">
                        <el-image :src="goodImage" style="width: 250px; height: 250px"
                                  :preview-src-list="[goods.image1,goods.image2,goods.image3]"></el-image>
                      </el-carousel-item>
                    </el-carousel>
                    <div style="padding: 14px;">
                      <span>{{ goods.goodsName }}</span>
                      <span style="float: right; margin-right: 10px">￥{{ goods.goodsPrice }}</span>
                      <div class="bottom clearfix" style="display: flex">
                      <span
                          style="max-width: 150px;min-width: 150px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; "
                          class="time">
                        {{ goods.description }}
                      </span>
                        <el-button type="text" class="button" @click="func(goods)">查看详情 ></el-button>
                      </div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
            </template>
          </div>
        </div>

        <!--    商品信息页面    -->
        <div ref="goodsInfo" style="margin-top:20px; margin-left: 10px; display: none">
          <!--          <el-button icon="el-icon-arrow-left" size="large" type="text" @click="backfunc">返回</el-button>-->
          <el-page-header @back="backfunc" content="商品详情页面" style="margin-left: 70px; color: #409EFF">
          </el-page-header>
          <el-form :data="goodsInfo">
            <div style="width: 1000px; ">
              <el-carousel :interval="4000" type="card" arrow="always" height="400px"
                           style="margin: auto;width: 800px; ">
                <el-carousel-item v-for="image in [goodsInfo.image1,goodsInfo.image2,goodsInfo.image3]"
                                  style="width: 405px;">
                  <el-image :src="image" style="width: 400px; height: 400px"
                            :preview-src-list="[goodsInfo.image1,goodsInfo.image2,goodsInfo.image3]"></el-image>
                </el-carousel-item>
              </el-carousel>
            </div>

            <div style="width: 1000px; ">
              <el-descriptions :column="1" style="margin: auto; width: 800px; font-size: 18px ">
                <el-descriptions-item label="商品名称">{{ goodsInfo.goodsName }}</el-descriptions-item>
                <el-descriptions-item label="花卉品种">{{ goodsInfo.flowerSpecies }}</el-descriptions-item>
                <el-descriptions-item label="商品价格"> ￥ {{ goodsInfo.goodsPrice }}</el-descriptions-item>
                <el-descriptions-item label="商品产地">{{ goodsInfo.goodsAddress }}</el-descriptions-item>
                <el-descriptions-item label="商品数量">{{ goodsInfo.goodsNumber }}件</el-descriptions-item>
                <el-descriptions-item label="商品详情">{{ goodsInfo.description }}</el-descriptions-item>
              </el-descriptions>
              <el-button slot="reference" type="danger" round @click="purchase"
                         style="width: 150px; float: right; margin:30px 100px 50px 30px;">
                购&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp买
              </el-button>
            </div>
          </el-form>
        </div>
        <!--购买弹窗-->
        <el-dialog title="订单信息" :visible.sync="dialogFormVisible" width="65%" center>
          <div style="display: flex">
            <el-form :model="orderForm" ref="orderForm" :rules="orderRule" status-icon :label-width="'110px'">
              <div style="display: flex">
                <el-form-item label="商品名称" prop="goodsName">
                  <el-input v-model="goodsInfo.goodsName" class="dialog-el-input" disabled style="width: 205px;">
                  </el-input>
                </el-form-item>
                <el-form-item label="商品数量" prop="goodsNumber">
                  <el-input v-model.number="goodsNumber" class="dialog-el-input" autocomplete="off"
                            maxlength="11" clearable>
                  </el-input>
                </el-form-item>
                <el-form-item label="交易金额" prop="amount">
                  <el-input v-model.number="orderForm.amount" class="dialog-el-input" disabled>
                  </el-input>
                </el-form-item>
              </div>
              <div style="display: flex">
                <el-form-item label="卖家手机" prop="sellerPhone">
                  <el-input v-model.number="orderForm.sellerPhone" class="dialog-el-input" disabled>
                  </el-input>
                </el-form-item>
                <el-form-item label="买家手机" prop="buyerPhone">
                  <el-input v-model.number="orderForm.buyerPhone" class="dialog-el-input" autocomplete="off"
                            maxlength="11" clearable>
                  </el-input>
                </el-form-item>
                <el-form-item label="下单时间" prop="orderTime">
                  <el-input v-model="orderForm.orderTime" class="dialog-el-input" disabled>
                  </el-input>
                </el-form-item>
              </div>
              <el-form-item label="收货地址" prop="receiveAddress">
                <el-input v-model="orderForm.receiveAddress" class="dialog-el-input" style="width:800px"
                          autocomplete="off" maxlength="16" clearable></el-input>
              </el-form-item>

            </el-form>
          </div>
          <div slot="footer" class="dialog-footer">
            <el-button @click="cancelPurchase">取&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp消</el-button>
            <el-button type="primary" style="margin-left: 50px" @click="submitForm">
              确&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp定
            </el-button>
          </div>
        </el-dialog>

      </el-main>
    </el-container>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Index",
  components: {},
  data() {
    return {
      restaurants: [],
      state1: '',
      state2: '',
      flowerSpecies: [],
      selectSearch: '',
      inputSearch: '',
      goodsData: [],
      goodsInfo: {
        goodsName: '',
        goodsPrice: '',
        sellState: '',
        sellerPhone: '',
        goodsNumber: '',
        sellNumber: '',
        flowerSpecies: '',
        goodsAddress: '',
        description: '',
        image1: '',
        image2: '',
        image3: ''
      },

      /* 购买弹窗 */
      dialogFormVisible: false,
      goodsNumber: '',
      orderForm: {
        goodsId: '',
        sellerPhone: '',
        buyerPhone: '',
        sellNumber: '',
        amount: '',
        receiveAddress: '',
        orderTime: '',
        deliveryTime: '',
        completeTime: '',
      },
      orderRule: {
        goodsNumber: [{request: true, message: "商品数量不能为空", trigger: 'blur'}],
        buyerPhone: [{request: true, message: "手机号码不能为空", trigger: 'blur'}],
        receiveAddress: [{request: true, message: "收货地址不能为空", trigger: 'blur'}]
      }
      /* 购买弹窗 */
    }
  },
  created() {
    // console.log(this.$global.userPhone)
    this.load();
    try {
      // console.log(this.$route.query.userPhone)
      if (this.$route.query.userPhone) {
        this.GLOBAL.userPhone = this.$route.query.userPhone
      }
      // console.log(this.GLOBAL.userPhone)
    } catch (e) {
      // console.log("error")
      console.log(e)
    }
  },

  watch: {
    goodsNumber: {
      handler() {
        this.orderForm.sellNumber = this.goodsNumber
        this.orderForm.amount = (this.goodsNumber * this.goodsInfo.goodsPrice).toFixed(2)
      }
    }
  },
  methods: {
    load() {
      let flowerSpecies = '';
      for (let i = 0; i < this.flowerSpecies.length; i++) {
        flowerSpecies += this.flowerSpecies[i] + ":";
      }
      if (flowerSpecies.length > 0)
        flowerSpecies = flowerSpecies.substring(0, flowerSpecies.length - 1)
      // console.log(flowerSpecies.substring(0, flowerSpecies.length - 1))
      request.get("/goods/list", {
        params: {species: flowerSpecies, searchType: this.selectSearch, search: this.inputSearch}
      }).then(res => {
        // console.log(res);
        this.goodsData = res.data;
        // this.total = res.data.total;
      })
    },
    purchase() {
      this.dialogFormVisible = true
      this.orderForm.goodsId = this.goodsInfo.id
      this.goodsNumber = ''
      this.orderForm.sellerPhone = this.goodsInfo.sellerPhone
      this.orderForm.buyerPhone = this.GLOBAL.userPhone
      this.orderForm.orderTime = new Date().toLocaleDateString() + " " + new Date().toTimeString().substring(0, 8)
      this.orderForm.receiveAddress = this.GLOBAL.address
    },
    cancelPurchase() {
      this.dialogFormVisible = false
    },
    submitForm() {
      this.$refs.orderForm.validate((valid) => {
        if (valid) {
          if (this.goodsInfo.goodsNumber - this.goodsNumber < 0) {
            this.$message({
              type: "error",
              message: "该产品存货不足！！"
            });
            this.dialogFormVisible = false; //关闭弹窗
            return
          }
          //商品数量减少
          this.goodsInfo.goodsNumber = this.goodsInfo.goodsNumber - this.goodsNumber
          request.put("/goods", this.goodsInfo).then(res => {
            // console.log(res);
            if (res.code === '0') {
              // this.$message({
              //   type: "success",
              //   message: "修改成功！"
              // });
            } else {
              this.$message({
                type: "error",
                message: "该产品已售空！！"
              });
              this.dialogFormVisible = false; //关闭弹窗
              return
            }
            this.dialogFormVisible = false; //关闭弹窗
          });
          //提交订单
          request.post("/order", this.orderForm).then(res => {
            // console.log(res);
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "购买成功！"
              });
            } else {
              this.$message({
                type: "error",
                message: "购买失败！"
              });
            }
            this.dialogFormVisible = false; //关闭弹窗
          })
        }
      });
    },
    backfunc() {
      this.$refs.goodsInfo.style.display = 'none'
      this.$refs.goodsList.style.display = 'block'
    },
    func(goods) {
      if (this.GLOBAL.userStatus === "false") {
        this.$message({
          type: "warning",
          message: "请先登录账号"
        })
        return
      }
      // console.log(goods)
      this.goodsInfo = goods
      this.$refs.goodsList.style.display = "none"
      this.$refs.goodsInfo.style.display = "block"
    },
    querySearch(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    handleSelect(item) {
      let flag = false
      this.flowerSpecies.forEach((currentValue) => {
        if (currentValue === this.state1) {
          flag = true
        }
      })
      if (!flag)
        this.flowerSpecies.push(this.state1)
      this.state1 = ''
      this.load()
    },
    handleClose(tag) {
      this.flowerSpecies.splice(this.flowerSpecies.indexOf(tag), 1);
      this.load()
    },
    // dataChange() {
    //   // console.log(this.GLOBAL.userPhone)
    //   // if (this.GLOBAL.userPhone !== null) {
    //   // }
    // }
  },
  mounted() {
    request.get("/goods/species").then(res => {
      if (res.code === '0') {
        let rest = []
        for (let i = 0; i < res.data.length; i++) {
          rest.push({"value": res.data[i]})
        }
        // console.log(rest)
        this.restaurants = rest;
      }
    })

    // // 每隔一秒就监听全局变量
    // setInterval(() => {
    //   setTimeout(this.dataChange, 0)
    // }, 1000)
  }
}
</script>

<style scoped>
.el-select .el-input {
  width: 130px;
}

.input-with-select .el-input-group__prepend {
  background-color: #fff;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.el-carousel__item {
  background-color: #e7e7e7;
  width: 250px;
}

.el-carousel /deep/ .el-carousel__container {
  height: 220px;
}
</style>