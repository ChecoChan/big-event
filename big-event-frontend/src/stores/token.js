import {ref} from "vue"
import {defineStore} from "pinia"

/**
 * defineStore 参数描述：
 *      第一个参数：给状态起名，具有唯一性
 *      第二个参数：函数，定义该状态中拥有的内容
 * defineStore 返回值描述：
 *      返回一个对象，将来可以调用该函数，得到第二个参数中返回的内容
 */
export const useTokenStore = defineStore("token", () => {
    // 定义 token
    const token = ref("");
    // 修改 token 的函数
    const setToken = (newToken) => {
        token.value = newToken;
    }
    // 移除 token 的函数
    const removeToken = () => {
        token.value = "";
    }
    return {token, setToken, removeToken};
},{
    persist: true
});
