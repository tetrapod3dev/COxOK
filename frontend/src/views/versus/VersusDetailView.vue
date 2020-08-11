<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://cdn.pixabay.com/photo/2017/07/28/13/29/spices-2548653_960_720.jpg') ;"
      ></parallax>

      <div class="content-center">
        <h1 class="title">요리대전</h1>
      </div>
    </div>

    <div class="section detail-versus">
      <div class="container">
        <div v-if="selectedMax == 0" class="button-container">
          <b-form-select class="btn btn-primary btn-round btn-lg" id="round">
            <template v-slot:first>
              <b-form-select-option :value="null" selected disabled>--선택--</b-form-select-option>
            </template>

            <b-form-select-option
              v-for="length in possibleLength"
              :key="length"
              :value="length"
            >{{length}}강</b-form-select-option>
          </b-form-select>
          <button class="btn btn-primary btn-round btn-lg" @click="submitRound">선택</button>
        </div>
      </div>
    </div>

    <div v-if="selectedMax == 0" class="section container">
      <div class="row">
        <div class="col-6">
          <card type="background" style="background-color: #B4D9C4">
            <h1 class="versus-first-title">V</h1>
          </card>
        </div>
        <div class="col-6">
          <card type="background" style="background-color: #F2A7A0">
            <h1 class="versus-first-title">S</h1>
          </card>
        </div>
      </div>
    </div>
    <div v-if="selectedMax > 1" class="section container">
      <h2 class="versus-title">{{ selectedMax }}강전</h2>
      <div class="row">
        <div v-for="recipe in nowRecipes" :key="recipe.id" class="col-6">
          <card type="profile" style="width:465px;height:400px;">
            <img :src="imageSrc(recipe.recipeThumbnailSrc)" @click="selectRecipe(recipe)" class="versus-card-image">

            <h4 class="card-title text-left">{{ recipe.recipeName }}</h4>
            <h6
              v-b-modal.modal-lg
              @click="showDetail(recipe.recipeId)"
              class="card-footer text-info text-right align-self-end"
            >{{recipe.recipeId}}상세보기</h6>
          </card>
        </div>

        <!-- <b-modal id="modal-lg" size="lg" :title="recipeDetail.recipeName">
          <p>{{ recipeDetail.recipeDetail }}</p>
        </b-modal>-->

        <b-modal id="modal-lg" size="lg" ok-only :title="recipeDetail.recipeName">
          <p>{{ recipeDetail.recipeDetail }}</p>
        </b-modal>
      </div>
    </div>
    <div v-if="this.selectedMax == 1" class="section container">
      <h2 class="versus-title">1등</h2>
      <div class="row">
        <div class="col-md-6">
          <card type="profile" style="width:465px;height:400px;">
            <div
              class="versus-card-image"
              :style="'background-image: url('+ imageSrc(recipes[0].recipeThumbnailSrc)+')'"
            ></div>
            <h4 class="card-title text-left">{{ recipes[0].recipeName }}</h4>
            <router-link
              :to="{name:'RecipeDetailView', params: { recipe_id: recipes[0].recipeId }}"
              tag="h6"
              class="card-footer text-info text-right align-self-end"
            >보러가기</router-link>
          </card>
        </div>
        <div class="col-md-6">
          <div v-for="result in versusResult" :key="result.recipeId">
            <!-- <div>레시피 아이디: {{ result.recipeId }}</div> -->
            <div>레시피 이름: {{ result.recipeName }}</div>
            <div>레시피 승점: {{ result.count ? result.count : 0 }}</div>
            <!-- <div
          :style="'width:100px;height:100px;background-image: url('+ imageSrc(result.recipeThumbnailSrc)+')'"
            ></div>-->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Card } from "@/components/global";

import SERVER from "@/api/api";
import axios from "axios";
import _ from "lodash";

export default {
  name: "VersusDetailView",
  data() {
    return {
      TempMax: 4,
      selectedMax: 0,
      cur: 0,
      selectedRecipes: [],
      recipesAll: [],
      recipes: [],
      versusResult: [],
      possibleLength: [],
      recipeDetail: {},
    };
  },
  components: {
    Card,
  },
  computed: {
    nowRecipes() {
      return this.recipes.slice(this.cur, this.cur + 2);
    },
  },
  methods: {
    showModal() {
      const loginModal = document.querySelector("#recipe-modal");
      loginModal.style.display = "block";
    },
    hideModal() {
      const loginModal = document.querySelector("#recipe-modal");
      loginModal.style.display = "none";
    },

    selectRecipe(recipe) {
      this.selectedRecipes.push(recipe);
      this.cur += 2;
      if (this.cur == this.selectedMax) {
        this.cur = 0;
        this.selectedMax = this.selectedMax / 2;
        this.recipes = _.shuffle(this.selectedRecipes);
        this.selectedRecipes = [];
      }
      if (this.selectedMax == 1) {
        this.submitWinner();
      }
    },
    showDetail(recipe_id) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.recipeDetail + recipe_id)
        .then((res) => {
          this.recipeDetail = res.data.recipe;
          this.showModal();
        })
        .catch((err) => console.log(err.response));
    },
    submitRound() {
      const selection = document.getElementById("round");
      this.selectedMax = selection.value;

      this.recipes = _.shuffle(this.recipes.slice(0, this.selectedMax));
    },
    imageSrc(recipeSrc) {
      return SERVER.IMAGE_URL + recipeSrc;
    },
    submitWinner() {
      let versusResult = {
        recipeId: this.recipes[0].recipeId,
        versusId: this.$route.params.versus_id,
      };
      axios
        .post(SERVER.URL + SERVER.ROUTES.versusWin, versusResult)
        .then((res) => {
          if (res.status === 200) {
            this.getVersusTotal();
          }
        })
        .catch((err) => console.log(err));
    },
    getVersusTotal() {
      axios
        .get(
          SERVER.URL + SERVER.ROUTES.versusResult + this.$route.params.versus_id
        )
        .then((res) => {
          this.versusResult = this.recipesAll
            .map((recipe) => {
              return Object.assign(
                recipe,
                res.data.versus.find((cntWinner) => {
                  return cntWinner && recipe.recipeId === cntWinner.recipeId;
                })
              );
            })
            .sort((recipe1, recipe2) => {
              return recipe2.count - recipe1.count;
            });
        })
        .catch((err) => console.log(err));
    },
  },
  created() {
    // this.recipes = _.shuffle(this.recipes.slice(0, this.maxNow))
    axios
      .get(
        SERVER.URL + SERVER.ROUTES.versusDetail + this.$route.params.versus_id
      )
      .then((res) => {
        this.recipes = res.data.versus.recipeList;
        this.recipesAll = res.data.versus.recipeList;
        const recipeLength = res.data.versus.recipeList.length;
        while (this.TempMax <= recipeLength) {
          this.possibleLength.push(this.TempMax);
          this.TempMax *= 2;
        }
      })
      .catch((err) => console.log(err));

    // const recipeLength = this.recipes.length

    // this.TempMax /= 2
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap");

.detail-versus .button-container {
  margin-top: -112px;
}
.detail-versus .custom-select {
  width: 148px;
  height: 48px;
}
.detail-versus .custom-select option {
  background-color: #ffffff;
  color: #636363;
  font-size: 20px;
  text-align: left;
  width: 148px;
  height: 48px;
}
.versus-first-title {
  color: #ffffff;
  font-size: 200px;
  font-family: "Rock Salt", cursive;
}
.versus-title {
  margin-top: -112px;
}
.versus-card-image {
  width: 425px;
  height: 260px;
  background-size: cover;
  background-position: center;
}
</style>