<template>
  <div class="container">
    온라인 상세보기
    <h3>{{ online.title }}</h3>
    
    <div v-html="online.content"></div>

    <div v-if="online.video != null">
      <div class="embed-responsive embed-responsive-16by9">
        <iframe class="embed-responsive-item" :src="videoUrl" allowfullscreen></iframe>
      </div>
    </div>

    <div v-else>
      <a :href="online.link" target="_blank">강의 링크로 이동 {{ online.link }}</a>
    </div>

  </div>
</template>

<script>
export default {
  name: 'ClubOnlineDetailView',
  data() {
    return {
      online: 
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
        // {
        //   onlineId: 4,
        //   title: '실시간 강의 예시입니다.',
        //   content: 'ㅎㅎㅎ<font size="7">ㅎㅎ</font>ㅎㅎㅎㅎㅎ',
        //   type: '실시간강의',
        //   date: '2020-08-26T02:05:00',
        //   link: 'http://i3a104.p.ssafy.io/',
        //   thumbnailSrc: '2020081117554132_1234.png',
        //   video: null,
        // }
    }
  },
  computed: {
    videoUrl() {
      return `https://youtube.com/embed/${this.online.video}`
    }
  }
}
</script>

<style>

</style>