<template>
  <div class="container">

    <h2 class="my-5">소모임 작성 페이지입니당.</h2>

    <div class="row">
      <!-- 사진 입력 및 미리보기 -->
      <div class="col-4 offset-1">
        <img :src="clubPreview">
        <input type="file" @change="changePhoto">
      </div>

      <div class="col-7 text-left">
        <!-- 유형(type) -->
        <div>
          <select v-model="clubPost.type">
            <option disabled value="">Please select one</option>
            <option>쿠킹 클래스</option>
            <option>공유 키친</option>
            <option>홈파티</option>
          </select>
          <span>선택함: {{ clubPost.type }}</span>
        </div>

        <!-- 제목 입력 -->
        <input class="w-50" type="text" v-model="clubPost.title">
      </div>
    </div>

    <!-- 텍스트 에디터 -->
    <CxkEditor :value.sync="clubPost.content" />



    <div class="row">
      <!-- 날짜 시간 입력 -->
      <datetime type="datetime" v-model="date" format="yyyy-MM-dd HH:mm:ss" value-zone="Asia/Seoul" class="col-10 offset-1"></datetime>

      <!-- 날짜 입력 -->
      <!-- <b-form-datepicker id="example-datepicker" v-model="clubPost.date" class="mb-2 col-4 offset-2"></b-form-datepicker> -->
      <!-- 시간 입력 -->
      <!-- <b-time v-model="clubPost.time" show-seconds class="col-4"></b-time> -->
    </div>

    <div class="row">
      <!-- 참여자 수 -->
      <div class="col-6">
        <p>참여자 수: </p>
        <input type="number" v-model="clubPost.joinLimit">
      </div>

      <!-- 가격 -->
      <div class="col-6">
        <p>가격: </p>
        <input type="number" v-model="clubPost.price">
      </div>
    </div>
    

    <button v-b-modal.modal-lg>레시피 선택</button>

    <b-modal id="modal-lg" ref="my-modal" hide-footer size="lg" title="Test">
      <button @click="changeSelectorShow">카테고리 검색기</button>

      <CategorySelector 
        v-show="showSelector"
        @searchRecipe="categorySubmit"
        @removeSelect="removeSelect" />

      <div class="row" v-show="!showSelector">
        <div v-for="recipe in recipes" :key="recipe.recipeId" @click="selectRecipe(recipe)" class="col-4">
          <img :src="imageSrc(recipe)">
          <p>{{ recipe.recipeName }}</p>
        </div>
      </div>

      <PageButton
        v-if="recipes.length > 0"
        class="d-flex justify-content-center"
        :curPage="curPage"
        :maxPage="maxPage"
        @move-page="movePage"
      />
    </b-modal>

    <div>
      {{ clubPost.selectedRecipe }}
    </div>

    <!-- 주소 입력 -->
    <div class="row">
      <input type="text" v-model="clubPost.postcode" readonly placeholder="우편번호" class="col-4 offset-2">
      <input type="button" @click="onClickAddr" value="우편번호 찾기" class="col-2 offset-1"><br>
      <input type="text" v-model="clubPost.address" readonly placeholder="주소" class="col-4 offset-2"><br>
      <input type="text" v-model="clubPost.detailAdress" placeholder="상세주소" class="col-3 offset-1">
    </div>

    <!-- 주소 입력 후 위치를 지도에서 확인 가능 -->
    <div v-show="clubPost.address != null" id="map" style="width:500px;height:400px;"></div>

    <button @click="makeMeet">제출</button>
  </div>
</template>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'
import SERVER from '@/api/api'

import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import CategorySelector from "@/components/recipes/CategorySelector.vue"
import PageButton from "../../components/common/PageButtons.vue";

import { Form } from 'element-ui';
import { Datetime } from 'vue-datetime';

const API_KEY = process.env.VUE_APP_KAKAO_API_KEY
// content, date, joinLimit, lat, lng, photo, price, recipeId, thumbnailSrc, title, type
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
        selectedRecipe: null,
        meetPhoto: null,
        joinLimit: null,
        price: null,
        type: null,
        date: null,
        time: null,
      },
      clubPreview: null,
      recipes: [],
      curPage: 1,
      maxPage: null,
      showSelector: false,
      date: null,
    }
  },
  components: {
    CxkEditor,
    CategorySelector,
    PageButton,
    datetime: Datetime
  },
  computed: {
    ...mapGetters(["config", "searchingData"]),
    fullAddress() {
      return this.clubPost.address + ' ' + this.clubPost.detailAdress
    },
    fullTime() {
      return this.date.slice(0, 10) + ' ' + this.date.slice(11, 19)
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
    this.allRecipe(1)
  },
  methods: {
    imageSrc(recipe) {
      return SERVER.IMAGE_URL + recipe.recipeThumbnailSrc;
    },

    ...mapActions(['logout']),

    hideModal() {
      this.$refs['my-modal'].hide()
    },

    changePhoto(event) {
      const file = event.target.files[0]
      this.clubPreview = URL.createObjectURL(file);
      this.clubPost.meetPhoto = file
    },

    movePage(page) {
      console.log(page);
      if (page == "«") {
        this.curPage = 1 * 1;
      } else if (page == "»") {
        this.curPage = this.maxPage * 1
      } else if (page != this.curPage) {
        this.curPage = page * 1
      }
      this.changePage()
      scroll(0, 0);
    },
    // movePrev() {
    //   this.curPage -= 1
    //   this.changePage()
    // },
    // moveNext() {
    //   this.curPage += 1
    //   this.changePage()
    // },

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
    selectRecipe(recipe) {
      this.clubPost.selectedRecipe = recipe
      this.hideModal()
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
    },

    makeMeet() {
      let frm = new FormData()
    
      frm.append("address", this.fullAddress);
      frm.append("title", this.clubPost.title);
      frm.append("content", this.clubPost.content);
      frm.append("date", this.fullTime)
      frm.append("joinLimit", this.clubPost.joinLimit)
      frm.append("lat", this.clubPost.latitude)
      frm.append("lng", this.clubPost.longitude)
      frm.append("photo", this.clubPost.meetPhoto)
      frm.append("price", this.clubPost.price)
      frm.append("recipeId", this.clubPost.selectedRecipe.recipeId)
      frm.append("type", this.clubPost.type)

      let configs = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: this.config,
        },
      };

      for (var key of frm.keys()) {
        console.log(key);
      }

      for (var value of frm.values()) {
        console.log(value);
      }

      console.log(configs)
      axios
        .post(SERVER.URL + SERVER.ROUTES.clubRegister, frm, configs)
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          // if (err.response.status) {
          //   alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
          //   this.logout()
          // }
          console.log(err.response)
          })
    }

  },
}
</script>

<style>

</style>