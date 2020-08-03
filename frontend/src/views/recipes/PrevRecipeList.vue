<template>
  <div>
    Test
  </div>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  name: 'Test',
  methods: {
    ...mapMutations(['setSearchData'])
  },
  created() {
    this.setSearchData({
      'selectedCategory': [],
      'selectedIngredients': [],
      'selectedIngredientsName': [],
    })
    this.$router.push({ name: 'RecipeListView', params: { pageNum: 1} })
  }
}
</script>

<style>

</style>