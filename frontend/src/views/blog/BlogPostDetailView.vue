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
    <!-- 게시글 작성 시작 -->
    <div class="section">
      <div class="container">
        <div class="row justify-content-md-center" id="row_style">
          <!-- 제목 -->
          <div>제목 : {{post.title}}</div>
          <br />
          <!-- 내용 -->
          <div>내용 : {{post.content}}</div>
          <br />

          <!-- 태그 시작 -->
          태그 :
          <div>{{post.tag1}}</div>
          <div>{{post.tag2}}</div>
          <div>{{post.tag3}}</div>
          <br />
          <!-- recipe -->
          레시피 :
          <div>{{post.recipe_id}}</div>
        </div>
      </div>
    </div>
    <!-- 게시글 작성 끝 -->
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
      post: {
        title: "test tit",
        content: "test con",
        tag1: "test t1",
        tag2: "test t2",
        tag3: "test t3",
        recipe_id: "test ri",
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
          console.log(res);
          this.post = res.data.blog;
        })
        .catch((err) => console.log(err.response));
    },
  },
};
</script>

<style>
</style>