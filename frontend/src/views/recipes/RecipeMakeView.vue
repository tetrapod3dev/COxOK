<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.pexels.com/photos/406152/pexels-photo-406152.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260') ;"
      ></parallax>
      <div class="content-center">
        <h1>레시피 상세 정보 입력</h1>
      </div>
    </div>

    <div class="section section-about-us">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto text-center">
            <div class="row">
              <input
                ref="thumbnailInput"
                type="file"
                name="photo"
                id="recipe_thumbnail"
                hidden
                @change="onChangeThumbnail"
              />
              <div class="row">
                <div class="col-4">
                  <img id="recipe-preview" :src="recipePreview" class="w-100" />
                  <a @click="onClickThumbnailUpload" class="w-100">
                    <n-button type="primary" round block>업로드</n-button>
                  </a>
                </div>

                <div class="col-8">
                  <div class="row">
                    <p>레시피 제목</p>
                    <input class="col-8" type="text" v-model="recipeName" />
                  </div>
                  <div class="row">
                    <p>레시피 설명</p>
                    <input class="col-8" type="text" v-model="recipeDetail" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <hr class="my-5" />

        <div class="row">
          <div class="col-6">
            <h4>난이도</h4>
            <b-form-rating v-model="level" inline></b-form-rating>
          </div>

          <div class="col-6">
            <h4>소요 시간</h4>
            <input type="number" id="cookTime" v-model="cookTime" /> 분
          </div>
        </div>

        <hr class="my-5" />

        <div>
          <h3 class="text-left">카테고리 선택</h3>
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
                  @click="selectCategory(category.foodCategoryId)"
                />
                {{ category['foodCategoryName']}}
              </p>
            </div>
          </div>
        </div>

        <hr class="my-5" />

        <div>
          <h3 class="text-left">재료 선택</h3>
          <div class="row" v-for="(selectedIngredient, index) in selectedIngredients" :key="index">
            <b-form-input
              :list="getIngredientDatalistId(index)"
              :id="getIngredientInputId(index)"
              v-model="selectedIngredient.ingredient"
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

          <div class="row">
            <n-button type="secondary" @click="addIngredient">
              <i class="now-ui-icons ui-1_simple-add"></i>
            </n-button>
          </div>
        </div>

        <hr class="my-5" />

        <h2>상세 과정 입력</h2>

        <div v-show="tempInputs.length > 0" class="upload-image">
          <draggable v-model="tempInputs" group="recipeDetail" @start="drag=true" @end="drag=false">
            <div v-for="(tempInput, index) in tempInputs" :key="index" class="row my-3">
              <img :src="tempInput.imageSrc" class="col-4 offset-1">
              <input type="text" v-model="tempInput.content" class="col-5">
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

        <button class="btn btn-danger" @click="preTest">제출</button>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import SERVER from "@/api/api";

import { mapGetters } from "vuex";

import axios from "axios";
import draggable from 'vuedraggable'
import { Button, FormGroupInput as FgInput } from "@/components/global";

