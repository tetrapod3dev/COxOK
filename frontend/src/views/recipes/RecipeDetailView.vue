<template>
  <div class="wrapper">
    <section id="top">
      <card type="background" :style="'background-image: url(' + recipeThumbnailSrc + ')'">
        <div class="card-title text-left">
          <h3>{{ recipe.recipeName }}</h3>
        </div>
        <div class="card-footer text-left">
          <div class="stats">
            <span v-popover:popover-fav>
              <i class="fa fa-star-o"></i>
              {{ recipe.avgRating }}
            </span>
            <el-popover
              ref="popover-fav"
              popper-class="popover"
              placement="bottom"
              width="200"
              trigger="hover"
            >
              <div class="popover-body">평점(5점 만점)</div>
            </el-popover>
            <span v-popover:popover-tim class="col-4">
              <i class="now-ui-icons ui-2_time-alarm"></i>
              {{ recipe.cookTime }}
            </span>
            <el-popover
              ref="popover-tim"
              popper-class="popover"
              placement="bottom"
              width="200"
              trigger="hover"
            >
              <div class="popover-body">분</div>
            </el-popover>
            <span v-popover:popover-exc class="col-4">
              <i class="fa fa-exclamation-triangle"></i>
              {{ recipe.level }}
            </span>
            <el-popover
              ref="popover-exc"
              popper-class="popover"
              placement="bottom"
              width="200"
              trigger="hover"
            >
              <div class="popover-body">난이도(5점 만점)</div>
            </el-popover>
          </div>
        </div>
      </card>
    </section>
    <!--     *********    TEAM 1     *********      -->
    <div class="team-1">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto text-left">
            <h2 class="title">{{ recipe.recipeName }}</h2>
            <h4 class="description">{{ recipe.recipeDetail }}</h4>
          </div>
        </div>
        <div class="row"></div>
        <div class="row justify-content-center">
          <div class="col-md-4 ml-1">
            <div class="col-md-8 ml-auto mr-auto">
              <h3 class="text-left mb-3">목차</h3>
              <p @click="scrollDoc('ingredientInfo')" class="text-left text-link">1. 재료 정보</p>
              <p @click="scrollDoc('nutrientInfo')" class="text-left text-link">2. 영양소 정보</p>
              <p @click="scrollDoc('detailInfo')" class="text-left text-link">3. 상세 요리 과정</p>
              <p @click="scrollDoc('reviewInfo')" class="text-left text-link">4. 한줄평 보기</p>
            </div>
          </div>
          <div class="col-md-4 mr-1">
            <div class="col-md-12 ml-auto mr-auto">
              <h3 class="text-left mb-3">재료 정보</h3>
              <div class="row">
                <div
                  v-for="(ingredient, index) in recipe.ingredientList"
                  :key="ingredient.name"
                  class="col-6 my-3"
                >
                  <div class="row">
                    <input @click="checkIngredient(index, $event)" type="checkbox" />
                    <p :id="makeId(index)">{{ ingredient.name }}</p>
                    <p>{{ ingredient.amount }} {{ingredient.unit}}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--     *********    END TEAM 1      *********      -->

    <section id="nutrientInfo">
      <div class="container">
        <div class="row">
          <div class="col-md-10 ml-auto mr-auto">
            <h3 class="text-left pt-5 mb-3">영양소 정보</h3>
            <div class="row">
              <RadarGraph :recipeDataSet="recipeDataSet" class="col-4" />
              <div class="col-8 row p-4">
                <div
                  v-for="(amount, nutrient, index) in recipeDataSet"
                  :key="nutrient"
                  class="col-6 row my-4"
                >
                  <button class="btn btn-outline-primary col-4 offset-2">{{ nutrient }}</button>
                  <p class="col-4 align-self-center m-0">{{ amount }} {{ unitList[index] }}</p>
                </div>
              </div>
            </div>
            <p class="text-left">
              <i class="fas fa-exclamation-triangle fa-1x" style="color: red;"></i>영양소 정보는 실제와 차이가 있을 수 있습니다.
            </p>
          </div>
        </div>
      </div>
    </section>
    <div class="blogs-3">
      <div class="container">
        <div class="row">
          <div class="col-md-10 ml-auto mr-auto">
            <h2 class="title">상세 요리 과정</h2>
            <br />
            <card
              type="blog"
              plain
              v-for="(recipePhoto, index) in recipe.recipePhotoList"
              :key="index"
            >
              <div class="row">
                <div class="col-md-4">
                  <div class="card-image">
                    <img class="img img-raised" :src="imageSrc(recipePhoto)" />
                  </div>
                </div>
                <div class="col-md-8 text-left">
                  <h3 class="card-title">{{index + 1}}</h3>
                  <h4 class="card-description" style="color:#000000">{{recipePhoto.photoDetail }}</h4>
                </div>
              </div>
            </card>
          </div>
        </div>
      </div>
    </div>
    <div class="section section-comments">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto">
            <div class="media-area">
              <h3 class="title text-center">한줄평</h3>
              <ReviewList :reviewList="recipe.reviewDtoList" />
            </div>

            <div class="media media-post">
              <div class="media-body">
                <!-- 
                v-model="form.comment"-->

                <ReviewMake @submitReview="submitReview" />
                <div class="media-footer">
                  <span @click="copyRecipe" class="pull-left">
                    <n-button type="info" round block>
                      <i class="fas fa-share-alt"></i> 공유
                    </n-button>
                  </span>
                  <span @click="changeLike" class="pull-left">
                    <n-button type="danger" round block>
                      <i :class="isLiked ? 'fa fa-heart' : 'fa fa-heart-o'" aria-hidden="true"></i>
                      {{ likeCnt }}
                    </n-button>
                  </span>
                </div>
              </div>
            </div>
            <!-- end media-post -->
          </div>
        </div>
      </div>
    </div>
    <button @click="scrollDoc('top')" id="scroll-top-btn" class="btn btn-outline-primary">
      <i class="fas fa-angle-up"></i>
    </button>
  </div>
