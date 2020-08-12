<template>
  <div>
    <h1 class="my-5">Test</h1>
    <select v-model="type" @change="typeChange">
      <!-- <option disabled selected>{{ type }}</option> -->
      <option>유저</option>
      <option>재료</option>
      <option>재료 추가</option>
      <option>신고당한 레시피</option>
    </select>

    <AdminUserList v-if="type=='유저'" />
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
          alert('관리자가 아닙니다! 장난 ㄴㄴ요')
          this.$router.push({ name: "Main" });
        } else {
          alert('관리자입니다! 환영해요')
        }
      })
      .catch(err => console.log(err.response))
  },
  methods: {
    typeChange() {
      console.log('ㅇㅋ')
    }
  }
}
</script>

<style>

</style>