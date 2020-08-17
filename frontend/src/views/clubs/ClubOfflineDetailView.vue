<template>
  <div class="wrapper">
    <section id="top">
      <card
        type="background"
        style="background-image: url('https://previews.123rf.com/images/seamartini/seamartini1609/seamartini160900068/62637928-cooking-and-kitchen-utensils-seamless-background-wallpaper-with-vector-pattern-icons-of-pizza-bread-.jpg') ;"
      >
        <div class="card-title text-left">
          <h1 style>{{ meet.title }}</h1>
        </div>
        <div class="card-footer text-left">
          <div class="stats">
            <div class="card-footer text-left">
              <div class="stats">
                <i class="fas fa-edit"></i>
                {{ meet.writerNickname }}
              </div>
            </div>
            <div v-if="user == meet.userId">
            </div>
          </div>
        </div>
      </card>
    </section>

    <div class="container">
      <div class="meet-board row col-12 mt-5" style="min-height:380px">
        <div class="col-6">
          <img :src="thumbnailSrc" class="img-fluid" />
        </div>
        <div class="col-6">
          <div class="row">
            <p class="meet-subtitle text-left col-12">타입: {{ meet.type }}</p>
            <p class="meet-content text-left col-12">날짜: {{ simpleDate(meet.date) }}</p>
            <p class="meet-content text-left col-12">시간: {{ simpleTime(meet.date) }}</p>
            <p class="meet-content text-left col-12">장소: {{ meet.address }}</p>
            <p class="col-12 text-left meet-content">가격: {{ meet.price }}원</p>
          </div>
          <div v-if="user != meet.userId" class="join-btn-div">
            <button class="btn" style="width:100%" @click="changeJoinClub">{{ joinMent }}</button>
          </div>
        </div>
      </div>
      <div class="meet-board row mt-5">
        <div class="col-12">
          <h3 class="text-left">레시피</h3>
          <div class="col-md-8 ml-auto mr-auto">
            <card type="profile">
              <div slot="raw-content" class="row">
                <div class="col-md-5 mt-auto mb-auto">
                  <div class="card-image ml-3">
                    <router-link
                      :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }"
                    >
                      <img class="img img-zoom" :src="imageSrc(recipe)" />
                    </router-link>
                  </div>
                </div>
                <div class="col-md-7">
                  <div class="card-body text-left">
                    <h3 class="card-title">{{recipe.recipeName}}</h3>
                    <h6 class="category text-primary text-left">{{recipe.nickname}}</h6>
                    <p class="card-description">{{recipe.recipeDetail}}</p>
                    <router-link
                      class="card-footer"
                      :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }"
                    >상세보기</router-link>
                  </div>
                </div>
              </div>
            </card>
          </div>
        </div>

        <div class="col-12 mt-5">
          <h3 class="text-left">상세 내용</h3>
          <div class="detail-main-content" v-html="meet.content" />
        </div>
        <div class="col-12 mt-5">
          <h3 class="text-left">지도 보기</h3>
        <div id="map" style="width:100%;height:400px;" class="mx-auto"></div>
        <p class="text-left" style="font-weight:bold">주소 : {{ meet.address }}</p>
        </div>

        <div class="col-12">
          <h3 class="text-left mt-5">현재 참가자 ({{ meet.meetJoinList.length }} / {{ meet.joinLimit }})</h3>
          
          <div class="row">
          <p
              v-for="(user, index) in meet.meetJoinList"
              :key="user.userId"
              class="col-2"
            ><img :id="getUserIndex(index)" class="join-member-profile img-raised mb-5" :src="userProfile(index)" />
              <b-tooltip :target="getUserIndex(index)" triggers="hover" placement="bottom">
                {{user.nickname}}
              </b-tooltip>
            </p>
          </div>
        </div>
      </div>
    </div>

    <router-link
      v-if="user == meet.userId"
      :to="{ name: 'ClubOfflineUpdateView', params: { club_id: meet.meetId } }"
    >
      <div class="section make-versus">
        <div class="container">
          <div class="button-container">
            <button class="learn-more submit">수정</button>
            <button class="learn-more" @click="deleteClub" style="margin-left: 20px;">삭제</button>
          </div>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import SERVER from "@/api/api";
