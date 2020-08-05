<template>
  <div class="wrapper">
    <!-- 페이지 배너 시작 -->
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.pexels.com/photos/406152/pexels-photo-406152.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260') ;"
      ></parallax>
      <div class="content-center">
        <h1>블로그 게시글 작성 페이지</h1>
      </div>
    </div>
    <!-- 페이지 배너 끝 -->

    <!-- 게시글 작성 시작 -->
    <div class="section">
      <div class="container">
        <div class="row justify-content-md-center" id="row_style">
          <!-- 제목 -->
          <input type="text" class="form-control" placeholder="제목" v-model="blogPost.title" />

          <!-- 텍스트 에디터 -->
          <CxkEditor v-on:content="contentHandler" />
          <!-- 태그 시작 -->
          <b-form-tags v-model="blogTags" no-outer-focus class="mb-2">
            <template v-slot="{ tags, inputAttrs, inputHandlers, tagVariant, addTag, removeTag }">
              <b-input-group v-show="blogTags.length < 3" class="mb-2">
                <b-form-input v-bind="inputAttrs" v-on="inputHandlers" class="form-control"></b-form-input>
                <b-input-group-append>
                  <b-button @click="addTag()" variant="primary">Add</b-button>
                </b-input-group-append>
              </b-input-group>
              <div class="d-inline-block" style="font-size: 1.5rem;">
                <b-form-tag
                  v-for="tag in tags"
                  @remove="removeTag(tag)"
                  :key="tag"
                  :title="tag"
                  :variant="tagVariant"
                  class="mr-1"
                >{{ tag }}</b-form-tag>
              </div>
            </template>
          </b-form-tags>
        </div>

        <div class="form-group">
          <button @click="preTest" class="btn btn-primary" id="submit">등록</button>
          <button @click="preTest" class="btn btn-default" id="submit">취소</button>
        </div>
      </div>
    </div>
    <!-- 게시글 작성 끝 -->
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
        tag1: null,
        tag2: null,
        tag3: null,
        recipe_id: null,
      },
    };
  },
  components: {
    CxkEditor,
  },
  computed: {
    ...mapGetters(["config"]),
    tagState() {
      return this.blogPost.length > 2 && this.blogPost.legnth < 0;
    },
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

      this.blogPost.tag1 = null;
      this.blogPost.tag2 = null;
      this.blogPost.tag3 = null;
      if (this.blogTags.length > 2) {
        this.blogPost.tag3 = this.blogTags[2];
      }
      if (this.blogTags.length > 1) {
        this.blogPost.tag2 = this.blogTags[1];
      }
      if (this.blogTags.length > 0) {
        this.blogPost.tag1 = this.blogTags[0];
      }

      console.log(this.blogPost);
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
