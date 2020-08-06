<template>
  <div id="app">
    <!-- Preloader Start Here -->
    <div id="preloader"></div>
    <!-- Preloader End Here -->
    <Header />
    <div>
      <router-view />
    </div>
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";

export default {
  name: "App",
  components: {
    Header,
    Footer,
  },
  computed: {},
  data() {
    return {};
  },
  mounted() {
    var fadeTarget = document.getElementById("preloader");
    var fadeEffect = setInterval(function () {
        if (!fadeTarget.style.opacity) {
            fadeTarget.style.opacity = 1;
        }
        if (fadeTarget.style.opacity > 0) {
            fadeTarget.style.opacity -= 0.1;
        } else {
            clearInterval(fadeEffect);
        }
    }, 200);
    fadeTarget.style.display = "none";
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#preloader {
	background: #ffffff url('http://i3a104.p.ssafy.io/img/preloader.gif') no-repeat scroll center center;
	height: 100%;
	left: 0;
	overflow: visible;
	position: fixed;
	top: 0;
	width: 100%;
	z-index: 9999999;
}

</style>
