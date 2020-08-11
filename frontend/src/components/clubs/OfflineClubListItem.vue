<template>
  <router-link :to="{ name: 'ClubDetailView', params: {club_id: club.meetId} }">
    <img :src="imageSrc(club.thumbnailSrc)">
  </router-link>
</template>

<script>
import SERVER from '@/api/api'

export default {
  name: 'OfflineClubListItem',
  props: {
    club: Object
  },
  methods: {
    // Thumbnail 주소
    imageSrc(link) {
      return SERVER.IMAGE_URL + link
    }
  }
}
</script>

<style>

</style>