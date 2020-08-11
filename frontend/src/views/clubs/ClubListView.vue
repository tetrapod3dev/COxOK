<template>
  <div class="container">
    <h2>소모임 리스트 페이지(메인)입니당.</h2>
    
    <router-link :to="{ name: 'ClubMakeView' }"><button class="btn btn-outline-secondary mx-2">소모임 만들기</button></router-link>

    <div class="row">
      <router-link v-for="club in curClubs" :key="club.meetId" :to="{ name: 'ClubDetailView', params: { club_id: club.meetId } }" class="col-4">
        <img :src="imageSrc(club)">
      </router-link>
    </div>
  </div>
</template>

<script>
import SERVER from '@/api/api'
import axios from 'axios'

export default {
  name: 'ClubListView',
  data() {
    return {
      curPage: 1,
      curClubs: [],
    }
  },
  created() {
    axios
      .get(SERVER.URL + SERVER.ROUTES.clubList + (this.curPage-1))
      .then(res => {
        this.curClubs = res.data.list
      })
      .catch(err => console.log(err))
  },
  methods: {
    imageSrc(club) {
      return SERVER.IMAGE_URL + club.thumbnailSrc
    }
  }
}
</script>

<style>

</style>