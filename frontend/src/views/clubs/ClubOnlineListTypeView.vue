<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        :style="{ backgroundImage: 'url(\'' + require('@/assets/meet.jpg') + '\')' }"
      ></parallax>

      <div class="content-center">
        <h1 class="title">코~옥하기</h1>
      </div>
    </div>

    <div class="section online-list">
      <div class="container">
        <div class="button-container">
          <button class="learn-more" @click="goBackPage" style="margin-left: 20px;"><i class="fas fa-undo-alt"></i><p>목록으로</p></button>
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

button {
  position: relative;
  display: inline-block;
  cursor: pointer;
  outline: none;
  border: 0;
  vertical-align: middle;
  text-decoration: none;
}
button.learn-more {
  font-weight: 600;
  height: 60px;
  color: #382b22;
  text-transform: uppercase;
  padding: 0.3em 1.5em;
  background: #f2efe4;
  border: 2px solid #b69f81;
  border-left: 0;
  border-radius: 0;
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
  -webkit-transition: background 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: background 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    background 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    background 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more:first-child {
  border-top-left-radius: 0.75em;
  border-bottom-left-radius: 0.75em;
  padding-left: 2em;
  border-left: 2px solid #b69f81;
}
button.learn-more:last-child {
  border-top-right-radius: 0.75em;
  border-bottom-right-radius: 0.75em;
  padding-right: 2em;
}
button.learn-more::before {
  position: absolute;
  content: "";
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #f2d4ae;
  border-radius: inherit;
  box-shadow: 0 0 0 2px #b69f81, 0 0.625em 0 0 #f2f0ce;
  -webkit-transform: translate3d(0, 0.75em, -1em);
  transform: translate3d(0, 0.75em, -1em);
  -webkit-transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    box-shadow 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more:hover {
  background: #f2efe4;
  -webkit-transform: translate(0, 0.25em);
  transform: translate(0, 0.25em);
}
button.learn-more:hover::before {
  box-shadow: 0 0 0 2px #b69f81, 0 0.5em 0 0 #f2f0ce;
  -webkit-transform: translate3d(0, 0.5em, -1em);
  transform: translate3d(0, 0.5em, -1em);
}
button.learn-more:active {
  background: #f2efe4;
  -webkit-transform: translate(0em, 0.75em);
  transform: translate(0em, 0.75em);
}
button.learn-more:active::before {
  box-shadow: 0 0 0 2px #b69f81, 0 0 #f2f0ce;
  -webkit-transform: translate3d(0, 0, -1em);
  transform: translate3d(0, 0, -1em);
}
</style>