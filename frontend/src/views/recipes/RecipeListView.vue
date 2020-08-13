<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://livwanillustration.com/portfolio/recipe-illustrations/french-food-illustrations.jpg') ;"
      ></parallax>
    </div>
    <div class="subscribe-line subscribe-line-white">
      <div class="container">
        <div class="row">
          <div class="col-md-2 ml-auto" @click="changeShow">
            <n-button type="primary" round block>레시피 검색</n-button>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row"></div>
      <CategorySelector
        v-show="categoryShow"
        @searchRecipe="categorySubmit"
        @removeSelect="removeSelect"
      />

      <div v-if="recipes.length > 0" class="row">
        <div
          v-for="recipe in recipes"
          :key="recipe.recipeId"
          class="col-12 col-lg-4 col-md-6 px-5 my-3"
        >
          <RecipeListItem :recipe="recipe" />
        </div>
      </div>

      <div v-else class="mt-4">
        <h2>선택하신 조건에 맞는 레시피가 없습니다.</h2>
      </div>

      <div class="col-md-2 ml-auto">
            <router-link to="/recipes/make">
              <n-button type="primary" round block>레시피 작성</n-button>
            </router-link>
          </div>

      <PageButton
        v-if="recipes.length > 0"
        class="d-flex justify-content-center"
        :curPage="curPage"
        :maxPage="maxPage"
        @move-page="movePage"
      />
    </div>
  </div>
</template>

<script>
import CategorySelector from "../../components/recipes/CategorySelector.vue";
import RecipeListItem from "../../components/recipes/RecipeListItem.vue";
import PageButton from "../../components/common/PageButtons.vue";
import { Button } from "@/components/global";

import axios from "axios";
import SERVER from "@/api/api";

import { mapGetters } from "vuex";

export default {
  name: "RecipeListView",
  data() {
    return {
      categoryShow: false,
      recipes: [],
      curPage: this.$route.params.pageNum * 1,
      maxPage: 10,
      searchData: {
        selectedCategory: [],
        selectedIngredients: [],
      },
    };
  },
  components: {
    CategorySelector,
    RecipeListItem,
    PageButton,
    [Button.name]: Button,
  },
  methods: {
    changeShow() {
      this.categoryShow = !this.categoryShow;
    },
    categorySubmit() {
      if (
        this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length !=
        0
      ) {
        if (this.$route.params.pageNum != 1) {
          this.$router.push({ params: { pageNum: 1 } });
        } else {
          this.changePage(1);
        }
      } else {
        alert("검색 항목을 입력해주세요!");
      }
    },
    removeSelect() {
      if (this.$route.params.pageNum != 1) {
        this.$router.push({ params: { pageNum: 1 } });
      } else {
        this.changePage(1);
      }
    },
    movePage(page) {
      console.log(page);
      if (page == "«") {
        this.$router.push({ params: { pageNum: 1 } });
      } else if (page == "»") {
        this.$router.push({ params: { pageNum: this.maxPage } });
      } else if (page != this.curPage) {
        this.$router.push({ params: { pageNum: parseInt(page) } });
      }
      scroll(0, 0);
    },
    changePage(page) {
      if (
        this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length !=
        0
      ) {
        this.searchRecipe(page);
      } else {
        this.allRecipe(page);
      }
    },
    allRecipe(page) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.recipeList + (page - 1))
        .then((res) => {
          this.recipes = res.data.list;
          this.maxPage = parseInt((res.data.total - 1) / 6) + 1;
          this.categoryShow = false;
        });
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
          this.recipes = res.data.list;
          this.maxPage = parseInt((res.data.total - 1) / 6) + 1;
          this.categoryShow = false;
        })
        .catch((err) => console.log(err));
    },
  },
  created() {
    this.curPage = this.$route.params.pageNum * 1;
    this.changePage(this.curPage);
  },
  watch: {
    $route() {
      this.curPage = parseInt(this.$route.params.pageNum);
      this.changePage(this.curPage);
    },
  },
  computed: {
    ...mapGetters(["searchingData"]),
  },
};
</script>

<style>
</style>