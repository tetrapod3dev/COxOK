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
            {{ meet.userId }}
            <div v-if="user == meet.userId">
              <router-link
                :to="{ name: 'ClubOfflineUpdateView', params: { club_id: meet.meetId } }"
              >
                <i class="fas fa-edit"></i>수정
              </router-link>
              <a @click="deleteClub">X삭제</a>
            </div>
          </div>
        </div>
      </card>
    </section>

    <div class="container">
      <div class="row mt-5">
        <div class="col-4">
          <img :src="thumbnailSrc" class="img-fluid" />
        </div>
        <div class="col-8">
          <div class="row">
            <div v-if="user != meet.userId">
              <button class="btn" @click="changeJoinClub">{{ joinMent }}</button>
            </div>
            <p class="meet-subtitle text-left col-12">{{ meet.type }}</p>
            <p class="meet-content text-left col-12">일자 : {{ meet.date }}</p>
            <p class="meet-content text-left col-12">장소 : {{ meet.address }}</p>
            <p class="col-12 text-left meet-content">가격: {{ meet.price }}원</p>
            <p
              class="col-12 text-left meet-content"
            >현재 참가자 : {{ meet.meetJoinList.length }} / {{ meet.joinLimit }}</p>
            <p
              v-for="user in meet.meetJoinList"
              :key="user.userId"
              class="col-2"
            >{{ user.nickname }}님</p>
          </div>
        </div>
        <div class="detail-main-content col-12" v-html="meet.content" />

        <div id="map" style="width:500px;height:400px;" class="mx-auto"></div>
        <p>주소 : {{ meet.address }}</p>
      </div>
    </div>
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
        date: null,
        joinLimit: null,
        lat: null,
        lng: null,
        meetId: null,
        meetJoinList: [],
        price: null,
        recipeId: null,
        thumbnailSrc: null,
        title: null,
        type: null,
        userId: null,
      },
      user: null,
      isIn: null,
    };
  },
  computed: {
    ...mapGetters(["config"]),
    thumbnailSrc() {
      return SERVER.IMAGE_URL + this.meet.thumbnailSrc;
    },
    joinMent() {
      return this.isIn ? "취소" : "참가";
    },
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
        .catch((err) => console.log(err));
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
            console.log(res);
            alert("삭제에 성공했습니다!");
            this.$router.push({ name: "ClubListView", params: { pageNum: 1 } });
          })
          .catch((err) => console.log(err.response));
      }
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
          })
          .catch((err) => console.log(err.response));
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
            this.isIn = true;
          })
          .catch((err) => console.log(err.response));
      }
    },
  },
};
</script>

<style scoped>
.top {
  background-color: gainsboro;
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
  border: 1px solid;
  margin-bottom: 10px;
}
</style>