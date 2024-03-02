// 导入 request.js 请求工具
import request from "@/utils/request";
import {defineStore} from "pinia";
import {ref} from "vue";

// 用户注册
export const userRegisterService = (registerData) => {
    const params = new URLSearchParams();
    for (let key in registerData)
        params.append(key, registerData[key]);
    return request.post('/user/register', params);
}

// 用户登录
export const userLoginService = (loginData) => {
    const params = new URLSearchParams();
    for (let key in loginData)
        params.append(key, loginData[key]);
    return request.post("/user/login", params);
}

// 获取用户信息
export const userInfoGetService = () => {
    return request.get('/user/userInfo');
}

// 定义个人中心状态
export const useUserInfoStore = defineStore("userInfo", () => {
    // 定义用户信息
    const info = ref({})
    // 修改用户信息的方法
    const setInfo = (newInfo) => {
        info.value = newInfo
    }
    // 清空用户信息的方法
    const removeInfo = () => {
        info.value = {}
    }
    return {info, setInfo, removeInfo}
}, {
    persist: true
})

// 修改基本信息
export const userInfoUpdateService = (userInfo) => {
    return request.put("/user/update", userInfo);
}

// 修改头像
export const userAvatarUpdateService = (avatarUrl) => {
    let params = new URLSearchParams();
    params.append("avatarUrl", avatarUrl);
    return request.patch("/user/updateAvatar", params);
}

// 修改密码
export const userPasswordUpdateService = (updatePwdParams) => {
    return request.patch("/user/updatePwd", updatePwdParams);
}