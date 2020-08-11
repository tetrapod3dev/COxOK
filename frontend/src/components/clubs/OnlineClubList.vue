<template>
  <div class="container">
    <div class="row">
      <h3 class="col-12">리스트..인데 아직이용</h3>
      <OnlineClubListItem v-for="online in onlines" :key="online.onlineId" :online="online" class="col-3" />
    </div>
  </div>
</template>

<script>
import OnlineClubListItem from './OnlineClubListItem'

export default { 
  name: "OnlineClubList",
  data() {
    return {
      onlines: [
        {
          onlineId: 2,
          title: '유튜브 테스트',
          content: 'ㅎ<font size="7">ㅎ</font>ㅎ',
          type: '유튜브강의',
          date: '2020-08-27T02:04:00',
          link: null,
          thumbnailSrc: 'https://i.ytimg.com/vi/PVcpF7J2oXs/default.jpg',
          video: 'PVcpF7J2oXs',
        },
        {
          onlineId: 4,
          title: '실시간 강의 예시입니다.',
          content: 'ㅎㅎㅎ<font size="7">ㅎㅎ</font>ㅎㅎㅎㅎㅎ',
          type: '실시간강의',
          date: '2020-08-26T02:05:00',
          link: 'http://i3a104.p.ssafy.io/',
          thumbnailSrc: '2020081117554132_1234.png',
          video: null,
        }
      ]
    }
  },
  components: {
    OnlineClubListItem
  },
  created() {
  }
}
</script>

<style>

</style>