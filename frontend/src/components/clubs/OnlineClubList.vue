<template>
  <div class="container">
    <div class="row">

      <h3 class="col-10">유튜브 강의</h3>
      <router-link :to="{ name: 'ClubOnlineListTypeView', params: {type: '유튜브강의', pageNum: 1} }" class="col-2">
        <button>더보기</button>
      </router-link>

      <div class="col-1" @click="moveYoutubePrev">
        이전
      </div>
      <div class="col-10 row">
        <OnlineClubListItem v-for="youtube in curYoutubes" :key="youtube.onlineId" :online="youtube" class="col-3" />
      </div>
      <div class="col-1" @click="moveYoutubeNext">
        다음
      </div>

      <h3 class="col-10">실시간 강의</h3>
      <router-link :to="{ name: 'ClubOnlineListTypeView', params: {type: '실시간강의', pageNum: 1} }" class="col-2">
        <button>더보기</button>
      </router-link>

      <div class="col-1" @click="moveMeetingPrev">
        이전
      </div>
      <div class="col-10 row">
        <OnlineClubListItem v-for="meeting in curMeetings" :key="meeting.onlineId" :online="meeting" class="col-3" />
      </div>
      <div class="col-1" @click="moveMeetingNext">
        다음
      </div>

    </div>
  </div>
</template>

<script>
import SERVER from '@/api/api'
import axios from 'axios'

import OnlineClubListItem from './OnlineClubListItem'

export default { 
  name: "OnlineClubList",
  data() {
    return {
      youtubes: [],
      meetings: [],
      youtubeCurPage: 0,
      meetingCurPage: 0,
    }
  },
  components: {
    OnlineClubListItem
  },
  created() {
    this.getYoutubes()
    this.getMeetings()
  },
  computed: {
    youtubeMaxPage() {
      return parseInt((this.youtubes.length-1) / 4) + 1
    },
    meetingMaxPage() {
      return parseInt((this.meetings.length-1) / 4) + 1
    },
    curYoutubes() {
      return this.youtubes.slice(this.youtubeCurPage*4, (this.youtubeCurPage+1)*4)
    },
    curMeetings() {
      return this.meetings.slice(this.meetingCurPage*4, (this.meetingCurPage+1)*4)
    },
  },
  methods: {
    getYoutubes() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.onlineListType + '유튜브강의/0')
        .then(res => {
          this.youtubes = res.data.list
        })
        .catch(err => console.log(err.response))
    },
    getMeetings() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.onlineListType + '실시간강의/0')
        .then(res => {
          this.meetings = res.data.list
        })
        .catch(err => console.log(err.response))
    },

    moveYoutubeNext() {
      this.youtubeCurPage += 1;
      if (this.youtubeCurPage == this.youtubeMaxPage) {
        this.youtubeCurPage = 0;
      }
    },
    moveYoutubePrev() {
      this.youtubeCurPage -= 1;
      if (this.youtubeCurPage == -1) {
        this.youtubeCurPage = this.youtubeMaxPage - 1;
      }
    },
    
    moveMeetingNext() {
      this.meetingCurPage += 1;
      if (this.meetingCurPage == this.meetingMaxPage) {
        this.meetingCurPage = 0;
      }
    },
    moveMeetingPrev() {
      this.meetingCurPage -= 1;
      if (this.meetingCurPage == -1) {
        this.meetingCurPage = this.meetingMaxPage - 1;
      }
    },
  }
}
</script>

<style>

</style>