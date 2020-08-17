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
          <!-- <b-form-select class="btn btn-primary btn-round btn-lg" id="round"> -->
            <b-form-select class="select-num mr-2" id="round">
            <template v-slot:first>
              <b-form-select-option :value="undefined" selected disabled>-- 선택 --</b-form-select-option>
            </template>

            <b-form-select-option
              v-for="length in possibleLength"
              :key="length"
              :value="length"
            >{{length}}강</b-form-select-option>
          </b-form-select>
          <button class="learn-more" @click="submitRound">코~옥</button>
          <!-- <button class="btn btn-primary btn-round btn-lg" @click="submitRound">코~옥</button> -->
        </div>
      </div>
    </div>

    <div v-if="selectedMax == 0" class="section container">
      <div class="row">
        <div class="col-6">
          <card  style="background-color: #8EC0E3">
            <h1 class="versus-first-title">V</h1>
          </card>
        </div>
        <div class="col-6">
          <card  style="background-color: #FCE4B8">
            <h1 class="versus-first-title">S</h1>
          </card>
        </div>
      </div>
    </div>
    <div v-if="selectedMax > 1" class="section container">
      <h2 class="versus-title">{{ selectedMax }}강전</h2>
      <div class="row">
        <div v-for="recipe in nowRecipes" :key="recipe.id" class="versus-card col-6">
          <card type="profile" style="width:465px;height:400px;">
            <img
              :src="imageSrc(recipe.recipeThumbnailSrc)"
              @click="selectRecipe(recipe)"
              class="versus-card-image"
            />

            <h4 class="card-title text-left versus-card-text">{{ recipe.recipeName }}</h4>
            <h6
              v-b-modal.modal-lg
              @click="showDetail(recipe.recipeId)"
              class="card-footer text-info text-right align-self-end"
            >상세보기</h6>
          </card>
        </div>

        <!-- <b-modal id="modal-lg" size="lg" :title="recipeDetail.recipeName">
          <p>{{ recipeDetail.recipeDetail }}</p>
        </b-modal>-->

        <b-modal id="modal-lg" size="lg" ok-only :title="recipeDetail.recipeName">
          <hr>
          <p>{{ recipeDetail.recipeDetail }}</p>
          <hr>
          <div class="additinal-info row mt-2 justify-content-center" >
            <div class="col-md-2">
              <i class="now-ui-icons ui-2_time-alarm" v-b-popover.hover="'조리시간'"></i><br>
              {{ recipeDetail.cookTime }}분
            </div>
            <div class="col-md-2">
              <i class="fas fa-fire-alt" v-b-popover.hover="'난이도 (1-5)'"></i><br>
              {{ recipeDetail.level }}
            </div>
          </div>
          <hr>
          <section id="nutrientInfo">
            <div class="container">
              <div class="row">
                <div class="col-md-10 ml-auto mr-auto">
                  <h3 class="text-left pt-5 mb-3">
                    <i class="fas fa-angle-double-right mr-1"></i>
                    영양소 정보
                  </h3>
                  <div class="nutri-list row ml-auto ">
                    <RadarGraph :recipeDataSet="recipeDataSet" class="col-8 col-lg-4 my-auto"/>
                    <div class="col-12 col-lg-8 row p-4 ml-2">
                      <div
                        v-for="(amount, nutrient, index) in recipeDataSet"
                        :key="nutrient"
                        class="col-6 row my-4"
                      >
                        <b-button variant="info col-6 ">{{nutrient}}</b-button>
                        <p class="col-5 align-self-center p-1 m-0 text-left">{{ amount }}{{ unitList[index] }}</p>
                      </div>
                    </div>
                  </div>
                  <p class="text-left">
                    <i class="fas fa-exclamation-triangle fa-1x mr-1" style="color: red;"></i>영양소 정보는 실제와 차이가 있을 수 있습니다.
                  </p>
                </div>
              </div>
            </div>
          </section>

        </b-modal>
      </div>
    </div>
    <div v-if="this.selectedMax == 1" class="section container">
      <h2 class="versus-title">1등</h2>
      <div class="row">
        <div class="versus-card col-md-6">
          <card type="profile" style="width:465px;height:400px;">
            <!-- <div
              class="versus-card-image"
              :style="'background-image: url('+ imageSrc(recipes[0].recipeThumbnailSrc)+')'"
            ></div> -->
            <img :src="imageSrc(recipes[0].recipeThumbnailSrc)" class="versus-card-image">
            <h4 class="card-title text-left versus-card-text">{{ recipes[0].recipeName }}</h4>
            <router-link
              :to="{name:'RecipeDetailView', params: { recipe_id: recipes[0].recipeId }}"
              tag="h6"
              class="card-footer text-info text-right align-self-end"
            >요리하기</router-link>
          </card>
        </div>
        <!-- <div class="ranking-table versus-card col-md-6"> -->
              <h4><i class="fas fa-utensils ml-3 mr-2"></i>코~옥 랭킹</h4>

                <div class = "col">
                  <div class = "card shadow">
                    <div class="table-responsive">
                      <table class="table align-items-center table-flush">
                        <thead class="thead-light">
                          <tr>
                            <th scope="col">이름</th>
                            <th scope="col">점수</th>
                          </tr>
                        </thead>

                        <tbody>
                          <div v-for="result in versusResult" :key="result.recipeId">
                              <tr >
                                <th scope="row">
                                  <div class="media align-items-center">
                                    <router-link :to="{name:'RecipeDetailView', params: { recipe_id: result.recipeId }}">
                                        <img :src="imageSrc(result.recipeThumbnailSrc)" 
                                    class="avatar rounded-circle mr-3">
                                    </router-link>
                                    
                                  </div>
                                  <!-- <div class="media-body">
                                    <span class="mb-0 text-sm">
                                      <router-link :to="{name:'RecipeDetailView', params: { recipe_id: result.recipeId }}">
                                        {{result.recipeName}}</router-link>
                                    </span>
                                  </div> -->
                                </th>

                                <td>
                                  <div class="d-flex align-items-center">
                                    <span class="mr-2">{{ result.count}}</span>
                                    <div>
                                        <div class="progress">
                                          <div class="progress-bar bg-success" 
                                          role="progressbar" :aria-valuenow="result.count" aria-valuemin="0" aria-valuemax="10" :style="'width:'+100*result.count/total +'%;'">     
                                          </div>
                                        </div>
                                    </div>
                                  </div>
                                </td>
                              </tr>
                          </div>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>  
                
                <!-- <div v-for="result in versusResult" :key="result.recipeId">
                  <hr>
                  <p class="text-left "> 
                    <router-link :to="{name:'RecipeDetailView', params: { recipe_id: result.recipeId }}">
                      {{result.recipeName}}</router-link></p>
                  <p class="text-left text-link">{{ result.count}}점</p>
                
               </div> -->
        </div>
      </div>
    </div>
  <!-- </div> -->
