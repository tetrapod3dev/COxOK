<template>
  <div>
  <button class="btn btn-secondary">{{ curSortKey }}</button>
  <b-dropdown id="dropdown-1" v-model="curSortKey" class="my-2">
    <b-dropdown-item v-for="(sortKey, index) in sortKeys" :key="index" @click="changeSortKey(sortKey)">{{ sortKey }}</b-dropdown-item>
  </b-dropdown>
  </div>
</template>

<script>
export default {
  name: 'MovieSortButtons',
  props: {
    curSortKey: {
      type: String
    },
    sortKeys: {
      type: Array
    }
  },
  methods: {
    changeSortKey(sortKey) {
      this.$emit('change-sortkey', sortKey)
    }
  }
}
</script>

<style>

</style>