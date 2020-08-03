<template>
  <div class="row">
    <BlogMenu />
    <h1>블로그 게시글 페이지입니다.</h1>
  </div>
</template>

<script>
import BlogMenu from "@/components/blog/BlogMenu.vue";
export default {
  name: "BlogPostDetailView",
  components: {
    BlogMenu,
  },
};
</script>

<style>
</style>