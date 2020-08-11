<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <h2 class="my-5">Test2</h2>

      <div class="content-center">
        <h1 class="title">요리대전</h1>
      </div>
    </div>
    <router-link
      :to="{ name: 'VersusMakeView' }"
      tag="button"
      class="btn btn-outline-secondary mx-2"
    >요리대전 만들기</router-link>

    <div class="container">
      <div class="row">
        <router-link
          class="col-12 col-lg-4 col-md-6"
          v-for="versus in versusList"
          :key="versus.versusId"
          :to="{name: 'VersusDetailView', params: {versus_id: versus.versusId} }"
        >
          <div class="card" style="width: 18rem;">
            <div class="card-body">
              <div class="row">
                <img class="col-6" :src="imageSrc1(versus)" />
                <img class="col-6" :src="imageSrc2(versus)" />
              </div>
              <h5 class="card-title">{{ versus.title }}</h5>
            </div>
          </div>
        </router-link>
      </div>
    </div>

    <div id="bottomSensor"></div>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/api";
import scrollmonitor from "scrollmonitor";

export default {
  name: "VersusHomeView",
  data() {
    return {
      versusList: [],
      curPage: 0,
    };
  },
  methods: {
    imageSrc1(versus) {
      return SERVER.IMAGE_URL + versus.recipeThumbnail1;
    },
    imageSrc2(versus) {
      return SERVER.IMAGE_URL + versus.recipeThumbnail2;
    },
    getVersus: function () {
      const self = this;
      if (this.curPage >= 0) {
        axios
          .get(SERVER.URL + SERVER.ROUTES.versusList + this.curPage)
          .then((res) => {
            if (res.data.list.length == 0) {
              self.curPage = -1;
            } else {
              self.curPage += 1;
              self.versusList = [...self.versusList, ...res.data.list];
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    addScrollWatcher: function () {
      const bottomSensor = document.querySelector("#bottomSensor");
      const watcher = scrollmonitor.create(bottomSensor);
      watcher.enterViewport(() => {
        setTimeout(() => {
          this.getVersus();
        }, 1000);
      });
    },
    loadUntilViewportIsFull: function () {
      const bottomSensor = document.querySelector("#bottomSensor");
      const watcher = scrollmonitor.create(bottomSensor);
      if (watcher.isFullyInViewport) {
        this.getVersus();
      }
    },
  },
  created: function () {
    this.getVersus();
  }
};
</script>

<style>
</style>