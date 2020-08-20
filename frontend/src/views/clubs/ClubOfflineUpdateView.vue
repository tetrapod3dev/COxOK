<template>
  <div class="wrapper">
    <section id="top">
      <card
        type="background"
        :style="{ backgroundImage: 'url(http://i3a104.p.ssafy.io/header/meet.jpg)' }"
      >
        <div class="card-title text-left">
          <h1 style>코옥 수정 하기</h1>
        </div>
      </card>
    </section>

    <div class="container">
      <button class="btn" @click="submitUpdate">제출</button>
      <div class="row">
        <!-- 사진 입력 및 미리보기 -->
        <div class="col-4 offset-1 ">
          <input
                ref="thumbnailInput"
                type="file"
                name="photo"
                id="recipe_thumbnail"
                hidden
                @change="changeThumbnail"
          />
          <img v-if="meet.thumbnail == undefined" :src="thumbnailSrc" class="mainImage"/>
          <img v-else :src="meet.thumbnailSrc" class="mainImage"/>
          <p>
            <a @click="onClickClubImage">
              <b-button pill style="color:white;background-color:rgb(249,99,50);">메인 사진 업로드</b-button>
            </a>
          </p>
        </div>

        <div class="col-7 text-left">
          <!-- 제목 입력 -->
          <div class="row">
            <b-col sm="3">
              <label for="meet-title">제목 :</label>
            </b-col>
            <b-col sm="9">
              <b-form-input
                id="meet-title"
                type="text"
                v-model="meet.title"
                readonly
                placeholder="Enter your name"
              ></b-form-input>
            </b-col>
          </div>

          <!-- 유형(type) -->
          <div class="row">
            <span class="col-3">타입 : {{ meet.type }}</span>
            <b-form-select v-model="meet.type" class="col-9">
              <b-form-select-option value="쿠킹클래스">쿠킹 클래스</b-form-select-option>
              <b-form-select-option value="공유키친">공유 키친</b-form-select-option>
              <b-form-select-option value="홈파티">홈파티</b-form-select-option>
            </b-form-select>
          </div>

          <div class="row">
            <!-- 날짜 시간 입력 -->
            <span class="col-3">날짜 :</span>
            <datetime
              type="datetime"
              v-model="meet.date"
              format="yyyy-MM-dd HH:mm:ss"
              value-zone="Asia/Seoul"
              class="col-9"
            ></datetime>

            <!-- 날짜 입력 -->
            <!-- <b-form-datepicker id="example-datepicker" v-model="clubPost.date" class="mb-2 col-4 offset-2"></b-form-datepicker> -->
            <!-- 시간 입력 -->
            <!-- <b-time v-model="clubPost.time" show-seconds class="col-4"></b-time> -->
          </div>

          <div class="row">
            <!-- 참여자 수 -->

            <span class="col-3">정원 :</span>
            <input class="col-9" type="number" v-model="meet.joinLimit" />
          </div>

          <div class="row">
            <!-- 참여자 수 -->

            <span class="col-3">가격 :</span>
            <input class="col-9" type="number" v-model="meet.price" />
          </div>
        </div>

        <div class="row">
          <div class="col-12 mr-auto ml-auto">
            <!-- 텍스트 에디터 -->
            <CxkEditor :value.sync="meet.content" />
          </div>
        </div>
      </div>

      <!-- 주소 입력 -->
      <div class="row">
        <b-col sm="3">
          <label for="meet-post-number">우편번호 :</label>
        </b-col>
        <b-col sm="6">
          <b-form-input
            id="meet-post-number"
            type="text"
            v-model="postcode"
            readonly
            placeholder="우편번호"
          ></b-form-input>
        </b-col>
        <button @click="onClickAddr" class="btn col-2">우편번호 찾기</button>
      </div>
      <div class="row">
        <b-col sm="3">
          <label for="meet-address1">주소 :</label>
        </b-col>
        <b-col sm="6">
          <b-form-input
            id="meet-address1"
            type="text"
            v-model="meet.address"
            readonly
            placeholder="주소"
          ></b-form-input>
        </b-col>
      </div>
      <div class="row">
        <b-col sm="3">
          <label for="meet-address2">상세주소 :</label>
        </b-col>
        <b-col sm="6">
          <b-form-input id="meet-address2" type="text" v-model="detailAddress" placeholder="상세주소"></b-form-input>
        </b-col>
      </div>

      <div id="map" style="width:500px;height:400px;" class="mx-auto"></div>
      
      <!-- 레시피 검색 -->
      <div class="row">
        <b-col sm="3">
          <span>레시피 :</span>
        </b-col>
        <b-col sm="6">
          <div v-if="selectedRecipe == null">
            {{ selectedRecipe }}
          </div>
          <div v-else>
            <b-col sm="12">
              <img class="img img-raised mb-5" :src="getThumbnail()" style="height:200px; margin-right:20px;"/>
              <p>{{ selectedRecipe.recipeName }}</p>
            </b-col>
          </div>
        </b-col>
        <div class="col-2 px-0">
          <button class="btn w-100" @click="changeSelectorShow">레시피 선택</button>
        </div>
        

        <div v-show="showSelector" class="col-12">
          <CategorySelector
            @searchRecipe="categorySubmit"
            @removeSelect="categorySubmit"/>

          <div v-for="recipe in recipes" :key="recipe.id" @click="selectRecipe(recipe)" class="row searched-recipes m-2" >
            <img :src="imageSrc(recipe)" class="col-4" />
            <div class="col-7">
              <h3 class="row">{{ recipe.recipeName }}</h3>
              <p class="row">{{ recipe.recipeDetail }}</p>
            </div>
          </div>
          <div id="bottomSensor"></div>
          <h2 v-if="isEnd">끝!</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import SERVER from "@/api/api";
