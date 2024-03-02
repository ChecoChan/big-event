<script setup>
import {ref} from 'vue'
import {userPasswordUpdateService} from "@/api/user";
import {ElMessage} from "element-plus";
import {useTokenStore} from "@/stores/token";

const updatePwdParams = ref({
  old_pwd: "",
  new_pwd: "",
  re_pwd: ""
});
const rules = {
  old_pwd: {required: true, message: "请输入旧密码", trigger: "blur"},
  new_pwd: {required: true, message: "请输入新密码", trigger: "blur"},
  re_pwd: {required: true, message: "请再次输入新密码", trigger: "blur"}
}

// Pinia 存储的 token 信息
const tokenStore = useTokenStore();

// 修改密码函数
const headers = {Authorization: "Bearer " + tokenStore.token};
const updateUserPassword = async () => {
  await userPasswordUpdateService(updatePwdParams.value);
  ElMessage.success("修改密码成功");
  tokenStore.removeToken(); // 清理 token
  clearUpdatePwdParamsData(); // 清理表单数据
}
// 清理表单数据函数
const clearUpdatePwdParamsData = () => {
  updatePwdParams.value.old_pwd = "";
  updatePwdParams.value.new_pwd = "";
  updatePwdParams.value.re_pwd = "";
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>密码修改</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form :model="updatePwdParams" :rules="rules" label-width="100px" size="large">
          <el-form-item label="原密码" prop="old_pwd">
            <el-input v-model="updatePwdParams.old_pwd"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="new_pwd">
            <el-input v-model="updatePwdParams.new_pwd" type="password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="re_pwd">
            <el-input v-model="updatePwdParams.re_pwd" type="password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateUserPassword">修改密码</el-button>
            <el-button type="primary" @click="clearUpdatePwdParamsData">重置表单</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>