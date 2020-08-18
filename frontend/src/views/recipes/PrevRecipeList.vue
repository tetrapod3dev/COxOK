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
      'level': 5,
      'cookTime': 120,
    })
    this.$router.push({ name: 'RecipeListView', params: { pageNum: 1} })
  }
}
</script>
