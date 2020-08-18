<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://i.pinimg.com/736x/7e/e8/45/7ee84510d6d06262623d1294208c1123.jpg') ;"
      ></parallax>

      <div class="container">
        <h1 class="title">코~옥하기</h1>
      </div>
    </div>

    <div class="section meet-button pb-0" >
      <div class="container">
        <div class="button-container">
          <button class="learn-more" @click="changeMainType('오프라인')">오프라인</button>
          <button v-if="isLoggedIn" class="learn-more" @click="makeClub">코~옥 만들기</button> 
          <button class="learn-more" @click="changeMainType('온라인')">온라인</button> 
        </div>
      </div>
    </div>

    <div class="container">
      <div class="row">
        <OfflineClubList v-if="isOffline" />
        <OnlineClubList v-else />
      </div>
    </div>
  </div>
</template>

<script>
import OfflineClubList from "@/components/clubs/OfflineClubList.vue";
import OnlineClubList from "@/components/clubs/OnlineClubList.vue";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "ClubListView",
  data() {
    return {};
  },
  components: {
    OfflineClubList,
    OnlineClubList,
  },
  computed: {
    ...mapGetters(["mainClubType", "isLoggedIn"]),
    isOffline() {
      return this.mainClubType == "오프라인" ? true : false;
    },
  },
  created() {},
  methods: {
    ...mapActions(["changeClubMainType"]),
    makeClub() {
      this.$router.push({name: 'ClubMakeView'})
    },
    changeMainType(type) {
      this.changeClubMainType(type);
    },
  },
};
</script>

<style scoped>
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