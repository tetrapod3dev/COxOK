<template>
  <div class="wrapper landing-page">
    <div class="page-header page-header-small">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.pexels.com/photos/1565982/pexels-photo-1565982.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260')"
      ></parallax>
      <div class="content-center">
        <h1 class="title">요리와 사람을 코×옥 더하다</h1>
      </div>
    </div>

    <div class="about-office my-5">
      <div class="container">
        <div class="row text-center">
          <div class="col-md-8 ml-auto mr-auto">
            <h2 class="title text-left mb-0">최근에 만들어진 요리들</h2>
          </div>
        </div>
        <div class="row">
          <div class="ml-auto mr-auto align-self-center" @click="movePrev">
            <n-button type="primary" round icon>
              <i class="now-ui-icons arrows-1_minimal-left"></i>
            </n-button>
          </div>
          <div class="col-10 row recent-recipes">
            <div v-for="recipe in recipes" :key="recipe.recipeId" class="col-lg-2 col-4 recipe-item p-0">
              <router-link
                class="card-link main-list-recipe"
                tag="div"
                :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }">
                <img :src="imageSrc(recipe.recipeThumbnailSrc)" class="recent-recipe-image">
              </router-link>
            </div>
          </div>
          <div class="ml-auto mr-auto align-self-center" @click="moveNext">
            <n-button type="primary" round icon>
              <i class="now-ui-icons arrows-1_minimal-right"></i>
            </n-button>
          </div>
        </div>
      </div>
    </div>

    
    <div v-if="favoriteCategory != null" class="about-office">
      <div class="container">
        <div class="row text-center">
          <div class="col-md-8 ml-auto mr-auto">
            <h2 class="title text-left mb-0">{{ favoriteCategory }} 요리는 어떠세요?</h2>
          </div>
        </div>
        <div class="row">
          <div class="col-1 align-self-center" @click="moveFavoritePrev">
            <n-button type="primary" round icon>
              <i class="now-ui-icons arrows-1_minimal-left"></i>
            </n-button>
          </div>
          <div class="col-10 row favortie-recipes">
            <div v-for="recipe in favoriteCurRecipes" :key="recipe.recipeId" class="col-2">
              <router-link
                class="card-link"
                :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }"
              >
                <img :src="imageSrc(recipe.recipeThumbnailSrc)" class="favorite-recipe-image" />
              </router-link>
            </div>
          </div>
          <div class="col-1 align-self-center" @click="moveFavoriteNext">
            <n-button type="primary" round icon>
              <i class="now-ui-icons arrows-1_minimal-right"></i>
            </n-button>
          </div>
        </div>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <h2 class="title">코슐랭 가이드</h2>
        <div class="row top-recipes">
          <router-link
            class="card-link col-3 mt-4"
            :to="{name: 'RecipeDetailView', params: {recipe_id: second.recipeId} }"
          >
            <h2>2등</h2>
            <b-card
              :img-src=imageSrc(second.recipeThumbnailSrc)
              img-alt="레시피 사진"
              img-width="350px"
              img-height="250px"
              img-top
              tag="article"
              style="max-width: 20rem;"
              class="recipe-card mb-2"
            >
              <div style="height:110px">
              <b-form-rating class="list-rating rating-inline" inline value="4" size="sm" v-model="second.avgRating" no-border variant="warning" readonly></b-form-rating>
              <div class="mt-auto mb-auto">
              <b-card-text class="recipe-card-text">
                {{second.recipeName}}
              </b-card-text>
              </div>
              </div>
            </b-card>
          </router-link>

          <router-link
            class="card-link col-3"
            :to="{name: 'RecipeDetailView', params: {recipe_id: first.recipeId} }"
          >
            <h2>1등</h2>
            <b-card
              :img-src=imageSrc(first.recipeThumbnailSrc)
              img-alt="레시피 사진"
              img-width="350px"
              img-height="250px"
              img-top
              tag="article"
              style="max-width: 20rem;"
              class="recipe-card mb-2"
            >
              <div style="height:110px">
              <b-form-rating class="list-rating rating-inline" inline value="4" size="sm" v-model="first.avgRating" no-border variant="warning" readonly></b-form-rating>
              <div class="mt-auto mb-auto">
              <b-card-text class="recipe-card-text">
                {{first.recipeName}}
              </b-card-text>
              </div>
              </div>
            </b-card>
          </router-link>

          <router-link
            class="card-link col-3 mt-5"
            :to="{name: 'RecipeDetailView', params: {recipe_id: third.recipeId} }"
          >
            <h2>3등</h2>
            <b-card
              :img-src=imageSrc(third.recipeThumbnailSrc)
              img-alt="레시피 사진"
              img-width="350px"
              img-height="250px"
              img-top
              tag="article"
              style="max-width: 20rem;"
              class="recipe-card mb-2"
            >
              <div style="height:110px">
              <b-form-rating class="list-rating rating-inline" inline value="4" size="sm" v-model="third.avgRating" no-border variant="warning" readonly></b-form-rating>
              <div class="mt-auto mb-auto">
              <b-card-text class="recipe-card-text">
                {{third.recipeName}}
              </b-card-text>
              </div>
              </div>
            </b-card>
          </router-link>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import SERVER from "@/api/api";
