<template>
  <div>
    <navbar
      type="primary"
      position="fixed"
      :transparent="transparent"
      :color-on-scroll="300"
      menu-classes="ml-auto"
    >
      <template>
        <router-link class="navbar-brand" :to="isLoggedIn ? '/main/' : '/'">
          <img src="@/assets/CO_OK-logo.png" width="40px" />
        </router-link>
      </template>
      <template slot="navbar-menu">
        <li class="nav-item">
          <router-link class="nav-link" :to="{ name: isLoggedIn ? 'Main' : 'Home'}">
            <i class="now-ui-icons shopping_shop"></i>
            <p>홈</p>
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to="{ name: 'PrevRecipeList' }">
            <i class="now-ui-icons education_paper"></i>
            <p>레시피</p>
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to="{ name: 'ClubListView', params: {pageNum: 1} }">
            <i class="now-ui-icons users_circle-08"></i>
            <p>소모임</p>
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to="{ name: 'VersusHomeView' }">
            <i class="now-ui-icons users_circle-08"></i>
            <p>요리대전</p>
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" v-if="isLoggedIn" :to="{ name: 'BlogHomeView' }">
            <i class="now-ui-icons users_circle-08"></i>
            <p>블로그</p>
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" v-if="isLoggedIn" @click="logout">
            <p>로그아웃</p>
            <p></p>
          </a>
          <a class="nav-link" v-if="!isLoggedIn">
            <LoginModal />
          </a>
        </li>
      </template>
    </navbar>
    <!-- <b-nav-item :to="/versus/">요리대전</b-nav-item> -->
    <!-- <b-nav-item :to="{ name: 'ClubListView', params: { pageNum: 1 } }">소모임</b-nav-item> -->
  </div>
</template>

<script>
import { Navbar } from "@/components/global";
import { mapGetters, mapActions } from "vuex";
import LoginModal from "../accounts/LoginModal.vue";

export default {
  name: "Header",
  props: {
    transparent: Boolean,
  },
  components: {
    LoginModal,
    Navbar,
  },
  computed: {
    ...mapGetters(["isLoggedIn"]),
  },
  watch: {},
  created() {},
  methods: {
    ...mapActions(["logout"]),
  },
  data() {
    return {
      keyword: "",
    };
  },
};
</script>

<style scoped></style>
