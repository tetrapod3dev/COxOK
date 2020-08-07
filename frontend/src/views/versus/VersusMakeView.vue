<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://cdn.pixabay.com/photo/2017/07/28/13/29/spices-2548653_960_720.jpg') ;"
      ></parallax>

      <div class="content-center">
        <h1 class="title">요리대전</h1>
      </div>
    </div>

    <div class="section make-versus">
      <div class="container">
        <div class="button-container">
          <button class="btn btn-primary btn-round btn-lg" @click="submitVersus">등록</button>
          <button class="btn btn-danger btn-round btn-lg" @click="goBackPage">취소</button>
        </div>
      </div>
    </div>
    <div class="container">
      <p class="selected-recipe-list-title">선택한 레시피 목록</p>
      <div class="selected-recipe-list row">
        <div class="ml-auto mr-auto align-self-center">
          <b-button variant="primary" class="btn-circle" pill @click="movePrev">
            <i class="now-ui-icons arrows-1_minimal-left"></i>
          </b-button>
        </div>

        <!-- <div class="col-10 row p-3">
          <div v-for="(recipe, index) in curRecipes" :key="index" class="card col-3 m-0">
            <img :src="imageSrc(recipe.recipeThumbnailSrc)" class="card-img-top" alt="레시피 사진" />
            <div class="card-body">
              <h5 class="card-title">{{ recipe.recipeName }}</h5>
              <p class="card-text">{{ recipe.recipeDetail}}</p>
              <button @click="removeSelectedRecipe(index)">x</button>
            </div>
          </div>
        </div>-->
        <div v-for="(recipe, index) in curRecipes" :key="index" class="col-md-4 col-lg-2">
          <card
            type="pricing"
            class="card-background selected-recipe-card"
            :style="'background-image: url('+ imageSrc(recipe.recipeThumbnailSrc) +')'"
          >
            <a class="selected-recipe-remove" @click="removeSelectedRecipe(index)">
              <i class="now-ui-icons ui-1_simple-remove"></i>
            </a>
            <h6 class="card-title">{{ recipe.recipeName }}</h6>
            <p class="card-description">{{ recipe.recipeDetail }}</p>
          </card>
        </div>

        <div class="ml-auto mr-auto align-self-center">
          <b-button variant="primary" class="btn-circle" pill @click="moveNext">
            <i class="now-ui-icons arrows-1_minimal-right"></i>
          </b-button>
        </div>
      </div>
    </div>
    <hr />

    <div class="container">
      <tabs
        class="row"
        pills
        type="primary"
        icons
        tab-nav-wrapper-classes="col-md-2"
        tab-content-classes="col-md-10"
        vertical
      >
        <tab-pane label="Profile">
          <template slot="label">
            <i class="now-ui-icons sport_trophy"></i> 요리 대전
          </template>

          <div class="container">
            <div>
              <label for="title">대회 이름</label>
              <b-form-input type="text" id="title" v-model="versusTitle" />
            </div>
            <br />
            <br />
            <div>
              <label for="content">대회 개요</label>
              <b-form-textarea rows="6" type="text" id="content" v-model="versusContent" />
            </div>
          </div>
          <div style="height:300px"></div>
        </tab-pane>
        <tab-pane label="Settings">
          <template slot="label">
            <i class="now-ui-icons sport_user-run"></i> 선수 선발
          </template>
          <CategorySelector @searchRecipe="categorySubmit" />

          <div v-for="recipe in recipes" :key="recipe.id" class="row">
            <img :src="imageSrc(recipe.recipeThumbnailSrc)" class="col-4" />
            <div class="col-7">
              <h3 class="row">{{ recipe.recipeName }} ({{ recipe.recipeId }})</h3>
              <p class="row">{{ recipe.recipeDetail }}</p>
            </div>
            <input
              type="checkbox"
              v-model="checker[recipe.recipeId]"
              class="col-1"
              @click="checkRecipe(recipe)"
            />
          </div>

          <div id="bottomSensor"></div>
        </tab-pane>
      </tabs>
    </div>
  </div>
</template>

<script>
import CategorySelector from "@/components/recipes/CategorySelector";

import { Tabs, TabPane, Card } from "@/components/global";

import { mapGetters, mapActions } from "vuex";
import SERVER from "@/api/api";

import axios from "axios";
import scrollmonitor from "scrollmonitor";

