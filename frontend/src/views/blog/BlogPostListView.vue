<template>
  <div class="row">
    <BlogMenu />

    <div class="col-10">
      <div class="d-flex bd-highlight mb-1">
        <router-link to="/blog/posts/make">
          <button class="btn btn-outline-secondary mx-2">글 작성</button>
        </router-link>

        <PostSortButtons
          :curSortKey="curSortKey"
          :sortKeys="sortKeys"
          @change-sortkey="changeSortKey"
        />

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

      <PostList :posts="posts" />

      <PageButtons :curPage="curPage" :maxPage="maxPage" @move-page="movePage" />
    </div>
  </div>
</template>

<script>
import PostList from "@/components/blog/PostList.vue";
import PageButtons from "@/components/common/PageButtons.vue";
import PostSortButtons from "@/components/blog/PostSortButtons.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";


export default {
  name: "BlogPostListView",
  data() {
    return {
      user: { nickname: "김유저", email: "test@test.com" },
      curPage: 1,
      maxPage: 20,
      posts: [
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
        {
          title: "1번 글입니다.",
          content: "1번 글 내용입니다.",
          created_at: "2020-07-22",
        },
      ],
      sortKeys: ["가나다순", "평점 높은순", "최근 출시일순", "인기도 순"],
      curSortKey: "가나다순",
    };
  },
  components: {
    PostList,
    PageButtons,
    PostSortButtons,
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
  },
  created() {
    this.curPage = this.$route.params.pageNum * 1;
  },
};
</script>

<style>
</style>