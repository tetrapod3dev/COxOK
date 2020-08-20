<template>
  <div class="wrapper">
    <section id="top">
      <card
        type="background"
        :style="{ backgroundImage: 'url(http://i3a104.p.ssafy.io/header/meet.jpg)' }"
      >
        <div class="card-title text-center">
          <h1 style>코~옥 만들기</h1>
        </div>
      </card>
    </section>
    
    <div class="container">
      
      <div class="tab">
        <div class="toggleWrapper">
          <input type="checkbox" class="dn" id="dn" @click="changeToggle"/>
          <label for="dn" class="toggle">
            <span class="toggle__handler">
              <span class="crater crater--1"></span>
              <span class="crater crater--2"></span>
              <span class="crater crater--3"></span>
            </span>
            <span class="star star--1"></span>
            <span class="star star--2"></span>
            <span class="star star--3"></span>
            <span class="star star--4"></span>
            <span class="star star--5"></span>
            <span class="star star--6"></span>
          </label>
        </div>
      </div>
      <br>
      <!-- <button class="btn my-5" @click="changeOff">오프라인</button>
      <button class="btn" @click="changeOn">온라인</button> -->

      <OfflineClubMake v-if="isOffline" />

      <OnlineClubMake v-else />
    </div>
  </div>
</template>

<script>
import OfflineClubMake from "@/components/clubs/OfflineClubMake.vue";
import OnlineClubMake from "@/components/clubs/OnlineClubMake.vue";
import { Card } from "@/components/global";
import { mapMutations } from 'vuex'

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
  created() {
    this.setSearchData({
      'selectedCategory': [],
      'selectedIngredients': [],
      'selectedIngredientsName': [],
      'level': 5,
      'cookTime': 120,
    })},
  methods: {
    ...mapMutations(['setSearchData']),
    changeToggle(){
      this.isOffline = !this.isOffline;
    },
    changeOff() {
      this.isOffline = true;
    },
    changeOn() {
      this.isOffline = false;
    },
  },
};
</script>

<style scoped>
.tab {
  font-family: 'Katuri';
  color: #fff;
}

.toggleWrapper {
  overflow: hidden;
}
.toggleWrapper input {
  position: absolute;
  left: -99em;
}

.toggle {
  cursor: pointer;
  display: inline-block;
  position: relative;
  width: 90px;
  height: 50px;
  background-color: #83D8FF;
  border-radius: 84px;
  transition: background-color 200ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
}
.toggle:before {
  content: 'Offline';
  position: absolute;
  left: -70px;
  top: 15px;
  font-size: 18px;
  color: black;
}
.toggle:after {
  content: 'Online';
  position: absolute;
  right: -75px;
  top: 15px;
  font-size: 18px;
  color: rgb(168, 166, 166);
}

.toggle__handler {
  display: inline-block;
  position: relative;
  z-index: 1;
  top: 3px;
  left: -20px;
  width: 44px;
  height: 44px;
  background-color: #FFCF96;
  border-radius: 50px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
  transition: all 400ms cubic-bezier(0.68, -0.55, 0.265, 1.55);
  transform: rotate(-45deg);
}
.toggle__handler .crater {
  position: absolute;
  background-color: #E8CDA5;
  opacity: 0;
  transition: opacity 200ms ease-in-out;
  border-radius: 100%;
}
.toggle__handler .crater--1 {
  top: 18px;
  left: 10px;
  width: 4px;
  height: 4px;
}
.toggle__handler .crater--2 {
  top: 28px;
  left: 22px;
  width: 6px;
  height: 6px;
}
.toggle__handler .crater--3 {
  top: 10px;
  left: 25px;
  width: 8px;
  height: 8px;
}

.star {
  position: absolute;
  background-color: #ffffff;
  transition: all 300ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
  border-radius: 50%;
}

.star--1 {
  top: 10px;
  left: 35px;
  z-index: 0;
  width: 30px;
  height: 3px;
}

.star--2 {
  top: 18px;
  left: 28px;
  z-index: 1;
  width: 30px;
  height: 3px;
}

.star--3 {
  top: 27px;
  left: 40px;
  z-index: 0;
  width: 30px;
  height: 3px;
}

.star--4,
.star--5,
.star--6 {
  opacity: 0;
  transition: all 300ms 0 cubic-bezier(0.445, 0.05, 0.55, 0.95);
}

.star--4 {
  top: 16px;
  left: 11px;
  z-index: 0;
  width: 2px;
  height: 2px;
  transform: translate3d(3px, 0, 0);
}

.star--5 {
  top: 32px;
  left: 17px;
  z-index: 0;
  width: 3px;
  height: 3px;
  transform: translate3d(3px, 0, 0);
}

.star--6 {
  top: 36px;
  left: 28px;
  z-index: 0;
  width: 2px;
  height: 2px;
  transform: translate3d(3px, 0, 0);
}

input:checked + .toggle {
  background-color: #749DD6;
}
input:checked + .toggle:before {
  color: rgb(168, 166, 166);
}
input:checked + .toggle:after {
  color: black;
}
input:checked + .toggle .toggle__handler {
  background-color: #FFE5B5;
  transform: translate3d(40px, 0, 0) rotate(0);
}
input:checked + .toggle .toggle__handler .crater {
  opacity: 1;
}
input:checked + .toggle .star--1 {
  width: 2px;
  height: 2px;
}
input:checked + .toggle .star--2 {
  width: 4px;
  height: 4px;
  transform: translate3d(-5px, 0, 0);
}
input:checked + .toggle .star--3 {
  width: 2px;
  height: 2px;
  transform: translate3d(-7px, 0, 0);
}
input:checked + .toggle .star--4,
input:checked + .toggle .star--5,
input:checked + .toggle .star--6 {
  opacity: 1;
  transform: translate3d(0, 0, 0);
}
input:checked + .toggle .star--4 {
  transition: all 300ms 200ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
}
input:checked + .toggle .star--5 {
  transition: all 300ms 300ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
}
input:checked + .toggle .star--6 {
  transition: all 300ms 400ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
}


</style>