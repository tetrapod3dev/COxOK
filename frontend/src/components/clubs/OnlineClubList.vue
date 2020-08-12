<template>
  <div class="container">
    <div class="row">
      <h3 class="col-12">유튜브 강의</h3>
      <OnlineClubListItem v-for="youtube in youtubes" :key="youtube.onlineId" :online="youtube" class="col-3" />

      <h3 class="col-12">실시간 강의</h3>
      <OnlineClubListItem v-for="meeting in meetings" :key="meeting.onlineId" :online="meeting" class="col-3" />
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
    }
  },
  components: {
    OnlineClubListItem
  },
  created() {
    this.getYoutubes()
    this.getMeetings()
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
    }
  }
}
</script>

<style>

</style>