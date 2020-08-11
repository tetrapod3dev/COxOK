<template>
  <router-link :to="{ name: 'ClubOnlineDetailView' }">
    <img :src="imageSrc(online)">
    <p>???</p>
  </router-link>
</template>

<script>
import SERVER from '@/api/api'

export default {
  name: 'OnlineClubListItem',
  props: {
    online: Object
  },
  methods: {
    imageSrc(online) {
      if (online.type == "유튜브강의") {
        return online.thumbnailSrc
      } else {
        return SERVER.IMAGE_URL + online.thumbnailSrc
      }
    }
  }
}
</script>

<style>

</style>