</template>

<script>
import RadarGraph from "../../components/recipes/RadarGraph.vue";
import ReviewMake from "../../components/recipes/ReviewMake.vue";
import ReviewList from "../../components/recipes/ReviewList.vue";
import { Card, Button } from "@/components/global";
import { Popover } from "element-ui";

import SERVER from "@/api/api";
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  name: "RecipeItemView",
  components: {
    RadarGraph,
    ReviewMake,
    ReviewList,
    Card,
    [Button.name]: Button,
    [Popover.name]: Popover,
  },
  data() {
    return {
      recipe: {},
      recipeDataSet: {},
      likeCnt: null,
      isLiked: null,
      unitList: ["kcal", "g", "g", "mg", "g", "g"],
    };
  },
  computed: {
    ...mapGetters(["config", "isLoggedIn"]),
    likeMent() {
      return this.isLiked ? "좋아요 취소" : "좋아요";
    },
    recipeThumbnailSrc() {
      return "http://i3a104.p.ssafy.io/img/" + this.recipe.recipeThumbnailSrc;
    },
  },
  methods: {
    makeId(index) {
      return "ingredient-" + index;
    },
    checkIngredient(index, event) {
      const checkedIngredient = document.querySelector("#ingredient-" + index);

      if (event.target.checked) {
        checkedIngredient.style.setProperty("text-decoration", "line-through");
      } else {
        checkedIngredient.style.setProperty("text-decoration", "none");
      }
    },
    scrollDoc(id) {
      if (id != "top") {
        var location = document.querySelector("#" + id).offsetTop;

        window.scrollTo({ top: location, behavior: "smooth" });
      } else {
        window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
      }
    },
    imageSrc(recipePhoto) {
      return "http://i3a104.p.ssafy.io/img/" + recipePhoto.photoSrc;
    },
    changeLike() {
      if (this.isLiked) {
        this.dislikeRecipe();
      } else {
        this.likeRecipe();
      }
    },
    submitReview(reviewData) {
      reviewData["recipeId"] = this.$route.params.recipe_id;
      axios
        .post(SERVER.URL + SERVER.ROUTES.reviewRegister, reviewData, {
          headers: {
            Authorization: this.config,
          },
        })
        .then(() => {
          this.getRecipe();
        })
        .catch((err) => console.log(err));
    },
    copyRecipe() {
      var tempCopyEl = document.createElement("textarea");
      tempCopyEl.innerHTML =
        "레시피 제목: " +
        this.recipe.recipeName +
        "\r\n레시피 주소: " +
        "localhost:3000" +
        this.$route.fullPath;
      document.body.appendChild(tempCopyEl);

      tempCopyEl.select();
      document.execCommand("copy");
      alert("레시피 제목 / 주소 복사 성공!\n\n" + tempCopyEl.innerHTML);
      document.body.removeChild(tempCopyEl);
    },
    likeRecipe() {
      const data = {
        recipeId: this.$route.params.recipe_id,
      };
      axios
        .post(SERVER.URL + SERVER.ROUTES.recipeLike, data, {
          headers: {
            Authorization: this.config,
          },
        })
        .then((res) => {
          this.likeCnt = res.data.likeCnt;
          this.isLiked = !this.isLiked;
        })
        .catch((err) => console.log(err));
    },
    dislikeRecipe() {
      axios
        .delete(SERVER.URL + SERVER.ROUTES.recipeDislike, {
          headers: {
            Authorization: this.config,
          },
          data: {
            recipeId: this.$route.params.recipe_id,
          },
        })
        .then((res) => {
          this.likeCnt = res.data.likeCnt;
          this.isLiked = !this.isLiked;
        })
        .catch((err) => console.log(err));
    },
    getRecipe() {
      if (!this.isLoggedIn) {
        axios
          .get(
            SERVER.URL +
              SERVER.ROUTES.recipeDetail +
              this.$route.params.recipe_id
          )
          .then((res) => {
            console.log(res);
            this.recipe = res.data.recipe;
            this.recipeDataSet = {
              calorie: res.data.recipe.calorie,
              carbon: res.data.recipe.carbon,
              fat: res.data.recipe.fat,
              natrium: res.data.recipe.natrium,
              protein: res.data.recipe.protein,
              sugar: res.data.recipe.sugar,
            };

            this.likeCnt = res.data.likeCnt;
            this.isLiked = res.data.userLike;
          })
          .catch((err) => console.log(err.response));
      } else {
        axios
          .get(
            SERVER.URL +
              SERVER.ROUTES.recipeDetail +
              this.$route.params.recipe_id,
            {
              headers: {
                Authorization: this.config,
              },
            }
          )
          .then((res) => {
            console.log(res);
            this.recipe = res.data.recipe;
            this.recipeDataSet = {
              calorie: res.data.recipe.calorie,
              carbon: res.data.recipe.carbon,
              fat: res.data.recipe.fat,
              natrium: res.data.recipe.natrium,
              protein: res.data.recipe.protein,
              sugar: res.data.recipe.sugar,
            };

            this.likeCnt = res.data.likeCnt;
            this.isLiked = res.data.userLike;
          })
          .catch((err) => console.log(err.response));
      }
    },
  },
  created() {
    this.getRecipe();
  },
  mounted() {},
};
</script>

<style scoped>
.top {
  background-color: gainsboro;
}

#scroll-top-btn {
  position: fixed;
  bottom: 100px;
  right: 100px;
}

.text-link:hover {
  cursor: pointer;
}
</style>