<template>
  <div class="container">
    <div class="row mt-5">
      <div class="col-4">
        <img v-if="recipe.recipeThumbnail == undefined" :src="imageSrc(recipe.recipeThumbnailSrc)">
        <img v-else :src="recipe.recipeThumbnailSrc">
        <input type="file" @change="changeThumbnail">
      </div>

      <div class="col-8">
        <div class="row">
          <p>레시피 제목</p>
          <input class="col-8" type="text" v-model="recipe.recipeName" />
        </div>
        <div class="row">
          <p>레시피 설명</p>
          <input class="col-8" type="text" v-model="recipe.recipeDetail" />
        </div>
      </div>
    </div>

    <hr>

    <h2>요리 정보 입력</h2>
    <div class="row">
      <div class="col-6">
        <h4>난이도</h4>
        <b-form-rating v-model="recipe.level" inline></b-form-rating>
      </div>

      <div class="col-6">
        <h4>소요 시간</h4>
        <input type="number" id="cookTime" v-model="recipe.cookTime" /> 분
      </div>
    </div>

    <hr>

    <h2 class="text-left">카테고리 선택</h2>
    <div class="row">
      <div
        v-for="category in categories"
        :key="category.foodCategoryId"
        class="col-2 d-flex justify-content-start"
      >
        <p>
          <input
            type="checkbox"
            class="mr-2"
            v-model="checker[category.foodCategoryId]"
            @click="selectCategory(category.foodCategoryId, category.foodCategoryName)"
          />
          {{ category['foodCategoryName']}}
        </p>
      </div>
    </div>

    <hr>

    <h2 class="text-left">재료 선택</h2>
    <div class="row" v-for="(selectedIngredient, index) in recipe.ingredientList" :key="index">
      <b-form-input
        :list="getIngredientDatalistId(index)"
        :id="getIngredientInputId(index)"
        v-model="selectedIngredient.name"
        @change="test(selectedIngredient)"
        class="col-5"
      />
      <b-form-datalist :id="getIngredientDatalistId(index)" :options="ingredientsName"></b-form-datalist>
      <div v-if="selectedIngredient.unit == null" class="col-2 offset-1">
        <i class="fas fa-exclamation-triangle fa-2x" style="color: red;"></i>
      </div>
      <div v-else class="col-3 m-0 row">
        <fg-input type="number" v-model="selectedIngredient.amount" class="col-6 offset-2" />
        <p class="col-2">{{ selectedIngredient.unit }}</p>
      </div>
      <a @click="removeIngredient(index)">
        <n-button type="primary" round icon>
          <i class="now-ui-icons ui-1_simple-delete"></i>
        </n-button>
      </a>
    </div>

    <div class="row" @click="addIngredient">
      <n-button type="secondary">
        <i class="now-ui-icons ui-1_simple-add"></i>
      </n-button>
    </div>

    <hr>

    
    <h2>상세 과정 입력</h2>

    <div class="upload-image">
      <draggable v-model="recipe.recipePhotoList" group="recipeDetail" @start="drag=true" @end="drag=false">
        <div v-for="(recipePhoto, index) in recipe.recipePhotoList" :key="index" class="row my-3">

          <div class="col-4 offset-1 row">
            <img v-if="recipePhoto.rawFile" :src="recipePhoto.photoSrc">
            <img v-else :src="imageSrc(recipePhoto.photoSrc)">
            <input type="file" @change="changeFile(index, $event)">
          </div>

          <input type="text" v-model="recipePhoto.photoDetail" class="col-5">
          <button class="btn btn-danger col-1" @click="removeRecipeDetail(index)">-</button>
        </div>
      </draggable>
    </div>

    <div class="input-group">
      <input
        type="text"
        class="form-control"
        placeholder="드래그 & 드랍하거나 인풋을 클릭하세용"
        v-model="filename"
        @dragover.prevent
        @drop.prevent="onDrop"
        multiple>
      <div class="input-group-append">
        <span class="input-group-text" @click="onClickFile">
          <i class="fa fa-paperclip" />
        </span>
      </div>
      <input hidden type=file class="file-input" ref="fileInput" multiple @change="onFileChange">
    </div>

    <button @click="submitUpdate">Test</button>

  </div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/api'
import {mapGetters} from 'vuex'
import { Button, FormGroupInput as FgInput } from "@/components/global";

import draggable from 'vuedraggable'

