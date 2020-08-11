<template>
  <div class="container">
    <div class="row">
      <h3 class="col-12">타입별 모아보기 ({{ type }})</h3>

      <OfflineClubListItem v-for="club in clubs" :key="club.meetId" :club="club" class="col-3" />
    </div>
    
  </div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/api'

import OfflineClubListItem from '@/components/clubs/OfflineClubListItem.vue'

export default {
  name: 'ClubListTypeView',
  data() {
    return {
      type: null,
      clubs: [],
    }
  },
  components: {
    OfflineClubListItem
  },
  created() {
    this.type = this.$route.params.type
    axios
      .get(SERVER.URL + SERVER.ROUTES.clubListType + this.$route.params.type)
      .then(res => {
        this.clubs = res.data.list
      })
      .catch(err => console.log(err))
  }
}
</script>

<style>

</style>