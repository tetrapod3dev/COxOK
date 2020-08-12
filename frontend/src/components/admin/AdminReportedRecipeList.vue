<template>
  <div>
    신고당한 레시피
  </div>
</template>

<script>
import SERVER from '@/api/api'
import axios from 'axios'

import { mapGetters } from 'vuex'

export default {
  name: 'AdminReportedRecipeList',
  data() {
    return {
      curPage: 1,
      curRecipes: [],
    }
  },
  computed: {
    ...mapGetters(['config'])
  },
  mounted() {
    this.getRecipes();
  },
  methods: {
    getRecipes() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.adminGetReportedRecipes, {
          headers: {
            Authorization: this.config,
          },
        })
        .then(res => {
          console.log(res)
        })
        .catch(err => console.log(err.response))
    }
  }
}
</script>

<style>

</style>