import axios from "axios";
import { mapGetters } from "vuex";

import { Card } from "@/components/global";

const API_KEY = process.env.VUE_APP_KAKAO_API_KEY;

export default {
  name: "ClubDetailView",
  data() {
    return {
      meet: {
        address: null,
        content: null,
        date: "2020-08-13T15:24:00",
        joinLimit: null,
        lat: null,
        lng: null,
        meetId: 0,
        meetJoinList: [],
        price: null,
        recipeId: 0,
        thumbnailSrc: "dochi.png",
        title: null,
        type: null,
        userId: null,
      },
      user: null,
      isIn: null,
      recipe: { recipeId: 0, recipeThumbnailSrc: "dochi.png" },
    };
  },
  computed: {
    ...mapGetters(["config"]),
    thumbnailSrc() {
      return SERVER.IMAGE_URL + this.meet.thumbnailSrc;
    },
    joinMent() {
      return this.isIn ? "취소하기" : "신청하기";
    },
    configs() {
      return {
        headers: {
          Authorization: this.config
        }
      }
    }
  },
  components: {
    Card,
  },
  created() {},
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
  },
  methods: {
    initMap() {
      const self = this
      axios
        .get(
          SERVER.URL + SERVER.ROUTES.clubDetail + this.$route.params.club_id,
          {
            headers: {
              Authorization: this.config,
            },
          }
        )
        .then((res) => {
          this.user = res.data.userId;
          this.meet = res.data.meet;
          if (res.data.meet.recipeId) {
            axios
              .get(
                SERVER.URL +
                  SERVER.ROUTES.recipeDetail +
                  res.data.meet.recipeId,
                self.configs
              )
              .then((res) => {
                this.recipe = res.data.recipe;
              })
              .catch((error) => {
                if (err.response.status == 401) {
                  alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
                  this.logout()
                }});
          }
          this.isIn =
            res.data.meet.meetJoinList
              .map((user) => user.userId)
              .indexOf(res.data.userId) >= 0
              ? true
              : false;

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

    deleteClub() {
      let response = confirm("삭제하실 건가요?");
      if (response) {
        axios
          .delete(
            SERVER.URL + SERVER.ROUTES.clubDelete + this.$route.params.club_id,
            {
              headers: {
                Authorization: this.config,
              },
            }
          )
          .then((res) => {
            alert("삭제에 성공했습니다!");
            this.$router.push({ name: "ClubListView", params: { pageNum: 1 } });
          })
          .catch((err) => {
            if (err.response.status == 401) {
              alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
              this.logout()
            }});
      }
    },

    getClubInfo() {
      axios
        .get(
          SERVER.URL + SERVER.ROUTES.clubDetail + this.$route.params.club_id, {
            headers: {
              Authorization: this.config,
            }})
        .then(res => {
          this.meet = res.data.meet
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },

    changeJoinClub() {
      if (this.isIn) {
        axios
          .delete(
            SERVER.URL + SERVER.ROUTES.cancelClub + this.$route.params.club_id,
            {
              headers: {
                Authorization: this.config,
              },
            }
          )
          .then(() => {
            alert("참가를 취소하셨습니다.");
            this.isIn = false;
            this.getClubInfo();
          })
          .catch((err) => {
            if (err.response.status == 401) {
              alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
              this.logout()
            }});
      } else {
        axios
          .post(
            SERVER.URL + SERVER.ROUTES.joinClub,
            { meetId: this.$route.params.club_id },
            {
              headers: {
                Authorization: this.config,
              },
            }
          )
          .then(() => {
            alert("참가 신청에 성공했습니다.");
            this.getClubInfo();
            this.isIn = true;
          })
          .catch((err) => {
            if (err.response.status == 401) {
              alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
              this.logout()
            }});
      }
    },
    simpleDate(date) {
      return date.slice(0,10);
    },
    simpleTime(date) {
      return date.slice(11,16);
    },
    userProfile(index) {
      return SERVER.IMAGE_URL + this.meet.meetJoinList[index].profilePhoto;
    },
    getUserIndex(index){
      return "user-profile" + index;
    },
    imageSrc(recipe) {
      return SERVER.IMAGE_URL + recipe.recipeThumbnailSrc;
    },
  },
};
</script>

<style scoped>
.top {
  background-color: gainsboro;
}

.meet-board {
  padding: 30px 60px 30px 60px;
  border-radius: 40px;
  box-shadow: 0px 8px 40px rgba(128, 128, 128, 0.15);
}

.meet-title {
  margin-bottom: 0;
}
.meet-subtitle {
  margin-bottom: 0;
}
.meet-content {
  margin-bottom: 0;
}
.detail-main-content {
  min-height: 200px;
  margin-top: 30px;
  margin-bottom: 10px;
}

.join-btn-div {
  position:absolute; 
  bottom:0px; 
  width:100%;
}

.stats {
  font-size: 25px;
}


/* 수정, 삭제 */
/* 등록 취소 버튼 */
@import url("https://fonts.googleapis.com/css?family=Rubik:700&display=swap");
* {
  box-sizing: border-box;
}
*::before, *::after {
  box-sizing: border-box;
}

body {
  font-family: 'Rubik', sans-serif;
  font-size: 1rem;
  line-height: 1.5;
  display: -webkit-box;
  display: flex;
  -webkit-box-align: center;
          align-items: center;
  -webkit-box-pack: center;
          justify-content: center;
  margin: 0;
  min-height: 100vh;
  background: #fff;
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
button.submit {
  color: #382b22;
  background: #D7FFF1;
  border: 2px solid #77AF9C;
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

button.submit::before {
  background: #67D5B5;
  box-shadow: 0 0 0 2px #77AF9C, 0 0.625em 0 0 #cff0da;
}


button.learn-more:hover {
  background: #ffe9e9;
  -webkit-transform: translate(0, 0.25em);
          transform: translate(0, 0.25em);
}

button.submit:hover {
  background: #D7FFF1;
}

button.learn-more:hover::before {
  box-shadow: 0 0 0 2px #b18597, 0 0.5em 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0.5em, -1em);
          transform: translate3d(0, 0.5em, -1em);
}

button.submit:hover::before {
  box-shadow: 0 0 0 2px #77AF9C, 0 0.5em 0 0 #cff0da;
}

button.learn-more:active {
  background: #ffe9e9;
  -webkit-transform: translate(0em, 0.75em);
          transform: translate(0em, 0.75em);
}

button.submit:active {
  background: #D7FFF1;
}

button.learn-more:active::before {
  box-shadow: 0 0 0 2px #b18597, 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0, -1em);
          transform: translate3d(0, 0, -1em);
}

button.submit:active::before {
  box-shadow: 0 0 0 2px #77AF9C, 0 0 #cff0da;
}

.card-image {
  overflow: hidden;
}
.card-image .img-zoom {
  transform: scale(1);
  transition: transform 0.3s ease-in-out;
}

.card-image .img-zoom:hover {
  transform: scale(1.2);
}



/* 옆에 목차? */
#idx-btn {
  position: fixed;
  left: 50px;
  top: 90px;
  z-index: 2;
  display:none;  
  padding: 0px 20px 20px 30px;
  border-radius: 20px;
  border: solid 1px lightgray;
}

.join-member-profile {
  width:60px;
  height:60px;
  border-radius:100%;
}

</style>