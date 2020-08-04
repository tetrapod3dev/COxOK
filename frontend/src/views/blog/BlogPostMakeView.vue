<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.pexels.com/photos/406152/pexels-photo-406152.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260') ;"
      ></parallax>
      <div class="content-center">
        <h1>블로그 게시글 작성 페이지</h1>
      </div>
    </div>
    <div class="section section-about-us">
      <div class="container">
        <div class="row justify-content-md-center" id="row_style">
          <div class="form-group">
            <input
              type="text"
              class="form-control"
              placeholder="제목"
              v-model="blogPost.title"
            />
          </div>

          <CxkEditor v-on:content="contentHandler" />
        </div>
        <b-form-tags
          input-id="tags-basic"
          v-model="blogTags"
          class="mb-2"
        ></b-form-tags>
        <div class="form-group">
          <button @click="preTest" class="btn btn-primary" id="submit">
            등록
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import axios from "axios";
import router from "@/router";
import SERVER from "@/api/api";
import { mapGetters } from "vuex";

export default {
  name: "BlogPostMakeView",
  data() {
    return {
      blogTags: [],
      blogPost: {
        title: "",
        content: "",
        tag1: "",
        tag2: "",
        tag3: "",
        recipe_id: null,
      },
    };
  },
  components: {
    CxkEditor,
  },
  computed: {
    ...mapGetters(["config"]),
  },
  methods: {
    contentHandler(event) {
      this.blogPost.content = event;
    },

    preTest() {
      let problems = [];
      if (this.blogPost.title == null) {
        problems.push("게시글 제목");
      }
      if (this.blogPost.content == null) {
        problems.push(" 게시글 내용");
      }

      if (problems.length == 0) {
        this.makeBlogPost();
      } else {
        alert("문제가 있는 위치: " + problems);
      }
    },
    makeBlogPost() {
      let configs = {
        headers: {
          Authorization: this.config,
        },
      };
      console.log("INPUT START");
      console.log(SERVER.URL + SERVER.ROUTES.blogRegister);
      console.log(this.blogPost);
      console.log("INPUT END");
      axios
        .post(SERVER.URL + SERVER.ROUTES.blogRegister, this.blogPost, configs)
        .then((res) => {
          console.log(res);
          router.push({ name: "BlogPostListView", params: { pageNum: 1 } });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style></style>
