<template>
  <div>
    <h2>소모임 상세 정보 페이지입니당.</h2>
    
    <p>주소 : {{ address }} {{ detailAdress }}</p>
    <div v-show="address != null" id="map" style="width:500px;height:400px;" class="mx-auto"></div>
  </div>
</template>


<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7903d1a2c2c53b0b1f5321ef4b9d7208&libraries=services"></script>
<script>
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
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
      center: new daum.maps.LatLng(this.latitude, this.longitude), // 지도의 중심좌표
      level: 5 // 지도의 확대 레벨
    };

    //지도를 미리 생성
    var map = new daum.maps.Map(mapContainer, mapOption);
    //주소-좌표 변환 객체를 생성
    var geocoder = new daum.maps.services.Geocoder();
    //마커를 미리 생성
    var marker = new daum.maps.Marker({
      position: new daum.maps.LatLng(this.latitude, this.longitude),
      map: map
    });
  }
}
</script>

<style>

</style>