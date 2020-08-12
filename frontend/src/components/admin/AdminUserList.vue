<template>
  <div class="container">
    <h2>Admin이 유저 정보를 봅니다..</h2>

    <div v-for="user in users" :key="user.userId" class="row">
      <p class="col-1"> {{ user.userId }} </p>
      <img :src="imageSrc(user.profilePhoto)" class="col-3">
      <p class="col-2"> {{ user.email }} </p>
      <p class="col-1"> {{ user.nickname }} </p>
      <p class="col-4"> {{ user.detail }} </p>
      <p class="col-1"> {{ user.auth }}</p>
    </div>

  </div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/api'

import { mapGetters } from 'vuex'

export default {
  name: 'AdminUserList',
  data() {
    return {
      users: {}
    }
  },
  computed: {
    ...mapGetters(['config']),
  },
  mounted() {
    console.log(1)
    axios
      .get(SERVER.URL + SERVER.ROUTES.adminGetUser, {
        headers: {
          Authorization: this.config,
        },
      })
      .then(res => {
        this.users = res.data.list
      })
      .catch(err => console.log(err.response))
  },
  methods: {
    imageSrc(imageLink) {
      return SERVER.IMAGE_URL + imageLink
    }
  }
}
</script>

<style>

</style>