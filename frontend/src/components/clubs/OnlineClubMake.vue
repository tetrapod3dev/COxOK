<template>
  <div>
    <button class="btn" @click="sendData">제출하기</button>

    <div class="row">
      <!-- 제목 입력 -->
      <b-col sm="3">
        <label for="meet-title">제목 :</label>
      </b-col>
      <b-col sm="6">
        <b-form-input type="text" v-model="title" />
      </b-col>
    </div>
    <div class="row">
      <!-- 타입 입력 -->
      <b-col sm="3">
        <label for="meet-type">타입 :</label>
      </b-col>
      <b-col sm="6">
        <b-form-select v-model="type" @change="initData">
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
      <b-col sm="6" @click="clickDateForm">
        <datetime
          class="meet-date"
          type="datetime"
          v-model="date"
          format="yyyy-MM-dd HH:mm:ss"
          value-zone="Asia/Seoul"
        ></datetime>
      </b-col>
    </div>
    <div v-if="isYouTube" class="row">
      <b-col sm="3">비디오 ID:</b-col>
      <b-col sm="6">
        <b-form-input type="text" v-model="video" />
      </b-col>
    </div>
    <div v-if="!isYouTube" class="row">
      <b-col sm="3">실시간 강의 링크:</b-col>
      <b-col sm="6">
        <b-form-input type="text" v-model="link" />
      </b-col>
    </div>
    <div v-if="!isYouTube" class="row">
      <b-col sm="3">실시간 강의 사진:</b-col>
      <b-col sm="6">
        <b-form-file @change="onChangeThumbnail" />
      </b-col>
    </div>

    <CxkEditor :value.sync="content" />
    <br />
  </div>
</template>

<script>
import SERVER from "@/api/api";
import axios from "axios";

const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY;
const API_URL = "https://www.googleapis.com/youtube/v3/videos";

import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import { Datetime } from "vue-datetime";
import { mapGetters } from "vuex";

export default {
  name: "OnlineClubMake",
  data() {
    return {
      type: "유튜브강의",
      title: null,
      content: null,
      date: null,
      video: null,
      link: null,
      thumbnailSrc: null,
      rawFile: null,
    };
  },
  components: {
    CxkEditor,
    datetime: Datetime,
  },
  computed: {
    ...mapGetters(["config"]),
    fullTime() {
      return this.date.slice(0, 19);
    },
    isYouTube() {
      return this.type == "유튜브강의" ? true : false;
    },
    submitVideo() {
      return this.video != null ? this.video : "null";
    },
    submitLink() {
      return this.link != null ? this.link : "null";
    },
  },
  methods: {
    onChangeThumbnail(event) {
      this.rawFile = event.target.files[0];
    },
    initData() {
      (this.video = null), (this.link = null), (this.rawFile = null);
    },

    makeOnlineClub: async function () {
      if (this.type == "실시간강의") {
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
              self.thumbnailSrc = res.data.photo[0];
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
              id: this.video,
            },
          })
          .then((res) => {
            this.thumbnailSrc =
              res.data.items[0].snippet.thumbnails.default.url;
          })
          .catch((err) => console.log(err.response));
      }
    },
    sendData: async function () {
      await this.makeOnlineClub();

      let body = {
        type: this.type,
        title: this.title,
        content: this.content,
        date: this.fullTime,
        link: this.submitLink,
        video: this.submitVideo,
        thumbnailSrc: this.thumbnailSrc,
      };

      let configs = {
        headers: {
          Authorization: this.config,
        },
      };

      axios
        .post(SERVER.URL + SERVER.ROUTES.onlineRegister, body, configs)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          // if (err.response.status) {
          //   alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
          //   this.logout()
          // }
          console.log(err.response);
        });
    },
    clickDateForm() {
      this.$refs.datetimePicker.open(event);
    },
  },
};
</script>

<style>
</style>