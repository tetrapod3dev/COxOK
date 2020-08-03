<template>
  <div class="row">
    <BlogMenu />
    <h2>개인 통계 관련 페이지입니다.</h2>
  </div>
</template>

<script>
import BlogMenu from "@/components/blog/BlogMenu.vue";

export default {
  name: "BlogGraphView",
  components: {
    BlogMenu,
  },
};
</script>

<style>
</style>