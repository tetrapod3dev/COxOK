<template>
  <div class="wrapper">
    <section id="top">
      <card
        type="background"
        style="background-image: url('https://previews.123rf.com/images/seamartini/seamartini1609/seamartini160900068/62637928-cooking-and-kitchen-utensils-seamless-background-wallpaper-with-vector-pattern-icons-of-pizza-bread-.jpg') ;"
      >
        <div class="card-title text-left">
          <h1 style>코~옥 만들기</h1>
        </div>
      </card>
    </section>
    <div class="container">
      <button class="btn my-5" @click="changeOff">오프라인</button>
      <button class="btn" @click="changeOn">온라인</button>

      <OfflineClubMake v-if="isOffline" />

      <OnlineClubMake v-else />
    </div>
  </div>
</template>

<script>
import OfflineClubMake from "@/components/clubs/OfflineClubMake.vue";
import OnlineClubMake from "@/components/clubs/OnlineClubMake.vue";
import { Card } from "@/components/global";

export default {
  name: "ClubMakeView",
  data() {
    return {
      isOffline: true,
    };
  },
  components: {
    OfflineClubMake,
    OnlineClubMake,
    Card,
  },
  computed: {},
  mounted() {},
  methods: {
    changeOff() {
      this.isOffline = true;
    },
    changeOn() {
      this.isOffline = false;
    },
  },
};
</script>

<style>
</style>