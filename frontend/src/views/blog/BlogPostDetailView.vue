<template>
  <div class="wrapper blog-post">
    <div class="page-header page-header-mini header-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image: url('https://images.pexels.com/photos/406152/pexels-photo-406152.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260')"></parallax>
      <blog-profile/>
    </div>
    <div class="section">
      <div class="container">
        
      <blog-menu/>
        <!-- 게시글 시작 -->

      <router-link
          :to="{ name: 'BlogPostUpdateView', params: { blogId: blogPost.blogId } }"
          tag="button"
          class="btn btn-outline-secondary mx-2"
      >수정</router-link>
    </div>
    <div class="section">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto">
            <hr/>
            <!-- 제목 -->
            <h3 class="title">{{blogPost.title}}</h3>
            <hr/>
            <!-- 내용 -->
            <p class="content" v-html="blogPost.content"></p>
            <!-- 태그 -->
            </div>
          </div>
        </div>
      </div>
      <div class="section section-blog-info">
        <div class="container">
          <div class="row">
            <div class="col-md-8 ml-auto mr-auto">
              <div class="row">
                <div class="col-md-6">
                  <div class="blog-tags">
                    Tags:
                    <span class="label label-primary" v-if="blogPost.tag1 !== null" v-text="'#' + blogPost.tag1"></span>
                    <span class="label label-primary" v-if="blogPost.tag2 !== null" v-text="'#' + blogPost.tag2"></span>
                    <span class="label label-primary" v-if="blogPost.tag3 !== null" v-text="'#' + blogPost.tag3"></span>
                  </div>
                </div>
              </div>
              <hr/>
            </div>
            <!-- 레시피 -->
            <card type="profile" plain>
              <template slot="raw-content">
                <div class="row">
                  <div class="col-md-8">
                    <div class="card-avatar">
                      <a href="#pablo">
                        <img class="img img-raised" src="img/mike.jpg">
                      </a>
                      <div class="ripple-container"></div>
                    </div>
                    <h4 class="card-title">Alec Thompson</h4>
                    <p class="description">I've been trying to figure out the bed design for the
                      master bedroom at our Hidden Hills compound...I like good music from
                      Youtube.</p>
                  </div>
                </div>
              </template>
            </card>
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

<style scoped>
/* .content{
  min-height: 1000px;
}  */
</style>