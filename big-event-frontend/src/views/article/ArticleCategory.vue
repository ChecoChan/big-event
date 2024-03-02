<script setup>
import {
  Edit,
  Delete
} from '@element-plus/icons-vue'
import {ref} from 'vue'
import {
  articleCategoryAddService,
  articleCategoryDeleteService,
  articleCategoryService,
  articleCategoryUpdateService
} from "@/api/article"
import {ElMessage, ElMessageBox} from "element-plus";

const categories = ref([
  {
    "id": 3,
    "categoryName": "美食",
    "categoryAlias": "meishi",
    "createTime": "2023-09-02 12:06:59",
    "updateTime": "2023-09-02 12:06:59"
  },
  {
    "id": 4,
    "categoryName": "娱乐",
    "categoryAlias": "yule",
    "createTime": "2023-09-02 12:08:16",
    "updateTime": "2023-09-02 12:08:16"
  },
  {
    "id": 5,
    "categoryName": "军事",
    "categoryAlias": "junshi",
    "createTime": "2023-09-02 12:08:33",
    "updateTime": "2023-09-02 12:08:33"
  }
])

// 获取文章分类列表函数
const articleCategoryList = async () => {
  let result = await articleCategoryService();
  categories.value = result.data;
}
articleCategoryList();

// 控制添加分类弹窗，默认不显示
const dialogVisible = ref(false)
// 添加分类数据模型
const categoryModel = ref({
  categoryName: '',
  categoryAlias: ''
})
// 添加分类表单校验
const rules = {
  categoryName: [
    {required: true, message: '请输入分类名称', trigger: 'blur'},
  ],
  categoryAlias: [
    {required: true, message: '请输入分类别名', trigger: 'blur'},
  ]
}
// 添加文章分类函数
const addCategory = async () => {
  let result = await articleCategoryAddService(categoryModel.value);
  ElMessage.success("添加文章分类成功");
  // 再次访问后端接口，查询所有分类
  await articleCategoryList();
  // 隐藏添加文章分类弹窗
  dialogVisible.value = false;
}

// 修改文章分类弹窗标题
const title = ref('');
// 修改文章分类回显函数
const updateCategoryEcho = (row) => {
  title.value = "修改文章分类";
  dialogVisible.value = true;
  // 将 row 中的数据赋值给 CategoryModel
  categoryModel.value.categoryName = row.categoryName;
  categoryModel.value.categoryAlias = row.categoryAlias;
  // 修改的时候需要传递文章分类 id，扩展一个文章分类 id 属性
  categoryModel.value.id = row.id;
}
// 修改文章分类函数
const updateCategory = async () => {
  let result = await articleCategoryUpdateService(categoryModel.value);
  ElMessage.success("修改文章分类成功");
  // 再次访问后端接口，查询所有分类
  await articleCategoryList();
  // 隐藏添加文章分类弹窗
  dialogVisible.value = false;
}

// 清空模型数据
const clearCategoryModel = () => {
  categoryModel.value.categoryName = '';
  categoryModel.value.categoryAlias = '';
}

// 删除文章分类函数与弹窗
const deleteCategory = (row) => {
  ElMessageBox.confirm(
      "你确认删除该文章分类吗？",
      "温馨提示",
      {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning"
      }
  )
      .then(async () => { // 用户点击了确认
        let result = await articleCategoryDeleteService(row.id);
        ElMessage.success("删除文章分类成功");
        // 刷新文章分类列表
        await articleCategoryList();
      })
      .catch(() => { //用户点击了取消
        ElMessage({
          type: "info",
          message: "取消文章分类删除"
        })
      })
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章分类</span>
        <div class="extra">
          <el-button type="primary" @click="dialogVisible = true; title = '添加文章分类'; clearCategoryModel()">
            添加文章分类
          </el-button>
        </div>
      </div>
    </template>
    <el-table :data="categories" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="分类名称" prop="categoryName"></el-table-column>
      <el-table-column label="分类别名" prop="categoryAlias"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary"
                     @click="title='修改文章分类'; dialogVisible = true; updateCategoryEcho(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据"/>
      </template>
    </el-table>

    <!-- 添加文章分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="分类别名" prop="categoryAlias">
          <el-input v-model="categoryModel.categoryAlias" minlength="1" maxlength="15"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary"
                       @click="title === '添加文章分类' ? addCategory() : updateCategory()">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>