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
            <router-link to="/blog/posts/make" tag="div" class="col-md-2 ml-auto">
              <n-button type="primary" round block>글 작성</n-button>
            </router-link>

            <PostList
              :posts="posts"
              :curPage="curPage"
              :maxPage="maxPage"
              :numPostPerPage="numPostPerPage"
              :total="total"
              class="Cookie-2"
            />

            <PageButtons
              class="d-flex justify-content-center"
              :curPage="curPage"
              :maxPage="maxPage"
              @move-page="movePage"
            />
            <!-- blog inner Page end -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogProfile from "@/components/blog/BlogProfile.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";

import PostList from "@/components/blog/PostList.vue";
import PageButtons from "@/components/common/PageButtons.vue";

import SERVER from "@/api/api";
import axios from "axios";
import { mapGetters } from "vuex";
import { Button } from "@/components/global";

export default {
  name: "BlogPostListView",
  bodyClass: "profile-page",
  data() {
    return {
      curPage: 0,
      maxPage: 0,
      numPostPerPage: 10,
      posts: [],
      total: 0,
    };
  },
  computed: {
    ...mapGetters(["config"]),
  },
  components: {
    PostList,
    PageButtons,
    BlogProfile,
    BlogMenu,
    [Button.name]: Button,
  },

  methods: {
    movePage(page) {
      if (page == "«") {
        this.$router.push({ params: { pageNum: 1 } });
      } else if (page == "»") {
        this.$router.push({ params: { pageNum: this.maxPage } });
      } else {
        this.$router.push({ params: { pageNum: parseInt(page) } });
      }
      scroll(0, 0);
    },
    changeSortKey(sortKey) {
      this.curSortKey = sortKey;
      this.curPage = 1;
    },
    changePage(page) {
      // if (
      //   this.searchingData.selectedCategory.length +
      //     this.searchingData.selectedIngredients.length !=
      //   0
      // ) {
      //   this.searchRecipe(page);
      // } else {
      this.allBlogPost(page);
      // }
    },
    allBlogPost() {
      let configs = {
        headers: {
          Authorization: this.config,
        },
      };
      axios
        .get(
          SERVER.URL + SERVER.ROUTES.blogMy + (this.$route.params.pageNum - 1),
          configs
        )
        .then((res) => {
          this.posts = res.data.blog;
          this.total = res.data.total;
          this.maxPage =
            parseInt((res.data.total - 1) / this.numPostPerPage) + 1;
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert("로그인 정보가 만료되었습니다! 다시 로그인해주세요.");
            this.logout();
          }
        });
    },
  },
  created() {
    this.curPage = this.$route.params.pageNum * 1;
    this.changePage(this.curPage);
  },

  watch: {
    $route() {
      this.curPage = parseInt(this.$route.params.pageNum);
      this.changePage(this.curPage);
    },
  },
};
</script>

<style>
</style>
