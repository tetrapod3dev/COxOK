<template>
  <div class="wrapper landing-page">
    <div class="page-header page-header-mini header-filter" filter-color="black">
      <parallax
        class="page-header-image"
        :style="{ backgroundImage: 'url(http://i3a104.p.ssafy.io/header/mypage.jpg)' }"
      ></parallax>
      <div class="container">
        <div class="photo-container">
          <img :src="profileImage" alt />
        </div>
        <h2 class="title">회원 정보 수정</h2>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="button-container">
          <button class="learn-more" @click="goBackPage">
            <i class="fas fa-undo-alt"></i>
            <p class="d-none d-sm-block">뒤로</p>
          </button>
          <button class="learn-more" @click="isChangingNickname ? notChecked : updateUserInfo()">
            <i class="fas fa-user-edit"></i>
            <p class="d-none d-sm-block">정보수정</p>
          </button>
          <button @click="withdrawal" class="learn-more">
            <i class="fas fa-user-slash"></i>
            <p class="d-none d-sm-block">회원탈퇴</p>
          </button>
          <button class="learn-more" @click="clickInputProfilePhoto">
            <i class="far fa-edit"></i>
            <p class="d-none d-sm-block">사진수정</p>
          </button>
          <input hidden id="input-form-profile" type="file" @change="changeProfilePhoto" />
          <button class="learn-more" @click="removeProfile">
            <i class="fas fa-eraser"></i>
            <p class="d-none d-sm-block">사진삭제</p>
          </button>
        </div>
        <div class="row">
          <!-- 회원 가입 form start -->
          <div class="col-lg-6 text-center ml-auto mr-auto col-md-8 update-user-form-group">
            <p class="title">회원정보</p>
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
          <p class="title">선호 카테고리</p>
          <!-- 회원 가입 form start -->
          <div class="col-lg-8 mr-auto ml-auto col-md-10 row">
            <div
              v-for="foodCategory in foodCategories"
              :key="foodCategory.foodCategoryId"
              @click="checkCategory(foodCategory.foodCategoryId)"
              class="col-2 text-left text-nowrap"
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
      user: { profilePhoto: "dochi.png" },
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
        if (err.response.status == 401) {
          alert("로그인 정보가 만료되었습니다! 다시 로그인해주세요.");
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
          .catch((err) => {
            if (err.response.status == 401) {
              alert("로그인 정보가 만료되었습니다! 다시 로그인해주세요.");
              this.logout();
            }
          });
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
          if (err.response.status == 401) {
            alert("로그인 정보가 만료되었습니다! 다시 로그인해주세요.");
            this.logout();
          }
        });
      this.$router.push("/blog/");
    },
    clickInputProfilePhoto() {
      document.getElementById("input-form-profile").click();
    },
    goBackPage() {
      this.$router.go(-1);
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
button {
  position: relative;
  display: inline-block;
  cursor: pointer;
  outline: none;
  border: 0;
  vertical-align: middle;
  text-decoration: none;
}
button.learn-more {
  font-weight: 600;
  height: 60px;
  color: #382b22;
  text-transform: uppercase;
  padding: 0.3em 1.5em;
  background: #f2efe4;
  border: 2px solid #b69f81;
  border-left: 0;
  border-radius: 0;
  -webkit-transform-style: preserve-3d;
  transform-style: preserve-3d;
  -webkit-transition: background 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: background 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    background 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    background 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more:first-child {
  border-top-left-radius: 0.75em;
  border-bottom-left-radius: 0.75em;
  padding-left: 2em;
  border-left: 2px solid #b69f81;
}
button.learn-more:last-child {
  border-top-right-radius: 0.75em;
  border-bottom-right-radius: 0.75em;
  padding-right: 2em;
}
button.learn-more::before {
  position: absolute;
  content: "";
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #f2d4ae;
  border-radius: inherit;
  box-shadow: 0 0 0 2px #b69f81, 0 0.625em 0 0 #f2f0ce;
  -webkit-transform: translate3d(0, 0.75em, -1em);
  transform: translate3d(0, 0.75em, -1em);
  -webkit-transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    box-shadow 150ms cubic-bezier(0, 0, 0.58, 1),
    -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more:hover {
  background: #f2efe4;
  -webkit-transform: translate(0, 0.25em);
  transform: translate(0, 0.25em);
}
button.learn-more:hover::before {
  box-shadow: 0 0 0 2px #b69f81, 0 0.5em 0 0 #f2f0ce;
  -webkit-transform: translate3d(0, 0.5em, -1em);
  transform: translate3d(0, 0.5em, -1em);
}
button.learn-more:active {
  background: #f2efe4;
  -webkit-transform: translate(0em, 0.75em);
  transform: translate(0em, 0.75em);
}
button.learn-more:active::before {
  box-shadow: 0 0 0 2px #b69f81, 0 0 #f2f0ce;
  -webkit-transform: translate3d(0, 0, -1em);
  transform: translate3d(0, 0, -1em);
}
</style>
