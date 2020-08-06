<template>
  <div class="container">
    <h2>소모임 작성 페이지입니당.</h2>

    <div class="row">
      <input type="text" v-model="postcode" placeholder="우편번호" class="col-4 offset-2">
      <input type="button" @click="onClickAddr" value="우편번호 찾기" class="col-2 offset-1"><br>
      <input type="text" v-model="address" placeholder="주소" class="col-4 offset-2"><br>
      <input type="text" v-model="detailAdress" placeholder="상세주소" class="col-3 offset-1">
    </div>

    <div v-show="address != null" id="map" style="width:500px;height:400px;"></div>
  </div>
</template>


<!-- 주소 검색 API, 위치확인 필요 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7903d1a2c2c53b0b1f5321ef4b9d7208&libraries=services"></script>
<script>
export default {
  name: 'ClubMakeView',
  data () {
    return {
      address: null,
      postcode: null,
      detailAdress: null,
      latitude: null,
      longitude: null,
    }
  },
  components: {
  },
  computed: {
  },
  methods: {
    onClickAddr() {
      const self = this

      var mapContainer = document.getElementById('map'), // 지도를 표시할 div
      mapOption = {
        center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
      };

      //지도를 미리 생성
      var map = new daum.maps.Map(mapContainer, mapOption);
      //주소-좌표 변환 객체를 생성
      var geocoder = new daum.maps.services.Geocoder();
      //마커를 미리 생성
      var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(37.537187, 127.005476),
        map: map
      });

      new daum.Postcode({
        oncomplete: function(data) {
          var addr = data.address; // 최종 주소 변수

          // 주소 정보를 해당 필드에 넣는다.
          self.postcode = data.zonecode;
          self.address = addr;
          // 주소로 상세 정보를 검색
          geocoder.addressSearch(data.address, function(results, status) {
              // 정상적으로 검색이 완료됐으면
            if (status === daum.maps.services.Status.OK) {

              var result = results[0]; //첫번째 결과의 값을 활용

              self.longitude = result.x
              self.latitude = result.y

              // 해당 주소에 대한 좌표를 받아서
              var coords = new daum.maps.LatLng(result.y, result.x);
              // 지도를 보여준다.
              mapContainer.style.display = "block";
              map.relayout();
              // 지도 중심을 변경한다.
              map.setCenter(coords);
              // 마커를 결과값으로 받은 위치로 옮긴다.
              marker.setPosition(coords)
            }
          });
        }
      }).open();
    }
  },
}
</script>

<style>

</style>