import axios from "axios";
import scrollmonitor from "scrollmonitor";

import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import CategorySelector from "@/components/recipes/CategorySelector.vue";

import { Card } from "@/components/global";
import { mapGetters, mapMutations } from "vuex";
import { Datetime } from "vue-datetime";

const API_KEY = process.env.VUE_APP_KAKAO_API_KEY;

export default {
  name: "OfflineClubUpdate",
  data() {
    return {
      meet: {
        address: null,
        content: null,
        date: null,
        joinLimit: null,
        lat: null,
        lng: null,
        meetId: null,
        meetJoinList: [],
        price: null,
        recipeId: null,
        thumbnailSrc: "dochi.png",
        title: null,
        type: null,
        userId: null,
      },
      selectedRecipe: null,
      recipes: [],
      curPage: 1,
      maxPage: 10,
      postcode: null,
      detailAddress: null,
      newThumbnailSrc: null,
      showSelector: false,
      isEnd: false,
    };
  },
  computed: {
    ...mapGetters(["config", "searchingData"]),
    thumbnailSrc() {
      return SERVER.IMAGE_URL + this.meet.thumbnailSrc;
    },
    fullAddress() {
      return this.meet.detailAddress != null
        ? this.meet.address + " " + this.detailAddress
        : this.meet.address;
    },
    fullTime() {
      return this.meet.date.slice(0, 19);
    },
  },
  components: {
    CxkEditor,
    CategorySelector,
    datetime: Datetime,
    Card,
  },
  created() {
    this.getRecipes();
    
    this.setSearchData({
      'selectedCategory': [],
      'selectedIngredients': [],
      'selectedIngredientsName': [],
      'level': 5,
      'cookTime': 120,
  })},
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${API_KEY}&autoload=false&libraries=services`;
      document.head.appendChild(script);
    }
    window.addEventListener("scroll", this.indexScrollFuncion);
    this.addScrollWatcher();
  },
  beforeDestory() {
    window.removeEventListener('scroll', this.indexScrollFuncion);
    clearInterval(this.widthInterval);
  },
  updated() {
    if (this.showSelector) {
      if (this.curPage-1 < this.maxPage) {
        this.loadUntilViewportIsFull();
      } else {
        this.isEnd = true
      }
    }
  },
  methods: {
    ...mapMutations(['setSearchData']),
    changeThumbnail(event) {
      const file = event.target.files[0];
      this.meet.thumbnailSrc = URL.createObjectURL(file);
      this.meet.thumbnail = file;
    },
    onClickClubImage(){
      this.$refs.thumbnailInput.click();
    },
    imageSrc(recipe) {
      return SERVER.IMAGE_URL + recipe.recipeThumbnailSrc;
    },
    getThumbnail(){
      return SERVER.IMAGE_URL + this.selectedRecipe.recipeThumbnailSrc;
    },

    initMap() {
      let configs = {headers: {
        Authorization: this.config,
      }}
      axios
        .get(
          SERVER.URL + SERVER.ROUTES.clubDetail + this.$route.params.club_id, configs)
        .then((res) => {
          if (res.data.userId != res.data.meet.userId) {
            alert('올바르지 않은 접근입니다!!!!!')
            this.$router.go(-1)
          } else {

            this.user = res.data.userId;
            this.meet = res.data.meet;
            this.isIn =
              res.data.meet.meetJoinList
                .map((user) => user.userId)
                .indexOf(res.data.userId) >= 0
                ? true
                : false;

            if (res.data.meet.recipeId) {
              axios
                .get(SERVER.URL + SERVER.ROUTES.recipeDetail + res.data.meet.recipeId, configs)
                .then((res) => {
                  this.selectedRecipe = res.data.recipe;
                })
                .catch((err) => {
                  if (err.response.status == 401) {
                    alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
                    this.logout()
                  }});
            }
          }

          var container = document.getElementById("map");
          var options = {
            center: new kakao.maps.LatLng(res.data.meet.lat, res.data.meet.lng),
            level: 3,
          };

          var map = new kakao.maps.Map(container, options);

          var geocoder = new daum.maps.services.Geocoder();

          var marker = new daum.maps.Marker({
            position: new daum.maps.LatLng(
              res.data.meet.lat,
              res.data.meet.lng
            ),
            map: map,
          });
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },

    onClickAddr() {
      const self = this;

      var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
          level: 3, // 지도의 확대 레벨
        };

      //지도를 미리 생성
      var map = new daum.maps.Map(mapContainer, mapOption);
      //주소-좌표 변환 객체를 생성
      var geocoder = new daum.maps.services.Geocoder();
      //마커를 미리 생성
      var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(37.537187, 127.005476),
        map: map,
      });

      new daum.Postcode({
        oncomplete: function (data) {
          var addr = data.address; // 최종 주소 변수

          // 주소 정보를 해당 필드에 넣는다.
          self.postcode = data.zonecode;
          self.meet.address = addr;
          // 주소로 상세 정보를 검색
          geocoder.addressSearch(data.address, function (results, status) {
            // 정상적으로 검색이 완료됐으면
            if (status === daum.maps.services.Status.OK) {
              var result = results[0]; //첫번째 결과의 값을 활용

              self.meet.lng = result.x;
              self.meet.lat = result.y;

              // 해당 주소에 대한 좌표를 받아서
              var coords = new daum.maps.LatLng(result.y, result.x);
              // 지도를 보여준다.
              mapContainer.style.display = "block";
              map.relayout();
              // 지도 중심을 변경한다.
              map.setCenter(coords);
              // 마커를 결과값으로 받은 위치로 옮긴다.
              marker.setPosition(coords);
            }
          });
        },
      }).open();
    },

    TestTumbnail: async function () {
      let frm = new FormData();
      const self = this;

      if (this.meet.thumbnail != undefined) {
        frm.append("photo", this.meet.thumbnail);
        // formData를 API에 전달해서 src 주소를 받습니다..
        // 그리고 그걸 recipe에 저장합니다.
        await axios
          .post(SERVER.URL + SERVER.ROUTES.photoRegister, frm, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((res) => {
            self.newThumbnailSrc = res.data.photo[0];
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    submitUpdate: async function () {
      await this.TestTumbnail();
      const thumbnail =
        this.newThumbnailSrc != null
          ? this.newThumbnailSrc
          : this.meet.thumbnailSrc;
      let body = {
        address: this.fullAddress,
        content: this.meet.content,
        date: this.fullTime,
        joinLimit: this.meet.joinLimit,
        lat: this.meet.lat,
        lng: this.meet.lng,
        meetId: parseInt(this.$route.params.club_id),
        price: this.meet.price,
        recipeId: this.selectedRecipe.recipeId,
        thumbnailSrc: thumbnail,
        title: this.meet.title,
        type: this.meet.type,
      };

      axios
        .put(SERVER.URL + SERVER.ROUTES.clubUpdate, body, {
          headers: {
            Authorization: this.config,
          },
        })
        .then((res) => {
          if (res.status == 200) {
            this.$router.push({
              name: "ClubOfflineDetailView",
              params: { club_id: this.$route.params.club_id },
            });
          }
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },

    
    changeSelectorShow() {
      this.showSelector = !this.showSelector;
    },
    addScrollWatcher() {
      const bottomSensor = document.querySelector("#bottomSensor");
      const watcher = scrollmonitor.create(bottomSensor);
      if (this.curPage < this.maxPage) {
        watcher.enterViewport(() => {
          setTimeout(() => {
            this.getRecipes();
          }, 1000);
        });
      }
    },
    loadUntilViewportIsFull() {
      const bottomSensor = document.querySelector("#bottomSensor");
      const watcher = scrollmonitor.create(bottomSensor);
      if (watcher.isFullyInViewport) {
        this.getRecipes();
      }
    },
    getRecipes() {
      if (
        (this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length ==
        0) && (this.searchingData.level == 5) && (this.searchingData.cookTime == 120)
      ) {
        this.allRecipe(this.curPage++);
      } else {
        this.searchRecipe(this.curPage++);
      }
    },
    categorySubmit() {
      this.isEnd = false;
      this.recipes = [];
      this.curPage = 1;
      this.getRecipes();
    },
    allRecipe(page) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.recipeList + (page - 1))
        .then((res) => {
          this.recipes = [...this.recipes, ...res.data.list];
          this.maxPage = parseInt((res.data.total - 1) / 6) + 1;
        })
        .catch((err) => console.log(err));
    },
    searchRecipe(page) {
      let frm = new FormData();

      // categories 배열에는 선택된 카테고리의 ID가 String으로 들어갑니다.
      this.searchingData.selectedCategory.forEach(function (selectedCategory) {
        frm.append("selectedCategory", selectedCategory);
      });
      this.searchingData.selectedIngredients.forEach(function (
        selectedIngredient
      ) {
        frm.append("selectedIngredients", selectedIngredient);
      });

      frm.append("level", this.searchingData.level)

      frm.append("cookTime", this.searchingData.cookTime)

      // recipe/search/{{page}} 라는 주소로 selectedCategory(선택된 카테고리의 id들) / selectedIngredients(선택된 재료들의 id)를 전달합니다.
      axios
        .post(SERVER.URL + SERVER.ROUTES.searchRecipe + (page - 1), frm)
        .then((res) => {
          this.recipes = [...this.recipes, ...res.data.list];
          this.maxPage = parseInt((res.data.total - 1) / 6) + 1;
        })
        .catch((err) => console.log(err));
    },
    selectRecipe(recipe) {
      this.selectedRecipe = recipe;
      this.showSelector = false
    },
    scrollToTop() {
      scroll(0, 0);
    },
  },
};
</script>

<style scoped>
.top {
  background-color: gainsboro;
}

.mainImage{
  width: 50%;
  height: 70%;
}

.searched-recipes:hover {
  cursor: pointer;
}
</style>