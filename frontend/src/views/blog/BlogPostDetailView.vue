<template>
  <div class="wrapper blog-post">
    <div class="page-header page-header-mini header-filter" filter-color="orange">
      <parallax
        class="page-header-image"
        :style="{ backgroundImage: 'url(http://i3a104.p.ssafy.io/header/mypage.jpg)' }"
      ></parallax>
      <blog-profile />
    </div>
    <div class="section">
      <div class="container">
        <blog-menu />
        <!-- 게시글 시작 -->

        <div class="form-group">
          <router-link
            :to="{ name: 'BlogPostUpdateView', params: { blogId: blogPost.blogId } }"
            tag="button"
            class="btn btn-outline-secondary mx-2"
          >수정</router-link>
          <button @click="deleteBlog" class="btn btn-default mx-2">삭제</button>
          <button @click="goBackPage" class="btn btn-default mx-2">뒤로</button>
        </div>
      </div>
      <div class="section">
        <div class="container">
          <div class="row">
            <div class="col-md-8 ml-auto mr-auto">
              <!-- 제목 -->
              <h3 class="blog-title text-left">{{blogPost.title}}</h3>
            </div>
          </div>
          <div class="row">
            <div class="col-md-8 ml-auto mr-auto blog-content blog-border-color">
              <!-- 내용 -->
              <span class v-html="blogPost.content"></span>
              <!-- 태그 -->
            </div>
          </div>
        </div>
      </div>
      <div class="section section-blog-info">
        <div class="container">
          <div class="row">
            <div
              v-if="blogPost.tag1"
              class="col-md-8 ml-auto mr-auto blog-boder-top blog-border-color"
            >
              <p class="blog-tags text-left">
                Tags:
                <span
                  class="label label-primary"
                  v-if="blogPost.tag1"
                  v-text="'#' + blogPost.tag1"
                ></span>
                <span class="label label-primary" v-if="blogPost.tag2" v-text="'#' + blogPost.tag2"></span>
                <span class="label label-primary" v-if="blogPost.tag3" v-text="'#' + blogPost.tag3"></span>
              </p>
            </div>
            <!-- 레시피 -->
            <div v-if="recipe.recipeId != 0" class="col-md-8 ml-auto mr-auto">
              <card type="profile">
                <div slot="raw-content" class="row">
                  <div class="col-md-5">
                    <div class="card-image">
                      <router-link
                        :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }"
                      >
                        <img class="img img-zoom" :src="imageSrc(recipe)" />
                      </router-link>
                    </div>
                  </div>
                  <div class="col-md-7">
                    <div class="card-body">
                      <h3 class="card-title">{{recipe.recipeName}}</h3>
                      <h6 class="category text-primary text-left">{{recipe.nickname}}</h6>
                      <p class="card-description">{{recipe.recipeDetail}}</p>
                      <router-link
                        class="card-footer"
                        :to="{name: 'RecipeDetailView', params: {recipe_id: recipe.recipeId} }"
                      >상세보기</router-link>
                    </div>
                  </div>
                </div>
              </card>
            </div>
          </div>
        </div>
        <!-- 게시글 끝 -->
      </div>
    </div>
  </div>
</template>

<script>
import BlogProfile from "@/components/blog/BlogProfile.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";
import { Card } from "@/components/global";

import SERVER from "@/api/api";
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  name: "BlogPostDetailView",
  bodyClass: "profile-page",
  components: {
    BlogProfile,
    BlogMenu,
    Card,
  },
  data() {
    return {
      blogPost: {
        title: "",
        content: "",
        tag1: null,
        tag2: null,
        tag3: null,
        recipeId: 0,
        blogId: 0,
      },
      recipe: { recipeId: 0, recipeThumbnailSrc: "dochi.png" },
    };
  },
  created() {
    this.getPosts();
  },
  computed: {
    ...mapGetters(["config"]),
  },
  methods: {
    getPosts() {
      let configs = {
        headers: {
          Authorization: this.config,
        },
      };
      axios
        .get(
          SERVER.URL + SERVER.ROUTES.blog + this.$route.params.blogId,
          configs
        )
        .then((res) => {
          this.blogPost = res.data.blog;
          if (this.blogPost.recipeId) {
            axios
              .get(
                SERVER.URL +
                  SERVER.ROUTES.recipeDetail +
                  this.blogPost.recipeId,
                configs
              )
              .then((res) => {
                this.recipe = res.data.recipe;
              })
              .catch((err) => {
                if (err.response.status == 401) {
                  alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
                  this.logout()
                }});
          }
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    imageSrc(recipe) {
      return SERVER.IMAGE_URL + recipe.recipeThumbnailSrc;
    },
    goBackPage() {
      this.$router.go(-1);
    },
    deleteBlog() {
      axios
        .delete(SERVER.URL + SERVER.ROUTES.blog + this.$route.params.blogId, {
          headers: {
            Authorization: this.config,
          },
        })
        .then(() => {
          this.$router.push({
            name: "BlogPostListView",
            params: { pageNum: 1 },
          });
        })
        .catch((err) => {
          if (err.response.status) {
            alert("세션 정보가 만료되었습니다! 다시 로그인해주세요.");
            this.logout();
          }
        });
    },
  },
};
</script>

<style scoped>
.card-image {
  overflow: hidden;
}
.card-image .img-zoom {
  transform: scale(1);
  transition: transform 0.3s ease-in-out;
}

.card-image .img-zoom:hover {
  transform: scale(1.2);
}
.blog-content {
  border-top: 1px solid;
  min-height: 200px;
}
.blog-border-color {
  border-color: #999999;
}
.blog-boder-top {
  border-top: 1px solid;
}
</style>