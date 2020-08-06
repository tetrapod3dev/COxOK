<template>
  <div class="wrapper">
    <div class="page-header page-header-mini header-filter" filter-color="black">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.unsplash.com/photo-1505576399279-565b52d4ac71?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80')"
      ></parallax>
      <blog-profile />
    </div>

    <div class="section">
      <div class="container">
        <blog-menu />

        <div class="row">
          <div class="col-md-12">
            <h2>작성한 레시피를 모아서 보여주는 페이지</h2>
            <div class="row">
              <RecipeListItem
                v-for="recipe in recipes"
                :key="recipe.recipe_id"
                :recipe="recipe"
                class="col-3 mx-3"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import RecipeListItem from "../../components/recipes/RecipeListItem.vue";
import BlogProfile from "@/components/blog/BlogProfile.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";

export default {
  name: "BlogRecipeListView",
  bodyClass: "profile-page",
  data() {
    return {
      recipes: [
        {
          recipe_id: 1,
          recipe_name: "맛있는 가지튀김",
          recipe_thumbnail:
            "https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F2756_000_1%2F20131109003054995_MNBAW8OPM.jpg%2Fsyj_66_i1.jpg%3Ftype%3Dw690_fst_n%26wm%3DY%22&twidth=615&theight=530&opts=17",
          recipe_detail:
            "두뇌를 맑게 하는 가지튀김, 가지는 식품의 색소나 발색제에서 나오는 발암 물질을 억제하는 효과가 있어 색소가 들어간 식품이나 햄 등을 많이 먹는 아이들에게 챙겨주는 것이 좋아요.",
        },
        {
          recipe_id: 2,
          recipe_name: "맛있는 가지튀김222",
          recipe_thumbnail:
            "https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F2756_000_1%2F20131109003054995_MNBAW8OPM.jpg%2Fsyj_66_i1.jpg%3Ftype%3Dw690_fst_n%26wm%3DY%22&twidth=615&theight=530&opts=17",
          recipe_detail: "2222",
        },
      ],
    };
  },
  components: {
    RecipeListItem,
    BlogProfile,
    BlogMenu,
  },
};
</script>

<style>
</style>