<template>
  <div class="container">
    <div class="meet-type row mb-5">
      <h2 class="title col-10 text-left">쿠킹 클래스</h2>
      <router-link :to="{ name: 'ClubOfflineListTypeView', params: {type: '쿠킹클래스', pageNum: 1} }" class="col-2 text-right mt-auto mb-auto">
        <button class="more-button">더보기</button>
      </router-link>

      <div class="row">
        <div class="prev-button col-1 mb-auto mt-auto" @click="moveCookingPrev">
          <i class="far fa-arrow-alt-circle-left"></i>
        </div>
        <div class="col-10 row">
          <OfflineClubListItem v-for="cookingClass in curCookings" :key="cookingClass.meetId" :club="cookingClass" class="col-4" />
        </div>
        <div class="next-button col-1 mb-auto mt-auto" @click="moveCookingNext">
          <i class="far fa-arrow-alt-circle-right"></i>
        </div>
      </div>
      
    </div>

    <div class="meet-type row mb-5">
      <h2 class="title col-10 text-left">공유 키친</h2>
      <router-link :to="{ name: 'ClubOfflineListTypeView', params: {type: '공유키친', pageNum: 1} }" class="col-2 text-right mt-auto mb-auto">
        <button class="more-button">더보기</button>
      </router-link>
      
      <div class="row">
        <div class="prev-button col-1 mb-auto mt-auto" @click="moveKitchenPrev">
          <i class="far fa-arrow-alt-circle-left"></i>
        </div>
        <div class="col-10 row">
          <OfflineClubListItem v-for="sharedKitchen in curKitchens" :key="sharedKitchen.meetId" :club="sharedKitchen" class="col-4" />
        </div>
        <div class="next-button col-1 mb-auto mt-auto" @click="moveKitchenNext">
          <i class="far fa-arrow-alt-circle-right"></i>
        </div>
      </div>
    </div>

    <div class="meet-type row mb-5">
      <h2 class="title col-10 text-left">홈 파티</h2>
      <router-link :to="{ name: 'ClubOfflineListTypeView', params: {type: '홈파티', pageNum: 1} }" class="col-2 text-right mt-auto mb-auto">
        <button class="more-button">더보기</button>
      </router-link>
      
      <div class="row">
        <div class="prev-button col-1 mb-auto mt-auto" @click="movePartyPrev">
          <i class="far fa-arrow-alt-circle-left"></i>
        </div>
        <div class="col-10 row">
          <OfflineClubListItem v-for="party in curParties" :key="party.meetId" :club="party" class="col-4" />
        </div>
        <div class="next-button col-1 mb-auto mt-auto" @click="movePartyNext">
          <i class="far fa-arrow-alt-circle-right"></i>
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
      return parseInt((this.cookingClasses.length-1) / 3) + 1
    },
    kitchenMaxPage() {
      return parseInt((this.sharedKitchens.length-1) / 3) + 1
    },
    partyMaxPage() {
      return parseInt((this.parties.length-1) / 3) + 1
    },
    curCookings() {
      return this.cookingClasses.slice(this.cookingCurPage*3, (this.cookingCurPage+1)*3)
    },
    curKitchens() {
      return this.sharedKitchens.slice(this.kitchenCurPage*3, (this.kitchenCurPage+1)*3)
    },
    curParties() {
      return this.sharedKitchens.slice(this.partyCurPage*3, (this.partyCurPage+1)*3)
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

<style scoped>
.meet-type {
  padding: 30px 60px 30px 60px;
  border-radius: 40px;
  box-shadow: 0px 8px 40px rgba(128, 128, 128, 0.15);
}

.more-button {
  font-size:25px;
}

.prev-button, .next-button {
  font-size:30px;
  color: orange;
}

</style>