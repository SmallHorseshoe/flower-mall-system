<template>
  <div style="padding: 10px">
    <!--    功能按钮部分    -->
    <el-header style="padding-left: 0; height: 40px">
      <el-input v-model="search" placeholder="请输入关键字" size="small" style="width: 200px; padding-right: 5px"
                clearable/>
      <el-button type="primary" size="small" @click="load()">查询</el-button>
      <el-button type="primary" size="small" @click="addItem()" >添加</el-button>
      <el-button type="primary" size="small">导入</el-button>
      <el-button type="primary" size="small">导出</el-button>
    </el-header>

    <!--    主体表格部分    -->
    <el-container>
      <div style="  width: 99.5%; ">
        <el-table :data="tableData" height="550px" border style="width: 100%;">
          <!--扩展表格-->
          <el-table-column type="expand">
            <template slot-scope="props">
              <div style="width: 99%; float: left">
                <el-descriptions :column="3" :size="descriptionsSize" style="margin-left: 47px" border>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-goods"></i>商品编号
                    </template>
                    <span>{{ props.row.goodsId }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-s-claim"></i>卖家手机号码
                    </template>
                    <span>{{ props.row.sellerPhone }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-s-promotion"></i>买家手机号码
                    </template>
                    <span>{{ props.row.buyerPhone }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-phone"></i>订单数量
                    </template>
                    <span>{{ props.row.sellNumber }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-goods"></i>交易金额
                    </template>
                    <span>{{ props.row.amount }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-sell"></i>收货地址
                    </template>
                    <span>{{ props.row.receiveAddress }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-orange"></i>下单时间
                    </template>
                    <span>{{ props.row.orderTime }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-location"></i>发货时间
                    </template>
                    <span>{{ props.row.deliveryTime }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item span="3">
                    <template slot="label">
                      <i class="el-icon-tickets"></i>成交时间
                    </template>
                    <span>{{ props.row.completeTime }}</span>
                  </el-descriptions-item>
                </el-descriptions>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="订单编号" prop="id" width="110px" align="center" sortable>
          </el-table-column>
          <el-table-column label="商品编号" prop="goodsId" sortable>
          </el-table-column>
          <el-table-column label="卖家手机号码" prop="sellerPhone">
          </el-table-column>
          <el-table-column label="买家手机号码" prop="buyerPhone">
          </el-table-column>
          <el-table-column label="订单数量" prop="sellNumber" sortable>
          </el-table-column>
          <el-table-column label="交易金额" prop="amount">
          </el-table-column>
          <el-table-column label="操作" width="134px" fixed="right">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              <el-popconfirm confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info"
                             icon-color="red" title="您确定删除这条数据吗？" @confirm="handleDelete(scope.row.id)">
                <el-button slot="reference" size="mini" type="danger">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-container>

    <!--    分页组件部分    -->
    <el-footer style="padding-top: 15px; ">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </el-footer>

    <!--    ‘新增’/‘编辑’ 按钮弹窗     -->
    <el-dialog title="订单信息" :visible.sync="dialogFormVisible" width="65%" center>
      <div style="display: flex">
        <el-form :model="orderForm" ref="orderForm" status-icon :rules="orderFormRules" :label-width="'100px'">
          <div style="display: flex">
            <el-form-item label="商品编号" prop="goodsId">
              <el-input v-model.number="orderForm.goodsId" class="dialog-el-input"
                        autocomplete="off" maxlength="11" clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="商品名称" prop="goodsName" >
              <el-input v-model="orderForm.goodsId" class="dialog-el-input" disabled
                        autocomplete="off" maxlength="11" clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="订单数量" prop="sellNumber">
              <el-input v-model.number="orderForm.sellNumber" class="dialog-el-input" autocomplete="off" maxlength="16"
                        clearable>
              </el-input>
            </el-form-item>
          </div>
          <div style="display: flex">
            <el-form-item label="卖家手机" prop="sellerPhone">
              <el-input v-model.number="orderForm.sellerPhone" class="dialog-el-input" autocomplete="off" maxlength="11"
                        clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="买家手机" prop="buyerPhone">
              <el-input v-model.number="orderForm.buyerPhone" class="dialog-el-input" autocomplete="off" maxlength="11"
                        clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="交易金额" prop="amount" show-password>
              <el-input v-model="orderForm.amount" class="dialog-el-input"
                        autocomplete="off" maxlength="16" clearable></el-input>
            </el-form-item>
          </div>
          <div style="display: flex">
            <el-form-item label="下单时间" prop="orderTime">
              <el-input v-model="orderForm.orderTime" class="dialog-el-input" autocomplete="off" maxlength="20"
                        clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="发货时间" prop="deliveryTime">
              <el-input v-model="orderForm.deliveryTime" class="dialog-el-input" autocomplete="off" maxlength="20"
                        clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="成交时间" prop="completeTime">
              <el-input v-model="orderForm.completeTime" class="dialog-el-input" autocomplete="off" maxlength="20"
                        clearable>
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
        <el-button @click="cancelAddItem()">取&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp消</el-button>
        <el-button type="primary" style="margin-left: 50px" @click="submitForm('orderForm')">
          确&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp定
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "FlowerOrder",
  components: {},
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
      search: '',
      dialogClick: '',
      dialogFormVisible: false,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      descriptionsSize: '100%',
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
      orderFormRules: {
        goodsId: [{required: true, message: "商品编号不能为空", trigger: 'blur'}],
        sellNumber: [{required: true, message: "订单数量不能为空", trigger: 'blur'}],
        amount: [{required: true, message: "交易金额不能为空", trigger: 'blur'}],
        sellerPhone: [
          {required: true, message: '手机号码不能为空', trigger: 'blur'},
          {validator: checkPhone, trigger: 'blur'}
        ],
        buyerPhone: [
          {required: true, message: '手机号码不能为空', trigger: 'blur'},
          {validator: checkPhone, trigger: 'blur'}
        ],
        receiveAddress: [{required: true, message: "交易金额不能为空"}],
      },
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/order", {
        params: {
          pageNum: this.currentPage, pageSize: this.pageSize, search: this.search
        }
      }).then(res => {
        // console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    addItem() {
      this.dialogClick = "addItem";
      // this.userForm = {};
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs.orderForm.resetFields();
      })
    },
    cancelAddItem() {
      // this.$refs["userForm"].resetField();
      this.dialogFormVisible = false;
    },
    handleEdit(row) {
      this.dialogClick = "handleEdit";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["orderForm"].resetFields();
        this.orderForm = JSON.parse(JSON.stringify(row));
      });
      // console.log(row)
    },
    handleDelete(id) {
      // console.log(id)
      request.delete("/order/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功！"
          });
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
        this.load();  //刷新表格
        this.dialogFormVisible = false; //关闭弹窗
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.load();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.dialogClick === "handleEdit") {  //更新
            request.put("/order", this.orderForm).then(res => {
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
              this.load();  //刷新表格
              this.dialogFormVisible = false; //关闭弹窗
            });
          } else if (this.dialogClick === "addItem") {  //新增
            request.post("/order", this.orderForm).then(res => {
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
              this.load();  //刷新表格
              this.dialogFormVisible = false; //关闭弹窗
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });

    },
  }
}
</script>
<style>
.demo-table-expand {
  font-size: 0;
  padding-left: 40px;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

.dialog-el-input {
  width: 200px;
}

</style>
