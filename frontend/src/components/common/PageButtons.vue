<template>
  <!-- <div class="btn btn-group">
    <button @click="movePage($event)" v-if="curPage-3 > 0">처음</button>
    <button @click="movePage($event)" v-if="curPage-2 > 0">{{ curPage - 2 }}</button>
    <button @click="movePage($event)" v-if="curPage-1 > 0">{{ curPage - 1 }}</button>
    <button>{{ curPage }}</button>
    <button @click="movePage($event)" v-if="curPage < maxPage">{{ curPage + 1 }}</button>
    <button @click="movePage($event)" v-if="curPage+1 < maxPage">{{ curPage + 2 }}</button>
    <button @click="movePage($event)" v-if="curPage+2 < maxPage">마지막</button>

  </div> -->
  <nav aria-label="Page navigation example">
    <ul class="pagination">
      <li class="page-item">
        <p @click="movePage($event)" class="page-link" aria-label="Previous">&laquo;</p>
      </li>
      <li @click="movePage($event)" v-if="curPage-1 > 0" class="page-item"><p class="page-link">{{ curPage - 1 }}</p></li>
      <li class="page-item"><p class="page-link">{{ curPage }}</p></li>
      <li @click="movePage($event)" v-if="curPage < maxPage" class="page-item"><p class="page-link">{{ curPage + 1 }}</p></li>
      <li class="page-item">
        <p @click="movePage($event)" class="page-link" aria-label="Next">&raquo;</p>
      </li>
    </ul>
  </nav>
  
</template>

<script>
export default {
  name: 'MoviePageButtons',
  props: {
    curPage: {
      type: Number,
    },
    maxPage: {
      type: Number,
    },
  },
  methods: {
    movePage(event) {
      this.$emit('move-page', event.target.outerText)
    }
  }
}
</script>

<style>
.page-link:hover {
  cursor: pointer;
}
</style>