export default {
  name: "RecipeMakeView",
  data() {
    return {
      recipeName: null,
      recipeDetail: null,
      recipePreview: null,
      level: 0,
      cookTime: null,
      categories: [],
      ingredients: {},
      ingredientsName: [],
      selectedCategories: [],
      selectedIngredients: [{ ingredient: null, amount: null, unit: null }],
      filename: '',
      addingFiles: [],
      fileList: [],
      tempInputs: [],
    };
  },
  components: {
    FgInput,
    [Button.name]: Button,
    draggable,
  },
  methods: {
    // 썸네일 변경 및 미리보기 관련 함수
    onClickThumbnailUpload() {
      this.$refs.thumbnailInput.click();
    },
    onChangeThumbnail() {
      const file = event.target.files[0];
      this.recipePreview = URL.createObjectURL(file);

      const previewImage = document.querySelectorAll("#recipe-preview");
      previewImage.src = this.recipePreview;
    },

    // 카테고리 선택 관련 함수
    selectCategory(id) {
      if (this.selectedCategories.indexOf(id) >= 0) {
        this.selectedCategories.splice(this.selectedCategories.indexOf(id), 1);
      } else {
        this.selectedCategories.push(id);
      }
    },

    // 재료 선택 관련 함수
    test(ingredient) {
      if (this.ingredientsName.indexOf(ingredient.ingredient) == -1) {
        ingredient.unit = null;
      } else {
        ingredient.unit = this.ingredients[ingredient.ingredient][1];
      }
    },
    addIngredient() {
      let tempfile = { ingredient: null, amount: null, unit: null };
      this.selectedIngredients.push(tempfile);
    },
    removeIngredient(index) {
      this.selectedIngredients.splice(index, 1);
    },

    // 레시피 상세 과정 관련 함수
    onDrop (event) {
      this.inputImageFile(event.dataTransfer.files)
    },
    onClickFile() {
      this.$refs.fileInput.click()
    },
    onFileChange(event) {
      this.inputImageFile(event.target.files)
    },
    inputImageFile (addingfiles) {
      this.addingFiles = addingfiles
      this.fileList = [...this.fileList, ...addingfiles]
      this.onClickUpload()
    },
    onClickUpload () {
      const self = this
      this.addingFiles.forEach(function(file) {
        self.tempInputs.push(
          {'imageSrc': URL.createObjectURL(file), 'content': null, 'rawFile': file}
          )
      })
    },
    removeRecipeDetail(index) {
      this.tempInputs.splice(index, 1);
    },

    // 제출 전 유효성 확인 함수
    preTest() {
      let problems = [];
      let photoFile = document.getElementById("recipe_thumbnail");
      if (photoFile.files.length == 0) {
        problems.push("레시피 썸네일");
      }
      if (this.recipeName == null) {
        problems.push("레시피 제목");
      }
      if (this.recipeDetail == null) {
        problems.push("레시피 설명");
      }
      if (this.level == 0) {
        problems.push("요리 난이도");
      }
      if (this.cookTime == null || this.cookTime < 0) {
        problems.push("요리 소요 시간");
      }
      if (this.selectedCategories.length == 0) {
        problems.push("카테고리");
      }
      this.selectedIngredients.forEach(function (selectedIngredient, index) {
        if (selectedIngredient.unit == null) {
          problems.push(index + 1 + "번째 재료");
        } else if (
          selectedIngredient.amount == null ||
          selectedIngredient.amount <= 0
        ) {
          problems.push(index + 1 + "번째 재료의 양");
        }
      });

      this.tempInputs.forEach(function (tempInput, index) {
        if (tempInput.rawFile == null) {
          problems.push(index + 1 + "번째 상세 과정의 사진");
        }
        if (tempInput.content == null) {
          problems.push(index + 1 + "번째 상세 과정의 설명");
        }
      });

      if (problems.length == 0) {
        this.makeRecipe();
      } else {
        alert("문제가 있는 위치: " + problems);
      }
    },
    makeRecipe() {
      // formData를 이용해서 여러 데이터와 파일을 string / blob 형식으로 보내줍니다.
      let frm = new FormData();
      let photoFile = document.getElementById("recipe_thumbnail");
      frm.append("recipeThumbnail", photoFile.files[0]);
      frm.append("recipeName", this.recipeName);
      frm.append("recipeDetail", this.recipeDetail);

      // 요리 난이도, 요리 소요 시간도 String으로 보내줍니다.
      frm.append("level", this.level);
      frm.append("cookTime", this.cookTime);

      // categories 배열에는 선택된 카테고리의 ID가 String으로 들어갑니다. / 숫자로 넣는건 테스트를 해봐야됩니다.abnf
      // 아래 반복문을 이렇게 고쳐보세요 frm.append('categories', selectedCategoryId * 1);
      this.selectedCategories.forEach(function (selectedCategory) {
        frm.append("categories", selectedCategory);
      });

      const self = this;
      // 재료의 경우 ingredientPk, ingredientAmount 2개의 배열로 나뉘어서 들어갑니다.
      this.selectedIngredients.forEach(function (ingredient) {
        frm.append("ingredientPk", self.ingredients[ingredient.ingredient][0]);
        frm.append("ingredientAmount", ingredient.amount);
      });

      // 상세 정보의 경우에도 description 배열에 String으로 들어갑니다.
      // 사진의 경우 photo배열에 Blob 파일로 들어갑니다.

      this.tempInputs.forEach(function (tempInput) {
        frm.append("description", tempInput.content);
        frm.append("photo", tempInput.rawFile);
      });


      let configs = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: this.config,
        },
      };

      axios
        .post(SERVER.URL + SERVER.ROUTES.recipeRegister, frm, configs)
        .then(() => {
          router.push({ name: "RecipeListView", params: { pageNum: 1 } });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getIngredientInputId(index) {
      return "input-with-list" + index;
    },
    getIngredientDatalistId(index) {
      return "input-list" + index;
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
  },
  computed: {
    ...mapGetters(["config"]),
  },
  created() {
    this.getCategory()
  },
};
</script>

<style scoped>
.imgUpBtn {
  height: 50px;
}
</style>