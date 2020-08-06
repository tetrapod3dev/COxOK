<template>
  <div class="col-2">
    <BlogProfile />
    <router-link
      :to="{ name: 'BlogPostListView', params: { pageNum: 1 } }"
      tag="button"
      class="btn btn-outline-secondary mx-2"
    >작성한 게시글</router-link>
    <router-link
      :to="{ name: 'BlogRecipeListView', params: { pageNum: 1 } }"
      tag="button"
      class="btn btn-outline-secondary mx-2"
    >작성한 레시피</router-link>
    <router-link
      :to="{ name: 'BlogMarkedRecipeListView', params: { pageNum: 1 } }"
      tag="button"
      class="btn btn-outline-secondary mx-2"
    >북마크 레시피</router-link>
    <router-link
      :to="{ name: 'BlogGraphView' }"
      tag="button"
      class="btn btn-outline-secondary mx-2"
    >통계</router-link>
    <router-link
      :to="{ name: 'UserSettingView' }"
      tag="button"
      class="btn btn-outline-secondary mx-2"
    >설정</router-link>
  </div>
</template>

<script>
import BlogProfile from "./BlogProfile.vue";

export default {
  name: "BlogMenu",
  data() {
    return {};
  },
  components: {
    BlogProfile,
  },
  methods: {},
  created() {},
};
</script>

<style scoped></style>
