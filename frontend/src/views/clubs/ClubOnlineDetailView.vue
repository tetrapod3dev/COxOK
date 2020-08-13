<template>
  <div class="container">
    온라인 상세보기
    <h2>작성자: {{ author }}</h2>
    <h2>현재 접속자: {{ userId }}</h2>

    <div v-if="author.id == userId">
      <router-link :to="{ name: 'ClubOnlineUpdateView', params: { club_id: online.onlineId } }"><button>수정</button></router-link>
      <button @click="deleteClub">삭제</button>
    </div>
    <div v-else>
      <button @click="changeJoinClub">{{ joinMent }}</button>
    </div>
    
    <h3>{{ online.title }}</h3>
    
    <div v-html="online.content"></div>

    <div v-if="online.video != null">
      <div class="embed-responsive embed-responsive-16by9">
        <iframe class="embed-responsive-item" :src="videoUrl" allowfullscreen></iframe>
      </div>
    </div>

    <div v-else>
      <a :href="online.link" target="_blank">강의 링크로 이동 {{ online.link }}</a>
    </div>

  </div>
</template>

<script>
import SERVER from '@/api/api'
import axios from 'axios'
import { mapGetters } from 'vuex'

export default {
  name: 'ClubOnlineDetailView',
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
      author: {
        id: null,
        nickname: null,
      },
      userId: null,
      isIn: false,
    }
  },
  computed: {
    ...mapGetters(['config']),
    videoUrl() {
      return `https://youtube.com/embed/${this.online.video}`
    },
    joinMent() {
      return (this.isIn) ? "취소" : "참가"
    }
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
        this.author = { id: res.data.online.userId , nickname: res.data.writerNickname}
        this.userId = res.data.userId
        this.isIn = (res.data.joinList.indexOf(res.data.userId) >= 0) ? true : false 
      })
      .catch(err => console.log(err.response))
  },
  methods: {
    deleteClub() {
      let response = confirm('삭제하실 건가요?')
      if (response) {
        axios
          .delete(SERVER.URL + SERVER.ROUTES.onlineDelete + this.$route.params.club_id, {
            headers: {
              "Authorization": this.config,
            },
          })
        .then(res => console.log(res))
        .catch(err => console.log(err.response))
      }
    },

    changeJoinClub() {
      if (this.isIn) {
        axios
          .delete(SERVER.URL + SERVER.ROUTES.cancelOnline + this.$route.params.club_id, {
            headers: {
              "Authorization": this.config,
            },
          })
          .then(() => {
            alert('참가를 취소하셨습니다.')
            this.isIn = false
          })
          .catch(err => console.log(err.response))
      } else {
        axios
          .post(SERVER.URL + SERVER.ROUTES.joinOnline, { "onlineId": this.$route.params.club_id }, {
            headers: {
              "Authorization": this.config,
            },
          })
          .then(() => {
            alert('참가 신청에 성공했습니다.')
            this.isIn = true
          })
          .catch(err => console.log(err.response))
      }
    }
  }
}
</script>

<style>

</style>