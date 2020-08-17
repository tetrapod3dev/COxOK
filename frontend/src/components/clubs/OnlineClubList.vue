<template>
  <div class="container">
    <div class="meet-type row mb-5">
      <h2 class="title col-10 text-left">유튜브 강의</h2>
      <router-link :to="{ name: 'ClubOnlineListTypeView', params: {type: '유튜브강의', pageNum: 1} }" class="col-2 text-right mt-auto mb-auto">
        <button class="more-button">더보기</button>
      </router-link>

      <div class="row">
        <div class="prev-button col-1 mb-auto mt-auto" @click="moveYoutubePrev">
          <i class="far fa-arrow-alt-circle-left"></i>
        </div>
        <div class="col-10 row">
          <OnlineClubListItem v-for="youtube in curYoutubes" :key="youtube.onlineId" :online="youtube" class="col-4" />
        </div>
        <div class="next-button col-1 mb-auto mt-auto" @click="moveYoutubeNext">
          <i class="far fa-arrow-alt-circle-right"></i>
        </div>
      </div>
    </div>

    <div class="meet-type row mb-5">
      <h2 class="title col-10 text-left">실시간 강의</h2>
      <router-link :to="{ name: 'ClubOnlineListTypeView', params: {type: '실시간강의', pageNum: 1} }" class="col-2 text-right mt-auto mb-auto">
        <button class="more-button">더보기</button>
      </router-link>

      <div class="row">
        <div class="prev-button col-1 mb-auto mt-auto" @click="moveMeetingPrev">
          <i class="far fa-arrow-alt-circle-left"></i>
        </div>
        <div class="col-10 row">
          <OnlineClubListItem v-for="meeting in curMeetings" :key="meeting.onlineId" :online="meeting" class="col-4" />
        </div>
        <div class="next-button col-1 mb-auto mt-auto" @click="moveMeetingNext">
          <i class="far fa-arrow-alt-circle-right"></i>
        </div>
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
      return parseInt((this.youtubes.length-1) / 3) + 1
    },
    meetingMaxPage() {
      return parseInt((this.meetings.length-1) / 3) + 1
    },
    curYoutubes() {
      return this.youtubes.slice(this.youtubeCurPage*3, (this.youtubeCurPage+1)*3)
    },
    curMeetings() {
      return this.meetings.slice(this.meetingCurPage*3, (this.meetingCurPage+1)*3)
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
<style scoped>
.meet-type {
  padding: 30px 60px 30px 60px;
  border-radius: 40px;
  box-shadow: 0px 8px 40px rgba(128, 128, 128, 0.15);
}

.more-button {
  font-size:25px;
}

.prev-button, .next-button {
  font-size:30px;
  color: orange;
}

</style>