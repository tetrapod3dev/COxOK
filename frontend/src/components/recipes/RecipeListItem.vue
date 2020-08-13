<template>
  <div>
    <router-link
      class="card-link"
      :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }"
    >
      <b-card
        :img-src=imgSrc
        img-alt="레시피 사진"
        img-width="350px"
        img-height="250px"
        img-top
        tag="article"
        style="max-width: 20rem;"
        class="recipe-card mb-2"
      >
        <div style="height:110px">
        <b-form-rating class="list-rating rating-inline" inline value="4" size="sm" v-model="recipe.avgRating" no-border variant="warning" readonly></b-form-rating>
        <div class="mt-auto mb-auto">
        <b-card-text class="recipe-card-text">
          {{recipe.recipeName}}
        </b-card-text>
        </div>
        </div>
      </b-card>
    </router-link>
  </div>
</template>

<script>
import SERVER from "@/api/api";

export default {
  name: "RecipeListItem",
  props: {
    recipe: Object,
  },
  components: {
  },
  computed: {
    imgSrc() {
      return SERVER.IMAGE_URL + this.recipe.recipeThumbnailSrc;
    },
  },
};
</script>

<style scoped>
.recipe-card {
  width: 350px;
  height: 420px;
}
.recipe-card-text {
  font-size: 20px;
  font-weight: bold;
}
.list-rating {
  font-size: 20px;
}

</style>