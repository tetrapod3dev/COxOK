<template>
  <div>
    <router-link
      class="card-link"
      :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }"
    >
      <card type="profile" style="width:300px;height:300px;">
        <div class="versus-card-image" :style="'background-image: url('+ imgSrc+')'" alt="레시피 사진"></div>
        <!-- <card class="card" style="width: 20rem;">
        <img slot="image" :src="imgSrc" class="card-img-top" alt="레시피 사진" />-->
        <div>
          <p class="card-text">{{ recipe.recipeName }}</p>
        </div>
      </card>
    </router-link>
  </div>
</template>

<script>
import SERVER from "@/api/api";
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
      return SERVER.IMAGE_URL + this.recipe.recipeThumbnailSrc;
    },
  },
};
</script>

<style scoped>
.versus-card-image {
  width: 260px;
  height: 200px;
  background-size: cover;
  background-position: center;
}
</style>