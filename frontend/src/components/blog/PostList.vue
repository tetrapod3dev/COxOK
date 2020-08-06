<template>
  <div>
    <table class="table">
      <thead>
        <tr class="bg-dark text-white">
          <th>번호</th>
          <th>제목</th>
          <th>등록일</th>
        </tr>
      </thead>

      <tbody v-for="(post, index) in posts" :key="index">
        <tr>
          <td>{{ total - (curPage - 1) * 10 - index }}</td>
          <td>
            <router-link
              :to="{ name: 'BlogPostDetailView', params: { blogId: post.blogId } }"
            >{{ post.title }}</router-link>
          </td>
          <td>{{ post.regTime }}</td>
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
};
</script>

<style>
</style>