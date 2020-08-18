<template>
  <footer class="footer footer-default">
    <div class="container mx-auto">
      <div class="row">
        <h3 class="col-2">Co~ok</h3>
        <p class="col-8 align-self-center">@ 2020 Copyright 씨없는수박</p>
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
.container, .row, h3, p, a {
  margin: 0;
  padding: 0;
}

</style>
