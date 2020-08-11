<template>
  <div class="container">
    <button @click="changeOff" class="my-5">오프라인</button>
    <button @click="changeOn">온라인</button>

    <h2 class="my-5">소모임 작성 페이지입니당.</h2>

    <OfflineClubMake v-if="isOffline" />

    <OnlineClubMake v-else />
  </div>
</template>

<script>
import OfflineClubMake from '@/components/clubs/OfflineClubMake.vue'
import OnlineClubMake from '@/components/clubs/OnlineClubMake.vue'

export default {
  name: 'ClubMakeView',
  data () {
    return {
      isOffline: true,
    }
  },
  components: {
    OfflineClubMake,
    OnlineClubMake
  },
  computed: {
  },
  mounted() {
  },
  methods: {
    changeOff() {
      this.isOffline = true;
    },
    changeOn() {
      this.isOffline = false;
    }
  },
}
</script>

<style>

</style>