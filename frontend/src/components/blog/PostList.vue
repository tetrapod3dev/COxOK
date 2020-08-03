<template>
  <div>
    <table class="table">
      <thead>
      <tr class="bg-dark text-white">
        <th>번호</th>
        <th>제목</th>
        <th>등록일</th>
        <th>추천</th>
      </tr>
      </thead>
      
      <tbody v-for="(post, index) in posts" :key="index">
        <tr>
        <td>{{ index + 1 }}</td>
        <td><router-link :to="{ name: 'BlogPostDetailView', params: { post_id: 1 } }">{{ post.title }}</router-link></td>
        <td>{{ post.created_at }}</td>
        <td>0</td>
        </tr>
      </tbody>

    </table>
  </div>
</template>

<script>
export default {
  name: 'PostList',
  props: {
    posts: {
      type: Array,
    },
  },
  methods: {
  },
}
</script>

<style>

</style>