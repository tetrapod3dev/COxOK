<template>
  <div class="container">

    <p class="my-5">Temp</p>
    
    <h2 class="my-5">소모임(온라인) 수정 페이지입니당.</h2>

    <select v-model="online.type" @change="initData">
      <option>유튜브강의</option>
      <option>실시간강의</option>
    </select>

    <input type="text" v-model="online.title">
    <br>
    <CxkEditor :value.sync="online.content" />

    <datetime type="datetime" v-model="online.date" format="yyyy-MM-dd HH:mm:ss" value-zone="Asia/Seoul" class="col-10 offset-1"></datetime>

    <div v-if="online.type == '유튜브강의'">
      비디오 ID: <input type="text" v-model="online.video">
    </div>
    <div v-else>
      실시간 강의 링크: <input type="text" v-model="online.link">
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

import { mapGetters } from 'vuex'

export default {
  name: 'ClubUpdateView',
  data() {
    return {
      online:  {
        onlineId: null,
        title: null,
        content: null,
        type: null,
        date: null,
        link: null,
        thumbnailSrc: null,
        video: null,
      },
      rawFile: null,
    }
  },
  computed: {
    ...mapGetters(['config']),
    fullTime() {
      return this.online.date.slice(0, 19)
    },
    submitVideo() {
      return (this.online.video != null) ? this.online.video : 'null'
    },
    submitLink() {
      return (this.online.link != null) ? this.online.link : 'null'
    }
  },
  components: {
    CxkEditor,
    datetime: Datetime,
  },
  created() {
    axios
      .get(SERVER.URL + SERVER.ROUTES.onlineDetail + this.$route.params.club_id, {
          headers: {
            "Authorization": this.config,
          },
        })
      .then(res => {
        this.online = res.data.online
      })
      .catch(err => console.log(err.response))
  },
  methods: {
    onChangeThumbnail(event) {
      this.rawFile = event.target.files[0]
    },
    initData() {
      this.online.video = null,
      this.online.link = null,
      this.online.rawFile = null
    },

    makeOnlineClub: async function() {
      if (this.online.type == '실시간강의') {
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
              self.online.thumbnailSrc = res.data.photo[0]
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
            id: this.online.video
          }
        })
        .then(res => {
          console.log(res)
          this.online.thumbnailSrc = res.data.items[0].snippet.thumbnails.default.url
        })
        .catch(err => console.log(err.response))
      }
    },
    sendData: async function() {
      await this.makeOnlineClub()

      let body = {
        'onlineId': this.online.onlineId,
        'type': this.online.type,
        'title': this.online.title,
        'content': this.online.content,
        'date': this.fullTime,
        'link': this.submitLink,
        'video': this.submitVideo,
        'thumbnailSrc': this.online.thumbnailSrc
      }

      let configs = {
        headers: {
          Authorization: this.config,
        },
      };

      console.log(body, configs)

      axios
        .put(SERVER.URL + SERVER.ROUTES.onlineRegister, body, configs)
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          // if (err.response.status) {
          //   alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
          //   this.logout()
          // }
          console.log(err.response)
        })
    }
  }
}
</script>

<style>

</style>