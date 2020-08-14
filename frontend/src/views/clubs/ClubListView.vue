<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://picjumbo.com/wp-content/uploads/new-years-eve-champagne-party-happy-new-year-2210x1473.jpg') ;"
      ></parallax>

      <div class="container">
        <h1 class="title">소모임 리스트 페이지(메인)입니당.</h1>
      </div>
    </div>
    <button class="btn" @click="changeMainType('오프라인')">오프라인</button>
    <button class="btn" @click="changeMainType('온라인')">온라인</button>

    <div class="container">
      <div class="row">
        <router-link :to="{ name: 'ClubMakeView' }">
          <button class="btn btn-outline-secondary mx-2">소모임 만들기</button>
        </router-link>

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

<style>
</style>