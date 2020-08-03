<template>
  <div class="col-2">
    <BlogProfile />
    <router-link :to="{ name: 'BlogPostListView', params: { pageNum: 1 } }">
      <button class="btn btn-outline-secondary mx-2">작성한 게시글</button>
    </router-link>
    <router-link :to="{ name: 'BlogRecipeListView', params: { pageNum: 1 } }">
      <button class="btn btn-outline-secondary mx-2">작성한 레시피</button>
    </router-link>
    <router-link
      :to="{ name: 'BlogMarkedRecipeListView', params: { pageNum: 1 } }"
    >
      <button class="btn btn-outline-secondary mx-2">북마크 레시피</button>
    </router-link>
    <router-link :to="{ name: 'BlogGraphView' }">
      <button class="btn btn-outline-secondary mx-2">통계</button>
    </router-link>
    <router-link :to="{ name: 'UserSettingView' }">
      <button class="btn btn-outline-secondary mx-2">설정</button>
    </router-link>
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
