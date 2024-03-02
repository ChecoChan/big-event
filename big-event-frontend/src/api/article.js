import request from "@/utils/request"
// import {useTokenStore} from "@/stores/token";

// 文章分类查询
export const articleCategoryService = () => {
    // const tokenStore = useTokenStore();
    // return request.get("/category", {headers: {"Authorization": tokenStore.token}});
    return request.get("/category");
}

// 添加文章分类
export const articleCategoryAddService = (categoryModel) => {
    return request.post("/category", categoryModel);
}

// 修改文章分类
export const articleCategoryUpdateService = (categoryModel) => {
    return request.put("/category", categoryModel);
}

// 删除文章分类
export const articleCategoryDeleteService = (id) => {
    return request.delete("/category?id=" + id);
}

// 获取文章列表
export const articleListService = (params) => {
    return request.get("/article", {params: params});
}

// 添加文章
export const articleAddService = (articleModel) => {
    return request.post("/article", articleModel);
}