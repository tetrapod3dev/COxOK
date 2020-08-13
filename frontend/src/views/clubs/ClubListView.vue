<template>
  <div class="container">
    <h2>소모임 리스트 페이지(메인)입니당.</h2>

    <select @change="changeMainType">
      <option disabled selected>{{ mainClubType }}</option>
      <option>오프라인</option>
      <option>온라인</option>
    </select>

    <router-link :to="{ name: 'ClubMakeView' }"><button class="btn btn-outline-secondary mx-2">소모임 만들기</button></router-link>
    
    <OfflineClubList v-if="isOffline" />
    <OnlineClubList v-else />
  </div>
</template>

<script>
import OfflineClubList from '@/components/clubs/OfflineClubList.vue'
import OnlineClubList from '@/components/clubs/OnlineClubList.vue'
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'ClubListView',
  data() {
    return {
    }
  },
  components: {
    OfflineClubList,
    OnlineClubList
  },
  computed: {
    ...mapGetters(['mainClubType']),
    isOffline() {
      return (this.mainClubType == '오프라인') ? true : false
    }
  },
  created() {
  },
  methods: {
    ...mapActions(['changeClubMainType']),
    changeMainType(event) {
      this.changeClubMainType(event.target.value)
    },

  }
}
</script>

<style>

</style>