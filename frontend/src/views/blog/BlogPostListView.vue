<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.pexels.com/photos/406152/pexels-photo-406152.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260') ;"
      ></parallax>
    </div>
    <BlogMenu />

    <div class="col-10">
      <div class="d-flex bd-highlight mb-1">
        <router-link to="/blog/posts/make">
          <button class="btn btn-outline-secondary mx-2">글 작성</button>
        </router-link>

        <form class="form-inline my-2 my-lg-0 bd-highlight" method="GET" action>
          <input
            class="form-control mr-sm-2"
            type="search"
            placeholder
            aria-label="Search"
            name="kw"
            value
          />
          <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit">검색</button>
        </form>
      </div>

      <PostList
        :posts="posts"
        :curPage="curPage"
        :maxPage="maxPage"
        :numPostPerPage="numPostPerPage"
      />

      <PageButtons :curPage="curPage" :maxPage="maxPage" @move-page="movePage" />
    </div>
  </div>
</template>

<script>
import PostList from "@/components/blog/PostList.vue";
import PageButtons from "@/components/common/PageButtons.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";

import axios from "axios";
import SERVER from "@/api/api";

export default {
  name: "BlogPostListView",
  data() {
    return {
      curPage: 1,
      maxPage: 20,
      numPostPerPage: 10,
      posts: [],
    };
  },
  components: {
    PostList,
    PageButtons,
    BlogMenu,
  },
  methods: {
    movePage(page) {
      if (page == "처음") {
        this.$router.push({ params: { pageNum: 1 } });
      } else if (page == "마지막") {
        this.$router.push({ params: { pageNum: this.maxPage } });
      } else {
        this.$router.push({ params: { pageNum: parseInt(page) } });
      }
      this.curPage = this.$route.params.pageNum * 1;
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
    allBlogPost(page) {
      let configs = {
        headers: {
          Authorization: this.config,
        },
      };
      axios
        .get(SERVER.URL + SERVER.ROUTES.blogList + (page - 1), configs)
        .then((res) => {
          console.log(res.data);
          this.posts = res.data.list;
          this.maxPage =
            parseInt((res.data.total - 1) / this.numPostPerPage) + 1;
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

<style></style>
