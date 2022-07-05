<template>
  <div style="padding: 10px">

    <!--    功能按钮部分    -->
    <el-header style="padding-left: 0; height: 40px">
      <el-input v-model="search" placeholder="请输入关键字" size="small" style="width: 200px; padding-right: 5px"
                clearable/>
      <el-button type="primary" size="small" @click="load()">查询</el-button>
      <el-button type="primary" size="small" @click="addItem()">添加</el-button>
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
              <div style="width: 80%; float: left">
                <el-descriptions :column="3" :size="descriptionsSize" style="margin-left: 47px" border>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-goods"></i>商品名称
                    </template>
                    <span>{{ props.row.goodsName }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-s-claim"></i>商品单价(元)
                    </template>
                    <span>{{ props.row.goodsPrice }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-sell"></i>销售数量(件)
                    </template>
                    <span>{{ props.row.sellNumber }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-phone"></i>卖家手机号码
                    </template>
                    <span>{{ props.row.sellerPhone }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-goods"></i>商品数量(件)
                    </template>
                    <span>{{ props.row.goodsNumber }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-s-promotion"></i>上架状态
                    </template>
                    <span>{{ props.row.sellState }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-orange"></i>花卉品种
                    </template>
                    <span>{{ props.row.flowerSpecies }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-location"></i>商品产地
                    </template>
                    <span>{{ props.row.goodsAddress }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item span="3">
                    <template slot="label">
                      <i class="el-icon-tickets"></i>商品说明
                    </template>
                    <span>{{ props.row.description }}</span>
                  </el-descriptions-item>
                </el-descriptions>
              </div>
              <div style=" margin:0 2px; float: left ; border: 1px solid #eaeaea">
                <el-avatar shape="square" :size="176" :src="props.row.image1" fit="fill"
                           style="background-color: #ffffff"
                           @error="goodsForm.image1='http://localhost:9090/image/defaultImage.png'"></el-avatar>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="商品编号" prop="id" width="110px" align="center" sortable>
          </el-table-column>
          <el-table-column label="商品名称" prop="goodsName" sortable>
          </el-table-column>
          <el-table-column label="商品单价(元)" prop="goodsPrice">
          </el-table-column>
          <el-table-column label="卖家手机号码" prop="sellerPhone">
          </el-table-column>
          <el-table-column label="商品数量" prop="goodsNumber" sortable>
          </el-table-column>
          <el-table-column label="销售数量" prop="sellNumber" sortable>
          </el-table-column>
          <el-table-column label="上架状态" prop="sellState" sortable>
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
    <el-dialog title="添加新商品" :visible.sync="dialogFormVisible" width="78%" center>
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
            <el-form-item label="卖家号码" prop="sellerPhone">
              <el-input v-model.number="goodsForm.sellerPhone" class="dialog-el-input" autocomplete="off" maxlength="11"
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
            <el-form-item label="销售数量" prop="sellNumber" style="width: 300px">
              <el-input v-model="goodsForm.sellNumber" class="dialog-el-input"
                        autocomplete="off" maxlength="16" clearable></el-input>
            </el-form-item>
          </div>
          <div style="display: flex">
            <el-form-item label="商品产地" prop="goodsAddress">
              <el-input v-model="goodsForm.goodsAddress" autocomplete="off" maxlength="50" style="width: 500px;"
                        clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="上架状态" prop="sellState">
              <el-radio v-model="goodsForm.sellState" label="上架中">上架中</el-radio>
              <el-radio v-model="goodsForm.sellState" label="已下架">已下架</el-radio>
            </el-form-item>
          </div>
          <el-form-item label="商品说明" prop="description">
            <el-input v-model="goodsForm.description" autocomplete="off" maxlength="50" clearable>
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
            <el-image :src="goodsForm.image1" style="display: block"
                      @error="goodsForm.image1='http://localhost:9090/image/defaultImage.png'"></el-image>
            <el-button size="small" type="primary" style="margin-top: 5px">点击更换图片</el-button>
          </el-upload>

        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelAddItem()">取&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp消</el-button>
        <el-button type="primary" style="margin-left: 50px" @click="submitForm('goodsForm')">
          确&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp定
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "FlowerProduct",
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
        goodsName: [{required: true, message: "商品名称不能为空", trigger: 'blur'}],
        goodsPrice: [{required: true, message: "商品价格不能为空", trigger: 'blur'}],
        sellState: [{validator: validateEmpty, trigger: 'blur'}],
        sellerPhone: [
          {required: true, message: '手机号码不能为空', trigger: 'blur'},
          {validator: checkPhone, trigger: 'blur'}
        ],
        goodsNumber: [{validator: validateEmpty, trigger: 'blur'}],
        flowerSpecies: [{validator: validateEmpty, trigger: 'blur'}],
      },
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/goods", {
        params: {
          pageNum: this.currentPage, pageSize: this.pageSize, search: this.search
        }
      }).then(res => {
        if (res.code === '0') {
          // console.log(res);
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
      })
    },
    addItem() {
      this.dialogClick = "addItem";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["goodsForm"].resetFields();
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
        this.$refs["goodsForm"].resetFields();
        this.goodsForm = JSON.parse(JSON.stringify(row));
      });
      // console.log(row)
    },
    handleDelete(id) {
      // console.log(id)
      request.delete("/goods/" + id).then(res => {
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
              this.load();  //刷新表格
              this.dialogFormVisible = false; //关闭弹窗
            });
          } else if (this.dialogClick === "addItem") {  //新增
            request.post("/goods", this.goodsForm).then(res => {
              // console.log(res);
              this.dialogFormVisible = false;
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
    handleAvatarSuccess(res) {
      // console.log(res)
      this.goodsForm.image1 = res.data
    }
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
