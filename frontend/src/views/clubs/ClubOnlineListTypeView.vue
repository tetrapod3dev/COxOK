<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://livwanillustration.com/portfolio/recipe-illustrations/french-food-illustrations.jpg') ;"
      ></parallax>

      <div class="content-center">
        <h1 class="title">코~옥하기</h1>
      </div>
    </div>

    <div class="section online-list">
      <div class="container">
        <div class="button-container">
          <button class="learn-more" @click="goBackPage" style="margin-left: 20px;">목록으로</button>
        </div>
      </div>
    </div>


    <div class="container">
      <div class="row">
        <h2 class="title col-12">{{ type }} 모아보기</h2>
        <OnlineClubListItem v-for="club in clubs" :key="club.meetId" :online="club" class="col-12 col-md-6 col-lg-3" />
      </div>

      <PageButtons 
        class="d-flex justify-content-center"
        :curPage="curPage"
        :maxPage="maxPage"
        @move-page="movePage"/>
      
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/api'

import OnlineClubListItem from '@/components/clubs/OnlineClubListItem.vue'
import PageButtons from '@/components/common/PageButtons.vue'

export default {
  name: 'ClubOnlineListTypeView',
  data() {
    return {
      type: null,
      clubs: [],
      curPage: null,
      maxPage: null,
    }
  },
  components: {
    OnlineClubListItem,
    PageButtons
  },
  created() {
    this.getClubs();
  },
  methods: {
    getClubs() {
      this.type = this.$route.params.type
      axios
        .get(SERVER.URL + SERVER.ROUTES.onlineListType + this.$route.params.type + '/' + (this.$route.params.pageNum-1))
        .then(res => {
          this.curPage = parseInt(this.$route.params.pageNum)
          this.maxPage = parseInt((res.data.total - 1) / 12) + 1
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
      this.getClubs();
      scroll(0, 0);
    },
    goBackPage() {
      this.$router.go(-1);
    },
  }
}
</script>

<style>
.online-list .button-container {
  margin-top: -112px;
}
</style>