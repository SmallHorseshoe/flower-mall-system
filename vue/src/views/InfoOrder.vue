<template>
  <div>
    <div style="margin-top: 10px">
      <el-card v-for="order in orderList" style="margin-bottom: 20px">
        <el-descriptions class="margin-top" :title="order.goodsName" :column="2" border
                         style="float: left;width: 480px">
          <template slot="extra">
            ￥<span style="margin-right: 20px; font-size: 18px">{{ order.amount }}</span>
          </template>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i> 商品数量(件)</template>
            {{ order.sellNumber }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-mobile-phone"></i> 商家电话</template>
            {{ order.sellerPhone }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-mobile-phone"></i> 下单时间</template>
            {{ order.orderTime }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-mobile-phone"></i> 到货时间</template>
            {{ order.completeTime }}
          </el-descriptions-item>
          <el-descriptions-item span="2">
            <template slot="label"><i class="el-icon-mobile-phone"></i> 收货地址</template>
            {{ order.receiveAddress }}
          </el-descriptions-item>
        </el-descriptions>
        <el-carousel height="170px" indicator-position="outside" style="width: 170px; float: right" arrow="always"
                     :autoplay="false">
          <el-carousel-item v-for="image in [order.image1,order.image2,order.image3]" style="background-color: #9ecdff">
            <el-image :src="image" style="width: 170px; height: 170px;"
                      @error="image='http://localhost:9090/image/defaultImage.png'"></el-image>
          </el-carousel-item>
        </el-carousel>
      </el-card>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "InfoOrder",
  data() {
    return {
      orderList: [],
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get("/order/orderInfo", {
        params: {userPhone: this.GLOBAL.userPhone}
      }).then(res => {
        if (res.code === '0') {
          // console.log(res);
          this.orderList = res.data
          // console.log(this.orderList)
        } else {
          console.log(res)
        }
      })
    },
  }
}
</script>

<style scoped>

</style>