</template>

<script>
import { Card } from "@/components/global";
import RadarGraph from "../../components/recipes/RadarGraph.vue";

import SERVER from "@/api/api";
import axios from "axios";
import _ from "lodash";

export default {
  name: "VersusDetailView",
  data() {
    return {
      total:0,
      TempMax: 4,
      selectedMax: 0,
      cur: 0,
      selectedRecipes: [],
      recipesAll: [],
      recipes: [],
      versusResult: [],
      possibleLength: [],
      recipeDetail: {},
      recipeDataSet: {},
      unitList: ["kcal", "g", "g", "mg", "g", "g"],
    };
  },
  components: {
    RadarGraph,
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
           this.recipeDataSet = {
            'calorie': res.data.recipe.calorie,
            'carbon': res.data.recipe.carbon,
            'fat': res.data.recipe.fat,
            'natrium': res.data.recipe.natrium,
            'protein': res.data.recipe.protein,
            'sugar': res.data.recipe.sugar,
          };

          this.showModal();
        })
        .catch((err) => console.log(err.response));
    },
    submitRound() {
      const selection = document.getElementById("round");
      this.selectedMax = selection.value;

     this.recipes = _.sampleSize(this.recipes, this.selectedMax);
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
          for (let index = 0; index < res.data.versus.length; index++) {
            this.total+=res.data.versus[index].count;
          }
          
          this.versusResult = res.data.versus.sort((recipe1, recipe2) => {
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
/* @import url("https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap"); */
@import url("https://fonts.googleapis.com/css2?family=Rowdies&display=swap");
@import url("https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700");

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
  /* font-family: "Rock Salt", cursive; */
   font-family: 'Rowdies', cursive;
}
.versus-title {
  margin-top: -112px;
  padding-bottom: 50px;
}
.versus-card-image {
  width: 425px;
  height: 260px;
  background-size: cover;
  background-position: center;
}

.versus-card-text {
  font-size: 20px;
  font-weight: bold;
  padding-top: 30px;

}

 .versus-card {
  display:inline-block;
  transition: all 0.8s;
}

.versus-card:hover {
  transform:scale(1.1);
} 

.additinal-info {
  font-size: 20px;
  text-align: center;
}

.additinal-info i {
  font-size: 50px;
}

.ranking-table {
  padding: 30px 60px 30px 60px;
  border-radius: 40px;
  box-shadow: 0px 8px 40px rgba(128, 128, 128, 0.15);
}

:root {
    --blue: #5e72e4;
    --indigo: #5603ad;
    --purple: #8965e0;
    --pink: #f3a4b5;
    --red: #f5365c;
    --orange: #fb6340;
    --yellow: #ffd600;
    --green: #2dce89;
    --teal: #11cdef;
    --cyan: #2bffc6;
    --white: #fff;
    --gray: #8898aa;
    --gray-dark: #32325d;
    --light: #ced4da;
    --lighter: #e9ecef;
    --primary: #5e72e4;
    --secondary: #f7fafc;
    --success: #2dce89;
    --info: #11cdef;
    --warning: #fb6340;
    --danger: #f5365c;
    --light: #adb5bd;
    --dark: #212529;
    --default: #172b4d;
    --white: #fff;
    --neutral: #fff;
    --darker: black;
    --breakpoint-xs: 0;
    --breakpoint-sm: 576px;
    --breakpoint-md: 768px;
    --breakpoint-lg: 992px;
    --breakpoint-xl: 1200px;
    --font-family-sans-serif: Open Sans, sans-serif;
    --font-family-monospace: SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace;
}

*,
*::before,
*::after {
    box-sizing: border-box;
}

@-ms-viewport {
    width: device-width;
}

figcaption,
footer,
main,
nav,
section {
    display: block;
}

body {
    font-family: Open Sans, sans-serif;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    margin: 0;
    text-align: left;
    color: #525f7f;
    background-color: #f8f9fe;
}

[tabindex='-1']:focus {
    outline: 0 !important;
}

h2,
h3 {
    margin-top: 0;
    margin-bottom: .5rem;
}

p {
    margin-top: 0;
    margin-bottom: 1rem;
}

ul {
    margin-top: 0;
    margin-bottom: 1rem;
}

ul ul {
    margin-bottom: 0;
}

dfn {
    font-style: italic;
}

strong {
    font-weight: bolder;
}

a {
    text-decoration: none;
    color: #5e72e4;
    background-color: transparent;
    -webkit-text-decoration-skip: objects;
}

a:hover {
    text-decoration: none;
    color: #233dd2;
}

a:not([href]):not([tabindex]) {
    text-decoration: none;
    color: inherit;
}

a:not([href]):not([tabindex]):hover,
a:not([href]):not([tabindex]):focus {
    text-decoration: none;
    color: inherit;
}

a:not([href]):not([tabindex]):focus {
    outline: 0;
}

img {
    vertical-align: middle;
    border-style: none;
}

table {
    border-collapse: collapse;
}

caption {
    padding-top: 1rem;
    padding-bottom: 1rem;
    caption-side: bottom;
    text-align: left;
    color: #8898aa;
}

th {
    text-align: inherit;
}

/* button {
    border-radius: 0;
}

button:focus {
    outline: 1px dotted;
    outline: 5px auto -webkit-focus-ring-color;
}

input,
button {
    font-family: inherit;
    font-size: inherit;
    line-height: inherit;
    margin: 0;
}

button,
input {
    overflow: visible;
}

button {
    text-transform: none;
}

button,
[type='reset'],
[type='submit'] {
    -webkit-appearance: button;
}

button::-moz-focus-inner,
[type='button']::-moz-focus-inner,
[type='reset']::-moz-focus-inner,
[type='submit']::-moz-focus-inner {
    padding: 0;
    border-style: none;
} */

input[type='radio'],
input[type='checkbox'] {
    box-sizing: border-box;
    padding: 0;
}

input[type='date'],
input[type='time'],
input[type='datetime-local'],
input[type='month'] {
    -webkit-appearance: listbox;
}

legend {
    font-size: 1.5rem;
    line-height: inherit;
    display: block;
    width: 100%;
    max-width: 100%;
    margin-bottom: .5rem;
    padding: 0;
    white-space: normal;
    color: inherit;
}

progress {
    vertical-align: baseline;
}

[type='number']::-webkit-inner-spin-button,
[type='number']::-webkit-outer-spin-button {
    height: auto;
}

[type='search'] {
    outline-offset: -2px;
    -webkit-appearance: none;
}

[type='search']::-webkit-search-cancel-button,
[type='search']::-webkit-search-decoration {
    -webkit-appearance: none;
}

::-webkit-file-upload-button {
    font: inherit;
    -webkit-appearance: button;
}

[hidden] {
    display: none !important;
}

h2,
h3,
.h2,
.h3 {
    font-family: inherit;
    font-weight: 600;
    line-height: 1.5;
    margin-bottom: .5rem;
    color: #32325d;
}

h2,
.h2 {
    font-size: 1.25rem;
}

h3,
.h3 {
    font-size: 1.0625rem;
}

.container {
    width: 100%;
    margin-right: auto;
    margin-left: auto;
    padding-right: 15px;
    padding-left: 15px;
}

@media (min-width: 576px) {
    .container {
        max-width: 540px;
    }
}

@media (min-width: 768px) {
    .container {
        max-width: 720px;
    }
}

@media (min-width: 992px) {
    .container {
        max-width: 960px;
    }
}

@media (min-width: 1200px) {
    .container {
        max-width: 1140px;
    }
}

.row {
    display: flex;
    margin-right: -15px;
    margin-left: -15px;
    flex-wrap: wrap;
}

.col,
.col-xl-6 {
    position: relative;
    width: 100%;
    min-height: 1px;
    padding-right: 15px;
    padding-left: 15px;
}

.col {
    max-width: 100%;
    flex-basis: 0;
    flex-grow: 1;
}

@media (min-width: 1200px) {

    .col-xl-6 {
        max-width: 50%;
        flex: 0 0 50%;
    }
}

.table {
    width: 100%;
    margin-bottom: 1rem;
    background-color: transparent;
}

.table th,
.table td {
    padding: 1rem;
    vertical-align: top;
    border-top: 1px solid #e9ecef;
}

.table thead th {
    vertical-align: bottom;
    border-bottom: 2px solid #e9ecef;
}

.table tbody + tbody {
    border-top: 2px solid #e9ecef;
}

.table .table {
    background-color: #f8f9fe;
}

.table-dark,
.table-dark > th,
.table-dark > td {
    background-color: #c1c2c3;
}

.table .thead-dark th {
    color: #f8f9fe;
    border-color: #1f3a68;
    background-color: #172b4d;
}

.table .thead-light th {
    color: #8898aa;
    border-color: #e9ecef;
    background-color: #f6f9fc;
}

.table-dark {
    color: #f8f9fe;
    background-color: #172b4d;
}

.table-dark th,
.table-dark td,
.table-dark thead th {
    border-color: #1f3a68;
}

.table-responsive {
    display: block;
    overflow-x: auto;
    width: 100%;
    -webkit-overflow-scrolling: touch;
    -ms-overflow-style: -ms-autohiding-scrollbar;
}

.btn {
    font-size: 1rem;
    font-weight: 600;
    line-height: 1.5;
    display: inline-block;
    padding: .625rem 1.25rem;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    transition: color .15s ease-in-out, background-color .15s ease-in-out, border-color .15s ease-in-out, box-shadow .15s ease-in-out;
    text-align: center;
    vertical-align: middle;
    white-space: nowrap;
    border: 1px solid transparent;
    border-radius: .375rem;
}

@media screen and (prefers-reduced-motion: reduce) {
    .btn {
        transition: none;
    }
}

.btn:hover,
.btn:focus {
    text-decoration: none;
}

.btn:focus {
    outline: 0;
    box-shadow: 0 7px 14px rgba(50, 50, 93, .1), 0 3px 6px rgba(0, 0, 0, .08);
}

.btn.disabled,
.btn:disabled {
    opacity: .65;
    box-shadow: none;
}

.btn:not(:disabled):not(.disabled) {
    cursor: pointer;
}

.btn:not(:disabled):not(.disabled):active,
.btn:not(:disabled):not(.disabled).active {
    box-shadow: none;
}

.btn:not(:disabled):not(.disabled):active:focus,
.btn:not(:disabled):not(.disabled).active:focus {
    box-shadow: 0 7px 14px rgba(50, 50, 93, .1), 0 3px 6px rgba(0, 0, 0, .08), none;
}

a.btn.disabled {
    pointer-events: none;
}

.btn-sm {
    font-size: .875rem;
    line-height: 1.5;
    padding: .25rem .5rem;
    border-radius: .375rem;
}

.dropdown {
    position: relative;
}

.dropdown-menu {
    font-size: 1rem;
    position: absolute;
    z-index: 1000;
    top: 100%;
    left: 0;
    display: none;
    float: left;
    min-width: 10rem;
    margin: .125rem 0 0;
    padding: .5rem 0;
    list-style: none;
    text-align: left;
    color: #525f7f;
    border: 0 solid rgba(0, 0, 0, .15);
    border-radius: .4375rem;
    background-color: #fff;
    background-clip: padding-box;
    box-shadow: 0 50px 100px rgba(50, 50, 93, .1), 0 15px 35px rgba(50, 50, 93, .15), 0 5px 15px rgba(0, 0, 0, .1);
}

.dropdown-menu-right {
    right: 0;
    left: auto;
}

.dropdown-menu[x-placement^='top'],
.dropdown-menu[x-placement^='right'],
.dropdown-menu[x-placement^='bottom'],
.dropdown-menu[x-placement^='left'] {
    right: auto;
    bottom: auto;
}

.dropdown-item {
    font-weight: 400;
    display: block;
    clear: both;
    width: 100%;
    padding: .25rem 1.5rem;
    text-align: inherit;
    white-space: nowrap;
    color: #212529;
    border: 0;
    background-color: transparent;
}

.dropdown-item:hover,
.dropdown-item:focus {
    text-decoration: none;
    color: #16181b;
    background-color: #f6f9fc;
}

.dropdown-item.active,
.dropdown-item:active {
    text-decoration: none;
    color: #fff;
    background-color: #5e72e4;
}

.dropdown-item.disabled,
.dropdown-item:disabled {
    color: #8898aa;
    background-color: transparent;
}

.nav {
    display: flex;
    margin-bottom: 0;
    padding-left: 0;
    list-style: none;
    flex-wrap: wrap;
}

.card {
    position: relative;
    display: flex;
    flex-direction: column;
    min-width: 0;
    word-wrap: break-word;
    border: 1px solid rgba(0, 0, 0, .05);
    border-radius: .375rem;
    background-color: #fff;
    background-clip: border-box;
}

.card-header {
    margin-bottom: 0;
    padding: 1.25rem 1.5rem;
    border-bottom: 1px solid rgba(0, 0, 0, .05);
    background-color: #fff;
}

.card-header:first-child {
    border-radius: calc(.375rem - 1px) calc(.375rem - 1px) 0 0;
}

.card-footer {
    padding: 1.25rem 1.5rem;
    border-top: 1px solid rgba(0, 0, 0, .05);
    background-color: #fff;
}

.card-footer:last-child {
    border-radius: 0 0 calc(.375rem - 1px) calc(.375rem - 1px);
}

.pagination {
    display: flex;
    padding-left: 0;
    list-style: none;
    border-radius: .375rem;
}

.page-link {
    line-height: 1.25;
    position: relative;
    display: block;
    margin-left: -1px;
    padding: .5rem .75rem;
    color: #8898aa;
    border: 1px solid #dee2e6;
    background-color: #fff;
}

.page-link:hover {
    z-index: 2;
    text-decoration: none;
    color: #8898aa;
    border-color: #dee2e6;
    background-color: #dee2e6;
}

.page-link:focus {
    z-index: 2;
    outline: 0;
    box-shadow: none;
}

.page-link:not(:disabled):not(.disabled) {
    cursor: pointer;
}

.page-item:first-child .page-link {
    margin-left: 0;
    border-top-left-radius: .375rem;
    border-bottom-left-radius: .375rem;
}

.page-item:last-child .page-link {
    border-top-right-radius: .375rem;
    border-bottom-right-radius: .375rem;
}

.page-item.active .page-link {
    z-index: 1;
    color: #fff;
    border-color: #5e72e4;
    background-color: #5e72e4;
}

.page-item.disabled .page-link {
    cursor: auto;
    pointer-events: none;
    color: #8898aa;
    border-color: #dee2e6;
    background-color: #fff;
}

.badge {
    font-size: 66%;
    font-weight: 600;
    line-height: 1;
    display: inline-block;
    padding: .35rem .375rem;
    text-align: center;
    vertical-align: baseline;
    white-space: nowrap;
    border-radius: .375rem;
}

.badge:empty {
    display: none;
}

.btn .badge {
    position: relative;
    top: -1px;
}

@keyframes progress-bar-stripes {
    from {
        background-position: 1rem 0;
    }

    to {
        background-position: 0 0;
    }
}

.progress {
    font-size: .75rem;
    display: flex;
    overflow: hidden;
    height: 1rem;
    border-radius: .375rem;
    background-color: #e9ecef;
    box-shadow: inset 0 .1rem .1rem rgba(0, 0, 0, .1);
}

.progress-bar {
    display: flex;
    flex-direction: column;
    transition: width .6s ease;
    text-align: center;
    white-space: nowrap;
    color: #fff;
    background-color: #5e72e4;
    justify-content: center;
}

@media screen and (prefers-reduced-motion: reduce) {
    .progress-bar {
        transition: none;
    }
}

.media {
    display: flex;
    align-items: flex-start;
}

.media-body {
    flex: 1 1;
}

.tooltip {
    font-family: Open Sans, sans-serif;
    font-size: .875rem;
    font-weight: 400;
    font-style: normal;
    line-height: 1.5;
    position: absolute;
    z-index: 1070;
    display: block;
    margin: 0;
    text-align: left;
    text-align: start;
    white-space: normal;
    text-decoration: none;
    letter-spacing: normal;
    word-spacing: normal;
    text-transform: none;
    word-wrap: break-word;
    word-break: normal;
    opacity: 0;
    text-shadow: none;
    line-break: auto;
}

.tooltip.show
{
    opacity: .9;
}
.tooltip .arrow
{
    position: absolute;

    display: block;

    width: .8rem;
    height: .4rem;
}
.tooltip .arrow::before
{
    position: absolute;

    content: '';

    border-style: solid;
    border-color: transparent;
}

.bs-tooltip-top,
.bs-tooltip-auto[x-placement^='top']
{
    padding: .4rem 0;
}
.bs-tooltip-top .arrow,
.bs-tooltip-auto[x-placement^='top'] .arrow
{
    bottom: 0;
}
.bs-tooltip-top .arrow::before,
.bs-tooltip-auto[x-placement^='top'] .arrow::before
{
    top: 0;

    border-width: .4rem .4rem 0;
    border-top-color: #000;
}

.bs-tooltip-right,
.bs-tooltip-auto[x-placement^='right']
{
    padding: 0 .4rem;
}
.bs-tooltip-right .arrow,
.bs-tooltip-auto[x-placement^='right'] .arrow
{
    left: 0;

    width: .4rem;
    height: .8rem;
}
.bs-tooltip-right .arrow::before,
.bs-tooltip-auto[x-placement^='right'] .arrow::before
{
    right: 0;

    border-width: .4rem .4rem .4rem 0;
    border-right-color: #000;
}

.bs-tooltip-bottom,
.bs-tooltip-auto[x-placement^='bottom']
{
    padding: .4rem 0;
}
.bs-tooltip-bottom .arrow,
.bs-tooltip-auto[x-placement^='bottom'] .arrow
{
    top: 0;
}
.bs-tooltip-bottom .arrow::before,
.bs-tooltip-auto[x-placement^='bottom'] .arrow::before
{
    bottom: 0;

    border-width: 0 .4rem .4rem;
    border-bottom-color: #000;
}

.bs-tooltip-left,
.bs-tooltip-auto[x-placement^='left']
{
    padding: 0 .4rem;
}
.bs-tooltip-left .arrow,
.bs-tooltip-auto[x-placement^='left'] .arrow
{
    right: 0;

    width: .4rem;
    height: .8rem;
}
.bs-tooltip-left .arrow::before,
.bs-tooltip-auto[x-placement^='left'] .arrow::before
{
    left: 0;

    border-width: .4rem 0 .4rem .4rem;
    border-left-color: #000;
}

.tooltip-inner
{
    max-width: 200px;
    padding: .25rem .5rem;

    text-align: center;

    color: #fff;
    border-radius: .375rem;
    background-color: #000;
}

.bg-success {
    background-color: #2dce89 !important;
}

a.bg-success:hover,
a.bg-success:focus,
button.bg-success:hover,
button.bg-success:focus {
    background-color: #24a46d !important;
}

.bg-info {
    background-color: #11cdef !important;
}

a.bg-info:hover,
a.bg-info:focus,
button.bg-info:hover,
button.bg-info:focus {
    background-color: #0da5c0 !important;
}

.bg-warning {
    background-color: #fb6340 !important;
}

a.bg-warning:hover,
a.bg-warning:focus,
button.bg-warning:hover,
button.bg-warning:focus {
    background-color: #fa3a0e !important;
}

.bg-danger {
    background-color: #f5365c !important;
}

a.bg-danger:hover,
a.bg-danger:focus,
button.bg-danger:hover,
button.bg-danger:focus {
    background-color: #ec0c38 !important;
}

.bg-default {
    background-color: #172b4d !important;
}

a.bg-default:hover,
a.bg-default:focus,
button.bg-default:hover,
button.bg-default:focus {
    background-color: #0b1526 !important;
}

.bg-transparent {
    background-color: transparent !important;
}

.border-0 {
    border: 0 !important;
}

.rounded-circle {
    border-radius: 50% !important;
}

.d-flex {
    display: flex !important;
}

.justify-content-end {
    justify-content: flex-end !important;
}

.align-items-center {
    align-items: center !important;
}

@media (min-width: 1200px) {

    .justify-content-xl-between {
        justify-content: space-between !important;
    }
}

.sr-only {
    position: absolute;
    overflow: hidden;
    clip: rect(0, 0, 0, 0);
    width: 1px;
    height: 1px;
    padding: 0;
    white-space: nowrap;
    border: 0;
}

.shadow {
    box-shadow: 0 0 2rem 0 rgba(136, 152, 170, .15) !important;
}

.mb-0 {
    margin-bottom: 0 !important;
}

.mr-2 {
    margin-right: .5rem !important;
}

.mr-3 {
    margin-right: 1rem !important;
}

.mr-4 {
    margin-right: 1.5rem !important;
}

.mt-5 {
    margin-top: 3rem !important;
}

.mb-5 {
    margin-bottom: 3rem !important;
}

.mt-7 {
    margin-top: 6rem !important;
}

.py-4 {
    padding-top: 1.5rem !important;
}

.py-4 {
    padding-bottom: 1.5rem !important;
}

.m-auto {
    margin: auto !important;
}

.text-right {
    text-align: right !important;
}

.text-center {
    text-align: center !important;
}

.text-white {
    color: #fff !important;
}

.text-light {
    color: #adb5bd !important;
}

a.text-light:hover,
a.text-light:focus {
    color: #919ca6 !important;
}

.text-white {
    color: #fff !important;
}

a.text-white:hover,
a.text-white:focus {
    color: #e6e6e6 !important;
}

@media print {
    *,
  *::before,
  *::after {
        box-shadow: none !important;
        text-shadow: none !important;
    }

    a:not(.btn) {
        text-decoration: underline;
    }

    thead {
        display: table-header-group;
    }

    tr,
  img {
        page-break-inside: avoid;
    }

    p,
  h2,
  h3 {
        orphans: 3;
        widows: 3;
    }

    h2,
  h3 {
        page-break-after: avoid;
    }

@page {
        size: a3;
    }

    body {
        min-width: 992px !important;
    }

    .container {
        min-width: 992px !important;
    }

    .badge {
        border: 1px solid #000;
    }

    .table {
        border-collapse: collapse !important;
    }

    .table td,
  .table th {
        background-color: #fff !important;
    }

    .table-dark {
        color: inherit;
    }

    .table-dark th,
  .table-dark td,
  .table-dark thead th,
  .table-dark tbody + tbody {
        border-color: #e9ecef;
    }

    .table .thead-dark th {
        color: inherit;
        border-color: #e9ecef;
    }
}

figcaption,
main {
    display: block;
}

main {
    overflow: hidden;
}

@keyframes floating-lg {
    0% {
        transform: translateY(0px);
    }

    50% {
        transform: translateY(15px);
    }

    100% {
        transform: translateY(0px);
    }
}

@keyframes floating {
    0% {
        transform: translateY(0px);
    }

    50% {
        transform: translateY(10px);
    }

    100% {
        transform: translateY(0px);
    }
}

@keyframes floating-sm {
    0% {
        transform: translateY(0px);
    }

    50% {
        transform: translateY(5px);
    }

    100% {
        transform: translateY(0px);
    }
}

[class*='shadow'] {
    transition: all .15s ease;
}

.text-sm {
    font-size: .875rem !important;
}

.text-white {
    color: #fff !important;
}

a.text-white:hover,
a.text-white:focus {
    color: #e6e6e6 !important;
}

.text-light {
    color: #ced4da !important;
}

a.text-light:hover,
a.text-light:focus {
    color: #b1bbc4 !important;
}

.avatar {
    font-size: 1rem;
    display: inline-flex;
    width: 48px;
    height: 48px;
    color: #fff;
    border-radius: 50%;
    background-color: #adb5bd;
    align-items: center;
    justify-content: center;
}

.avatar img {
    width: 100%;
    border-radius: 50%;
}

.avatar-sm {
    font-size: .875rem;
    width: 36px;
    height: 36px;
}

.avatar-group .avatar {
    position: relative;
    z-index: 2;
    border: 2px solid #fff;
}

.avatar-group .avatar:hover {
    z-index: 3;
}

.avatar-group .avatar + .avatar {
    margin-left: -1rem;
}

.badge {
    text-transform: uppercase;
}

.badge a {
    color: #fff;
}

.btn .badge:not(:first-child) {
    margin-left: .5rem;
}

.btn .badge:not(:last-child) {
    margin-right: .5rem;
}

.badge-dot {
    font-size: .875rem;
    font-weight: 400;
    padding-right: 0;
    padding-left: 0;
    text-transform: none;
    background: transparent;
}

.badge-dot strong {
    color: #32325d;
}

.badge-dot i {
    display: inline-block;
    width: .375rem;
    height: .375rem;
    margin-right: .375rem;
    vertical-align: middle;
    border-radius: 50%;
}

.btn {
    font-size: .875rem;
    position: relative;
    transition: all .15s ease;
    letter-spacing: .025em;
    text-transform: none;
    will-change: transform;
}

.btn:hover {
    transform: translateY(-1px);
    box-shadow: 0 7px 14px rgba(50, 50, 93, .1), 0 3px 6px rgba(0, 0, 0, .08);
}

.btn:not(:last-child) {
    margin-right: .5rem;
}

.btn i:not(:first-child) {
    margin-left: .5rem;
}

.btn i:not(:last-child) {
    margin-right: .5rem;
}

.btn-sm {
    font-size: .75rem;
}

[class*='btn-outline-'] {
    border-width: 1px;
}

.btn-icon-only {
    width: 2.375rem;
    height: 2.375rem;
    padding: 0;
}

a.btn-icon-only {
    line-height: 2.5;
}

.btn-icon-only.btn-sm {
    width: 2rem;
    height: 2rem;
}

.main-content {
    position: relative;
}

.dropdown {
    display: inline-block;
}

.dropdown-menu {
    min-width: 12rem;
}
.dropdown-menu.show
{
    display: block;
}


.dropdown-menu .dropdown-item {
    font-size: .875rem;
    padding: .5rem 1rem;
}

.dropdown-menu .dropdown-item > i {
    font-size: 1rem;
    margin-right: 1rem;
    vertical-align: -17%;
}

.dropdown-menu a.media > div:first-child {
    line-height: 1;
}

.dropdown-menu a.media p {
    color: #8898aa;
}

.dropdown-menu a.media:hover p {
    color: #172b4d !important;
}

.footer {
    padding: 2.5rem 0;
    background: #f7fafc;
}

.footer .copyright {
    font-size: .875rem;
}

@media (min-width: 768px) {

@keyframes show-navbar-dropdown {
        0% {
      transition: visibility .25s, opacity .25s, transform .25s;
        transform: translate(0, 10px) perspective(200px) rotateX(-2deg);
        opacity: 0;
    }

    100% {
        transform: translate(0, 0);
        opacity: 1;
    }
}

@keyframes hide-navbar-dropdown {
    from {
        opacity: 1;
    }

    to {
        transform: translate(0, 10px);
        opacity: 0;
    }
}
}

@keyframes show-navbar-collapse {
    0% {
        transform: scale(.95);
        transform-origin: 100% 0;
        opacity: 0;
    }

    100% {
        transform: scale(1);
        opacity: 1;
    }
}

@keyframes hide-navbar-collapse {
    from {
        transform: scale(1);
        transform-origin: 100% 0;
        opacity: 1;
    }

    to {
        transform: scale(.95);
        opacity: 0;
    }
}

.page-item.active .page-link {
    box-shadow: 0 7px 14px rgba(50, 50, 93, .1), 0 3px 6px rgba(0, 0, 0, .08);
}

.page-item .page-link,
.page-item span {
    font-size: .875rem;
    display: flex;
    width: 36px;
    height: 36px;
    margin: 0 3px;
    padding: 0;
    border-radius: 50% !important;
    align-items: center;
    justify-content: center;
}

.progress {
    overflow: hidden;
    height: 8px;
    margin-bottom: 1rem;
    border-radius: .25rem;
    background-color: #e9ecef;
    box-shadow: inset 0 1px 2px rgba(0, 0, 0, .1);
}

.progress .sr-only {
    font-size: 13px;
    line-height: 20px;
    left: 0;
    clip: auto;
    width: auto;
    height: 20px;
    margin: 0 0 0 30px;
}

.progress-bar {
    height: auto;
    border-radius: 0;
    box-shadow: none;
}

.table thead th {
    font-size: .65rem;
    padding-top: .75rem;
    padding-bottom: .75rem;
    letter-spacing: 1px;
    text-transform: uppercase;
    border-bottom: 1px solid #e9ecef;
}

.table th {
    font-weight: 600;
}

.table td .progress {
    width: 120px;
    height: 3px;
    margin: 0;
}

.table td,
.table th {
    font-size: .8125rem;
    white-space: nowrap;
}

.table.align-items-center td,
.table.align-items-center th {
    vertical-align: middle;
}

.table .thead-dark th {
    color: #4d7bca;
    background-color: #1c345d;
}

.table .thead-light th {
    color: #8898aa;
    background-color: #f6f9fc;
}

.table-flush td,
.table-flush th {
    border-right: 0;
    border-left: 0;
}

.table-flush tbody tr:first-child td,
.table-flush tbody tr:first-child th {
    border-top: 0;
}

.table-flush tbody tr:last-child td,
.table-flush tbody tr:last-child th {
    border-bottom: 0;
}

.card .table {
    margin-bottom: 0;
}

.card .table td,
.card .table th {
    padding-right: 1.5rem;
    padding-left: 1.5rem;
}

p {
    font-size: 1rem;
    font-weight: 300;
    line-height: 1.7;
}

@media (max-width: 768px) {
    .btn {
        margin-bottom: 10px;
    }
}

.select-num {
    background-color:#fff0f0;
    border:2px solid #b18597;
    border-radius: 10px;
}

button {
  position: relative;
  display: inline-block;
  cursor: pointer;
  outline: none;
  border: 0;
  vertical-align: middle;
  text-decoration: none;
  font-size: inherit;
  font-family: inherit;
}
button.learn-more {
  font-weight: 600;
  color: #382b22;
  text-transform: uppercase;
  padding: 1.25em 2em;
  background: #fff0f0;
  border: 2px solid #b18597;
  border-radius: 0.75em;
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
  -webkit-transition: background 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: background 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more::before {
  position: absolute;
  content: '';
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #f9c4d2;
  border-radius: inherit;
  box-shadow: 0 0 0 2px #b18597, 0 0.625em 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0.75em, -1em);
          transform: translate3d(0, 0.75em, -1em);
  -webkit-transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), box-shadow 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more:hover {
  background: #ffe9e9;
  -webkit-transform: translate(0, 0.25em);
          transform: translate(0, 0.25em);
}
button.learn-more:hover::before {
  box-shadow: 0 0 0 2px #b18597, 0 0.5em 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0.5em, -1em);
          transform: translate3d(0, 0.5em, -1em);
}
button.learn-more:active {
  background: #ffe9e9;
  -webkit-transform: translate(0em, 0.75em);
          transform: translate(0em, 0.75em);
}
button.learn-more:active::before {
  box-shadow: 0 0 0 2px #b18597, 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0, -1em);
          transform: translate3d(0, 0, -1em);
}


</style>