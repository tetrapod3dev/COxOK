<template>
  <div class="container">
    <h2>요리대전 만들기 페이지입니다.</h2>

    <div>
			<label for="title">요리대전 제목</label>
			<input type="text" id="title" v-model="versusTitle">
    </div>

    <div>
			<label for="content">요리대전 설명</label>
			<input type="text" id="content" v-model="versusContent">
    </div>
    
    <hr>

    <div class="row">
      <button class="col-1 btn btn-outline-primary" @click="movePrev">P</button>

      <div class="col-10 row">
        <div v-for="recipe in curRecipes" :key="recipe.recipeId" class="card col-3">
          <img :src="recipe.recipeThumbnailSrc" class="card-img-top" alt="레시피 사진">
          <div class="card-body">
            <h5 class="card-title">{{ recipe.recipeName }}</h5>
            <p class="card-text">{{ recipe.recipeDetail}}</p>
          </div>
        </div>
      </div>

      <button class="col-1 btn btn-outline-primary" @click="moveNext">N</button>
    </div>

    <hr>


    <CategorySelector 
      @searchRecipe="categorySubmit" />

    <div v-for="recipe in recipes" :key="recipe.id" class="row">
      <img :src="recipe.recipeThumbnailSrc" class="col-4">
      <div class="col-7">
        <h3 class="row">{{ recipe.recipeName }} ({{ recipe.recipeId }})</h3>
        <p class="row">{{ recipe.recipeDetail }}</p>
      </div>
      <input v-if="isSelected(recipe.recipeId)" type="checkbox" checked class="col-1" @click="checkRecipe(recipe)">
      <input v-else type="checkbox" class="col-1" @click="checkRecipe(recipe)">
    </div>

    
    <div id="bottomSensor"></div>

  </div>
</template>

<script>
import CategorySelector from '@/components/recipes/CategorySelector'

import { mapGetters } from 'vuex'
import SERVER from '@/api/api'

import axios from 'axios'
import scrollmonitor from 'scrollmonitor'

export default {
  name: 'VersusMakeView',
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
    }
  },
  components: {
    CategorySelector
  },
  computed: {
    selectedRecipesId() {
      return this.selectedRecipes.map(recipe => recipe.recipeId)
    },
    selectedMaxPage() {
      return parseInt((this.selectedRecipes.length-1) / 4)
    },
    curRecipes() {
      return this.selectedRecipes.slice(4*this.selectedCurPage, 4*(this.selectedCurPage+1))
    },
    ...mapGetters(["searchingData"]),
  },
  methods: {
    categorySubmit() {
      this.recipes = [];
      this.curPage = 1;
      if (
        this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length != 0
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
          this.recipes = [...this.recipes, ...res.data.list];
          this.maxPage = parseInt((res.data.total - 1) / 6);
        })
        .catch((err) => console.log(err));
    },
    checkRecipe(recipe) {
      if (this.selectedRecipesId.indexOf(recipe.recipeId) == -1) {
        this.selectedRecipes.push(recipe)
      } else {
        this.selectedRecipes.splice(this.selectedRecipesId.indexOf(recipe.recipeId), 1);
      }
    },
    isSelected(id) {
      return (this.selectedRecipesId.indexOf(id) >= 0) ? true : false
    },
    movePrev() {
      if (this.selectedCurPage != 0) {
        this.selectedCurPage -= 1
      } else {
        this.selectedCurPage = this.selectedMaxPage
      }
    },
    moveNext() {
      if (this.selectedCurPage < this.selectedMaxPage) {
        this.selectedCurPage += 1
      } else {
        this.selectedCurPage = 0
      }
    },
    addScrollWatcher() {
      const bottomSensor = document.querySelector('#bottomSensor')
      const watcher = scrollmonitor.create(bottomSensor)
      if (this.curPage < this.maxPage) {
        watcher.enterViewport(() => {
          setTimeout(() => {
            this.getRecipes()
          }, 1000)
        })
      }
    },
    loadUntilViewportIsFull() {
      const bottomSensor = document.querySelector('#bottomSensor')
      const watcher = scrollmonitor.create(bottomSensor)
      if (watcher.isFullyInViewport) {
        this.getRecipes()
      }
    },
    getRecipes() {
      if (
        this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length != 0
      ) {
        this.searchRecipe(this.curPage++);
      } else {
        this.allRecipe(this.curPage++);
      }
    },
    scrollToTop() {
      scroll(0, 0)
    },
  },
  created() {
    this.getRecipes()
  },
  mounted() {
    this.addScrollWatcher()
  },
  updated() {
    if (this.curPage < this.maxPage) {
      this.loadUntilViewportIsFull()
    }
  }
}
</script>

<style>

</style>