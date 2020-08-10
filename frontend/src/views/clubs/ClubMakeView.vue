<template>
  <div class="container">

    <h2 class="my-5">소모임 작성 페이지입니당.</h2>

    <!-- 제목 입력 -->
    <input type="text" v-model="clubPost.title">

    <!-- 텍스트 에디터 -->
    <CxkEditor :value.sync="clubPost.content" />


    <button v-b-modal.modal-lg>실험중</button>

    <b-modal id="modal-lg" size="lg" ok-only title="Test">
      <button @click="changeSelectorShow">카테고리 검색기</button>

      <CategorySelector 
        v-show="showSelector"
        @searchRecipe="categorySubmit"
        @removeSelect="removeSelect" />

      <div class="row" v-show="!showSelector">
        <div v-for="recipe in recipes" :key="recipe.recipeId" class="col-4">
          {{ recipe.recipeName }}
        </div>
      </div>

      <button @click="movePrev">Prev</button>
      <button @click="moveNext">Next</button>
    </b-modal>

    <!-- 주소 입력 -->
    <div class="row">
      <input type="text" v-model="clubPost.postcode" placeholder="우편번호" class="col-4 offset-2">
      <input type="button" @click="onClickAddr" value="우편번호 찾기" class="col-2 offset-1"><br>
      <input type="text" v-model="clubPost.address" placeholder="주소" class="col-4 offset-2"><br>
      <input type="text" v-model="clubPost.detailAdress" placeholder="상세주소" class="col-3 offset-1">
    </div>

    <!-- 주소 입력 후 위치를 지도에서 확인 가능 -->
    <div v-show="clubPost.address != null" id="map" style="width:500px;height:400px;"></div>
  </div>
</template>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import { mapGetters } from 'vuex'
import axios from 'axios'
import SERVER from '@/api/api'

import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import CategorySelector from "@/components/recipes/CategorySelector.vue"

const API_KEY = process.env.VUE_APP_KAKAO_API_KEY
// content, date, joinLimit, lat, lng, meetId, photo, price, recipeId, thumbnailSrc, title, type, userId
export default {
  name: 'ClubMakeView',
  data () {
    return {
      clubPost: {
        title: null,
        content: null,
        address: null,
        postcode: null,
        detailAdress: null,
        latitude: null,
        longitude: null,
      },
      recipes: [],
      curPage: 1,
      maxPage: null,
      showSelector: false
    }
  },
  components: {
    CxkEditor,
    CategorySelector,
  },
  computed: {
    ...mapGetters(["searchingData"]),
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
    this.allRecipe(1)
  },
  methods: {
    movePrev() {
      this.curPage -= 1
      this.changePage()
    },
    moveNext() {
      this.curPage += 1
      this.changePage()
    },

    changeSelectorShow() {
      this.showSelector = !this.showSelector
    },

    categorySubmit() {
      this.curPage = 1
      this.changePage();
    },
    removeSelect() {
      this.curPage = 1
      this.changePage();
    },
    
    changePage() {
      if (
        this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length !=
        0
      ) {
        this.searchRecipe(this.curPage);
      } else {
        this.allRecipe(this.curPage);
      }
      this.showSelector = false
    },

    allRecipe(page) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.recipeList + (page - 1))
        .then((res) => {
          this.recipes = res.data.list;
          this.maxPage = parseInt((res.data.total - 1) / 6) + 1;
          this.categoryShow = false;
        });
    },
    searchRecipe(page) {
      let frm = new FormData();

      // categories 배열에는 선택된 카테고리의 ID가 String으로 들어갑니다.
      this.searchingData.selectedCategory.forEach(function (selectedCategory) {
        frm.append("selectedCategory", selectedCategory);
      });

      this.searchingData.selectedIngredients.forEach(function (
        selectedIngredient
      ) {
        frm.append("selectedIngredients", selectedIngredient);
      });

      // recipe/search/{{page}} 라는 주소로 selectedCategory(선택된 카테고리의 id들) / selectedIngredients(선택된 재료들의 id)를 전달합니다.
      axios
        .post(SERVER.URL + SERVER.ROUTES.searchRecipe + (page - 1), frm)
        .then((res) => {
          this.recipes = res.data.list;
          this.maxPage = parseInt((res.data.total - 1) / 6) + 1;
        })
        .catch((err) => console.log(err));
    },

    initMap() {
      var container = document.getElementById('map');
      var options = {
        center: new kakao.maps.LatLng(this.clubPost.latitude, this.clubPost.longitude),
        level: 3
      };

      var map = new kakao.maps.Map(container, options);
      
      var geocoder = new daum.maps.services.Geocoder();
      
      var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(this.clubPost.latitude, this.clubPost.longitude),
        map: map
      });
    },

    onClickAddr() {
      const self = this

      var mapContainer = document.getElementById('map'), // 지도를 표시할 div
      mapOption = {
        center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
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
          self.clubPost.postcode = data.zonecode;
          self.clubPost.address = addr;
          // 주소로 상세 정보를 검색
          geocoder.addressSearch(data.address, function(results, status) {
              // 정상적으로 검색이 완료됐으면
            if (status === daum.maps.services.Status.OK) {

              var result = results[0]; //첫번째 결과의 값을 활용

              self.clubPost.longitude = result.x
              self.clubPost.latitude = result.y

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