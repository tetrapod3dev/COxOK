<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://livwanillustration.com/portfolio/recipe-illustrations/french-food-illustrations.jpg') ;"
      ></parallax>

      <div class="content-center">
        <h1 class="title">대결하기</h1>
      </div>
    </div>

  <div class="section versus-home">
      <div class="container">
        <div class="button-container">
          <router-link :to="{ name: 'VersusMakeView' }">
            <button class="learn-more">대결 작성</button>
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
                <img class="col-5" style="padding:0px" :src="imageSrc1(versus)" />
                VS
                <img class="col-5" style="padding:0px" :src="imageSrc2(versus)" />
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

export default {
  name: "VersusHomeView",
  data() {
    return {
      versusList: [],
      curPage: 0,
      isEnd: false
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
  font-size: inherit;
  font-family: inherit;
}
button.learn-more {
  font-weight: 600;
  color: #382b22;
  text-transform: uppercase;
  padding: 1.25em 2em;
  background: #fff0f0;
  border: 2px solid #b18597;
  border-radius: 0.75em;
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
  -webkit-transition: background 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: background 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more::before {
  position: absolute;
  content: '';
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #f9c4d2;
  border-radius: inherit;
  box-shadow: 0 0 0 2px #b18597, 0 0.625em 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0.75em, -1em);
          transform: translate3d(0, 0.75em, -1em);
  -webkit-transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), box-shadow 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more:hover {
  background: #ffe9e9;
  -webkit-transform: translate(0, 0.25em);
          transform: translate(0, 0.25em);
}
button.learn-more:hover::before {
  box-shadow: 0 0 0 2px #b18597, 0 0.5em 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0.5em, -1em);
          transform: translate3d(0, 0.5em, -1em);
}
button.learn-more:active {
  background: #ffe9e9;
  -webkit-transform: translate(0em, 0.75em);
          transform: translate(0em, 0.75em);
}
button.learn-more:active::before {
  box-shadow: 0 0 0 2px #b18597, 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0, -1em);
          transform: translate3d(0, 0, -1em);
}






.plus-minus {
  --primary: #1E2235;
  --secondary: #FAFBFF;
  --duration: .5s;
  -webkit-appearance: none;
  -moz-appearance: none;
  -webkit-tap-highlight-color: transparent;
  -webkit-mask-image: -webkit-radial-gradient(white, black);
  outline: none;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
  -webkit-perspective: 240px;
          perspective: 240px;
  border-radius: 50%;
  width: 36px;
  height: 36px;
  border: 4px solid var(--primary);
  background-size: 300% 300%;
  -webkit-transition: -webkit-transform .3s;
  transition: -webkit-transform .3s;
  transition: transform .3s;
  transition: transform .3s, -webkit-transform .3s;
  -webkit-transform: scale(var(--scale, 1)) translateZ(0);
          transform: scale(var(--scale, 1)) translateZ(0);
  -webkit-animation: var(--name, unchecked) var(--duration) ease forwards;
          animation: var(--name, unchecked) var(--duration) ease forwards;
}
.plus-minus:before, .plus-minus:after {
  content: '';
  position: absolute;
  width: 16px;
  height: var(--height, 16px);
  left: 6px;
  top: var(--top, 6px);
  background: var(--background, var(--primary));
  -webkit-animation: var(--name-icon-b, var(--name-icon, unchecked-icon)) var(--duration) ease forwards;
          animation: var(--name-icon-b, var(--name-icon, unchecked-icon)) var(--duration) ease forwards;
}
.plus-minus:before {
  -webkit-clip-path: polygon(0 6px, 6px 6px, 6px 0, 10px 0, 10px 6px, 16px 6px, 16px 10px, 10px 10px, 10px 16px, 6px 16px, 6px 10px, 0 10px);
          clip-path: polygon(0 6px, 6px 6px, 6px 0, 10px 0, 10px 6px, 16px 6px, 16px 10px, 10px 10px, 10px 16px, 6px 16px, 6px 10px, 0 10px);
}
.plus-minus:after {
  --height: 4px;
  --top: 12px;
  --background: var(--secondary);
  --name-icon-b: var(--name-icon-a, checked-icon);
}
.plus-minus:active {
  --scale: .95;
}
.plus-minus:checked {
  --name: checked;
  --name-icon-b: checked-icon;
  --name-icon-a: unchecked-icon;
}

@-webkit-keyframes checked-icon {
  from {
    -webkit-transform: translateZ(12px);
            transform: translateZ(12px);
  }
  to {
    -webkit-transform: translateX(16px) rotateY(90deg) translateZ(12px);
            transform: translateX(16px) rotateY(90deg) translateZ(12px);
  }
}

@keyframes checked-icon {
  from {
    -webkit-transform: translateZ(12px);
            transform: translateZ(12px);
  }
  to {
    -webkit-transform: translateX(16px) rotateY(90deg) translateZ(12px);
            transform: translateX(16px) rotateY(90deg) translateZ(12px);
  }
}
@-webkit-keyframes unchecked-icon {
  from {
    -webkit-transform: translateX(-16px) rotateY(-90deg) translateZ(12px);
            transform: translateX(-16px) rotateY(-90deg) translateZ(12px);
  }
  to {
    -webkit-transform: translateZ(12px);
            transform: translateZ(12px);
  }
}
@keyframes unchecked-icon {
  from {
    -webkit-transform: translateX(-16px) rotateY(-90deg) translateZ(12px);
            transform: translateX(-16px) rotateY(-90deg) translateZ(12px);
  }
  to {
    -webkit-transform: translateZ(12px);
            transform: translateZ(12px);
  }
}
@-webkit-keyframes checked {
  from {
    background-image: radial-gradient(ellipse at center, var(--primary) 0%, var(--primary) 25%, var(--secondary) 25.1%, var(--secondary) 100%);
    background-position: 100% 50%;
  }
  to {
    background-image: radial-gradient(ellipse at center, var(--primary) 0%, var(--primary) 25%, var(--secondary) 25.1%, var(--secondary) 100%);
    background-position: 50% 50%;
  }
}
@keyframes checked {
  from {
    background-image: radial-gradient(ellipse at center, var(--primary) 0%, var(--primary) 25%, var(--secondary) 25.1%, var(--secondary) 100%);
    background-position: 100% 50%;
  }
  to {
    background-image: radial-gradient(ellipse at center, var(--primary) 0%, var(--primary) 25%, var(--secondary) 25.1%, var(--secondary) 100%);
    background-position: 50% 50%;
  }
}
@-webkit-keyframes unchecked {
  from {
    background-image: radial-gradient(ellipse at center, var(--secondary) 0%, var(--secondary) 25%, var(--primary) 25.1%, var(--primary) 100%);
    background-position: 100% 50%;
  }
  to {
    background-image: radial-gradient(ellipse at center, var(--secondary) 0%, var(--secondary) 25%, var(--primary) 25.1%, var(--primary) 100%);
    background-position: 50% 50%;
  }
}
@keyframes unchecked {
  from {
    background-image: radial-gradient(ellipse at center, var(--secondary) 0%, var(--secondary) 25%, var(--primary) 25.1%, var(--primary) 100%);
    background-position: 100% 50%;
  }
  to {
    background-image: radial-gradient(ellipse at center, var(--secondary) 0%, var(--secondary) 25%, var(--primary) 25.1%, var(--primary) 100%);
    background-position: 50% 50%;
  }
}
html {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

* {
  box-sizing: inherit;
}
*:before, *:after {
  box-sizing: inherit;
}

</style>