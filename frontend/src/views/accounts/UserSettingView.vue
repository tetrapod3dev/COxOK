<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.pexels.com/photos/406152/pexels-photo-406152.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260') ;"
      ></parallax>
      <div class="content-center">
        <h1>회원정보</h1>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <!-- <BlogMenu /> -->
        <div class="col-12">
          <!-- <div class="mt-5 row">
        <label for="id_profile_image">프로필 사진:</label>
        <img :src="userimage" />
        <input type="file" name="profile_image" class="form-control-file" title id="id_userimage" />
          </div>-->
          <div class="mt-5 row">
            <label for="email" class="col-2 offset-1">이메일:</label>
            <input v-model="user.email" class="col-5" id="email" type="text" readonly />
          </div>

          <div class="mt-5 row">
            <label for="nickname" class="col-2 offset-1">닉네임:</label>
            <input v-model="user.nickname" class="col-5" id="nickname" type="text" />
            <button
              class="btn btn-outline-primary col-2"
              v-show="isChangingNickname"
              @click="checkNickname"
            >중복 확인</button>
          </div>

          <div class="mt-5 row">
            <label for="password" class="col-2 offset-1">비밀번호:</label>
            <input v-model="user.password" class="col-5" id="password" type="password" />
          </div>

          <div class="mt-5 row">
            <label for="passwordConfirm" class="col-2 offset-1">비밀번호 확인:</label>
            <input v-model="passwordConfirm" class="col-5" id="passwordConfirm" type="password" />
          </div>
          <p v-if="!checkPassword">비밀번호가 일치하지 않습니다.</p>
          <!-- 
      <div class="mt-5 row">
        <label>선호하는 카테고리</label>
        <div class="checkbox" id="id_food_category">
          <div class="form-check col" v-for="food in foodCategory" :key="food.food_category_id">
            <label class="form-check-label" :for="'id_food_category_' + food.food_category_id">
              <input
                checked
                class="form-check-input"
                :id="'id_food_category_' + food.food_category_id"
                name="food_category"
                title
                type="checkbox"
                :value="food.food_category_id"
              />
              {{ food.food_category_name }}
            </label>
          </div>
        </div>
          </div>-->

          <div v-if="isChangingNickname" class="mt-5 row justify-content-center">
            <button class="btn" @click="notChecked">정보 수정</button>
          </div>

          <div v-else class="mt-5 row justify-content-center">
            <button class="btn btn-outline-danger" @click="updateUserInfo()">정보 수정</button>
          </div>

          <button @click="withdrawal" class="btn btn-danger">회원 탈퇴</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

import SERVER from "@/api/api";

// import BlogMenu from "@/components/blog/BlogMenu.vue";

import { mapGetters, mapActions } from "vuex";
export default {
  name: "UserSettingView",
  data() {
    return {
      foodCategory: [
        { food_category_id: 1, food_category_name: "한식 요리" },
        { food_category_id: 2, food_category_name: "중식 요리" },
        { food_category_id: 3, food_category_name: "일식 요리" },
        { food_category_id: 4, food_category_name: "동남아 요리" },
        { food_category_id: 5, food_category_name: "인도 요리" },
        { food_category_id: 6, food_category_name: "멕시칸 요리" },
        { food_category_id: 7, food_category_name: "양식 요리" },
        { food_category_id: 8, food_category_name: "퓨전 요리" },
        { food_category_id: 11, food_category_name: "육류 요리" },
        { food_category_id: 12, food_category_name: "채소류 요리" },
        { food_category_id: 13, food_category_name: "찜/조림/구이 요리" },
        { food_category_id: 14, food_category_name: "볶음/튀김/부침 요리" },
        { food_category_id: 15, food_category_name: "나물/샐러드 요리" },
        { food_category_id: 16, food_category_name: "베이킹/디저트 요리" },
        { food_category_id: 17, food_category_name: "면 요리" },
        { food_category_id: 18, food_category_name: "음료/차/커피" },
        { food_category_id: 19, food_category_name: "김치 요리" },
        { food_category_id: 20, food_category_name: "해산물 요리" },
        { food_category_id: 21, food_category_name: "밥 요리" },
        { food_category_id: 22, food_category_name: "국물 요리" },
      ],
      user: {
        email: "",
        password: "",
        nickname: "",
      },
      currentNickname: "",
      passwordConfirm: "",
      userimage:
        "https://images.vexels.com/media/users/3/175535/isolated/lists/7206865561bf45af453fa84335297977-man-fringe-posture-flat-person.png",
    };
  },
  components: {
    // BlogMenu,
  },
  created() {
    axios
      .get(SERVER.URL + SERVER.ROUTES.myPage, {
        headers: { Authorization: this.$store.getters.config },
      })
      .then((res) => {
        this.user.email = res.data.user.email;
        this.user.nickname = res.data.user.nickname;
        this.currentNickname = this.user.nickname;
      })
      .catch((err) => {
        console.log(err);
      });
  },
  computed: {
    checkPassword() {
      return this.user.password == this.passwordConfirm ? true : false;
    },
    isChangingNickname() {
      return this.user.nickname != this.currentNickname ? true : false;
    },
    ...mapGetters(["config"]),
  },
  methods: {
    ...mapActions(["logout"]),
    withdrawal() {
      const self = this;
      let answer = confirm("정말로 탈퇴하실건가요..?");
      if (answer) {
        axios
          .delete(SERVER.URL + SERVER.ROUTES.withdrawal, {
            headers: {
              Authorization: this.config,
            },
          })
          .then((res) => {
            if (res.data.status == "success") {
              alert("회원 탈퇴에 성공했습니다!");
              self.logout();
            }
          })
          .catch((err) => console.log(err.response));
      } else {
        alert("회원 탈퇴를 취소했습니다!");
      }
    },
    checkNickname() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.checkNickname + this.user.nickname)
        .then((res) => {
          if (res.data.status == "success") {
            alert("사용가능한 닉네임 입니다.");
            this.currentNickname = this.user.nickname;
          }
        })
        .catch((err) => {
          alert("중복된 닉네임 입니다.");
          console.log(err);
        });
    },
    notChecked() {
      alert("닉네임 중복확인 필요");
    },
    updateUserInfo() {
      if (this.user.password == null || this.user.password == "") {
        alert("비밀번호를 입력하세요");
        return;
      }
      if (!this.checkPassword) {
        alert("비밀번호를 확인하세요");
        return;
      }
      axios
        .put(SERVER.URL + SERVER.ROUTES.update, this.user, {
          headers: { Authorization: this.config },
        })
        .then((res) => {
          if (res.data.status == "success") {
            alert("정보 수정 완료");
          }
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>
<style></style>
