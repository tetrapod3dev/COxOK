<template>
  <div class="container">
    <h2>Admin이 신고당한 레시피를 봅니당..</h2>

    <div class="row">
      <p class="col-3">레시피 사진</p>
      <p class="col-4">레시피 정보</p>
      <p class="col-3">신고 정보</p>
      <p class="col-2">버튼</p>
    </div>

    <div v-for="recipe in curRecipes" :key="recipe.reportRecipeDto.reportRecipeId" class="row" >
      <img :src="imageSrc(recipe.recipeResponseDto.recipeThumbnailSrc)" class="col-3">
      <div class="col-4">
        <h3>{{ recipe.recipeResponseDto.recipeName }}</h3>
        <p>{{ recipe.recipeResponseDto.recipeDetail }}</p>
      </div>
      <div class="col-3">
        <p>신고자 : {{ recipe.reportRecipeDto.nickname }}님</p>
        <p>신고사유 : {{ recipe.reportRecipeDto.reason }}</p>
      </div>
      <div class="col-2">
        <button>상세 보기</button>
        <button>삭제</button>
      </div>
    </div>

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
  name: 'AdminReportedRecipeList',
  data() {
    return {
      curPage: 1,
      maxPage: null,
      reportedRecipes: [],
    }
  },
  components: {
    PageButton
  },
  computed: {
    ...mapGetters(['config']),
    configs() {
      return { headers: {
        Authorization: this.config,
      }}
    },
    curRecipes() {
      return this.reportedRecipes.slice((this.curPage-1) * 10, this.curPage * 10)
    }
  },
  mounted() {
    this.getRecipes();
  },
  methods: {
    imageSrc(link) {
      return SERVER.IMAGE_URL + link
    },
    getRecipes() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.adminGetReportedRecipes, this.configs)
        .then(res => {
          this.reportedRecipes = res.data.list
          this.maxPage = parseInt((res.data.list.length - 1) / 10) + 1
        })
        .catch(err => console.log(err.response))
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
  }
}
</script>

<style>

</style>