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

          <div class="row" @click="addIngredient">
            <n-button type="secondary">
              <i class="now-ui-icons ui-1_simple-add"></i>
            </n-button>
          </div>
        </div>

        <hr class="my-5" />

        <div>
          <h2 class="text-left">상세 과정 입력창</h2>
          <div v-for="(tempInput, index) in tempInputs" :key="index" class="row">
            <div class="col-4">
              <input
                ref="imageInput"
                type="file"
                name="photo"
                class="photo"
                hidden
                @change="onChangeImages(index, $event)"
              />
              <img class="preview-image w-100" :src="tempInput.imageUrl" />
              <button
                type="button"
                class="imgUpBtn w-100"
                @click="onClickImageUpload(index)"
              >이미지 업로드</button>
            </div>

            <div class="col-7">
              <p class="text-left">내용</p>
              <input type="text" v-model="tempInput.description" class="w-100" />
            </div>

            <button class="btn btn-outline-secondary col-1" @click="removeRecipeDetail(index)">-</button>
          </div>

          <button class="btn btn-outline-secondary" @click="addOrder">+</button>
        </div>

        <button class="btn btn-danger" @click="preTest">제출</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";
import SERVER from "@/api/api";
import { mapGetters } from "vuex";

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
      tempInputs: [{ imageUrl: null, description: null }],
    };
  },
  components: {
    FgInput,
    [Button.name]: Button,
  },
  methods: {
    test(ingredient) {
      if (this.ingredientsName.indexOf(ingredient.ingredient) == -1) {
        ingredient.unit = null;
      } else {
        ingredient.unit = this.ingredients[ingredient.ingredient][1];
      }
    },
    onClickThumbnailUpload() {
      this.$refs.thumbnailInput.click();
    },
    onChangeThumbnail() {
      const file = event.target.files[0];
      this.recipePreview = URL.createObjectURL(file);

      const previewImage = document.querySelectorAll("#recipe-preview");
      previewImage.src = this.recipePreview;
    },
    onClickImageUpload(index) {
      this.$refs.imageInput[index].click();
    },
    onChangeImages(index, event) {
      const file = event.target.files[0]; // Get first index in files

      this.tempInputs[index].imageUrl = URL.createObjectURL(file); // Create File URL
      const previewImages = document.querySelectorAll(".preview-image");
      let self = this;
      previewImages.forEach(function (previewImage, index) {
        if (self.tempInputs[index].imageUrl == null) {
          self.tempInputs[index].imageUrl = null;
        } else {
          previewImage.src = self.tempInputs[index].imageUrl;
        }
      });
    },
    selectCategory(id) {
      if (this.selectedCategories.indexOf(id) >= 0) {
        this.selectedCategories.splice(this.selectedCategories.indexOf(id), 1);
      } else {
        this.selectedCategories.push(id);
      }
    },
    addIngredient() {
      let tempfile = { ingredient: null, amount: null, unit: null };
      this.selectedIngredients.push(tempfile);
    },
    removeIngredient(index) {
      this.selectedIngredients.splice(index, 1);
    },
    getIngredientInputId(index) {
      return "input-with-list" + index;
    },
    getIngredientDatalistId(index) {
      return "input-list" + index;
    },
    addOrder() {
      let tempfile = { imageURL: null, description: null };
      this.tempInputs.push(tempfile);
    },
    removeRecipeDetail(index) {
      this.tempInputs.splice(index, 1);
    },
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
        if (tempInput.imageUrl == null) {
          problems.push(index + 1 + "번째 상세 과정의 사진");
        }
        if (tempInput.description == null) {
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
      this.tempInputs.forEach(function (tempInput) {
        frm.append("description", tempInput.description);
      });

      // 사진의 경우 photo배열에 Blob 파일로 들어갑니다.
      const photoFiles = document.getElementsByClassName("photo");
      photoFiles.forEach(function (photoFile) {
        frm.append("photo", photoFile.files[0]);
      });

      let configs = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: this.config,
        },
      };

      axios
        .post(SERVER.URL + SERVER.ROUTES.recipeRegister, frm, configs)
        .then((res) => {
          console.log(res);
          router.push({ name: "RecipeListView", params: { pageNum: 1 } });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  computed: {
    ...mapGetters(["config"]),
  },
  created() {
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
};
</script>

<style scoped>
.imgUpBtn {
  height: 50px;
}
</style>