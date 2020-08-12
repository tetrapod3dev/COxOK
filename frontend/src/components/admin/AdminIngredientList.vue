<template>
  <div class="container">
    <div v-for="ingredient in curIngredients" :key="ingredient.ingredientId" class="row">
      <p>{{ ingredient.name }}</p>
    </div>
  </div>
</template>

<script>
import SERVER from '@/api/api'
import axios from 'axios'

import { mapGetters } from 'vuex'

export default {
  name: 'AdminIngredientList',
  data() {
    return {
      curPage: 1,
      curIngredients: [],
    }
  },
  computed: {
    ...mapGetters(['config'])
  },
  mounted() {
    this.getIngredients();
  },
  methods: {
    getIngredients() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.adminAllIngredients + (this.curPage - 1), {
          headers: {
            Authorization: this.config,
          },
        })
        .then(res => {
          this.curIngredients = res.data.ingredient
        })
        .catch(err => console.log(err.response))
    }
  }
}
</script>

<style>

</style>