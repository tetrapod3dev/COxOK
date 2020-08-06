<template>
  <div class="wrapper landing-page">
    <div class="page-header page-header-mini header-filter" filter-color="black">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.unsplash.com/photo-1505576399279-565b52d4ac71?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80')"
      ></parallax>
      <div class="container">
        <div class="photo-container">
          <img :src="profileImage" alt />
        </div>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="button-container">
          <button class="btn btn-primary btn-round btn-lg" @click="clickInputProfilePhoto">프로필 변경</button>
          <input hidden id="input-form-profile" type="file" @change="changeProfilePhoto" />
          <button class="btn btn-danger btn-round btn-lg" @click="removeProfile">프로필 삭제</button>
        </div>
        <h2 class="title">회원 정보 수정</h2>
        <div class="row">
          <!-- 회원 가입 form start -->
          <div class="col-lg-6 text-center ml-auto mr-auto col-md-8 update-user-form-group">
            <b-form-input
              v-model="user.email"
              id="email"
              class="col-12"
              type="text"
              placeholder="이메일"
              readonly
            />
            <br />
            <!-- 닉네임 input -->
            <div class="input-lg ml-auto mr-auto row">
              <b-form-input
                v-model="user.nickname"
                id="signup-nickname"
                :class="!isChangingNickname ? 'col-12' : 'col-10'"
                type="text"
                placeholder="닉네임"
                :state="!isChangingNickname ? true : null"
              />
              <b-button
                class="check-btn col-2"
                lg="2"
                v-show="isChangingNickname"
                @click="checkNickname"
                pill
                variant="primary"
                size="sm"
              >체크</b-button>
            </div>
            <br />
            <!-- 비밀번호 input -->
            <b-form-input
              v-model="user.password"
              class="col-12"
              id="signup-password"
              type="password"
              placeholder="비밀번호"
            />
            <br />
            <!-- 비밀번호 확인 input -->
            <b-form-input
              v-model="passwordConfirm"
              class="col-12"
              id="signup-passwordConfirm"
              type="password"
              placeholder="비밀번호 확인"
              :state="checkPassword"
            />
            <br />
            <div class="textarea-container">
              <p class="title">자기 소개</p>
              <textarea
                class="form-control"
                rows="4"
                cols="80"
                v-model="user.detail"
                placeholder="자기 소개"
              ></textarea>
            </div>
            <br />
            <br />
          </div>
        </div>
        <div class="container">
          <!-- 회원 가입 form start -->
          <div class="col-lg-8 mr-auto ml-auto col-md-10 row">
            <div
              v-for="foodCategory in foodCategories"
              :key="foodCategory.foodCategoryId"
              @click="checkCategory(foodCategory.foodCategoryId)"
              class="col-2 text-left"
            >
              <input
                type="checkbox"
                v-model="checker[foodCategory.foodCategoryId]"
                class="align-self-center my-2"
              />
              {{ foodCategory.foodCategoryName }}
            </div>
          </div>
        </div>
        <div class="col-12">
          <!-- <div class="mt-5 row">
        <label for="id_profile_image">프로필 사진:</label>
        <img :src="userimage" />
        <input type="file" name="profile_image" class="form-control-file" title id="id_userimage" />
          </div>-->
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

          <div class="mt-5 row justify-content-center">
            <button
              :class="isChangingNickname? 'btn' : 'btn btn-outline-danger'"
              @click="isChangingNickname ? notChecked : updateUserInfo()"
            >정보 수정</button>
            <button @click="withdrawal" class="btn btn-danger">회원 탈퇴</button>
          </div>
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
  bodyClass: "profile-page",
  data() {
    return {
      foodCategories: [],
      user: {},
      oldNickname: null,
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
        this.user = res.data.user;
        this.foodCategories = res.data.categories;
        this.selectedCategory = res.data.userFavoriteCategories;
        this.oldNickname = res.data.user.nickname;
        this.currentNickname = res.data.user.nickname;
        this.user.password = null;
      })
      .catch((err) => {
        if (err.response.status) {
          alert("세션 정보가 만료되었습니다! 다시 로그인해주세요.");
          this.logout();
        }
      });
  },
  computed: {
    checkPassword() {
      if (this.user.password == "" || this.user.password == null) {
        return null;
      }
      return this.user.password == this.passwordConfirm ? true : false;
    },
    isChangingNickname() {
      if (this.user.nickname == this.oldNickname) {
        return false;
      }
      if (this.user.nickname == this.currentNickname) {
        return false;
      }
      return true;
    },
    ...mapGetters(["config"]),
    profileImage() {
      return SERVER.IMAGE_URL + this.user.profilePhoto;
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
      const newProfilePhoto = event.target.files[0];
      console.log(newProfilePhoto);

      let frm = new FormData();
      const self = this;
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
          self.user.profilePhoto = res.data.photo[0];
        })
        .catch((err) => {
          console.log(err);
        });
    },
    removeProfile() {
      this.user.profilePhoto = "dochi.png";
    },
    checkCategory(id) {
      if (this.selectedCategory.indexOf(id) < 0) {
        this.selectedCategory.push(id);
      } else {
        this.selectedCategory.splice(this.selectedCategory.indexOf(id), 1);
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
        detail: this.user.detail,
        email: this.user.email,
        foodCategoryId: this.selectedCategory,
        nickname: this.user.nickname,
        password: this.user.password,
        profilePhoto: this.user.profilePhoto,
        userId: this.user.userId,
      };
      axios
        .put(SERVER.URL + SERVER.ROUTES.update, data, {
          headers: { Authorization: this.config },
        })
        .then((res) => {
          if (res.data.status == "success") {
            alert("정보 수정 완료");
            this.oldNickname = data.nickname;
          }
        })
        .catch((err) => {
          if (err.response.status) {
            alert("세션 정보가 만료되었습니다! 다시 로그인해주세요.");
            this.logout();
          }
        });
      this.$router.push("/blog/");
    },
    clickInputProfilePhoto() {
      document.getElementById("input-form-profile").click();
    },
  },
};
</script>
<style scoped>
.profile-page .photo-container img {
  height: 100px;
}

.update-user-form-group .check-btn {
  margin: 0px 0px 10px;
}
</style>
