<template>
  <div>
    <el-button @click="addGoods">上架新商品</el-button>
    <div style="margin-top: 10px">
      <el-card v-for="goods in goodsList" style="margin-bottom: 20px">
        <el-descriptions class="margin-top" title="商品信息" :column="1" size="small" border
                         style="float: left;width: 250px">
          <template slot="extra">
            <el-popconfirm v-if="goods.sellState==='上架中'" confirm-button-text='确定' cancel-button-text='取消'
                           icon="el-icon-info" icon-color="red" title="您确定下架该商品吗？" @confirm="handleOff(goods)">
              <el-button slot="reference" type="danger" size="small">下架</el-button>
            </el-popconfirm>
            <el-popconfirm v-else confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info"
                           icon-color="red" title="您确定上架该商品吗？" @confirm="handleUp(goods)">
              <el-button slot="reference" size="small">上架</el-button>
            </el-popconfirm>
            <el-button type="primary" size="small" @click="editGoods(goods)">编辑</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i> 商品名称</template>
            {{ goods.goodsName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-mobile-phone"></i> 商品价格（元）</template>
            ￥{{ goods.goodsPrice }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-mobile-phone"></i> 销售数量（件）</template>
            {{ goods.sellNumber }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-mobile-phone"></i> 售卖状态</template>
            {{ goods.sellState }}
          </el-descriptions-item>
        </el-descriptions>
        <el-carousel type="card" height="200px" style="width: 400px;float: right" arrow="always" :autoplay="false">
          <el-carousel-item v-for="image in [goods.image1,goods.image2,goods.image3]" style="background-color: #9ecdff">
            <el-image :src="image" style="width: 200px; height: 200px;"
                      @error="image='http://localhost:9090/image/defaultImage.png'"></el-image>
          </el-carousel-item>
        </el-carousel>
      </el-card>
    </div>

    <el-dialog title="商品信息" :visible.sync="dialogFormVisible" width="80%" center>
      <div style="display: flex">
        <el-form :model="goodsForm" ref="goodsForm" status-icon :rules="goodsFormRules" :label-width="'100px'">
          <div style="display: flex">
            <el-form-item label="商品名称" prop="goodsName">
              <el-input v-model="goodsForm.goodsName" class="dialog-el-input"
                        autocomplete="off" maxlength="11" clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="花卉种类" prop="flowerSpecies">
              <el-input v-model="goodsForm.flowerSpecies" class="dialog-el-input" autocomplete="off" maxlength="18"
                        clearable>
              </el-input>
            </el-form-item>
          </div>
          <div style="display: flex">
            <el-form-item label="商品单价" prop="goodsPrice">
              <el-input v-model="goodsForm.goodsPrice" class="dialog-el-input" autocomplete="off" maxlength="16"
                        clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="商品数量" prop="goodsNumber" show-password>
              <el-input v-model="goodsForm.goodsNumber" class="dialog-el-input"
                        autocomplete="off" maxlength="16" clearable></el-input>
            </el-form-item>
          </div>
          <div style="display: flex">
            <el-form-item label="销售数量" prop="sellNumber" style="width: 320px">
              <el-input v-model="goodsForm.sellNumber" class="dialog-el-input" disabled
                        autocomplete="off" maxlength="16" clearable></el-input>
            </el-form-item>
            <el-form-item label="上架状态" prop="sellState" style="width: 320px">
              <el-radio v-model="goodsForm.sellState" label="上架中">上架</el-radio>
              <el-radio v-model="goodsForm.sellState" label="已下架">下架</el-radio>
            </el-form-item>
          </div>
          <el-form-item label="商品产地" prop="goodsAddress">
            <el-input v-model="goodsForm.goodsAddress" autocomplete="off" maxlength="50" clearable>
            </el-input>
          </el-form-item>

          <el-form-item label="商品说明" prop="description">
            <el-input v-model="goodsForm.description" autocomplete="off" maxlength="50" clearable>
            </el-input>
          </el-form-item>

        </el-form>
        <!--相片-->
        <div style="margin-left: 20px; display: flex">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/image/upload/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess1">
            <el-image :src="goodsForm.image1" style="display: block;height: 170px; width: 170px"
                      @error="goodsForm.image1='http://localhost:9090/image/defaultImage.png'"></el-image>
            <el-button size="small" type="primary" style="margin-top: 5px">点击更换图片</el-button>
          </el-upload>
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/image/upload/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess2">
            <el-image :src="goodsForm.image2" style="display: block;height: 170px; width: 170px"
                      @error="goodsForm.image2='http://localhost:9090/image/defaultImage.png'"></el-image>
            <el-button size="small" type="primary" style="margin-top: 5px">点击更换图片</el-button>
          </el-upload>
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/image/upload/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess3">
            <el-image :src="goodsForm.image3" style="display: block;height: 170px; width: 170px"
                      @error="goodsForm.image3='http://localhost:9090/image/defaultImage.png'"></el-image>
            <el-button size="small" type="primary" style="margin-top: 5px">点击更换图片</el-button>
          </el-upload>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelAddItem">取&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp消</el-button>
        <el-button type="primary" style="margin-left: 50px" @click="submitForm">
          确&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: "infoGoods",
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
    var validateEmpty = (rule, value, callback) => {
      if (!value) {
        callback(new Error('数据不能为空'));
      } else {
        callback();
      }
    };
    return {
      dialogFormVisible: false,
      goodsList: [],
      dialogClick: '',
      goodsForm: {
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
      goodsFormRules: {
        goodsName: [{required: true, message: "商品名称不能为空"}],
        goodsPrice: [{required: true, message: "商品价格不能为空"}],
        sellState: [{validator: validateEmpty, trigger: 'blur'}],
        sellerPhone: [
          {required: true, message: '手机号码不能为空', trigger: 'blur'},
          {validator: checkPhone, trigger: 'blur'}
        ],
        goodsNumber: [{validator: validateEmpty, trigger: 'blur'}],
        flowerSpecies: [{validator: validateEmpty, trigger: 'blur'}],
      },
    }
  },
  methods: {
    load() {
      request.get("/goods/goodsInfo", {
        params: {userPhone: this.GLOBAL.userPhone}
      }).then(res => {
        if (res.code === '0') {
          // console.log(res);
          this.goodsList = res.data
          // console.log(this.goodsList)
        } else {
          console.log(res)
        }
      })
    },
    handleOff(goods) {
      this.goodsForm = JSON.parse(JSON.stringify(goods))
      this.goodsForm.sellState = "已下架"
      this.updataGoods()
    },
    handleUp(goods) {
      this.goodsForm = JSON.parse(JSON.stringify(goods))
      this.goodsForm.sellState = "上架中"
      this.updataGoods()
    },
    editGoods(goods) {
      this.dialogClick = "editGoods";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs.goodsForm.resetFields()
        this.goodsForm = JSON.parse(JSON.stringify(goods))
      })
    },
    handleAvatarSuccess1(res) {
      this.goodsForm.image1 = res.data
      // console.log(res)
      // console.log(this.goodsForm.image1)
    },
    handleAvatarSuccess2(res) {
      this.goodsForm.image2 = res.data
    },
    handleAvatarSuccess3(res) {
      this.goodsForm.image3 = res.data
    },
    cancelAddItem() {
      this.dialogFormVisible = false;
    },
    addGoods() {
      this.dialogClick = "addGoods";
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.goodsForm.resetFields()
        this.goodsForm.goodsAddress = this.GLOBAL.address
      })
      // console.log(this.goodsForm)
    },
    submitForm() {
      this.$refs.goodsForm.validate((valid) => {
        if (valid) {
          if (this.dialogClick === "editGoods") {  //更新
            this.updataGoods()
          } else if (this.dialogClick === "addGoods") {  //新增
            this.goodsForm.sellerPhone = this.GLOBAL.userPhone
            this.goodsForm.sellNumber = 0
            request.post("/goods", this.goodsForm).then(res => {
              // console.log(res);
              if (res.code === '0') {
                this.$message({
                  type: "success",
                  message: "添加成功！"
                });
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                });
              }
              this.dialogFormVisible = false; //关闭弹窗
              this.load();  //刷新表格
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    updataGoods() {
      request.put("/goods", this.goodsForm).then(res => {
        // console.log(res);
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "修改成功！"
          });
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
        this.dialogFormVisible = false; //关闭弹窗
        this.load();  //刷新表格
      });
    }
  },
  created() {
    this.load()
  }
}
</script>

<style scoped>

</style>