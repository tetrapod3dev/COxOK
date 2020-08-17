<template>
  <div>
    <table class="table">
      <thead>
        <tr class="bg-primary text-white text-left">
          <th>번호</th>
          <th>제목</th>
          <th>등록일</th>
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