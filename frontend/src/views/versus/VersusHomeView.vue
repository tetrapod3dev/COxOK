<template>
  <div>
    <h2>요리대전 메인 페이지입니다.</h2>
    <router-link :to="{ name: 'VersusMakeView' }"><button class="btn btn-outline-secondary mx-2">요리대전 만들기</button></router-link>

    <router-link v-for="versus in versusList" :key="versus.versusId" :to="{name: 'VersusDetailView', params: {versus_id: versus.versusId} }">
      <div class="card" style="width: 18rem;">
        <div class="card-body">
          <img :src="imageSrc(versus)">
          <h5 class="card-title">{{ versus.title }}</h5>
        </div>
      </div>
    </router-link>

    <div id="bottomSensor"></div>
    <button @click="scrollToTop" class="button-bottom">^</button>
  </div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/api'
import scrollmonitor from 'scrollmonitor'

export default {
  name: 'VersusHomeView',
  data() {
    return {
      versusList: [],
      curPage: 0,
    }
  },
  methods: {
    imageSrc(versus) {
      return SERVER.IMAGE_URL + versus.recipeThumbnail1
    },
    getVersus: function () {
      const self = this
      if (this.curPage >= 0) {
        axios.get(SERVER.URL + SERVER.ROUTES.versusList + this.curPage)
          .then(res => {
            if (res.data.list.length == 0) {
              self.curPage = -1
            } else {
              self.curPage += 1
              self.versusList = [...self.versusList, ...res.data.list]
            }
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    addScrollWatcher: function () {
      const bottomSensor = document.querySelector('#bottomSensor')
      const watcher = scrollmonitor.create(bottomSensor)
      watcher.enterViewport(() => {
        setTimeout(() => {
          this.getVersus()
        }, 1000)
      })
    },
    scrollToTop: function () {
      scroll(0, 0)
    },
    loadUntilViewportIsFull: function () {
      const bottomSensor = document.querySelector('#bottomSensor')
      const watcher = scrollmonitor.create(bottomSensor)
      if (watcher.isFullyInViewport) {
        this.getVersus()
      }
    },
  },
  created: function () {
    this.getVersus()
  },
  mounted: function() {
    this.addScrollWatcher()
  },
  updated: function() {
    this.loadUntilViewportIsFull()
  }
}
</script>

<style>

</style>