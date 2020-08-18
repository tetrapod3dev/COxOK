<template>
  <div class="wrapper">
    <section id="top">
      <card type="background" style="background-image: url('https://previews.123rf.com/images/seamartini/seamartini1609/seamartini160900068/62637928-cooking-and-kitchen-utensils-seamless-background-wallpaper-with-vector-pattern-icons-of-pizza-bread-.jpg') ;" >
        <div class="card-title text-left">
          <h1 style="">{{ recipe.recipeName }}</h1>
        </div>
        <div class="card-footer text-left">
          <div class="stats">
            <i class="fas fa-edit"></i>
            {{ recipe.nickname }}
          </div>
        </div>
      </card>
    </section>

    <div id="idx-btn" class="row mt-5 text-left"> 
      <h4><i class="far fa-list-alt ml-3 mr-2"></i>목차</h4>
      <div>
        <p @click="scrollDoc('ingredientInfo')" class="text-left text-link idx-obj">1. 재료 리스트</p>
        <p @click="scrollDoc('nutrientInfo')" class="text-left text-link idx-obj">2. 영양소 정보</p>
        <p @click="scrollDoc('detailInfo')" class="text-left text-link idx-obj">3. 상세 요리 과정</p>
        <p @click="scrollDoc('reviewInfo')" class="text-left text-link idx-obj">4. 한줄평 보기</p>
      </div>
    </div>
    
    <!--     *********    TEAM 1     *********      -->
     <div class="team-1" id="recipeInfo">
      <div class="container">
        <div class="recipe-info row">
          <div class="col-md-8 ml-auto mr-auto">
            <img class="img img-raised mb-5" :src="recipeThumbnailSrc" />
            <h3 class="description mb-5">{{ recipe.recipeDetail }}</h3>
          </div>
        </div>
        <div class="additinal-info row mt-2 justify-content-center" >
          <div class="col-md-2">
            <i class="now-ui-icons ui-2_time-alarm" v-b-popover.hover="'조리시간'"></i><br>
            {{ recipe.cookTime }}분
          </div>
          <div class="col-md-2">
            <i class="fas fa-fire-alt" v-b-popover.hover="'난이도 (1-5)'"></i><br>
            {{ recipe.level }}
          </div>
        </div>  
         <div>
          <h3 class="title text-center mt-5 mb-1">요리 분류</h3>
          <div class="category">
             <span class="categories"
                v-for="(category) in categories"
                :key="category"
              >
              {{category}}    
             </span>
          </div>
        </div>
        
        <div>
          <h3 class="title text-center mt-5 mb-1">레시피 점수</h3>
          <div class="avgRating">
            <b-form-rating id="rating-lg rating-inline" inline value="4" size="lg" v-model="recipe.avgRating" no-border variant="warning" readonly></b-form-rating>
          </div>
        </div>
        
      </div>
    </div>
    <!--     *********    END TEAM 1      *********      -->

      <section id="ingredientInfo" >
        <div class="container">
          <div class="row">
            <div class="col-md-10 ml-auto mr-auto">
              <h3 class="ingre-title text-left pt-5 mb-3">
                <i class="fas fa-angle-double-right mr-1"></i>
                재료 리스트 
                <span>(2인 기준)</span>
              </h3>
              <div class="row col-md-10 ml-auto">
                <div
                  v-for="(ingredient, index) in recipe.ingredientList"
                  :key="ingredient.name"
                  class="col-12 col-md-6 my-3"
                >
                  <div class="ingre-list row">
                    <!-- <input @click="checkIngredient(index, $event)" type="checkbox" class="align-self-center mr-2"/> -->
                    <p :id="makeId(index)" class="mb-0">
                      <i class="far fa-check-circle"></i>
                      {{ ingredient.name }} {{ ingredient.amount }}{{ingredient.unit}}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

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
                    <b-button variant="info col-5 col-lg-4">{{nutrient}}</b-button>
                    <p class="col-4 align-self-center m-0 text-left">{{ amount }}{{ unitList[index] }}</p>
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
    
    <div class="blogs-3" id="detailInfo">
      <div class="container">
        <div class="row">
          <div class="cook-detail col-md-10 ml-auto mr-auto" >
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
                  <h2 class="card-title">#{{index + 1}}</h2>
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
    <div class="section section-comments" id="reviewInfo" >
      <div class="container">
        <div class="row">
          <div class="review-sec col-md-10 ml-auto mr-auto">
            <div class="media-area">
              <h3 class="title mb-5">
                요리 한줄평
                <span style="color:gray;">({{recipe.reviewDtoList.length}})</span>
              </h3>
              <ReviewList :loginUserId="loginUserId" :reviewList="recipe.reviewDtoList" @deleteReview="deleteReview" @modifyMod="modifyMod" @updateReview="updateReview" />
              <p v-if="recipe.reviewDtoList.length == 0">작성된 한줄평이 없습니다.</p>
            </div>

            <div class="media media-post mt-2">
              <div class="media-body">
                <!-- 
                v-model="form.comment"-->

                <ReviewMake v-if="isLoggedIn && !isReviewed && (loginUserId != recipe.userId)" @submitReview="submitReview" />
                <h5 v-else-if="!isLoggedIn"><i class="fas fa-exclamation-circle mr-2"></i>한줄평을 작성하기 위해서는 로그인을 해주세요.</h5>

                <div class="media-footer col-10">
                  <span @click="changeLike" class="pull-right ml-2">
                    <n-button v-show="isLoggedIn" type="success" round block>
                      <i :class="isLiked ? 'fa fa-heart' : 'fa fa-heart-o'" aria-hidden="true"></i>
                      {{ likeCnt }}
                    </n-button>
                  </span>
                  <span @click="copyRecipe" class="pull-right ml-2">
                    <n-button type="info" round block>
                      <i class="fas fa-share-alt"></i> 공유
                    </n-button>
                  </span>
                  <!-- 수정해야함 신고 -->
                  <span v-if="isLoggedIn" @click="showModal" class="pull-right">
                    <n-button type="danger" round block>
                      <i class="fas fa-bullhorn"></i> 신고
                    </n-button>
                  </span>

                  <b-modal hide-footer ref="report-modal" title="신고하기">
                    <p class="my-4">신고 사유를 선택해주세요.</p>

                    <b-form-select v-model="reportReason" :options="sampleReasons"></b-form-select>

                    <b-form-input v-model="detailReason" placeholder="상세 사유를 적어주세요 (선택 사항)"></b-form-input>

                    <n-button type="danger" round block @click.native="reportRecipe">신고</n-button>
                  </b-modal>
                </div>
              </div>
            </div>
            <!-- end media-post -->
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <!-- 관련 유튜브 영상 위치입니다!! -->
      <h3 class="title">관련 유튜브 영상 보기</h3>

      <div v-if="videoIds.length > 0" class="youtube row">
        <a target="_blank" v-for="(video, index) in videoIds" :key="index" :href="youtubeLink(video)" class="col-4">
          <img :src="videoUrls[index]">
        </a>
      </div>
      
      <div v-if="videoIds.length <= 0">
        <h3>관련 유튜브가 등록되지 않은 레시피입니다. (추후 추가 예정입니다.)</h3>
      </div>

    </div>
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
      widthInterval:'',
      recipeDataSet: {},
      likeCnt: null,
      isLiked: null,
      loginUserId: null,
      recipe: {
        reviewDtoList: [],
        recipeId: 0,
        recipeThumbnailSrc: "dochi.png"
      },
      categories:[],
      unitList: ["kcal", "g", "g", "mg", "g", "g"],
      tempUserId: [],
      videos: [],
      reportReason: null,
      sampleReasons: ['기타', '부적절한 사진', '올바르지 않은 정보'],
      detailReason: null,
      videoIds: [],
      videoUrls: [],
    };
  },
  created() {
    this.getInitRecipe();
    this.getYoutube();
  },
  mounted() {
    window.addEventListener("scroll", this.indexScrollFuncion);
    this.winWidth();
  },
  beforeDestory() {
    window.removeEventListener('scroll', this.indexScrollFuncion);
    clearInterval(this.widthInterval);
  },
  computed: {
    ...mapGetters(["config", "isLoggedIn"]),
    configs() {
      return {headers: {
        Authorization: this.config,
      }}
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
    youtubeLink(video) {
      return "https://www.youtube.com/watch?v=" + video
    },
    checkIngredient(index, event) {
      const checkedIngredient = document.querySelector("#ingredient-" + index);

      if (event.target.checked) {
        checkedIngredient.style.setProperty("text-decoration", "line-through");
      } else {
        checkedIngredient.style.setProperty("text-decoration", "none");
      }
    },
    indexScrollFuncion() {
      if(window.innerWidth > 1440 && document.getElementById("idx-btn") != null) {
        if (
          document.body.scrollTop > 400 ||
          document.documentElement.scrollTop > 400
        ) {
          document.getElementById("idx-btn").style.display = "block";
        } else {
          document.getElementById("idx-btn").style.display = "none";
        }
      }
    },
    winWidth: function () { 
        this.widthInterval = setInterval(() => {
            var w = window.innerWidth;
            if (w < 1440 && document.getElementById("idx-btn") != null) {
              document.getElementById("idx-btn").style.display = "none";
            }
        }, 100);
    },
    scrollDoc(id) {
      if (id != "top") {
        var location = document.querySelector("#" + id).offsetTop-100;

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
      let response = confirm('정말 레시피를 삭제하시겠습니까?')
      if (response) {
        axios.delete(SERVER.URL + SERVER.ROUTES.recipeDelte + this.recipe.recipeId, {
          headers: {
            Authorization: this.config,
          },
        })
        .then(() => {
          this.$router.push({ name:'RecipeListView', params: { pageNum: 1 } });
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
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
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    deleteReview(reviewId) {
      let response = confirm('정말 한줄평을 삭제하시겠습니까?')
      if (response) {
        axios.delete(SERVER.URL + SERVER.ROUTES.reviewDelete + reviewId, {
            headers: {
              Authorization: this.config,
            },
          })
          .then(() => {
            this.getRecipe();
          })
          .catch(err => {
            if (err.response.status == 401) {
              alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
              this.logout()
            }});
      }
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
        .then(() => {
          alert('한줄평 입력에 성공했습니다!')
        })
        .catch(err => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
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

    reportRecipe() {
      let body = {
        recipeId: this.$route.params.recipe_id,
        reason: this.reportReason + '/' + this.detailReason
      }
      axios
        .post(SERVER.URL + SERVER.ROUTES.reportRecipe, body, this.configs)
        .then(res => {
          if(res.status == 200) {
            alert('레시피를 성공적으로 신고했습니다.')
            this.hideModal()
          }
        })
        .catch(err => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },

    likeRecipe() {
      const data = {
        recipeId: this.$route.params.recipe_id,
      };
      axios
        .post(SERVER.URL + SERVER.ROUTES.recipeLike, data, this.configs)
        .then((res) => {
          this.likeCnt = res.data.likeCnt;
          this.isLiked = !this.isLiked;
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
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
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
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
    getInitRecipe() {
      let config = (this.config == "Bearer null") ? null : { headers: {Authorization: this.config} }

      axios
        .get(
          SERVER.URL +
            SERVER.ROUTES.recipeDetail +
            this.$route.params.recipe_id, config
        )
        .then((res) => {
          this.recipe = res.data.recipe
          this.recipeDataSet = {
            'calorie': res.data.recipe.calorie,
            'carbon': res.data.recipe.carbon,
            'fat': res.data.recipe.fat,
            'natrium': res.data.recipe.natrium,
            'protein': res.data.recipe.protein,
            'sugar': res.data.recipe.sugar,
          };
          this.categories = res.data.recipe.foodCategoryName
          this.likeCnt = res.data.likeCnt;
          this.isLiked = res.data.userLike;
          this.loginUserId = res.data.loginUserId

          this.recipe.reviewDtoList.forEach(function (review) {
            review['changing'] = false
          })
        })
        .catch((err) => console.log(err.response));
    },
    getYoutube() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getYoutube + this.$route.params.recipe_id)
        .then(res => {
          this.videoIds = res.data.list.map(video => video.videoId)
          this.videoUrls = res.data.list.map(video => video.thumbnailSrc)
        })
        .catch(err => console.log(err.response))
    },

    
    showModal() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.reportRecipe + this.$route.params.recipe_id, this.configs)
        .then(res => {
          if (res.data.isReport) {
            alert('신고 내용을 검토하고 있습니다...')
          } else {
            this.$refs['report-modal'].show()
          }
        })
        .catch(err => console.log(err))
    },
    hideModal() {
      this.$refs['report-modal'].hide()
    },


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

#idx-btn {
  position: fixed;
  left: 50px;
  top: 90px;
  z-index: 99999;
  display:none;  
  padding: 0px 20px 20px 30px;
  border-radius: 20px;
  border: solid 1px lightgray;
}

.text-link:hover {
  color:orangered;
}

.avgRating {
  font-size: 40px;
}

.stats {
  font-size: 25px;
}

.additinal-info {
  font-size: 20px;
}

.additinal-info i {
  font-size: 50px;
}

.ingre-title span {
  color: darkgray;
}

.ingre-list {
  font-size: 17px;
}

.ingre-list i {
   color:red;
}

.card-title {
  font-weight: bold;
}

.cook-detail {
  padding: 30px 60px 30px 60px;
  border-radius: 40px;
  box-shadow: 0px 8px 40px rgba(128, 128, 128, 0.15);
}

.review-sec {
  padding: 30px 60px 30px 60px;
  border-radius: 40px;
  box-shadow: 0px 8px 40px rgba(128, 128, 128, 0.15);
}


.youtube img {
  width: 250px;
}

.youtube p {
  font-size: 18px;
}

.idx-obj:hover {
  cursor: pointer;
}

.category{
  padding-top: 40px;
  font-size: 20px;
}
.categories{
  padding-right: 5px;
  padding-left:5px;
}
</style>