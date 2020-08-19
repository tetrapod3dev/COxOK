<template>
  <div class="wrapper landing-page">
    <div class="page-header">
      <parallax
        class="page-header-image" style="background-size:80%"
        :style="{ backgroundImage: 'url(http://i3a104.p.ssafy.io/header/3753985.jpg)' }"
      ></parallax>
      <div class="content-center">
        <h1 class="title d-inline-block border border-white rounded-lg move-btn py-0" @click="scrollPage">CO~OK</h1>
        <h2>함께하면 가능하다</h2>
      </div>
    </div>

    <div class="about-office my-5" id="mainPage">
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
                <div style="height:180px;">
                  <!-- <b-form-rating class="list-rating rating-inline" inline value="4" size="sm" v-model="recipe.avgRating" no-border variant="warning" readonly></b-form-rating> -->
                  <div class="mt-auto mb-auto">
                    <b-card-text class="recipe-card-text row">
                      <p class="col-12">{{ recipeNameTrun(recipe.recipeName) }}</p>
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
            v-for="(recipe, index) in topRecipes"
            :key="recipe.recipeId"
            class="card-link col-3"
            :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }"
          >
            <h2>{{ index +1 }}등</h2>
            <b-card
              :img-src=imageSrc(recipe.recipeThumbnailSrc)
              img-alt="레시피 사진"
              img-width="350px"
              img-height="250px"
              img-top
              tag="article"
              style="max-width: 20rem;"
              class="recipe-card mb-2"
            >
              <div style="height:180px;">
                  <!-- <b-form-rating class="list-rating rating-inline" inline value="4" size="sm" v-model="recipe.avgRating" no-border variant="warning" readonly></b-form-rating> -->
                  <div class="mt-auto mb-auto">
                    <b-card-text class="recipe-card-text row">
                      <p class="col-12">{{ recipeNameTrun(recipe.recipeName) }}</p>
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
      recipes: [{recipeId: 0, recipeThumbnailSrc: "dochi.png", recipeName: ""}],
      maxPage: 2,
      curPage: 0,
      form: {
        firstName: "",
        email: "",
        message: "",
      },
      topRecipes: [
        {recipeId: 0, recipeThumbnailSrc: "dochi.png", recipeName: ""}
      ]
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
    scrollPage() {
      var location = document.querySelector("#mainPage").offsetTop-100;

      window.scrollTo({ top: location, behavior: "smooth" });
    },
    avgRound(avgRating){
      return Math.round(avgRating * 100) / 100;
    },
    imageSrc(recipePhoto) {
      return "http://i3a104.p.ssafy.io/img/" + recipePhoto;
    },
    recipeNameTrun(recipeName) {
      if (recipeName.length > 27) {
        return recipeName.slice(0, 24) + '...'
      } else {
        return recipeName.slice(0, 27)
      }
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
          this.topRecipes = res.data.versus
        })
        .catch(err => console.log(err.response))
    }
  },
};
</script>

<style scoped>
.move-btn:hover {
  cursor: pointer;
  background-color: orange;
}

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

.recipe-info{
  color: #a79d9c;
}

.recipe-icon{
  display: flex;
  position: absolute; 
  bottom: 0px; 
}

</style>