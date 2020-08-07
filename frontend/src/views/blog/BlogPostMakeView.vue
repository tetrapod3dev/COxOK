<template>
  <div class="wrapper">
    <div class="page-header page-header-mini header-filter" filter-color="black">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.unsplash.com/photo-1505576399279-565b52d4ac71?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80')"
      ></parallax>
      <blog-profile />
    </div>

    <div class="section">
      <div class="container">
        <blog-menu />

        <div class="row">
          <div class="col-md-12">
            <!-- blog inner page start -->
            <div class="row justify-content-md-center" id="row_style">
              <!-- 제목 -->
              <input type="text" class="form-control" placeholder="제목" v-model="blogPost.title" />

              <!-- 텍스트 에디터 -->
              <CxkEditor :value.sync="blogPost.content" />
              <!-- 태그 시작 -->
              <b-form-tags v-model="blogTags" no-outer-focus class="mb-2">
                <template
                  v-slot="{ tags, inputAttrs, inputHandlers, tagVariant, addTag, removeTag }"
                >
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
              <button @click="goBackPage" class="btn btn-default">취소</button>
            </div>
          </div>
        </div>
        <!-- 게시글 작성 끝 -->
      </div>
    </div>
  </div>
</template>

<script>
import BlogProfile from "@/components/blog/BlogProfile.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";

import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import axios from "axios";
import router from "@/router";
import SERVER from "@/api/api";
import { mapGetters } from "vuex";

export default {
  name: "BlogPostMakeView",
  bodyClass: "profile-page",
  data() {
    return {
      blogTags: [],
      blogPost: {
        title: null,
        content: null,
        tag1: null,
        tag2: null,
        tag3: null,
        recipe_id: null,
      },
    };
  },
  components: {
    CxkEditor,
    BlogProfile,
    BlogMenu,
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
      if (this.blogPost.title === null || this.blogPost.title === "") {
        problems.push("게시글 제목");
      }
      if (this.blogPost.content === null || this.blogPost.content === "") {
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
      axios
        .post(SERVER.URL + SERVER.ROUTES.blog, this.blogPost, configs)
        .then((res) => {
          console.log(res);
          router.push({ name: "BlogPostListView", params: { pageNum: 1 } });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goBackPage() {
      this.$router.go(-1);
    },
  },
};
</script>

<style></style>
