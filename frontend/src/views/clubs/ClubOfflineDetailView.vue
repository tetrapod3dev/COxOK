<template>
  <div class="container">
    <div class="row mt-5">
      <img :src="thumbnailSrc" class="col-4">
      <div class="col-8">
        <div class="row">
          <h2 class="text-left col-12">{{ meet.title }} / {{ meet.type }}</h2>
          <p class="col-12">현재 참가자 : {{ meet.meetJoinList.length }} / {{ meet.joinLimit }}</p>
          <p v-for="user in meet.meetJoinList" :key="user.userId" class="col-2">{{ user.nickname }}님</p>
        </div>

        <div class="row">
          <p>일자: {{ meet.date }}</p>
          <p>가격: {{ meet.price }}원</p>
        </div>
      </div>
    </div>
    
    <div v-if="user == meet.userId">
      <router-link :to="{ name: 'ClubOfflineUpdateView', params: { club_id: meet.meetId } }"><button>수정</button></router-link>
      <button @click="deleteClub">삭제</button>
    </div>
    
    <div v-else>
      <button @click="changeJoinClub">{{ joinMent }}</button>
    </div>
    
    <div v-html="meet.content" />

    <p>주소 : {{ meet.address }}</p>
    <div id="map" style="width:500px;height:400px;" class="mx-auto"></div>

  </div>
</template>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import SERVER from '@/api/api'
import axios from 'axios'
import { mapGetters } from 'vuex'

const API_KEY = process.env.VUE_APP_KAKAO_API_KEY

export default {
  name: 'ClubDetailView',
  data() {
    return {
      meet: {
        address: null,
        content: null,
        date: null,
        joinLimit: null,
        lat: null,
        lng: null,
        meetId: null,
        meetJoinList: [],
        price: null,
        recipeId: null,
        thumbnailSrc: null,
        title: null,
        type: null,
        userId: null,
      },
      user: null,
      isIn: null,
    }
  },
  computed: {
    ...mapGetters(['config']),
    thumbnailSrc() {
      return SERVER.IMAGE_URL + this.meet.thumbnailSrc
    },
    joinMent() {
      return (this.isIn) ? "취소" : "참가"
    }
  },
  created() {
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement('script');
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${API_KEY}&autoload=false&libraries=services`;
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.clubDetail + this.$route.params.club_id, {
          headers: {
            "Authorization": this.config,
          },
        })
        .then(res => {
          this.user = res.data.userId
          this.meet = res.data.meet
          this.isIn = (res.data.meet.meetJoinList.map(user => user.userId).indexOf(res.data.userId) >= 0) ? true : false 

          var container = document.getElementById('map');
          var options = {
            center: new kakao.maps.LatLng(res.data.meet.lat, res.data.meet.lng),
            level: 3
          };

          var map = new kakao.maps.Map(container, options);
          
          var geocoder = new daum.maps.services.Geocoder();
          
          var marker = new daum.maps.Marker({
            position: new daum.maps.LatLng(res.data.meet.lat, res.data.meet.lng),
            map: map
          });
        })
        .catch(err => console.log(err))

    },

    deleteClub() {
      let response = confirm('삭제하실 건가요?')
      if (response) {
        axios
          .delete(SERVER.URL + SERVER.ROUTES.clubDelete + this.$route.params.club_id, {
            headers: {
              "Authorization": this.config,
            },
          })
          .then(res => {
            console.log(res)
            alert('삭제에 성공했습니다!')
            this.$router.push({ name: 'ClubListView', params: { pageNum: 1} });
          })
          .catch(err => console.log(err.response))
          }
    },

    changeJoinClub() {
      if (this.isIn) {
        axios
          .delete(SERVER.URL + SERVER.ROUTES.cancelClub + this.$route.params.club_id, {
            headers: {
              "Authorization": this.config,
            },
          })
          .then(() => {
            alert('참가를 취소하셨습니다.')
            this.isIn = false
          })
          .catch(err => console.log(err.response))
      } else {
        axios
          .post(SERVER.URL + SERVER.ROUTES.joinClub, { "meetId": this.$route.params.club_id }, {
            headers: {
              "Authorization": this.config,
            },
          })
          .then(() => {
            alert('참가 신청에 성공했습니다.')
            this.isIn = true
          })
          .catch(err => console.log(err.response))
      }
    }
  }
}
</script>

<style>

</style>