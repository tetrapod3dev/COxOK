<template>
  <div class="container">
    <h1 class="my-5">Header</h1>
    <select v-model="type" class="border rounded-lg p-2 d-flex justify-content-start">
      <!-- <option disabled selected>{{ type }}</option> -->
      <option>유저</option>
      <option>재료</option>
      <option>재료 추가</option>
      <option>신고당한 레시피</option>
    </select>

    <AdminUserList class="float-none" v-if="type=='유저'" />
    <AdminIngredientList v-else-if="type=='재료'" />
    <AdminModiIngredientList v-else-if="type=='재료 추가'" />
    <AdminReportedRecipeList v-else />
  </div>
</template>

<script>
import SERVER from '@/api/api'
import axios from 'axios'

import { mapGetters } from 'vuex'

import AdminUserList from '@/components/admin/AdminUserList.vue'
import AdminIngredientList from '@/components/admin/AdminIngredientList.vue'
import AdminModiIngredientList from '@/components/admin/AdminModiIngredientList.vue'
import AdminReportedRecipeList from '@/components/admin/AdminReportedRecipeList.vue'

export default {
  name: 'AdminMainView',
  data() {
    return {
      type: '유저',
    }
  },
  components: {
    AdminUserList,
    AdminIngredientList,
    AdminModiIngredientList,
    AdminReportedRecipeList
  },
  computed: {
    ...mapGetters(['config'])
  },
  created() {
    axios
      .get(SERVER.URL + SERVER.ROUTES.myPage, {
        headers: {
          Authorization: this.config,
        },
      })
      .then(res => {
        if ((res.data.user.email != 'admin@co-ok.com') || (res.data.user.nickname != 'admin')) {
          alert('관리자가 아닙니다! 장난 ㄴㄴ용')
          this.$router.push({ name: "Main" });
        }
      })
      .catch(err => console.log(err.response))
  },
  methods: {
  }
}
</script>

<style>

</style>