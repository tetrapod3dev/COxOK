<template>
  <div>
    <select v-model="type" @change="initData">
      <option>유튜브강의</option>
      <option>실시간강의</option>
    </select>

    <input type="text" v-model="title">
    <br>
    <CxkEditor :value.sync="content" />

    <datetime type="datetime" v-model="date" format="yyyy-MM-dd HH:mm:ss" value-zone="Asia/Seoul" class="col-10 offset-1"></datetime>

    <div v-if="isYouTube">
      비디오 ID: <input type="text" v-model="video">
    </div>
    <div v-else>
      실시간 강의 링크: <input type="text" v-model="link">
      실시간 강의 사진: <input type="file" @change="onChangeThumbnail" >
    </div>

    <button @click="sendData">제출하기</button>

  </div>
</template>

<script>
import SERVER from '@/api/api'
import axios from 'axios'

const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY
const API_URL = 'https://www.googleapis.com/youtube/v3/videos'

import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import { Datetime } from 'vue-datetime';

export default {
  name: "OnlineClubMake",
  data() {
    return {
      type: '유튜브강의',
      title: null,
      content: null,
      date: null,
      video: null,
      link: null,
      thumbnailSrc: null,
      rawFile: null,
    }
  },
  components: {
    CxkEditor,
    datetime: Datetime,
  },
  computed: {
    fullTime() {
      return this.date.slice(0, 19)
    },
    isYouTube() {
      return (this.type == '유튜브강의') ? true : false
    },
    submitVideo() {
      return (this.video != null) ? this.video : 'null'
    },
    submitLink() {
      return (this.link != null) ? this.link : 'null'
    }
  },
  methods: {
    onChangeThumbnail(event) {
      this.rawFile = event.target.files[0]
    },
    initData() {
      this.video = null,
      this.link = null,
      this.rawFile = null
    },

    makeOnlineClub: async function() {
      if (this.type == '실시간강의') {
        let frm = new FormData()
        const self = this
        if (this.rawFile != null) {
          frm.append("photo", this.rawFile);
          await axios
            .post(SERVER.URL + SERVER.ROUTES.photoRegister, frm, {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            })
            .then((res) => {
              self.thumbnailSrc = res.data.photo[0]
            })
            .catch((err) => {
              console.log(err);
            });
        }
      } else {
        await axios.get(API_URL, {
          params: {
            key: API_KEY,
            part: 'snippet',
            type: 'video',
            id: this.video
          }
        })
        .then(res => {
          this.thumbnailSrc = res.data.items[0].snippet.thumbnails.default.url
        })
        .catch(err => console.log(err.response))
      }
    },
    sendData: async function() {
      await this.makeOnlineClub()

      let body = {
        'type': this.type,
        'title': this.title,
        'content': this.content,
        'date': this.fullTime,
        'link': this.link,
        'video': this.video,
        'thumbnailSrc': this.thumbnailSrc
      }

      console.log(body)
    }
  }
}
</script>

<style>

</style>