<template>
  <div>
  </div>
</template>

<script>
export default {
  name: 'EmailAuthView',
  created() {
    alert('인증에 성공했습니다!')
    this.$router.push({ name: 'Home' })
  }
}
</script>
