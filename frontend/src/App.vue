<template>
  <div id="app">
    <!-- Preloader Start Here -->
    <div id="preloader"></div>
    <!-- Preloader End Here -->
    <Header />
    <div>
      <router-view />
    </div>
    <button @click="scrollToTop" id="button-bottom" class="btn-circle">
      <i class="now-ui-icons arrows-1_minimal-up"></i>
    </button>
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
  created() {
    window.addEventListener("scroll", this.scrollFunction);
  },
  destroyed() {
    window.removeEventListener("scroll", this.scrollFunction);
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
  },
  methods: {
    scrollToTop: function () {
      window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
    },
    scrollFunction() {
      if (
        document.body.scrollTop > 20 ||
        document.documentElement.scrollTop > 20
      ) {
        document.getElementById("button-bottom").style.display = "block";
      } else {
        document.getElementById("button-bottom").style.display = "none";
      }
    },
  },
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
  background: #ffffff url("http://i3a104.p.ssafy.io/img/preloader.gif")
    no-repeat scroll center center;
  height: 100%;
  left: 0;
  overflow: visible;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 9999999;
}

#button-bottom {
  display: none;
  position: fixed;
  bottom: 20px;
  right: 30px;
  z-index: 99999;
  border: none;
  outline: none;
  background-color: #f96332;
  color: white;
  cursor: pointer;
}

#button-bottom:hover {
  background-color: #f67a50;
}

.btn-circle {
  width: 40px;
  height: 40px;
  padding: 6px 0px;
  border-radius: 20px;
  text-align: center;
  font-size: 12px;
  line-height: 1.42857;
}
</style>
