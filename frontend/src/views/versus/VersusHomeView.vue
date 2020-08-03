<template>
  <div>
    <h2>요리대전 메인 페이지입니다.</h2>
    <router-link :to="{ name: 'VersusMakeView' }"><button class="btn btn-outline-secondary mx-2">요리대전 만들기</button></router-link>

    <router-link v-for="versus_dummy in versus_dummys" :key="versus_dummy.id" :to="{name: 'VersusDetailView', params: {versus_id: versus_dummy.id} }">
      <div class="card" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">{{ versus_dummy.title }}</h5>
          <p class="card-text text-left">{{ versus_dummy.content }}</p>
        </div>
      </div>
    </router-link>

    <div class="row">
      <div v-for="photo in photos" :key="'A' + photo.id" class="col-4">
        <h5>{{ photo.title }}</h5>
        <img :src="photo.thumbnailUrl" :alt="photo.title">
      </div>
      <div id="bottomSensor"></div>
      <button @click="scrollToTop" class="button-bottom">^</button>
    </div>

  </div>
</template>

<script>
import axios from 'axios'
import scrollmonitor from 'scrollmonitor'

export default {
  name: 'VersusHomeView',
  data() {
    return {
      versus_dummys: [
        {
          'id': 1,
          'title': '더미 데이터 제목',
          'content': '더미 데이터 설명',
        },
        {
          'id': 2,
          'title': '더미 데이터 제목222',
          'content': '더미 데이터 설명222',
        },
      ],
      photos: [],
      page: 1,
    }
  },
  methods: {
    getPhotos: function () {
      const options = {
        params: {
          _page: this.page++,
          _limit: 6,
        }
      }
      axios.get('https://jsonplaceholder.typicode.com/photos', options)
        .then(res => {
          this.photos = [...this.photos, ...res.data]
        })
        .catch(err => {
          console.log(err)
        })
    },
    addScrollWatcher: function () {
      const bottomSensor = document.querySelector('#bottomSensor')
      const watcher = scrollmonitor.create(bottomSensor)
      watcher.enterViewport(() => {
        setTimeout(() => {
          this.getPhotos()
        }, 100)
      })
    },
    scrollToTop: function () {
      scroll(0, 0)
    },
    loadUntilViewportIsFull: function () {
      const bottomSensor = document.querySelector('#bottomSensor')
      const watcher = scrollmonitor.create(bottomSensor)
      if (watcher.isFullyInViewport) {
        this.getPhotos()
      }
    },
  },
  created: function () {
    this.getPhotos()
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