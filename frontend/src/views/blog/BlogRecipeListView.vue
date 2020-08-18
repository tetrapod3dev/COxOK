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
        <br />
        <tabs
          class="row"
          pills
          type="primary"
          icons
          tab-nav-wrapper-classes="col-md-2"
          tab-content-classes="col-md-10"
          vertical
        >
          <tab-pane label="myrecipe">
            <template slot="label">
              <i class="now-ui-icons design-2_ruler-pencil"></i> 작성한 레시피
            </template>
            <div class="row">
              <RecipeListItem
                v-for="recipe in myrecipes"
                :key="recipe.recipe_id"
                :recipe="recipe"
                class="col-12 col-sm-6 col-md-4 mx-2"
              />
              <div v-if="myrecipes.length == 0" class="col-12 col-sm-6 col-md-4 mx-2">
                <b-card tag="article" style="max-width: 20rem;" class="recipe-card mb-2">
                  <div style="height:110px">
                    <div class="mt-auto mb-auto align-self-center">
                      <b-card-text class="recipe-card-text text-center">레시피를 작성해보세요</b-card-text>

                      <router-link :to="{ name: 'PrevRecipeList' }">
                        <b-card-text class="recipe-card-text text-center text-info">레시피 구경하기</b-card-text>
                      </router-link>

                      <router-link to="/blog/posts/make" tag="div">
                        <b-card-text class="recipe-card-text text-center text-success">작성하러 가기</b-card-text>
                      </router-link>
                    </div>
                  </div>
                </b-card>
              </div>
            </div>
          </tab-pane>
          <tab-pane label="likerecipe">
            <template slot="label">
              <i class="now-ui-icons ui-2_favourite-28"></i> 좋아하는 레시피
            </template>
            <div class="row">
              <RecipeListItem
                v-for="recipe in likerecipes"
                :key="recipe.recipe_id"
                :recipe="recipe"
                class="col-12 col-sm-6 col-md-4 mx-2"
              />
              <div v-if="likerecipes.length == 0" class="col-12 col-sm-6 col-md-4 mx-2">
                <b-card tag="article" style="max-width: 20rem;" class="recipe-card mb-2">
                  <div style="height:110px">
                    <div class="mt-auto mb-auto align-self-center">
                      <b-card-text class="recipe-card-text text-center">좋아하는 레시피를 추가해보세요</b-card-text>

                      <router-link :to="{ name: 'PrevRecipeList' }">
                        <b-card-text class="recipe-card-text text-center text-info">레시피 구경하기</b-card-text>
                      </router-link>

                      <router-link to="/blog/posts/make" tag="div">
                        <b-card-text class="recipe-card-text text-center text-success">작성하러 가기</b-card-text>
                      </router-link>
                    </div>
                  </div>
                </b-card>
              </div>
            </div>
          </tab-pane>
        </tabs>
      </div>
    </div>
  </div>
</template>

<script>
import RecipeListItem from "../../components/recipes/RecipeListItem.vue";
import BlogProfile from "@/components/blog/BlogProfile.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";

import { Tabs, TabPane } from "@/components/global";

import axios from "axios";
import SERVER from "@/api/api";
import { mapGetters } from "vuex";

export default {
  name: "BlogRecipeListView",
  bodyClass: "profile-page",
  data() {
    return {
      myrecipes: [],
      likerecipes: [],
    };
  },
  computed: {
    ...mapGetters(["config"]),
  },
  components: {
    RecipeListItem,
    BlogProfile,
    BlogMenu,
    Tabs,
    TabPane,
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
        .get(SERVER.URL + SERVER.ROUTES.userMyRecipe, configs)
        .then((res) => {
          this.myrecipes = res.data.writeRecipeList;
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert("로그인 정보가 만료되었습니다! 다시 로그인해주세요.");
            this.logout();
          }
        });
      axios
        .get(SERVER.URL + SERVER.ROUTES.userLikeRecipe, configs)
        .then((res) => {
          this.likerecipes = res.data.userLikeRecipe;
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert("로그인 정보가 만료되었습니다! 다시 로그인해주세요.");
            this.logout();
          }
        });
    },
  },
};
</script>

<style scoped>
</style>