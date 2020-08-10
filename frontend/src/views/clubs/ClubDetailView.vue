<template>
  <div>
    <h2>소모임 상세 정보 페이지입니당.</h2>
    
    <p>주소 : {{ address }} {{ detailAdress }}</p>
    <div id="map" style="width:500px;height:400px;" class="mx-auto"></div>
  </div>
</template>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
const API_KEY = process.env.VUE_APP_KAKAO_API_KEY
export default {
  name: 'ClubDetailView',
  data() {
    return {
      address:"서울 강남구 테헤란로 212",
      detailAdress:"101호",
      latitude:"37.5012767241426",
      longitude:"127.039600248343",
      postcode:"06220",
    }
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
      var container = document.getElementById('map');
      var options = {
        center: new kakao.maps.LatLng(this.latitude, this.longitude),
        level: 3
      };

      var map = new kakao.maps.Map(container, options);
      
      var geocoder = new daum.maps.services.Geocoder();
      
      var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(this.latitude, this.longitude),
        map: map
      });
    },
  }
}
</script>

<style>

</style>