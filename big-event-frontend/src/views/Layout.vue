<script setup>
import {
  Management,
  Promotion,
  UserFilled,
  User,
  Crop,
  EditPen,
  SwitchButton,
  CaretBottom
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import {ref} from "vue";
import {userInfoGetService} from "@/api/user";
import {useUserInfoStore} from "@/api/user";
import {useRouter} from "vue-router";
import {ElMessageBox} from "element-plus";
import {useTokenStore} from "@/stores/token";

const userInfoStore = useUserInfoStore();
const getUserInfo = async () => {
  let result = await userInfoGetService();
  userInfoStore.info = result.data;
}
getUserInfo();



const router = useRouter();
const tokenStore = useTokenStore();
// 下拉条被点击后的回调函数
const handleCommand = (command) => {
  if (command !== "logout") {
    router.push("/user/" + command);
  } else {
    ElMessageBox.confirm(
        "你确定退出吗？",
        "温馨提示",
        {
          confirmButtonText: "确认",
          cancelButtonText: "取消",
          type: "warning"
        }
    )
        .then(async () => { // 用户点击了确认
          // 清空 Pinia 中的 token 和个人信息
          userInfoStore.removeInfo();
          tokenStore.removeToken();
          // 跳转登录页面
          await router.push("/login");
        })
        .catch(() => { // 用户点击了取消
        })
  }
}
</script>

<template>
  <!-- element-plus 容器 -->
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu active-text-color="#ffd04b" background-color="#232323" text-color="#fff" router>
        <el-menu-item index="/article/category">
          <el-icon>
            <Management/>
          </el-icon>
          <span>文章分类</span>
        </el-menu-item>
        <el-menu-item index="/article/manage">
          <el-icon>
            <Promotion/>
          </el-icon>
          <span>文章管理</span>
        </el-menu-item>
        <el-sub-menu>
          <template #title>
            <el-icon>
              <UserFilled/>
            </el-icon>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/user/info">
            <el-icon>
              <User/>
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>
          <el-menu-item index="/user/avatar">
            <el-icon>
              <Crop/>
            </el-icon>
            <span>更换头像</span>
          </el-menu-item>
          <el-menu-item index="/user/resetPassword">
            <el-icon>
              <EditPen/>
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <!--        <div>用户：<strong>Checo</strong></div>-->
        <div>用户：<strong>{{ userInfoStore.info.nickname ? userInfoStore.info.nickname : userInfoStore.info.username}}</strong></div>
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <!-- <el-avatar :src="avatar"/> -->
            <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar"/>
              <el-icon>
                  <CaretBottom/>
              </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item :icon="User" command="info">基本资料</el-dropdown-item>
              <el-dropdown-item :icon="Crop" command="avatar">更换头像</el-dropdown-item>
              <el-dropdown-item :icon="EditPen" command="resetPassword">重置密码</el-dropdown-item>
              <el-dropdown-item :icon="SwitchButton" command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
          内容展示区
        </div>-->
        <router-view></router-view>
      </el-main>
      <!-- 底部区域 -->
      <el-footer>Big-Event ©2023 Created by Checo</el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    &__logo {
      height: 120px;
      background: url('@/assets/logo.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>