<template>
  <div>
    <table class="table table-hover">
      <thead>
        <tr class="text-left">
          <th style="width:20%">번호</th>
          <th style="width:60%">제목</th>
          <th style="width:20%">등록일</th>
        </tr>
      </thead>

      <tbody v-for="(post, index) in posts" :key="index" class="text-left">
        <tr>
          <td>{{ total - (curPage - 1) * 10 - index }}</td>
          <td>
            <router-link
              :to="{ name: 'BlogPostDetailView', params: { blogId: post.blogId } }"
            >{{ post.title }}</router-link>
          </td>
          <td>{{ formattingDate(post.regTime) }}</td>
        </tr>
      </tbody>

      <tbody v-if="posts.length == 0" class="text-center">
        <tr>
          <td colspan="3">글을 작성해보세요</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "PostList",
  props: {
    posts: {
      type: Array,
    },
    curPage: Number,
    maxPage: Number,
    numPostPerPage: Number,
    total: Number,
  },
  methods: {
    formattingDate(regTime) {
      return regTime.split("T")[0];
    },
  },
};
</script>

<style scoped>
</style>