<template>
  <div style="padding: 10px">

    <!--    功能按钮    -->
    <el-header style="padding-left: 0; height: 40px">
      <el-input v-model="search" placeholder="请输入关键字" size="small" style="width: 200px; padding-right: 5px"
                clearable/>
      <el-button type="primary" size="small" @click="load()">查询</el-button>
      <el-button type="primary" size="small" @click="addItem()">添加</el-button>
      <el-button type="primary" size="small">导入</el-button>
      <el-button type="primary" size="small">导出</el-button>
    </el-header>

    <!--    主体表格    -->
    <el-container>
      <div style="  width: 100%; ">
        <el-table :data="tableData" height="550px" border style="width: 99.5%;">
          <el-table-column type="expand">
            <template slot-scope="props">
              <div style="width: 80%; float: left">
                <el-descriptions :column="3" :size="descriptionsSize" style="margin-left: 47px" border>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-mobile-phone"></i>手机号码
                    </template>
                    <span>{{ props.row.userPhone }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-lock"></i>账号密码
                    </template>
                    <span type="password">{{ props.row.password }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-s-custom"></i>账号昵称
                    </template>
                    <span>{{ props.row.nickname }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-user"></i>用户姓名
                    </template>
                    <span>{{ props.row.userName }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-user"></i>用户性别
                    </template>
                    <span>{{ props.row.sex }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-user"></i>身份证号码
                    </template>
                    <span>{{ props.row.idCard }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item span="3">
                    <template slot="label">
                      <i class="el-icon-location-outline"></i>居住地址
                    </template>
                    <span>{{ props.row.userAddress }}</span>
                  </el-descriptions-item>
                  <el-descriptions-item span="3">
                    <template slot="label">
                      <i class="el-icon-tickets"></i>个性签名
                    </template>
                    <span>{{ props.row.signature }}</span>
                  </el-descriptions-item>
                </el-descriptions>
              </div>
              <div style=" margin:0 2px; float: left ; border: 1px solid #e3e0e0">
                <el-avatar shape="square" :src="props.row.avatar" :size="176" fit="fill" style="background-color: white"
                           @error="userForm.avatar='http://localhost:9090/image/defaultUser.png'"></el-avatar>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="手机号码" prop="userPhone" width="150px" sortable>
          </el-table-column>
          <el-table-column label="账号昵称" prop="nickname">
          </el-table-column>
          <el-table-column label="用户姓名" prop="userName">
          </el-table-column>
          <el-table-column label="用户性别" prop="sex">
          </el-table-column>
          <el-table-column label="身份证号" prop="idCard" sortable>
          </el-table-column>
          <el-table-column label="操作" width="134px" fixed="right">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              <el-popconfirm confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info"
                             icon-color="red" title="您确定删除这条数据吗？" @confirm="handleDelete(scope.row.userPhone)">
                <el-button slot="reference" size="mini" type="danger">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-container>

    <!--    分页组件    -->
    <el-footer style="padding-top: 15px;">
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
    <el-dialog title="添加新用户" :visible.sync="dialogFormVisible" width="78%" center>
      <div style="display: flex">
        <el-form :model="userForm" ref="userForm" status-icon :rules="userFormRules" :label-width="'100px'">
          <div style="display: flex">
            <el-form-item label="手机号码" prop="userPhone">
              <el-input v-model.number="userForm.userPhone" class="dialog-el-input"
                        autocomplete="off" maxlength="11" clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="账号昵称" prop="nickname">
              <el-input v-model="userForm.nickname" class="dialog-el-input" autocomplete="off" maxlength="16"
                        clearable>
              </el-input>
            </el-form-item>
            <!--        <el-form-item label="账号密码" prop="pass" autocomplete="off" maxlength="16" show-password>-->
            <!--          <el-input v-model="addForm.pass" type="password" clearable style="width: 80%"></el-input>-->
            <!--        </el-form-item>-->
            <el-form-item label="账号密码" prop="password" show-password>
              <el-input v-model="userForm.password" class="dialog-el-input"
                        autocomplete="off" maxlength="16" clearable></el-input>
            </el-form-item>
          </div>
          <div style="display: flex">
            <el-form-item label="用户姓名" prop="userName">
              <el-input v-model="userForm.userName" class="dialog-el-input" autocomplete="off" maxlength="16"
                        clearable>
              </el-input>
            </el-form-item>
            <el-form-item label="用户性别" prop="sex" style="width: 300px">
              <el-radio v-model="userForm.sex" label="男">男</el-radio>
              <el-radio v-model="userForm.sex" label="女">女</el-radio>
            </el-form-item>
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="userForm.idCard" class="dialog-el-input" autocomplete="off" maxlength="18"
                        clearable>
              </el-input>
            </el-form-item>
          </div>
          <el-form-item label="用户住址" prop="userAddress">
            <el-input v-model="userForm.userAddress" autocomplete="off" maxlength="50" clearable>
            </el-input>
          </el-form-item>
          <el-form-item label="个性签名" prop="signature">
            <el-input v-model="userForm.signature" autocomplete="off" maxlength="50" clearable>
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
            <el-image :src="userForm.avatar" style="display: block"
                      @error="userForm.avatar='http://localhost:9090/image/defaultUser.png'"></el-image>
            <el-button size="small" type="primary">点击更换头像</el-button>
          </el-upload>

        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelAddItem()">取&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp消</el-button>
        <el-button type="primary" style="margin-left: 50px" @click="submitForm('userForm')">
          确&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp定
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
// @ is an alias to /src

import request from "@/utils/request";

export default {
  name: 'UserView',
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
      userForm: {
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
      userFormRules: {
        userPhone: [{validator: checkPhone, trigger: 'blur'}],
        nickname: [{validator: validateEmpty, trigger: 'blur'}],
        password: [{validator: validateEmpty, trigger: 'blur'}],
        userName: [{validator: validateEmpty, trigger: 'blur'}],
        idCard: [{validator: validateEmpty, trigger: 'blur'}],
        sex: [{validator: validateEmpty, trigger: 'blur'}]
      },
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/user", {
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
        // console.log(this.$refs["userForm"])
        this.$refs["userForm"].resetFields();
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
        this.$refs["userForm"].resetFields();
        this.userForm = JSON.parse(JSON.stringify(row));
      });
      // console.log(row)
    },
    handleDelete(id) {
      // console.log(id)
      request.delete("/user/" + id).then(res => {
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
            request.put("/user", this.userForm).then(res => {
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
            request.post("/user", this.userForm).then(res => {
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
      this.userForm.avatar = res.data
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
