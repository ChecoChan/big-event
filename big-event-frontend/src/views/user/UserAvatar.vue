<script setup>
import {Plus, Upload} from '@element-plus/icons-vue'
import {ref} from 'vue'
import avatar from '@/assets/default.png'
import {userAvatarUpdateService, useUserInfoStore} from "@/api/user";
import {useTokenStore} from "@/stores/token";
import {ElMessage} from "element-plus";

const uploadRef = ref()

// 用户头像回显函数
const userInfoStore = useUserInfoStore();
// const imgUrl = avatar
const imgUrl = ref(userInfoStore.info.userPic);

// 读取 token
const tokenStore = useTokenStore();
// 头像上传成功回调函数
const uploadSuccess = (result) => {
  imgUrl.value = result.data;
}

// 修改头像函数
const updateAvatar = async () => {
  let result = await userAvatarUpdateService(imgUrl.value);
  ElMessage.success("修改头像成功");
  // 更新 Pinia 中的数据
  userInfoStore.info.userPic = imgUrl.value;
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>更换头像</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-upload
            ref="uploadRef"
            class="avatar-uploader"
            :show-file-list="false"
            auto-upload="true"
            action="/api/upload"
            name="file"
            :headers="{'Authorization:': tokenStore.token}"
            :on-success="uploadSuccess"
        >
          <img v-if="imgUrl" :src="imgUrl" class="avatar"/>
          <img v-else :src="avatar" width="278"/>
        </el-upload>
        <br/>
        <el-button type="primary" :icon="Plus" size="large" @click="uploadRef.$el.querySelector('input').click()">
          选择图片
        </el-button>
        <el-button type="success" :icon="Upload" size="large" @click="updateAvatar">
          上传头像
        </el-button>
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  :deep() {
    .avatar {
      width: 278px;
      height: 278px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 278px;
      height: 278px;
      text-align: center;
    }
  }
}
</style>