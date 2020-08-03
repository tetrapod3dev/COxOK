<template>
  <div>
    <button class="btn btn-primary" @click="onClickAddr">Test</button>
  </div>
</template>

<!-- 주소 검색 API, 위치확인 필요 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
export default {
  name: 'About',
  methods: {
    onClickAddr() {
      const info = this
      new daum.Postcode({
        oncomplete(data) {
        var roadAddr = data.roadAddress;
        var extraRoadAddr = ''
        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
          extraRoadAddr += data.bname
        }
        if (data.buildingName !== '' && data.apartment === 'Y') {
          extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName)
        }
        if (extraRoadAddr !== '') {
          extraRoadAddr = ' (' + extraRoadAddr + ')'
        }
        console.log(data.zonecode)
        console.log(roadAddr)
        }
      }).open()
    },
  }
}
</script>
<style>

</style>