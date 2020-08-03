<template>
  <div>
    <h2>소모임 리스트 페이지(메인)입니당.</h2>
    
    <router-link :to="{ name: 'ClubDetailView', params: { club_id: 1 } }"><button class="btn btn-outline-secondary mx-2">상세보기</button></router-link>
    
    <router-link :to="{ name: 'ClubMakeView' }"><button class="btn btn-outline-secondary mx-2">소모임 만들기</button></router-link>
  </div>
</template>

<script>
export default {
  name: 'ClubListView',
}
</script>

<style>

</style>