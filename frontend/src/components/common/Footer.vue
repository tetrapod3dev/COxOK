<template>
  <footer class="footer footer-default">
    <div class="container mx-auto">
      <div class="row">
        <img class="col-1" src="http://i3a104.p.ssafy.io/logo/CO_OK-logo.png" />
        <p class="col-8 offset-1 align-self-center">@ 2020 Copyright SSAFY</p>
        <h3 class="col-2"><router-link :to="{name: 'About'}" class="text-decoration-none">About Us</router-link></h3>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "Footer",
};
</script>

<style scoped>
.container, .row, h3, p, a, img {
  margin: 0;
  padding: 0;
}

</style>
