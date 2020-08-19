<template>
  <div class="wrapper">
    <section id="top">
      <card
        type="background"
        :style="{ backgroundImage: 'url(\'' + require('@/assets/meet.jpg') + '\')' }"
      >
        <div class="card-title text-left">
          <h1 style>코옥 수정 하기</h1>
        </div>
      </card>
    </section>
    <div class="container">
      <button class="btn" @click="sendData">제출하기</button>

      <div class="row">
        <!-- 제목 입력 -->
        <b-col sm="3">
          <label for="meet-title">제목 :</label>
        </b-col>
        <b-col sm="6">
          <b-form-input type="text" v-model="online.title" />
        </b-col>
      </div>
      <div class="row">
        <!-- 타입 입력 -->
        <b-col sm="3">
          <label for="meet-type">타입 :</label>
        </b-col>
        <b-col sm="6">
          <b-form-select v-model="online.type" @change="initData">
            <b-form-select-option :value="null">--선택--</b-form-select-option>
            <b-form-select-option value="유튜브강의">유튜브강의</b-form-select-option>
            <b-form-select-option value="실시간강의">실시간강의</b-form-select-option>
          </b-form-select>
        </b-col>
      </div>
      <div class="row">
        <!-- 날짜 시간 입력 -->

        <b-col sm="3">
          <label for="meet-date">날짜 :</label>
        </b-col>
        <b-col sm="6">
          <datetime
            @click.native.self="clickDateForm"
            ref="datetimePicker"
            class="meet-date"
            type="datetime"
            v-model="online.date"
            format="yyyy-MM-dd HH:mm:ss"
            value-zone="Asia/Seoul"
          ></datetime>
        </b-col>
      </div>

      <div v-if="online.type == '유튜브강의'" class="row">
        <b-col sm="3">비디오 ID:</b-col>
        <b-col sm="6">
          <b-form-input type="text" v-model="online.video" />
        </b-col>
      </div>
      <div v-if="online.type != '유튜브강의'" class="row">
        <b-col sm="3">실시간 강의 링크:</b-col>
        <b-col sm="6">
          <b-form-input type="text" v-model="online.link" />
        </b-col>
      </div>
      <div v-if="online.type != '유튜브강의'" class="row">
        <b-col sm="3">실시간 강의 사진:</b-col>
        <b-col sm="6">
          <b-form-file @change="onChangeThumbnail" />
        </b-col>
      </div>

      <CxkEditor :value.sync="online.content" />
      <br />
    </div>
  </div>
</template>

<script>
import SERVER from "@/api/api";
import axios from "axios";
import { Card } from "@/components/global";

const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY;
const API_URL = "https://www.googleapis.com/youtube/v3/videos";

import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import { Datetime } from "vue-datetime";

import { mapGetters } from "vuex";

export default {
  name: "ClubUpdateView",
  data() {
    return {
      online: {
        onlineId: 0,
        title: null,
        content: null,
        type: null,
        date: null,
        link: null,
        thumbnailSrc: "dochi.png",
        video: null,
      },
      rawFile: null,
    };
  },
  computed: {
    ...mapGetters(["config"]),
    fullTime() {
      return this.online.date.slice(0, 19);
    },
    submitVideo() {
      return this.online.video != null ? this.online.video : "null";
    },
    submitLink() {
      return this.online.link != null ? this.online.link : "null";
    },
  },
  components: {
    CxkEditor,
    datetime: Datetime,
    Card,
  },
  created() {
    axios
      .get(
        SERVER.URL + SERVER.ROUTES.onlineDetail + this.$route.params.club_id,
        {
          headers: {
            Authorization: this.config,
          },
        }
      )
      .then((res) => {
        if (res.data.userId != res.data.online.userId) {
          alert('올바르지 않은 접근입니다!!!!!')
          this.$router.go(-1)
        } else {
          this.online = res.data.online;
        }
      })
      .catch((err) => {
        if (err.response.status == 401) {
          alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
          this.logout()
        }});
  },
  methods: {
    onChangeThumbnail(event) {
      this.rawFile = event.target.files[0];
    },
    initData() {
      this.online.video = null;
      this.online.link = null;
      this.online.rawFile = null;
    },

    makeOnlineClub: async function () {
      if (this.online.type == "실시간강의") {
        let frm = new FormData();
        const self = this;
        if (this.rawFile != null) {
          frm.append("photo", this.rawFile);
          await axios
            .post(SERVER.URL + SERVER.ROUTES.photoRegister, frm, {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            })
            .then((res) => {
              self.online.thumbnailSrc = res.data.photo[0];
            })
            .catch((err) => {
              console.log(err);
            });
        }
      } else {
        await axios
          .get(API_URL, {
            params: {
              key: API_KEY,
              part: "snippet",
              type: "video",
              id: this.online.video,
            },
          })
          .then((res) => {
            this.online.thumbnailSrc =
              res.data.items[0].snippet.thumbnails.medium.url;
          })
          .catch((err) => console.log(err.response));
      }
    },
    sendData: async function () {
      await this.makeOnlineClub();

      let body = {
        onlineId: this.online.onlineId,
        type: this.online.type,
        title: this.online.title,
        content: this.online.content,
        date: this.fullTime,
        link: this.submitLink,
        video: this.submitVideo,
        thumbnailSrc: this.online.thumbnailSrc,
      };

      let configs = {
        headers: {
          Authorization: this.config,
        },
      };

      axios
        .put(SERVER.URL + SERVER.ROUTES.onlineRegister, body, configs)
        .then(() => {
          this.$router.push({
            name: "ClubOnlineDetailView",
            params: { club_id: this.$route.params.club_id },
          });
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});

    },

    clickDateForm() {
      this.$refs.datetimePicker.open(event);
    },
  },
};
</script>
