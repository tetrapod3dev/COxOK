<template>
  <div class="wrapper landing-page">
    <div class="page-header">
      <parallax
        class="page-header-image" style="background-size:80%"
        :style="{ backgroundImage: 'url(\'' + require('@/assets/3753985.jpg') + '\')' }"
      ></parallax>
      <div class="content-center">
        <h1 class="title">CO~OK</h1>
        <h2>함께하면 가능하다</h2>
      </div>
    </div>

    <div class="about-office my-5">
      <div class="container">
        <div class="row text-center">
          <div class="col-md-8 ml-auto mr-auto">
            <h2 class="title mb-0">최근에 만들어진 요리들</h2>
          </div>
        </div>
        <div class="row">
          <div class="ml-auto mr-auto align-self-center" @click="movePrev">
            <n-button type="primary" round icon>
              <i class="now-ui-icons arrows-1_minimal-left"></i>
            </n-button>
          </div>
          <div class="col-10 row justify-content-around recipes">
            
            <router-link
              v-for="recipe in curRecipes" :key="recipe.recipeId"
              class="col-3 p-0 m-0 card-link"
              :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }">
              <b-card
                :img-src=imageSrc(recipe.recipeThumbnailSrc)
                img-alt="레시피 사진"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="recipe-card"
              >
                <div>
                <b-form-rating class="list-rating rating-inline" inline value="4" size="sm" v-model="recipe.avgRating" no-border variant="warning" readonly></b-form-rating>
                <div class="mt-auto mb-auto">
                <b-card-text class="recipe-card-text row">
                  <p class="col-12">{{recipe.recipeName}}</p>
                  <div class="col-6">
                    <i class="now-ui-icons ui-2_time-alarm" v-b-popover.hover="'조리시간'"></i><br>
                    {{ recipe.cookTime }}분
                  </div>
                  <div class="col-6">
                    <i class="fas fa-fire-alt" v-b-popover.hover="'난이도 (1-5)'"></i><br>
                    {{ recipe.level }}
                  </div>
                </b-card-text>
                </div>
                </div>
              </b-card>
            </router-link>

          </div>
          <div class="ml-auto mr-auto align-self-center" @click="moveNext">
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
        <div class="row recipes">
          <router-link
            class="card-link col-3"
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
            class="card-link col-3"
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
import SERVER from "@/api/api";
import axios from "axios";
import { mapGetters } from 'vuex'
import { Button, FormGroupInput } from "@/components/global";

export default {
  name: "Home",
  data() {
    return {
      recipes: [{recipeId: 0, recipeThumbnailSrc: "dochi.png"}],
      maxPage: 2,
      curPage: 0,
      form: {
        firstName: "",
        email: "",
        message: "",
      },
      first: {recipeId: 0, recipeThumbnailSrc: "dochi.png"},
      second: {recipeId: 0, recipeThumbnailSrc: "dochi.png"},
      third: {recipeId: 0, recipeThumbnailSrc: "dochi.png"},
    };
  },
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  computed: {
    ...mapGetters(['isLoggedIn']),
    curRecipes() {
      return this.recipes.slice(
        this.curPage * 3,
        (this.curPage + 1) * 3
      );
    },
  },
  created() {
    if (this.isLoggedIn) {
      this.$router.push({name: 'Main'})
    }
    this.getRecipes();
    this.getVersusRank();
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
    },
    movePrev() {
      this.curPage -= 1;
      if (this.curPage == -1) {
        this.curPage = this.maxPage - 1;
      }
    },
    getRecipes() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.recipeList + 0)
        .then((res) => {
          this.recipes = res.data.list;
        })
        .catch((err) => console.log(err));
    },

    getVersusRank() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.versusRank)
        .then(res => {
          this.first = res.data.versus[0]
          this.second = res.data.versus[1]
          this.third = res.data.versus[2]
        })
        .catch(err => console.log(err.response))
    }
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

.recipes {
  display: flex;
  justify-content:space-around;
}

.recipes > a {
  border-radius: 50px;
  transition: all 0.5s ease-in-out;
}

.recipes > a > article > img {
  height: 200px;
}

.recipes > a:hover {
  transform: scale(1.2);
  transition: transform.5s;
  cursor: pointer;
}

</style>