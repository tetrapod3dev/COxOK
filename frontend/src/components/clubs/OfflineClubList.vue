<template>
  <div class="container">
    오프라인 소모임 리스트
    <div class="row">
      <h2 class="col-10">쿠킹 클래스!!!</h2>
      <router-link :to="{ name: 'ClubOfflineListTypeView', params: {type: '쿠킹클래스', pageNum: 1} }" class="col-2">
        <button>더보기</button>
      </router-link>

      <div class="row">
        <div class="col-1" @click="moveCookingPrev">
          이전
        </div>
        <div class="col-10 row">
          <OfflineClubListItem v-for="cookingClass in curCookings" :key="cookingClass.meetId" :club="cookingClass" class="col-3" />
        </div>
        <div class="col-1" @click="moveCookingNext">
          다음
        </div>
      </div>
      
    </div>

    <div class="row">
      <h2 class="col-10">공유치킨?</h2>
      <router-link :to="{ name: 'ClubOfflineListTypeView', params: {type: '공유키친', pageNum: 1} }" class="col-2">
        <button>더보기</button>
      </router-link>
      
      <div class="row">
        <div class="col-1" @click="moveKitchenPrev">
          이전
        </div>
        <div class="col-10 row">
          <OfflineClubListItem v-for="sharedKitchen in curKitchens" :key="sharedKitchen.meetId" :club="sharedKitchen" class="col-3" />
        </div>
        <div class="col-1" @click="moveKitchenNext">
          다음
        </div>
      </div>
    </div>

    <div class="row">
      <h2 class="col-10">홈파티</h2>
      <router-link :to="{ name: 'ClubOfflineListTypeView', params: {type: '홈파티', pageNum: 1} }" class="col-2">
        <button>더보기</button>
      </router-link>
      
      <div class="row">
        <div class="col-1" @click="movePartyPrev">
          이전
        </div>
        <div class="col-10 row">
          <OfflineClubListItem v-for="party in curParties" :key="party.meetId" :club="party" class="col-3" />
        </div>
        <div class="col-1" @click="movePartyNext">
          다음
        </div>
      </div>
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
      cookingCurPage: 0,
      kitchenCurPage: 0,
      partyCurPage: 0,
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
  computed: {
    cookingMaxPage() {
      return parseInt((this.cookingClasses.length-1) / 4) + 1
    },
    kitchenMaxPage() {
      return parseInt((this.sharedKitchens.length-1) / 4) + 1
    },
    partyMaxPage() {
      return parseInt((this.parties.length-1) / 4) + 1
    },
    curCookings() {
      return this.cookingClasses.slice(this.cookingCurPage*4, (this.cookingCurPage+1)*4)
    },
    curKitchens() {
      return this.sharedKitchens.slice(this.kitchenCurPage*4, (this.kitchenCurPage+1)*4)
    },
    curParties() {
      return this.sharedKitchens.slice(this.partyCurPage*4, (this.partyCurPage+1)*4)
    },
  },
  methods: {
    // 쿠킹 클래스 가져오기
    getCookingClass() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.clubListType + '쿠킹클래스/0')
        .then(res => {
          this.cookingClasses = res.data.list
        })
        .catch(err => console.log(err))
    }, 
    // 공유 주방 가져오기
    getSharedKitchen() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.clubListType + '공유키친/0')
        .then(res => {
          this.sharedKitchens = res.data.list
        })
        .catch(err => console.log(err))
    }, 
    // 쿠킹 클래스 가져오기
    getParty() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.clubListType + '홈파티/0')
        .then(res => {
          this.parties = res.data.list
        })
        .catch(err => console.log(err))
    },
    
    moveCookingNext() {
      this.cookingCurPage += 1;
      if (this.cookingCurPage == this.cookingMaxPage) {
        this.cookingCurPage = 0;
      }
    },
    moveCookingPrev() {
      this.cookingCurPage -= 1;
      if (this.cookingCurPage == -1) {
        this.cookingCurPage = this.cookingMaxPage - 1;
      }
    },

    moveKitchenNext() {
      this.kitchenCurPage += 1;
      if (this.kitchenCurPage == this.kitchenMaxPage) {
        this.kitchenCurPage = 0;
      }
    },
    moveKitchenPrev() {
      this.kitchenCurPage -= 1;
      if (this.kitchenCurPage == -1) {
        this.kitchenCurPage = this.kitchenMaxPage - 1;
      }
    },

    movePartyNext() {
      this.partyCurPage += 1;
      if (this.partyCurPage == this.partyMaxPage) {
        this.partyCurPage = 0;
      }
    },
    movePartyPrev() {
      this.partyCurPage -= 1;
      if (this.partyCurPage == -1) {
        this.partyCurPage = this.partyMaxPage - 1;
      }
    },
  }
}
</script>

<style>

</style>