<template>
  <div>
    <router-link
      class="card-link"
      :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }"
    >
      <card class="card" style="width: 20rem;">
        <img slot="image" :src="imgSrc" class="card-img-top" alt="레시피 사진" />
        <div>
          <p class="card-text">{{ recipe.recipeName }}</p>
        </div>
      </card>
    </router-link>
  </div>
</template>

<script>
import { Card } from "@/components/global";

export default {
  name: "RecipeListItem",
  props: {
    recipe: Object,
  },
  components: {
    Card,
  },
  computed: {
    imgSrc() {
      return "http://i3a104.p.ssafy.io/img/" + this.recipe.recipeThumbnailSrc;
    },
  },
};
</script>

<style scoped>
</style>