export default {
  name: 'RecipeUpdateView',
  data() {
    return {
      recipe: {
        foodCategoryId: [],
      },
      
      filename: '',
      addingFiles: [],
      fileList: [],
      tempInputs: [],

      tempFilesSrc: [],
      categories: {},
      ingredients: {},
      recipeThumbnail: null,
      recipePhotos: null,
      ingredientsName: [],
    }
  },
  computed: {
    ...mapGetters(['config']),
    photoSrcs() {
      return this.recipe.recipePhotoList.map(recipePhoto => recipePhoto.photoSrc)
    },
    photoDetails() {
      return this.recipe.recipePhotoList.map(recipePhoto => recipePhoto.photoDetail)
    },
    ingredientPk() {
      return this.recipe.ingredientList.map(ingredient => ingredient.ingredientId)
    },
    ingredientAmount() {
      return this.recipe.ingredientList.map(ingredient => ingredient.amount)
    },
    checker() {
      let tempChecker = {};
      const self = this;

      this.categories.forEach(function (category) {
        if (self.recipe.foodCategoryId.indexOf(category.foodCategoryId) >= 0) {
          tempChecker[category.foodCategoryId] = true;
        } else {
          tempChecker[category.foodCategoryId] = false;
        }
      });

      return tempChecker;
    },
  },
  components: {
    FgInput,
    [Button.name]: Button,
    draggable,
  },
  created() {
    axios
      .get(
        SERVER.URL +
          SERVER.ROUTES.updateInfo +
          this.$route.params.recipe_id,
          {
            headers: {
              Authorization: this.config,
            },
          }
        )
      .then((res) => {
        this.recipe = res.data.recipe
      })
      .catch((err) => console.log(err.response));
      
    this.getCategory()
  },
  methods: {
    changeThumbnail(event) {
      const file = event.target.files[0]
      this.recipe.recipeThumbnailSrc = URL.createObjectURL(file)
      this.recipe.recipeThumbnail = file
    },
    changeFile(index, event) {
      const file = event.target.files[0]
      this.recipe.recipePhotoList[index].photoSrc = URL.createObjectURL(file)
      this.recipe.recipePhotoList[index].rawFile = file
    },
    getCategory() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.goRegister)
        .then((res) => {
          this.categories = res.data.catogories;
          const self = this;
          res.data.ingredients.forEach(function (ingredient) {
            self.ingredients[ingredient.name] = [
              ingredient.ingredientId,
              ingredient.unit,
            ];
          });
          this.ingredientsName = Object.keys(this.ingredients);
        })
        .catch((err) => console.log(err));
    },

    imageSrc(fileSrc) {
      return SERVER.IMAGE_URL + fileSrc
    },

    selectCategory(id, name) {
      if (this.recipe.foodCategoryId.indexOf(id) >= 0) {
        this.recipe.foodCategoryId.splice(this.recipe.foodCategoryId.indexOf(id), 1);
        this.recipe.foodCategoryName.splice(this.recipe.foodCategoryName.indexOf(name), 1);
      } else {
        this.recipe.foodCategoryId.push(id);
        this.recipe.foodCategoryName.push(name);
      }
    },

    
    getIngredientInputId(index) {
      return "input-with-list" + index;
    },
    getIngredientDatalistId(index) {
      return "input-list" + index;
    },
    addIngredient() {
      this.recipe.ingredientList.push({'name': null, 'amount': null, 'unit': null, 'ingredientId': null})
    },
    
    test(ingredient) {
      if (this.ingredientsName.indexOf(ingredient.name) == -1) {
        ingredient.unit = null;
        ingredient.ingredientId = null;
      } else {
        ingredient.unit = this.ingredients[ingredient.name][1];
        ingredient.ingredientId = this.ingredients[ingredient.name][0];
      }
    },
    // 레시피 상세 과정 관련 함수
    onDrop (event) {
      this.inputImageFile(event.dataTransfer.files)
    },
    onClickFile() {
      this.$refs.fileInput.click()
    },
    onFileChange(event) {
      this.onClickUpload(event.target.files)
    },
    onClickUpload (addingfiles) {
      this.addingFiles = addingfiles

      const self = this
      this.addingFiles.forEach(function(file) {
        self.recipe.recipePhotoList.push(
          {'photoSrc': URL.createObjectURL(file), 'photoDetail': null, 'rawFile': file}
          )
      })
    },
    removeRecipeDetail(index) {
      this.recipe.recipePhotoList.splice(index, 1);
    },
    TestTumbnail: async function() {
      let frm = new FormData();
      const self = this

      if (this.recipe.recipeThumbnail != undefined) {
          frm.append("photo", this.recipe.recipeThumbnail);
        // formData를 API에 전달해서 src 주소를 받습니다..
        // 그리고 그걸 recipe에 저장합니다.
        await axios
          .post(SERVER.URL + SERVER.ROUTES.photoRegister, frm, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((res) => {
            self.recipeThumbnail = res.data.photo[0]
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    TestBtn: async function() {
      // 새로운 사진을 등록한 경우 해당 파일들을 formData에 push합니다.
      let frm = new FormData();
      let testNumber = 0;
      this.recipe.recipePhotoList.forEach(function(recipePhoto) {
        if (recipePhoto.rawFile != undefined) {
          frm.append("photo", recipePhoto.rawFile);
          testNumber++;
        }
      })

      // formData를 API에 전달해서 src 주소를 받습니다..
      // 그리고 그걸 recipe에 저장합니다.
      if (testNumber > 0) {
        const self = this
        await axios
          .post(SERVER.URL + SERVER.ROUTES.photoRegister, frm, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((res) => {
            let tempSrcs = res.data.photo
            self.recipe.recipePhotoList.forEach(function(recipePhoto) {
              if (recipePhoto.rawFile != undefined) {
                recipePhoto.photoSrc = tempSrcs.shift()
              }
            })
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    submitUpdate: async function() {
      await this.TestBtn()
      await this.TestTumbnail()

      const recipeThumbnail = (this.recipeThumbnail != null) ? this.recipeThumbnail : this.recipe.recipeThumbnailSrc
      const recipePhotos = this.photoSrcs
      let body = {
        userId: parseInt(this.recipe.userId),
        recipeId: parseInt(this.$route.params.recipe_id),
        recipeName: this.recipe.recipeName,
        recipeDetail: this.recipe.recipeDetail,
        recipeThumbnail: recipeThumbnail,
        categories: this.recipe.foodCategoryId,
        ingredientPk: this.ingredientPk,
        ingredientAmount: this.ingredientAmount,
        description: this.photoDetails,
        photo: recipePhotos,
        level: this.recipe.level,
        cookTime: this.recipe.cookTime,
      }
      axios.post(SERVER.URL + SERVER.ROUTES.recipeUpdate, body, 
        {
          headers: {
            Authorization: this.config,
          },
        })
        .then(res => {
          console.log(res)
          this.$router.push({ name: "RecipeDetailView", params: { recipe_id: this.$route.params.recipe_id } });
        })
        .catch(err => console.log(err))
    },
  }
}
</script>

<style>

</style>