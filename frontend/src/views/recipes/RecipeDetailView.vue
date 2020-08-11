<template>
  <div class="wrapper">
    <section id="top">
      <card type="background" style="background-image: url('https://previews.123rf.com/images/seamartini/seamartini1609/seamartini160900068/62637928-cooking-and-kitchen-utensils-seamless-background-wallpaper-with-vector-pattern-icons-of-pizza-bread-.jpg') ;" >
        <div class="card-title text-left">
          <h1 style="">{{ recipe.recipeName }}</h1>
        </div>
        <div class="card-footer text-left">
          <div class="stats">
            <span style="font-size:25px;">
              <i class="fas fa-edit"></i>
              {{ recipe.nickname }}
            </span>
          </div>
        </div>
      </card>
    </section>
    <!--     *********    TEAM 1     *********      -->
    <div class="team-1" id="recipeInfo">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto">
            <img class="img img-raised mb-5" :src="recipeThumbnailSrc" />
            <h3 class="description mb-5">{{ recipe.recipeDetail }}</h3>
          </div>
        </div>
        <div class="row mt-5 mb-5">
          <div class="col-md-3 offset-3" style="font-size:30px;" >
            <i class="now-ui-icons ui-2_time-alarm" style="font-size:60px;"></i><br>
            {{ recipe.cookTime }}분
          </div>
          <div class="col-md-3" style="font-size:30px;">
            <i class="fa fa-exclamation-triangle" style="font-size:60px;"></i><br>
            {{ recipe.level }}
          </div>
        </div>
        <div class="row justify-content-center mt-5">
          <div class="col-md-4 ml-1">
            <div class="col-md-8 ml-auto mr-auto">
              <h3 class="text-left mb-3">목차</h3>
              <p @click="scrollDoc('recipeInfo')" class="text-left text-link">1. 레시피 설명</p>
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
                    <input @click="checkIngredient(index, $event)" type="checkbox" class="align-self-center mr-2"/>
                    <p :id="makeId(index)" class="mb-0">{{ ingredient.name }} {{ ingredient.amount }} {{ingredient.unit}}</p>
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
    <div class="blogs-3" id="detailInfo">
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

    <div v-if="loginUserId == recipe.userId">
      <router-link :to="{ name: 'RecipeUpdateView', params: { recipe_id: recipe.recipeId } }">
        <button class="btn btn-primary">수정</button>
      </router-link>
      <button class="btn btn-primary" @click="deleteRecipe">삭제</button>
    </div>
    <div class="section section-comments" id="reviewInfo">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto">
            <h3 class="title text-center mb-3">평점</h3>
            <div class="avgRating">
              <b-form-rating id="rating-lg rating-inline" inline value="4" size="lg" v-model="recipe.avgRating" no-border variant="warning" readonly></b-form-rating>
            </div>
            <div class="media-area mt-3">
              <h3 class="title text-center">한줄평</h3>
              <ReviewList :loginUserId="loginUserId" :reviewList="recipe.reviewDtoList" @deleteReview="deleteReview" @modifyMod="modifyMod" @updateReview="updateReview" />
              <p v-if="recipe.reviewDtoList.length == 0">작성된 한줄평이 없습니다.</p>
            </div>

            <div class="media media-post">
              <div class="media-body">
                <!-- 
                v-model="form.comment"-->

                <ReviewMake v-if="(loginUserId > 0) && !isReviewed" @submitReview="submitReview" />
                <h3 v-else>한줄평을 작성하기 위해서는 로그인을 해주세요.</h3>

                <div class="media-footer">
                  <span @click="copyRecipe" class="pull-left">
                    <n-button type="info" round block>
                      <i class="fas fa-share-alt"></i> 공유
                    </n-button>
                  </span>
                  <span @click="changeLike" class="pull-left">
                    <n-button v-show="(loginUserId > 0)" type="danger" round block>
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
import { mapGetters, mapActions } from "vuex";

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
      recipeDataSet: {},
      likeCnt: null,
      isLiked: null,
      loginUserId: null,
      recipe: {
        'reviewDtoList': [],
      },
      unitList: ["kcal", "g", "g", "mg", "g", "g"],
      tempUserId: [],
    };
  },
  computed: {
    ...mapGetters(["config", "isLoggedIn"]),
    likeMent() {
      return this.isLiked ? "좋아요 취소" : "좋아요";
    },
    recipeThumbnailSrc() {
      return SERVER.IMAGE_URL + this.recipe.recipeThumbnailSrc;
    },
    avgRating() {
      return Math.round(this.recipe.avgRating * 10) / 10
    },
    reviewedUser() {
      return this.recipe.reviewDtoList.map(reviewDto => reviewDto.userId)
    },
    isReviewed() {
      return (this.reviewedUser.indexOf(this.loginUserId) >= 0) ? true : false
    }
  },
  methods: {
    ...mapActions(['logout']),
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
      return SERVER.IMAGE_URL + recipePhoto.photoSrc;
    },
    changeLike() {
      if (this.isLiked) {
        this.dislikeRecipe();
      } else {
        this.likeRecipe();
      }
    },
    deleteRecipe() {
      let response = confirm('진짜요??? 에이..설마...')
      if (response) {
        axios.delete(SERVER.URL + SERVER.ROUTES.recipeDelte + this.recipe.recipeId, {
          headers: {
            Authorization: this.config,
          },
        })
        .then(() => {
          this.$router.push({ name:'RecipeListView', params: { pageNum: 1 } });
        })
        .catch(err => {
          if (err.response.status) {
            alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
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
        .catch((err) => {
          if (err.response.status) {
            alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    deleteReview(reviewId) {
      axios.delete(SERVER.URL + SERVER.ROUTES.reviewDelete + reviewId, {
          headers: {
            Authorization: this.config,
          },
        })
        .then(() => {
          this.getRecipe();
        })
        .catch(err => {
          if (err.response.status) {
            alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    modifyMod(review) {
      // this.$set(review, 'changing', true);
      review['changing'] = !review['changing']
    },
    updateReview(review) {
      let reviewData = {
        'rating': review.rating,
        'content': review.content
      }
      axios.put(SERVER.URL + SERVER.ROUTES.reviewUpdate + review.reviewId, reviewData, {
          headers: {
            Authorization: this.config,
          },
        })
        .catch(err => {
          if (err.response.status) {
            alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },

    copyRecipe() {
      var tempCopyEl = document.createElement("textarea");
      tempCopyEl.innerHTML =
        "레시피 제목: " +
        this.recipe.recipeName +
        "\r\n레시피 주소: " +
        SERVER.URL +
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
        .catch((err) => {
          if (err.response.status) {
            alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
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
        .catch((err) => {
          if (err.response.status) {
            alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    getRecipe() {
      let config = (this.config == "Bearer null") ? null : { headers: {Authorization: this.config} }

      axios
        .get(
          SERVER.URL +
            SERVER.ROUTES.recipeDetail +
            this.$route.params.recipe_id, config
        )
        .then((res) => {
          console.log(res)
          this.recipe = res.data.recipe
          this.recipeDataSet = {
            'calorie': res.data.recipe.calorie,
            'carbon': res.data.recipe.carbon,
            'fat': res.data.recipe.fat,
            'natrium': res.data.recipe.natrium,
            'protein': res.data.recipe.protein,
            'sugar': res.data.recipe.sugar,
          };

          this.likeCnt = res.data.likeCnt;
          this.isLiked = res.data.userLike;
          this.loginUserId = res.data.loginUserId

          this.recipe.reviewDtoList.forEach(function (review) {
            review['changing'] = false
          })

        })
        .catch((err) => console.log(err.response));
    },
  },
  created() {
    this.getRecipe();
  },
  mounted() {
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
.top {
  background-color: gainsboro;
}

.wrapper {
  font-family: 'Nanum Gothic', sans-serif;
}

#scroll-top-btn {
  position: fixed;
  bottom: 100px;
  right: 100px;
}

.text-link:hover {
  cursor: pointer;
}

.avgRating {
  font-size: 40px;
}
</style>