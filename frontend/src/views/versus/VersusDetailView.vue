<template>
  <div class="container">
    <h2>요리대전 상세 페이지입니다.</h2>
    
    <div v-if="maxNow != 1">
      <h2>{{ maxNow }}강전</h2>
      <div class="row">
        <div v-for="recipe in nowRecipes" :key="recipe.id" class="col-6">
          <div @click="selectRecipe(recipe)" >
            <button class="btn btn-outline-primary">{{ recipe.title }}</button>
          </div>
          <button @click="showDetail(recipe.id)">{{ recipe.id }}번 상세보기</button>
        </div>
      </div>
    </div>
    <div v-else>
      1등 : {{ recipes[0].title }}
    </div>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  name: 'VersusDetailView',
  data() {
    return {
      maxNow: 16,
      cur: 0,
      selectedRecipes: [],
      recipes: [
        {'title': '1번 요리', '평점': 4.2, 'id': 1},
        {'title': '2번 요리', '평점': 4.2, 'id': 2},
        {'title': '3번 요리', '평점': 4.2, 'id': 3},
        {'title': '4번 요리', '평점': 4.2, 'id': 4},
        {'title': '5번 요리', '평점': 4.2, 'id': 5},
        {'title': '6번 요리', '평점': 4.2, 'id': 6},
        {'title': '7번 요리', '평점': 4.2, 'id': 7},
        {'title': '8번 요리', '평점': 4.2, 'id': 8},
        {'title': '9번 요리', '평점': 4.2, 'id': 9},
        {'title': '10번 요리', '평점': 4.2, 'id': 10},
        {'title': '11번 요리', '평점': 4.2, 'id': 11},
        {'title': '12번 요리', '평점': 4.2, 'id': 12},
        {'title': '13번 요리', '평점': 4.2, 'id': 13},
        {'title': '14번 요리', '평점': 4.2, 'id': 14},
        {'title': '15번 요리', '평점': 4.2, 'id': 15},
        {'title': '16번 요리', '평점': 4.2, 'id': 16},
      ],
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
      if (this.cur == this.maxNow) {
        this.cur = 0
        this.maxNow = this.maxNow / 2
        this.recipes = _.shuffle(this.selectedRecipes)
        this.selectedRecipes = []
      }
    },
    showDetail(recipe_id) {
      console.log(recipe_id)
    }
  },
  created() {
    this.recipes = _.shuffle(this.recipes.slice(0, 16))
  }
}
</script>

<style>

</style>