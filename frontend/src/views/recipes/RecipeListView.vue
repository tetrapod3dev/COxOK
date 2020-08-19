<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        :style="{ backgroundImage: 'url(http://i3a104.p.ssafy.io/header/cook.jpg)' }"
      ></parallax>
      <div class="container">
        <h1 class="title">요리하기</h1>
      </div>
    </div>

    
    <div class="section meet-button pb-0" >
      <div class="container">
        <div class="button-container">
          <button class="learn-more" @click="changeShow"><i class="fas fa-search"></i><p>검색</p></button> 
          <button v-if="isLoggedIn" class="learn-more" @click="moveMake"><i class="far fa-edit"></i> <p>작성</p></button>
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
    };
  },
  components: {
    CategorySelector,
    RecipeListItem,
    PageButton,
    [Button.name]: Button,
  },
  computed: {
    ...mapGetters(["searchingData", "isLoggedIn"]),
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
  methods: {
    changeShow() {
      this.categoryShow = !this.categoryShow;
    },
    moveMake() {
      this.$router.push({name: 'RecipeMakeView'})
    },
    categorySubmit() {
      if (
        (this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length ==
        0) && (this.searchingData.level == 5) && (this.searchingData.cookTime == 120)
      ) {
        alert("검색 항목을 입력해주세요!");
      } else {
        if (this.$route.params.pageNum != 1) {
          this.$router.push({ params: { pageNum: 1 } });
        } else {
          this.changePage(1);
        }
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
        (this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length ==
        0) && (this.searchingData.level == 5) && (this.searchingData.cookTime == 120)
      ) {
        this.allRecipe(page);
      } else {
        this.searchRecipe(page);
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

      frm.append("level", this.searchingData.level)

      frm.append("cookTime", this.searchingData.cookTime)

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
};
</script>

<style scoped>
button {
  position: relative;
  display: inline-block;
  cursor: pointer;
  outline: none;
  border: 0;
  vertical-align: middle;
  text-decoration: none;
}
button.learn-more {
  font-weight: 600;
  height: 60px;
  color: #382b22;
  text-transform: uppercase;
  padding: 0.3em 1.5em;
  background: #f2efe4;
  border: 2px solid #b69f81;
  border-left: 0;
  border-radius: 0;
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
  -webkit-transition: background 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: background 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    background 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    background 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more:first-child {
  border-top-left-radius: 0.75em;
  border-bottom-left-radius: 0.75em;
  padding-left: 2em;
  border-left: 2px solid #b69f81;
}
button.learn-more:last-child {
  border-top-right-radius: 0.75em;
  border-bottom-right-radius: 0.75em;
  padding-right: 2em;
}
button.learn-more::before {
  position: absolute;
  content: "";
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #f2d4ae;
  border-radius: inherit;
  box-shadow: 0 0 0 2px #b69f81, 0 0.625em 0 0 #f2f0ce;
  -webkit-transform: translate3d(0, 0.75em, -1em);
  transform: translate3d(0, 0.75em, -1em);
  -webkit-transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    box-shadow 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more:hover {
  background: #f2efe4;
  -webkit-transform: translate(0, 0.25em);
  transform: translate(0, 0.25em);
}
button.learn-more:hover::before {
  box-shadow: 0 0 0 2px #b69f81, 0 0.5em 0 0 #f2f0ce;
  -webkit-transform: translate3d(0, 0.5em, -1em);
  transform: translate3d(0, 0.5em, -1em);
}
button.learn-more:active {
  background: #f2efe4;
  -webkit-transform: translate(0em, 0.75em);
  transform: translate(0em, 0.75em);
}
button.learn-more:active::before {
  box-shadow: 0 0 0 2px #b69f81, 0 0 #f2f0ce;
  -webkit-transform: translate3d(0, 0, -1em);
  transform: translate3d(0, 0, -1em);
}

.meet-button .button-container {
  margin-top: -112px;
}
</style>