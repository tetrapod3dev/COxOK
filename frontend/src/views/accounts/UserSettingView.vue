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
          <img :src="profileImage">
          <input type="file" @change="changeProfilePhoto" />
          <button @click="removeProfile">프로필 없애기</button>
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
          <section class="my-5 row">
            <h3 class="col-3 offset-2">자기 소개글</h3>
            <textarea class="col-8 offset-2" rows="5" cols="40" v-model="user.detail"></textarea>
          </section>

          <div class="row">
            <div v-for="foodCategory in foodCategories" :key="foodCategory.foodCategoryId" @click="checkCategory(foodCategory.foodCategoryId)" class="col-2 text-left">
              <input type="checkbox" v-model="checker[foodCategory.foodCategoryId]" class="align-self-center my-2"> {{ foodCategory.foodCategoryName }}
            </div>
          </div>

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
      foodCategories: [],
      user: {},
      currentNickname: null,
      passwordConfirm: null,
      selectedCategory: [],
    };
  },
  components: {
    // BlogMenu,
  },
  created() {
    axios
      .get(SERVER.URL + SERVER.ROUTES.myPage, {
        headers: { Authorization: this.config },
      })
      .then((res) => {
        this.user = res.data.user
        this.foodCategories = res.data.categories
        this.selectedCategory = res.data.userFavoriteCategories
        this.currentNickname = res.data.user.nickname
        this.user.password = null
      })
      .catch((err) => {
        if (err.response.status) {
          alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
          this.logout()
        }});
  },
  computed: {
    checkPassword() {
      return this.user.password == this.passwordConfirm ? true : false;
    },
    isChangingNickname() {
      return this.user.nickname != this.currentNickname ? true : false;
    },
    ...mapGetters(["config"]),
    profileImage() {
      return SERVER.IMAGE_URL + this.user.profilePhoto
    },
    checker() {
      let tempChecker = {};
      const self = this;

      this.foodCategories.forEach(function (category) {
        if (self.selectedCategory.indexOf(category.foodCategoryId) >= 0) {
          tempChecker[category.foodCategoryId] = true;
        } else {
          tempChecker[category.foodCategoryId] = false;
        }
      });
      return tempChecker;
    },
  },
  methods: {
    ...mapActions(["logout"]),
    changeProfilePhoto(event) {
      const newProfilePhoto = event.target.files[0]
      console.log(newProfilePhoto)

      let frm = new FormData();
      const self = this
      frm.append("photo", newProfilePhoto);
    // formData를 API에 전달해서 src 주소를 받습니다..
      // 그리고 그걸 저장합니다.
      axios
        .post(SERVER.URL + SERVER.ROUTES.photoRegister, frm, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          self.user.profilePhoto = res.data.photo[0]
        })
        .catch((err) => {
          console.log(err);
        });

    },
    removeProfile() {
      this.user.profilePhoto = 'dochi.png'
    },
    checkCategory(id) {
      if (this.selectedCategory.indexOf(id) < 0) {
        this.selectedCategory.push(id)
      } else {
        this.selectedCategory.splice(this.selectedCategory.indexOf(id), 1)
      }
    },
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

      let data = {
        "detail": this.user.detail,
        "email": this.user.email,
        "foodCategoryId": this.selectedCategory,
        "nickname": this.user.nickname,
        "password": this.user.password,
        "profilePhoto": this.user.profilePhoto,
        "userId": this.user.userId
      }
      axios
        .put(SERVER.URL + SERVER.ROUTES.update, data, {
          headers: { Authorization: this.config },
        })
        .then((res) => {
          if (res.data.status == "success") {
            alert("정보 수정 완료");
          }
        })
        .catch((err) => {
          if (err.response.status) {
            alert('세션 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
  },
};
</script>
<style></style>