import axios from "axios";

import { mapGetters } from "vuex";
import { Button, FormGroupInput } from "@/components/global";

export default {
  name: "Main",
  data() {
    return {
      recipes: [{recipeId: null}],
      maxPage: 3,
      curPage: 0,
      favoriteCategory: null,
      favoriteRecipes: [{recipeId: null}],
      favoriteCurPage: 0,
      first: {recipeId: null},
      second: {recipeId: null},
      third: {recipeId: null},
    };
  },
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  computed: {
    ...mapGetters(["config"]),
    favoriteMaxPage() {
      return parseInt((this.favoriteRecipes.length - 1) / 6) + 1;
    },
    favoriteCurRecipes() {
      return this.favoriteRecipes.slice(
        this.favoriteCurPage * 6,
        (this.favoriteCurPage + 1) * 6
      );
    },
  },
  methods: {
    imageSrc(recipePhoto) {
      return "http://i3a104.p.ssafy.io/img/" + recipePhoto;
    },
    moveNext() {
      this.curPage += 1;
      if (this.curPage == this.maxPage) {
        this.curPage = 0;
      }
      this.getRecipes(this.curPage);
    },
    movePrev() {
      this.curPage -= 1;
      if (this.curPage == -1) {
        this.curPage = this.maxPage - 1;
      }
      this.getRecipes(this.curPage);
    },
    getRecipes(page) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.recipeList + page)
        .then((res) => {
          this.recipes = res.data.list;
        })
        .catch((err) => console.log(err));
    },

    moveFavoriteNext() {
      this.favoriteCurPage += 1;
      if (this.favoriteCurPage == this.favoriteMaxPage) {
        this.favoriteCurPage = 0;
      }
    },
    moveFavoritePrev() {
      this.favoriteCurPage -= 1;
      if (this.favoriteCurPage == -1) {
        this.favoriteCurPage = this.favoriteMaxPage - 1;
      }
    },
    getFavoriteRecipes() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.favoriteRecipes, {
          headers: {
            Authorization: this.config,
          },
        })
        .then(res => {
          if (res.data.msg == "선호 카테고리가 없습니다.") {
            this.favoriteCategory = null
          } else {
            this.favoriteCategory = res.data.category.foodCategoryName
            this.favoriteRecipes = res.data.recipe.slice(0, 18)
          }
        })
        .catch((err) => console.log(err));
    },

    getVersusRank() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.versusRank)
        .then((res) => {
          this.first = res.data.versus[0];
          this.second = res.data.versus[1];
          this.third = res.data.versus[2];
        })
        .catch((err) => console.log(err.response));
    },
  },
  created() {
    this.getRecipes(0);
    this.getFavoriteRecipes();
    this.getVersusRank();
  },
};
</script>

<style scoped>
.main-list-recipe {
  width: 120px;
  height: 100px;
  background-size: cover;
  background-position: center;
}

.recipe-item {
  cursor: pointer;
}

.recent-recipe-image {
  height: 100px;
}

.favorite-recipe-image {
  height: 100px;
}

.top-recipes {
  display: flex;
  justify-content:space-around;
}

.top-recipes > a {
  border-radius: 50px;
  transition: all 0.5s ease-in-out;
}

.top-recipes > a > article > img {
  height: 200px;
}

.top-recipes > a:hover {
  transform: scale(1.2);
  transition: transform.5s;
  cursor: pointer;
}

.favortie-recipes > div {
  border-radius: 50px;
  transition: all 0.5s ease-in-out;
}

.favortie-recipes > div:hover {
  transform: scale(1.2);
  transition: transform.5s;
  cursor: pointer;
}


.recent-recipes > div {
  border-radius: 50px;
  transition: all 0.5s ease-in-out;
}

.recent-recipes > div:hover {
  transform: scale(1.2);
  transition: transform.5s;
  cursor: pointer;
}
</style>
