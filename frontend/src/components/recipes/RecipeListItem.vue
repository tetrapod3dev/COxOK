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
        <div>
          <div class="mt-auto mb-auto">
            <b-card-text class="recipe-card-text row">
              <p class="col-12">{{ recipe.recipeName }}</p>
              <div class="recipe-icon col-12">
                <div class="col-4 recipe-info">
                  <i class="now-ui-icons ui-2_time-alarm" v-b-popover.hover="'조리시간'"></i><br>
                  {{ recipe.cookTime }}분
                </div>
                <div class="col-4 recipe-info">
                  <i class="far fa-star" v-b-popover.hover="'평점'"></i><br>
                  {{ avgRound(recipe.avgRating) }}
                </div>
                <div class="col-4 recipe-info">
                  <i class="fas fa-fire-alt" v-b-popover.hover="'난이도 (1-5)'"></i><br>
                  {{ recipe.level }}
                </div>
              </div>
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
  methods: {
    avgRound(avgRating){
      return Math.round(avgRating * 100) / 100;
    }
  }
};
</script>

<style scoped>
.recipe-card {
  width: 350px;
  height: 500px;
  display:inline-block;
  transition: all 0.8s;
}
.recipe-card:hover {
  transform:scale(1.1);
}
.recipe-card-text {
  font-size: 20px;
  font-weight: bold;
}
.list-rating {
  font-size: 20px;
}

.recipe-info{
  color: #a79d9c;
}

.recipe-icon{
  display: flex;
  position: absolute; 
  bottom: 0px; 
}

</style>