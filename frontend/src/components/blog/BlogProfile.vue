<template>
  <div class="container">
    <div class="photo-container">
      <router-link to="/blog/">
        <img :src="profileSrc" alt />
      </router-link>
    </div>
    <h3 class="title">{{user.nickname}}</h3>
    <p class="category">{{user.email}}</p>
  </div>
</template>

<script>
import SERVER from "@/api/api";
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  name: "BlogPorfile",
  data() {
    return {
      user: {
        email: "",
        nickname: "",
        profilePhoto: "",
      },
    };
  },
  computed: {
    ...mapGetters(["config"]),
    profileSrc() {
      return SERVER.IMAGE_URL + this.user.profilePhoto;
    },
  },
  components: {},
  methods: {},
  created() {
    let configs = {
      headers: {
        Authorization: this.config,
      },
    };
    axios
      .get(SERVER.URL + SERVER.ROUTES.myPage, configs)
      .then((res) => {
        this.user = res.data.user;
      })
      .catch((err) => console.log(err.response));
  },
};
</script>

<style scoped>
.profile-page .photo-container img {
  height: 100px;
}
.profile-page .photo-container {
  -webkit-transition: -webkit-transform 0.4s;
  transition: transform 0.4s;
}
.profile-page .photo-container:hover {
  height: 100px;
  -webkit-transform: scale(1.2) rotate(0.01deg);
  transform: scale(1.2) rotate(0.01deg);
}
</style>