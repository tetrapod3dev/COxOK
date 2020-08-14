<template>
  <div class="wrapper">
    <section id="top">
      <card
        type="background"
        style="background-image: url('https://previews.123rf.com/images/seamartini/seamartini1609/seamartini160900068/62637928-cooking-and-kitchen-utensils-seamless-background-wallpaper-with-vector-pattern-icons-of-pizza-bread-.jpg') ;"
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
        <div class="col-4 offset-1">
          <img v-if="meet.thumbnail == undefined" :src="thumbnailSrc" />
          <img v-else :src="meet.thumbnailSrc" />
          <input type="file" @change="changeThumbnail" />
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
              <b-form-select-option :value="null">--선택--</b-form-select-option>
              <b-form-select-option value="쿠킹 클래스">쿠킹 클래스</b-form-select-option>
              <b-form-select-option value="공유 키친">공유 키친</b-form-select-option>
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

            <span class="col-3">참가자 :</span>
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
    </div>
  </div>
</template>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import SERVER from "@/api/api";
import axios from "axios";

import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import { Card } from "@/components/global";
import { mapGetters } from "vuex";
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
        thumbnailSrc: null,
        title: null,
        type: null,
        userId: null,
      },
      postcode: null,
      detailAddress: null,
      newThumbnailSrc: null,
    };
  },
  computed: {
    ...mapGetters(["config"]),
    thumbnailSrc() {
      return SERVER.IMAGE_URL + this.meet.thumbnailSrc;
    },
    fullAddress() {
      return this.meet.detailAddress == null
        ? this.meet.address + " " + this.detailAddress
        : this.meet.address;
    },
    fullTime() {
      return this.meet.date.slice(0, 19);
    },
  },
  components: {
    CxkEditor,
    datetime: Datetime,
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
    changeThumbnail(event) {
      const file = event.target.files[0];
      this.meet.thumbnailSrc = URL.createObjectURL(file);
      this.meet.thumbnail = file;
    },

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
        recipeId: this.meet.recipeId,
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
              name: "ClubDetailView",
              params: { club_id: this.$route.params.club_id },
            });
          }
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
  },
};
</script>

<style scoped>
.top {
  background-color: gainsboro;
}
</style>