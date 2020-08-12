<template>
  <div class="wrapper">
    <!-- 필터 -->
    <div class="page-header page-header-mini header-filter" filter-color="black">
      <!-- 배너 배경 사진 -->
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.unsplash.com/photo-1505576399279-565b52d4ac71?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80')"
      ></parallax>
      <!-- 프로필 -->
      <blog-profile />
    </div>
    <div class="section blog-main">
      <div class="container">
        <!-- 블로그 메뉴 -->
        <blog-menu />
        <p class="description">좋아하는 카테고리</p>
        <b-badge v-for="tag in tags" :key="tag" pill variant="dark">{{tag}}</b-badge>
        <!-- 자기 소개 -->
        <h3 class="title">자기 소개</h3>
        <h5
          class="description text-center"
        >{{user.detail == '' || user.detail == null ? '안녕하세요. ' + user.nickname + '입니다.' : user.detail}}</h5>

        <!-- blog main page start -->
        <div class="container">
          <div class="row">
            <div class="col-md-4">
              <div class="info info-hover">
                <div class="icon icon-success icon-circle">
                  <i class="now-ui-icons education_paper"></i>
                </div>
                <h4 class="info-title">{{userTotal.recipe}}</h4>
                <p class="description">작성 레시피</p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="info info-hover">
                <div class="icon icon-primary icon-circle">
                  <i class="fa fa-heart"></i>
                </div>
                <h4 class="info-title">{{userTotal.like}}</h4>
                <p class="description">좋아하는 레시피</p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="info info-hover">
                <div class="icon icon-info icon-circle">
                  <i class="fa fa-group"></i>
                </div>
                <h4 class="info-title">{{userTotal.meet}}</h4>
                <p class="description">소모임 활동</p>
              </div>
            </div>
            <div class="social-description"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SERVER from "@/api/api";
import axios from "axios";
import { mapGetters } from "vuex";
// import { Card, Tabs, TabPane, Badge, InfoSection } from "@/components/global";
import BlogProfile from "@/components/blog/BlogProfile.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";

export default {
  name: "BlogHomeView",
  bodyClass: "profile-page",
  data() {
    return {
      user: {
        nickname: "",
        detail: "",
      },
      userTotal: {
        like: 0,
        meet: 0,
        recipe: 0,
      },
      foodCategories: [],
      selectedCategory: [],
      tags: [],
    };
  },
  components: {
    BlogProfile,
    BlogMenu,
  },
  computed: {
    ...mapGetters(["config"]),
  },
  created() {
    this.getSelectedCategory();
    this.getUserTotal();
  },
  methods: {
    getUserTotal() {
      let configs = {
        headers: {
          Authorization: this.config,
        },
      };
      axios
        .get(SERVER.URL + SERVER.ROUTES.userTotal, configs)
        .then((res) => {
          this.userTotal.like = res.data.like;
          this.userTotal.meet = res.data.meet;
          this.userTotal.recipe = res.data.recipe;
        })
        .catch((err) => console.log(err.response));
    },
    getSelectedCategory() {
      let configs = {
        headers: {
          Authorization: this.config,
        },
      };
      axios
        .get(SERVER.URL + SERVER.ROUTES.myPage, configs)
        .then((res) => {
          this.user = res.data.user;
          this.foodCategories = res.data.categories;
          this.selectedCategory = res.data.userFavoriteCategories;
          this.selectedCategory.sort((a, b) => a - b);
          for (
            let indexSelectedCategory = 0, indexFoodCategories = 0;
            indexSelectedCategory < this.selectedCategory.length;
            indexSelectedCategory++
          ) {
            while (
              this.foodCategories[indexFoodCategories]["foodCategoryId"] <
              this.selectedCategory[indexSelectedCategory]
            ) {
              indexFoodCategories++;
            }
            this.tags.push(
              this.foodCategories[indexFoodCategories]["foodCategoryName"]
            );
            indexFoodCategories++;
          }
        })
        .catch((err) => console.log(err.response));
    },
  },
};
</script>

<style scoped>
.blog-main .description {
  color: #333333;
}
</style>