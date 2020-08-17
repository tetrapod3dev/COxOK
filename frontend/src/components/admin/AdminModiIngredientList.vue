<template>
  <div class="container">
    <h2>추가 요청이 들어온 재료들 목록</h2>


    <div class="row border-bottom">
      <p class="col-1">ID</p>
      <p class="col-2">Name</p>
      <p class="col-3">기본 정보</p>
      <p class="col-4">단위 당 영양소 정보</p>
      <p class="col-2">버튼들</p>
    </div>
    <div v-for="ingredient in curIngredients" :key="ingredient.ingredientId" class="row">
      <p class="col-1">{{ ingredient.ingredientId }}</p>
      <p class="col-2">{{ ingredient.name }}</p>
      <ul class="col-3 row list-group list-group-horizontal text-left mx-0">
        <li class="list-group-item col-8 offset-2 border rounded-lg">상세정보 : {{ ingredient.detail }}</li>
        <li class="list-group-item col-8 offset-2 border rounded-lg">단위량 : {{ ingredient.baseAmount }}</li>
        <li class="list-group-item col-8 offset-2 border rounded-lg">단위 : {{ ingredient.unit }}</li>
      </ul>
      <ul class="col-4 row list-group list-group-horizontal text-left mx-0">
        <li class="list-group-item col-6 border rounded-lg">칼로리 : {{ ingredient.calorie }} kcal</li>
        <li class="list-group-item col-6 border rounded-lg">탄수화물 : {{ ingredient.carbon }} g</li>
        <li class="list-group-item col-6 border rounded-lg">단백질 : {{ ingredient.protein }} g</li>
        <li class="list-group-item col-6 border rounded-lg">지방 : {{ ingredient.fat }} g</li>
        <li class="list-group-item col-6 border rounded-lg">나트륨 : {{ ingredient.natrium }} mg</li>
        <li class="list-group-item col-6 border rounded-lg">당 : {{ ingredient.sugar }} g</li>
      </ul>
      <div class="col-2">
        <b-button v-b-modal.modal @click="selectIngredient(ingredient)">정보 수정</b-button>
      </div>

      <hr class="col-12">
    </div>
    
    <b-modal hide-footer id="modal" ref="modal" size="md" title="재료 정보 수정" class="row">
      <input class="col-12" v-model="selectedIngredient.name">
      
      <hr class="col-12">

      <textarea type="text" class="col-12" v-model="selectedIngredient.detail" placeholder="설명" rows="3" />
      <input type="number" class="col-6" v-model="selectedIngredient.baseAmount">
      <select v-model="selectedIngredient.unit" class="col-3">
        <option v-for="unit in units" :key="unit">{{ unit }}</option>
      </select>

      <hr class="col-12">
      
      <div class="row col-12">
        <p class="col-3">칼로리</p>
        <input type="number" v-model="selectedIngredient.calorie" class="col-2">
        <p class="col-1">kcal</p>
        
        <p class="col-3">탄수화물</p>
        <input type="number" v-model="selectedIngredient.carbon" class="col-2">
        <p class="col-1">g</p>
        
        <p class="col-3">단백질</p>
        <input type="number" v-model="selectedIngredient.protein" class="col-2">
        <p class="col-1">g</p>
        
        <p class="col-3">지방</p>
        <input type="number" v-model="selectedIngredient.fat" class="col-2">
        <p class="col-1">g</p>
        
        <p class="col-3">나트륨</p>
        <input type="number" v-model="selectedIngredient.natrium" class="col-2">
        <p class="col-1">mg</p>
        
        <p class="col-3">당</p>
        <input type="number" v-model="selectedIngredient.sugar" class="col-2">
        <p class="col-1">g</p>
      </div>

      <hr class="col-12">

      <b-button @click="sendData(selectedIngredient.ingredientId)">수정</b-button>

    </b-modal>

    
    <PageButton
      class="d-flex justify-content-center"
      :curPage="curPage"
      :maxPage="maxPage"
      @move-page="movePage"
    />
  </div>
</template>

<script>
import PageButton from "@/components/common/PageButtons.vue";

import SERVER from '@/api/api'
import axios from 'axios'

import { mapGetters } from 'vuex'

export default {
  name: 'AdminModiIngredientList',
  data() {
    return {
      curPage: 1,
      maxPage: null,
      curIngredients: [],
      selectedIngredient: {},
      units: ['g', '큰술', '작은술', '꼬집', '봉', '개', 'ml', '장']
    }
  },
  components: {
    PageButton,
  },
  computed: {
    ...mapGetters(['config']),
    configs() {
      return {headers: {
        Authorization: this.config,
      }}
    }
  },
  mounted() {
    this.getIngredients();
  },
  methods: {
    getIngredients() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.adminModiIngredients + (this.curPage - 1), this.configs)
        .then(res => {
          this.curIngredients = res.data.ingredient
          this.maxPage = parseInt((res.data.ingredient.length - 1) / 20) + 1
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    movePage(page) {
      if (page == "«") {
        this.curPage = 1
      } else if (page == "»") {
        this.curPage = this.maxPage
      } else if (page != this.curPage) {
        this.curPage = parseInt(page)
      }
      scroll(0, 0);
    },
    selectIngredient(ingredient) {
      this.selectedIngredient = ingredient
    },
    sendData(ingredientId) {
      let body = {
        "ingredientId": ingredientId,
        "name": this.selectedIngredient.name,

        "detail": this.selectedIngredient.detail,
        "baseAmount": parseInt(this.selectedIngredient.baseAmount),
        "unit": this.selectedIngredient.unit,

        "calorie": parseInt(this.selectedIngredient.calorie),
        "carbon": parseInt(this.selectedIngredient.carbon),
        "fat": parseInt(this.selectedIngredient.fat),
        "sugar": parseInt(this.selectedIngredient.sugar),
        "natrium": parseInt(this.selectedIngredient.natrium),
        "protein": parseInt(this.selectedIngredient.protein),
      }
      axios
        .put(SERVER.URL + SERVER.ROUTES.adminIngredient, body, this.configs)
        .then(() => {
          alert('재료 정보 수정에 성공했습니다!')
          this.$refs['modal'].hide()
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    }
  }
}
</script>

<style>

</style>