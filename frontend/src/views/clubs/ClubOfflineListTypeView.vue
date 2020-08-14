<template>
  <div class="container">
    <div class="row">
      <h3 class="col-12">타입별 모아보기 ({{ type }})</h3>

      <OfflineClubListItem v-for="club in clubs" :key="club.meetId" :club="club" class="col-3" />
    </div>

    <PageButtons 
      class="d-flex justify-content-center"
      :curPage="curPage"
      :maxPage="maxPage"
      @move-page="movePage"/>
    
  </div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/api'

import OfflineClubListItem from '@/components/clubs/OfflineClubListItem.vue'
import PageButtons from '@/components/common/PageButtons.vue'

export default {
  name: 'ClubListTypeView',
  data() {
    return {
      type: null,
      clubs: [],
      curPage: null,
      maxPage: null,
    }
  },
  components: {
    OfflineClubListItem,
    PageButtons
  },
  created() {
    this.getClubs();
  },
  methods: {
    getClubs() {
      this.type = this.$route.params.type
      axios
        .get(SERVER.URL + SERVER.ROUTES.clubListType + this.$route.params.type + '/' + (this.$route.params.pageNum-1))
        .then(res => {
          this.curPage = parseInt(this.$route.params.pageNum)
          this.maxPage = parseInt((res.data.total - 1) / 6) + 1
          this.clubs = res.data.list
        })
        .catch(err => console.log(err))
    },

    movePage(page) {
      if (page == "«") {
        this.$router.push({ params: { type: this.type, pageNum: 1 } });
      } else if (page == "»") {
        this.$router.push({ params: { type: this.type, pageNum: this.maxPage } });
      } else if (page != this.curPage) {
        this.$router.push({ params: { type: this.type, pageNum: parseInt(page) } });
      }
      scroll(0, 0);
    },
  }
}
</script>

<style>

</style>