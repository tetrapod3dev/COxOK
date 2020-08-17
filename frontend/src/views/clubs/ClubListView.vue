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

    <div class="section meet-button" >
      <div class="container">
        <div class="button-container">
          <button class="learn-more btn mr-2" @click="changeMainType('오프라인')">오프라인</button>
          <button class="learn-more btn" @click="changeMainType('온라인')">온라인</button> 
        </div>
      </div>
      <div class="Cookie-1" style="font-size:30px;font-weight:bold">{{this.mainClubType}} CO~OK</div>
    </div>

    <div class="container">
      <div class="row">
        <div class="col-12 text-right">
          <router-link :to="{ name: 'ClubMakeView' }">
            <button class="btn btn-outline-secondary mx-2">소모임 만들기</button>
          </router-link>
        </div>
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
    ...mapGetters(["mainClubType"]),
    isOffline() {
      return this.mainClubType == "오프라인" ? true : false;
    },
  },
  created() {},
  methods: {
    ...mapActions(["changeClubMainType"]),
    changeMainType(type) {
      this.changeClubMainType(type);
    },
  },
};
</script>

<style scoped>
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
  color: red;
  background: #ffe9e9;
  -webkit-transform: translate(0em, 0.75em);
          transform: translate(0em, 0.75em);
}
button.learn-more:active::before {
  box-shadow: 0 0 0 2px #b18597, 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0, -1em);
          transform: translate3d(0, 0, -1em);
}

.meet-button .button-container {
  margin-top: -112px;
}
</style>