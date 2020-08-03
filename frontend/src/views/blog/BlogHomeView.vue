<template>
  <div class="row">
    <BlogMenu />블로그 메인 페이지
  </div>
</template>

<script>
import BlogMenu from "@/components/blog/BlogMenu.vue";

export default {
  name: "BlogHomeView",
  components: {
    BlogMenu,
  },
};
</script>

<style>
</style>