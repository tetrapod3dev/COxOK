<template>
  <div>
    <navbar
      type="primary"
      position="fixed"
      :transparent="transparent"
      :color-on-scroll="20"
      menu-classes="ml-auto"
    >
      <template>
        <router-link class="navbar-brand" :to="isLoggedIn ? '/main/' : '/'">
          <img src="@/assets/CO_OK-logo.png" width="40px" />
        </router-link>
      </template>
      <template slot="navbar-menu">
        <li class="nav-item dropdown">
          <drop-down class="nav-item" icon="now-ui-icons shopping_shop" title="둘러보기">
            <router-link class="dropdown-item" :to="{ name: isLoggedIn ? 'Main' : 'Home'}">홈</router-link>
            <router-link class="dropdown-item" :to="{ name: 'About'}">씨없는수박</router-link>
          </drop-down>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to="{ name: 'PrevRecipeList' }">
            <i class="now-ui-icons education_paper"></i>
            <p>요리하기</p>
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to="{ name: 'ClubListView', params: {pageNum: 1} }">
            <i class="now-ui-icons users_circle-08"></i>
            <p>코옥하기</p>
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to="{ name: 'VersusHomeView' }">
            <i class="now-ui-icons users_circle-08"></i>
            <p>대결하기</p>
          </router-link>
        </li>
        <li class="nav-item" v-if="isLoggedIn">
          <drop-down class="nav-item" :image="profileSrc" :title="user.nickname">
            <router-link class="dropdown-item" :to="{ name: 'BlogHomeView' }">
              <p>마이페이지</p>
            </router-link>
            <a class="dropdown-item" @click="logout">로그아웃</a>
          </drop-down>
        </li>
        <li class="nav-item" v-if="!isLoggedIn">
          <a class="nav-link">
            <LoginModal icon="now-ui-icons users_circle-08" />
          </a>
        </li>
      </template>
    </navbar>
    <!-- <b-nav-item :to="/versus/">요리대전</b-nav-item> -->
    <!-- <b-nav-item :to="{ name: 'ClubListView', params: { pageNum: 1 } }">소모임</b-nav-item> -->
  </div>
</template>

<script>
import SERVER from "@/api/api";
import axios from "axios";

import { Navbar, DropDown } from "@/components/global";
import { mapGetters, mapActions } from "vuex";
import LoginModal from "../accounts/LoginModal.vue";

export default {
  name: "Header",
  props: {
    transparent: Boolean,
  },
  data() {
    return {
      user: {
        email: "",
        nickname: "",
        profilePhoto: "",
      },
      keyword: "",
    };
  },
  components: {
    LoginModal,
    Navbar,
    DropDown,
  },
  computed: {
    ...mapGetters(["isLoggedIn", "config"]),
    profileSrc() {
      return SERVER.IMAGE_URL + this.user.profilePhoto;
    },
  },
  watch: {},
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
  methods: {
    ...mapActions(["logout"]),
  },
};
</script>

<style scoped></style>
