<template>
  <div class="container">
    <h2>요리대전 상세 페이지입니다.</h2>
    
    <div v-if="selectedMax == 0">
      <select name="rounds" id="round">
        <option value="" selected>-- 선택 --</option>
        <option v-for="length in possibleLength" :key="length" :value="length">{{ length }} 강</option>
      </select>

      <button @click="submitRound">선택</button>
    </div>

    <div v-else-if="selectedMax != 1">
      <h2>{{ selectedMax }}강전</h2>
      <div class="row">
        <div v-for="recipe in nowRecipes" :key="recipe.id" class="col-6">
          <div @click="selectRecipe(recipe)" >
            <img :src="imageSrc(recipe.recipeThumbnailSrc)">
            <button class="btn btn-outline-primary">{{ recipe.recipeName }}</button>
          </div>
          <button @click="showDetail(recipe.recipeId)">{{ recipe.recipeId }}번 상세보기</button>
        </div>
      </div>
    </div>

    <div v-else>
      <h1>1등</h1>
      <img :src="imageSrc(recipes[0].recipeThumbnailSrc)">
      <button class="btn btn-outline-primary">{{ recipes[0].recipeName }}</button>
    </div>

  </div>
</template>

<script>
import SERVER from '@/api/api'

import axios from 'axios'
import _ from 'lodash'

export default {
  name: 'VersusDetailView',
  data() {
    return {
      TempMax: 4,
      selectedMax: 0,
      cur: 0,
      selectedRecipes: [],
      recipes: [],
      possibleLength: [],
    }
  },
  computed: {
    nowRecipes() {
      return this.recipes.slice(this.cur, this.cur+2)
    }
  },
  methods: {
    selectRecipe(recipe) {
      this.selectedRecipes.push(recipe)
      this.cur += 2
      if (this.cur == this.selectedMax) {
        this.cur = 0
        this.selectedMax = this.selectedMax / 2
        this.recipes = _.shuffle(this.selectedRecipes)
        this.selectedRecipes = []
      }
      if (this.selectedMax == 2) {
        console.log(this.selectedRecipes[0])
      }
    },
    showDetail(recipe_id) {
      console.log(recipe_id)
    },
    submitRound() {
      const selection = document.getElementById('round')
      this.selectedMax = selection.value

      this.recipes = _.shuffle(this.recipes.slice(0, this.selectedMax))
    },
    imageSrc(recipeSrc) {
      return SERVER.IMAGE_URL + recipeSrc
    }
  },
  created() {
    // this.recipes = _.shuffle(this.recipes.slice(0, this.maxNow))
    axios.get(SERVER.URL + SERVER.ROUTES.versusDetail + this.$route.params.versus_id)
      .then(res => {
        this.recipes = res.data.versus.recipeList

        const recipeLength = res.data.versus.recipeList.length
        while (this.TempMax <= recipeLength) {
          this.possibleLength.push(this.TempMax)
          this.TempMax *= 2
        }
      })
      .catch(err => console.log(err))

    // const recipeLength = this.recipes.length
    
    // this.TempMax /= 2
  }
}
</script>

<style>

</style>