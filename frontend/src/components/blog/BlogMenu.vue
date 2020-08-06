<template>
  <div class="button-container">
    <router-link
      :to="{ name: 'BlogPostListView', params: { pageNum: 1 } }"
      class="btn btn-primary btn-round btn-lg"
    >게시글</router-link>
    <router-link
      :to="{ name: 'BlogRecipeListView', params: { pageNum: 1 } }"
      class="btn btn-primary btn-round btn-lg"
    >내가 쓴 레시피</router-link>
    <router-link
      :to="{ name: 'BlogMarkedRecipeListView', params: { pageNum: 1 } }"
      class="btn btn-primary btn-round btn-lg"
    >좋아하는 레시피</router-link>
    <router-link
      :to="{ name: 'UserSettingView' }"
      class="btn btn-default btn-icon btn-round btn-lg"
    >
    <i class="now-ui-icons ui-1_settings-gear-63"></i>
    </router-link>
  </div>
</template>

<script>

export default {
  name: "BlogMenu",
  data() {
    return {};
  },
  methods: {},
  created() {},
};
</script>

<style scoped></style>
