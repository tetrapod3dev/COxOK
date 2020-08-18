<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        :style="{ backgroundImage: 'url(\'' + require('@/assets/versus.jpg') + '\')' }"
      ></parallax>

      <div class="container">
        <h1 class="title">대결하기</h1>
      </div>
    </div>

  <div class="section versus-home">
      <div class="container">
        <div class="button-container">
          <router-link :to="{ name: 'VersusMakeView' }">
            <button v-if="isLoggedIn" class="learn-more">대결 작성</button>
          </router-link>
        </div>
      </div>
    </div>
    
    <div class="container">
      <div class="row">
        <router-link
          class="col-12 col-md-6 col-lg-4 text-decoration-none"
          v-for="versus in versusList"
          :key="versus.versusId"
          :to="{name: 'VersusDetailView', params: {versus_id: versus.versusId} }"
        >
          <b-card
            tag="article"
            style="max-width: 20rem;"
            class="versus-card mb-2"
          >
            <div class="row">
              <div class="versus-img">
                <img class="col-5" style="padding:0px; height:120px; bottom:4px;" :src="imageSrc1(versus)" />
                <!-- VS -->
                <i class="fas fa-utensils ml-2 mr-2"></i>
                <img class="col-5" style="padding:0px; height:120px; bottom:4px;" :src="imageSrc2(versus)" />
              </div>
            </div>
            <div class="mt-auto mb-auto">
            <b-card-text class="versus-card-text mt-2 p-2">
              {{versus.title}}
            </b-card-text>
            </div>
          </b-card>
        </router-link>
      </div>
    </div>
    <div id="bottomSensor" class="mb-2"></div>
    <h4 v-if="isEnd">- - -  리스트의 마지막입니다  - - -</h4>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/api";
import scrollmonitor from "scrollmonitor";
import { mapGetters } from 'vuex'

export default {
  name: "VersusHomeView",
  data() {
    return {
      versusList: [],
      curPage: 0,
      isEnd: false
    };
  },
  computed: {
    ...mapGetters(['isLoggedIn'])
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
              this.isEnd = true;
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
    scrollToTop: function () {
      window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
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
  },
  mounted: function () {
    this.addScrollWatcher();
  },
  updated: function () {
    this.loadUntilViewportIsFull();
  },
};
</script>

<style scoped>
.versus-home .button-container {
  margin-top: -112px;
}
.versus-card {
  width: 300px;
  height: 150px;
  font-size: 22px;
  font-weight: bold;

  max-width: 300px;
	position: relative;
	border-radius: 8px;
	overflow: hidden;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	-webkit-box-shadow: 0px 2px 7px 0px rgba(0,0,0,0.75);
	-moz-box-shadow: 0px 2px 7px 0px rgba(0,0,0,0.75);
	box-shadow: 0px 2px 7px 0px rgba(0,0,0,0.75); 
}
.versus-card-text{
	font-size: 25px;
	font-weight: bold;
	color: #f9f9f9;
	margin: 2px;
	opacity: 0;
	transition: 1s;
}
.versus-img{
	width: 100%;
}

.versus-card .versus-card-text{
	position: absolute;
	top: 0;
	left: 100%;
	z-index: 100;
	width: 100%;
	height: 100%;
	background: rgba(0,0,0,0.6);
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	transition: 0.35s;
}
.versus-card:hover .versus-card-text{
	left: 0;
}
.versus-card .versus-card-text:hover{
	opacity: 1;
}

@import url("https://fonts.googleapis.com/css?family=Rubik:700&display=swap");
* {
  box-sizing: border-box;
}
*::before, *::after {
  box-sizing: border-box;
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

.meet-button .button-container {
  margin-top: -112px;
}
</style>