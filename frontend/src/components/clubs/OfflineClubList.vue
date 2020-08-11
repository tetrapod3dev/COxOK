<template>
  <div class="container">
    오프라인 소모임 리스트
    <div class="row">
      <h2 class="col-10">쿠킹 클래스!!!</h2>
      <router-link :to="{ name: 'ClubListTypeView', params: {type: '쿠킹클래스'} }" class="col-2">
        <button>더보기</button>
      </router-link>
      <OfflineClubListItem v-for="cookingClass in cookingClasses" :key="cookingClass.meetId" :club="cookingClass" class="col-3" />
    </div>

    <div class="row">
      <h2 class="col-10">공유치킨?</h2>
      <router-link :to="{ name: 'ClubListTypeView', params: {type: '공유키친'} }" class="col-2">
        <button>더보기</button>
      </router-link>
      <OfflineClubListItem v-for="sharedKitchen in sharedKitchens" :key="sharedKitchen.meetId" :club="sharedKitchen" class="col-3" />
    </div>

    <div class="row">
      <h2 class="col-10">홈파티</h2>
      <router-link :to="{ name: 'ClubListTypeView', params: {type: '홈파티'} }" class="col-2">
        <button>더보기</button>
      </router-link>
      <OfflineClubListItem v-for="party in parties" :key="party.meetId" :club="party" class="col-3" />
    </div>

  </div>
</template>

<script>
import SERVER from '@/api/api'
import axios from 'axios'
import OfflineClubListItem from './OfflineClubListItem.vue'

export default {
  name: 'OfflineClubList',
  data() {
    return {
      cookingClasses: [],
      sharedKitchens: [],
      parties: [],
    }
  },
  components: {
    OfflineClubListItem,
  },
  created() {
    this.getCookingClass()
    this.getSharedKitchen()
    this.getParty()
  },
  methods: {
    // 쿠킹 클래스 가져오기
    getCookingClass() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.clubListType + '쿠킹클래스')
        .then(res => {
          this.cookingClasses = res.data.list
        })
        .catch(err => console.log(err))
    }, 
    // 공유 주방 가져오기
    getSharedKitchen() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.clubListType + '공유키친')
        .then(res => {
          this.sharedKitchens = res.data.list
        })
        .catch(err => console.log(err))
    }, 
    // 쿠킹 클래스 가져오기
    getParty() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.clubListType + '홈파티')
        .then(res => {
          this.parties = res.data.list
        })
        .catch(err => console.log(err))
    },
  }
}
</script>

<style>

</style>