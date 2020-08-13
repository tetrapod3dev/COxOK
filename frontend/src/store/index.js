import Vue from "vue";
import Vuex from "vuex";

import cookies from 'vue-cookies' // cookie 사용(토큰 저장)
import router from "@/router";
import axios from "axios";

import SERVER from "@/api/api";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    // authToken: localStorage.getItem("auth-token"),
    authToken: cookies.get('auth-token'),
    // 레시피 검색 시 사용하는 searchData입니다.
    searchData: {
      selectedCategory: [],
      selectedIngredients: [],
      selectedIngredientsName: [],
    },
    mainType: '오프라인',
  },
  getters: {
    isLoggedIn(state) {
      return !!state.authToken;
    },
    config: (state) => `Bearer ${state.authToken}`,
    searchingData: (state) => state.searchData,
    mainClubType: (state) => state.mainType,
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.authToken = token;
      // localStorage.setItem("auth-token", token);
      cookies.set('auth-token', token, 60 * 60 * 2)
      // state의 authToken을 바꾸고, 이왕 한 김에 localStorage에서도 갱신
    },
    setSearchData(state, searchData) {
      state.searchData = searchData;
      // 레시피 검색에서 사용하는 searchData를 변경합니다..
    },
    setMainClubType(state, mainType) {
      state.mainType = mainType
    }
  },
  actions: {
    // 회원 관리 개념
    postAuthData({ commit }, info) {
      axios
        .post(SERVER.URL + info.location, info.data)
        .then((res) => {
          if (info.name == "signup") {
            alert("회원 가입에 성공했습니다! 이메일을 확인해주세요.");
          } else {
            commit("SET_TOKEN", res.data.token);
          }
          router.push({ name: "Main" });
        })
        .catch((err) => {
          console.log(err.response);
          if (err.response.data.msg == "이메일 인증 미완료") {
            let answer = confirm(
              "이메일 미인증 사용자입니다. 이메일을 재전송할까요?"
            );
            if (answer) {
              alert("아쉽게도 이메일 인증은 미구현입니다!");
            }
          } else {
            alert(err.response.data.msg);
            console.log(err.response);
          }
        });
    },

    signup({ dispatch }, signupData) {
      const info = {
        data: signupData,
        location: SERVER.ROUTES.signup,
        name: "signup",
      };
      dispatch("postAuthData", info);
    },

    login({ dispatch }, loginData) {
      const info = {
        data: loginData,
        location: SERVER.ROUTES.login,
      };
      dispatch("postAuthData", info);
    },

    logout({ commit }) {
      commit("SET_TOKEN", null);
      // localStorage.removeItem("auth-token");
      cookies.remove('auth-token')
      router.push({ name: "Home" });
    },

    changeSearchData({ commit }, searchData) {
      commit("setSearchData", searchData);
    },

    changeClubMainType({ commit }, mainType) {
      commit("setMainClubType", mainType)
    }
  },

  modules: {},
});
