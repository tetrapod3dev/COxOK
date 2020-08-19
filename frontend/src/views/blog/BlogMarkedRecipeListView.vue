<template>
  <div class="wrapper">
    <div class="page-header page-header-mini header-filter" filter-color="black">
      <parallax
        class="page-header-image"
        :style="{ backgroundImage: 'url(http://i3a104.p.ssafy.io/header/mypage.jpg)' }"
      ></parallax>
      <blog-profile />
    </div>

    <div class="section">
      <div class="container">
        <blog-menu />

        <div class="row">
          <div class="col-md-12">
            <h2>북마크한 레시피를 모아서 보여주는 페이지</h2>
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

import axios from "axios";
import SERVER from "@/api/api";
import { mapGetters } from "vuex";

export default {
  name: "BlogMarkedRecipeListView",
  bodyClass: "profile-page",
  data() {
    return {
      recipes: [],
    };
  },
  computed: {
    ...mapGetters(["config"]),
  },
  components: {
    RecipeListItem,
    BlogProfile,
    BlogMenu,
  },
  created() {
    this.allRecipe();
  },
  watch: {
    $route() {
      this.allPage();
    },
  },
  methods: {
    allRecipe() {
      let configs = {
        headers: {
          Authorization: this.config,
        },
      };
      axios
        .get(SERVER.URL + SERVER.ROUTES.userLikeRecipe, configs)
        .then((res) => {
          this.recipes = res.data.userLikeRecipe;
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
  },
};
</script>
