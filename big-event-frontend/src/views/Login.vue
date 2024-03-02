<script setup>
import {ref} from "vue";
import {Lock, User} from "@element-plus/icons-vue";
import {userLoginService, userRegisterService} from "@/api/user";
import {ElMessage} from 'element-plus'
import {useRouter} from "vue-router";
import {useTokenStore} from "@/stores/token";

// 控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false);

// 注册表单或登录表单数据
const registerOrLoginData = ref({
  username: "",
  password: "",
  rePassword: ""
});

// 校验密码函数
const checkRePassword = (rules, value, callback) => {
  if (value === "") {
    callback(new Error("请在此输入密码"))
  } else if (value !== registerOrLoginData.value.password) {
    callback(new Error("两次密码输入不一致"))
  } else {
    callback()
  }
}

// 表单校验规则
const rules = {
  username: [
    {require: true, message: "请输入用户名", trigger: "blur"},
    {
      min: 5,
      max: 16,
      message: "用户名为 5-16 位非空字符",
      trigger: "blur",
    },
  ],
  password: [
    {require: true, message: "请输入密码", trigger: "blur"},
    {
      min: 5,
      max: 16,
      message: "密码为 5-16 位非空字符",
      trigger: "blur",
    },
  ],
  rePassword: [{validator: checkRePassword, trigger: "blur"}],
};

// 注册函数：调用后端接口完成注册
const register = async () => {
  let result = await userRegisterService(registerOrLoginData.value)
  // if (result.code === 0) alert("注册成功");
  // else alert("注册失败");
  ElMessage.success("注册成功");
}

// 登录函数：调用后端接口完成登录
const router = useRouter();
const tokenStore = useTokenStore();
const login = async () => {
  let result = await userLoginService(registerOrLoginData.value)
  // if (result.code === 0) alert("登录成功");
  // else alert("登录失败");
  ElMessage.success("登录成功");
  // 登录成功，将返回的 token 存储到 Pinia 中
  tokenStore.setToken(result.data);
  // 跳转首页
  await router.push("/");
}

// 清空注册或登录表单数据函数
const clearRegisterOrLoginData = () => {
  registerOrLoginData.value = {
    username: "",
    password: "",
    rePassword: ""
  }
}
</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 注册表单 -->
      <el-form
          ref="form"
          size="large"
          autocomplete="off"
          v-if="isRegister"
          :model="registerOrLoginData"
          :rules="rules"
      >
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input
              :prefix-icon="User"
              placeholder="请输入用户名"
              v-model="registerOrLoginData.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              :prefix-icon="Lock"
              type="password"
              placeholder="请输入密码"
              v-model="registerOrLoginData.password"
          ></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input
              :prefix-icon="Lock"
              type="password"
              placeholder="请输入再次密码"
              v-model="registerOrLoginData.rePassword"
          ></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="register">
            注册
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link
              type="info"
              :underline="false"
              @click="isRegister = false; clearRegisterOrLoginData()"
          >
            ← 返回
          </el-link>
        </el-form-item>
      </el-form>
      <!-- 登录表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-else :model="registerOrLoginData" :rules="rules">
        <el-form-item>
          <h1>登录</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input
              :prefix-icon="User"
              placeholder="请输入用户名"
              v-model="registerOrLoginData.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              name="password"
              :prefix-icon="Lock"
              type="password"
              placeholder="请输入密码"
              v-model="registerOrLoginData.password"
          ></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码？</el-link>
          </div>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="login">
            登录
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link
              type="info"
              :underline="false"
              @click="isRegister = true; clearRegisterOrLoginData()"
          >
            注册 →
          </el-link>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url("@/assets/logo2.png") no-repeat 60% center / 240px auto,
    url("@/assets/login_bg.jpg") no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>
