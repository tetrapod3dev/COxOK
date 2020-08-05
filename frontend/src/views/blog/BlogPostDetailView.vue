<template>
  <div class="wrapper">
    <!-- 페이지 배너 시작 -->
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.pexels.com/photos/406152/pexels-photo-406152.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260') ;"
      ></parallax>
      <div class="content-center">
        <h1>블로그</h1>
      </div>
    </div>
    <!-- 페이지 배너 끝 -->
    <BlogMenu />
    <!-- 게시글 시작 -->

    <router-link
      :to="{ name: 'BlogPostUpdateView', params: { blogId: blogPost.blogId } }"
      tag="button"
    >수정</router-link>
    <div class="section">
      <div class="container">
        <div class="justify-content-md-center" id="row_style">
          <!-- 제목 -->
          <div>제목 : {{blogPost.title}}</div>
          <br />
          <!-- 내용 -->
          <div>내용</div>
          <div v-html="blogPost.content" style="border: solid"></div>
          <br />
          <hr />
          <!-- 태그 시작 -->
          태그 :
          <div>{{blogPost.tag1}}</div>
          <div>{{blogPost.tag2}}</div>
          <div>{{blogPost.tag3}}</div>
          <br />
          <!-- recipe -->
          레시피 :
          <div>{{blogPost.recipe_id}}</div>
        </div>
      </div>
    </div>
    <!-- 게시글 끝 -->
  </div>
</template>

<script>
import BlogMenu from "@/components/blog/BlogMenu.vue";

import SERVER from "@/api/api";
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  name: "BlogPostDetailView",
  components: {
    BlogMenu,
  },
  data() {
    return {
      blogPost: {
        title: "",
        content: "",
        tag1: null,
        tag2: null,
        tag3: null,
        recipe_id: null,
      },
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
        })
        .catch((err) => console.log(err.response));
    },
  },
};
</script>

<style>
</style>