export default {
  name: "VersusMakeView",
  data() {
    return {
      versusTitle: null,
      versusContent: null,
      selectedRecipes: [],
      recipes: [],
      selectedCurPage: null,
      curPage: 1,
      maxPage: 10,
      searchData: {
        selectedCategory: [],
        selectedIngredients: [],
      },
    };
  },
  components: {
    CategorySelector,
    Tabs,
    Card,
    TabPane,
  },
  computed: {
    ...mapGetters(["config", "searchingData"]),
    selectedRecipesId() {
      return this.selectedRecipes.map((recipe) => recipe.recipeId);
    },
    selectedMaxPage() {
      return parseInt((this.selectedRecipes.length - 1) / 4);
    },
    curRecipes() {
      return this.selectedRecipes.slice(
        4 * this.selectedCurPage,
        4 * (this.selectedCurPage + 1)
      );
    },
    checker() {
      let tempChecker = {};
      const self = this;

      this.recipes.forEach(function (recipe) {
        if (self.selectedRecipesId.indexOf(recipe.recipeId) >= 0) {
          tempChecker[recipe.recipeId] = true;
        } else {
          tempChecker[recipe.recipeId] = false;
        }
      });

      return tempChecker;
    },
  },
  methods: {
    ...mapActions(["logout"]),
    removeSelectedRecipe(index) {
      this.selectedRecipes.splice(this.selectedCurPage * 4 + index, 1);
    },
    imageSrc(image) {
      return SERVER.URL + "/img/" + image;
    },
    categorySubmit() {
      this.recipes = [];
      this.curPage = 1;
      if (
        this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length !=
        0
      ) {
        this.searchRecipe(this.curPage++);
      } else {
        this.allRecipe(this.curPage++);
      }
    },
    allRecipe(page) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.recipeList + (page - 1))
        .then((res) => {
          this.recipes = [...this.recipes, ...res.data.list];
          this.maxPage = parseInt((res.data.total - 1) / 6);
        })
        .catch((err) => console.log(err));
    },
    searchRecipe(page) {
      let frm = new FormData();

      // categories 배열에는 선택된 카테고리의 ID가 String으로 들어갑니다.
      this.searchingData.selectedCategory.forEach(function (selectedCategory) {
        frm.append("selectedCategory", selectedCategory);
      });
      this.searchingData.selectedIngredients.forEach(function (
        selectedIngredient
      ) {
        frm.append("selectedIngredients", selectedIngredient);
      });

      // recipe/search/{{page}} 라는 주소로 selectedCategory(선택된 카테고리의 id들) / selectedIngredients(선택된 재료들의 id)를 전달합니다.
      axios
        .post(SERVER.URL + SERVER.ROUTES.searchRecipe + (page - 1), frm)
        .then((res) => {
          this.recipes = [...this.recipes, ...res.data.list];
          this.maxPage = parseInt((res.data.total - 1) / 6);
        })
        .catch((err) => console.log(err));
    },
    checkRecipe(recipe) {
      if (this.selectedRecipesId.indexOf(recipe.recipeId) == -1) {
        this.selectedRecipes.push(recipe);
      } else {
        this.selectedRecipes.splice(
          this.selectedRecipesId.indexOf(recipe.recipeId),
          1
        );
      }
    },
    movePrev() {
      if (this.selectedCurPage != 0) {
        this.selectedCurPage -= 1;
      } else {
        this.selectedCurPage = this.selectedMaxPage;
      }
    },
    moveNext() {
      if (this.selectedCurPage < this.selectedMaxPage) {
        this.selectedCurPage += 1;
      } else {
        this.selectedCurPage = 0;
      }
    },
    addScrollWatcher() {
      const bottomSensor = document.querySelector("#bottomSensor");
      const watcher = scrollmonitor.create(bottomSensor);
      if (this.curPage < this.maxPage) {
        watcher.enterViewport(() => {
          setTimeout(() => {
            this.getRecipes();
          }, 1000);
        });
      }
    },
    loadUntilViewportIsFull() {
      const bottomSensor = document.querySelector("#bottomSensor");
      const watcher = scrollmonitor.create(bottomSensor);
      if (watcher.isFullyInViewport) {
        this.getRecipes();
      }
    },
    getRecipes() {
      if (
        this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length !=
        0
      ) {
        this.searchRecipe(this.curPage++);
      } else {
        this.allRecipe(this.curPage++);
      }
    },
    scrollToTop() {
      scroll(0, 0);
    },
    submitVersus() {
      let versusData = {
        title: this.versusTitle,
        content: this.versusContent,
        recipeIdList: [
          103,
          92,
          86,
          85,
          84,
          82,
          81,
          80,
          79,
          78,
          77,
          76,
          75,
          74,
          73,
          71,
        ],
      };

      axios
        .post(SERVER.URL + SERVER.ROUTES.versusRegister, versusData, {
          headers: {
            Authorization: this.config,
          },
        })
        .then((res) => {
          console.log(res);
          this.$router.push({ name: "VersusHomeView" });
        })
        .catch((err) => {
          if (err.response.status) {
            alert("세션 정보가 만료되었습니다! 다시 로그인해주세요.");
            this.logout();
          }
        });
    },
    goBackPage() {
      this.$router.go(-1);
    },
  },
  created() {
    this.getRecipes();
  },
  mounted() {
    this.addScrollWatcher();
  },
  updated() {
    if (this.curPage < this.maxPage) {
      this.loadUntilViewportIsFull();
    }
  },
};
</script>

<style scoped>
.make-versus .button-container {
  margin-top: -112px;
}
.selected-recipe-list {
  height: 240px;
}
.selected-recipe-list .selected-recipe-remove i {
  color: #aaaaaa;
}
.selected-recipe-list .selected-recipe-remove:hover i {
  color: #ffffff;
}
.selected-recipe-list .selected-recipe-remove {
  position: relative;
  top: -10px;
  right: -50px;
  cursor: pointer;
}
.btn-circle {
  width: 30px;
  height: 30px;
  padding: 6px 0px;
  border-radius: 15px;
  text-align: center;
  font-size: 12px;
  line-height: 1.42857;
}
.selected-recipe-card {
  width: 8rem;
  height: 12rem;
}
</style>