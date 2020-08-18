<template>
  <div class="wrapper landing-page">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        :style="{ backgroundImage: 'url(\'' + require('@/assets/mypage.jpg') + '\')' }"
      ></parallax>
      <div class="content-center">
        <h1>회원가입</h1>
      </div>
    </div>
    <div class="section section-contact-us text-center">
      <div class="container">
        <p class="description">회원가입을 위에 아래에 내용을 기입해 주세요</p>
        <div class="row">
          <!-- 회원 가입 form start -->
          <div class="col-lg-6 text-center ml-auto mr-auto col-md-8 signup-form-group">
            <!-- 이메일 input -->
            <div class="input-lg ml-auto mr-auto row">
              <b-form-input
                v-model="signupData.email"
                id="signup-email"
                :class="isNotDupEmail ? 'col-12' : 'col-10'"
                type="text"
                placeholder="이메일"
                :state="isNotDupEmail ? true : null"
                @keydown="returnEmail"
              />
              <b-button
                class="check-btn"
                lg="2"
                v-show="!isNotDupEmail"
                @click="checkEmail"
                pill
                variant="primary"
                size="sm"
              >인증</b-button>
            </div>
            <p v-show="emailMessage" v-text="emailMessage" class="text-center"></p>
            <br />
            <!-- 닉네임 input -->
            <div class="input-lg ml-auto mr-auto row">
              <b-form-input
                v-model="signupData.nickname"
                id="signup-nickname"
                :class="isNotDupNickname ? 'col-12' : 'col-10'"
                type="text"
                placeholder="닉네임"
                :state="isNotDupNickname ? true : null"
                @keydown="returnNickname"
              />
              <b-button
                class="check-btn"
                lg="2"
                v-show="!isNotDupNickname"
                @click="checkNickname"
                pill
                variant="primary"
                size="sm"
              >체크</b-button>
            </div>
            <br />
            <!-- 비밀번호 input -->
            <b-form-input
              v-model="signupData.password"
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
            <div class="mt-5 row justify-content-center">
              <b-button
                pill
                :class="isValid ? 'btn btn-danger' : 'btn'"
                @click="isValid ? signup(signupData) : notChecked"
              >회원 가입</b-button>
            </div>
          </div>
          <!-- 회원 가입 form end -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import axios from "axios";

import SERVER from "@/api/api";

import { Button } from "@/components/global";

function checktest(str) {
  var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
  return regExp.test(str) ? true : false;
}

export default {
  name: "SignupView",
  bodyClass: "landing-page",
  data() {
    return {
      signupData: {
        email: null,
        nickname: null,
        password: "",
      },
      passwordConfirm: "",
      isNotDupEmail: false,
      isNotDupNickname: null,
      emailMessage: null,
      showDismissibleAlert: false,
    };
  },
  components: {
    [Button.name]: Button,
  },
  computed: {
    checkPassword() {
      if (this.signupData.password === "") {
        return null;
      }

      return this.signupData.password == this.passwordConfirm ? true : false;
    },
    isValid() {
      return (
        this.isNotDupEmail &&
        this.isNotDupNickname &&
        this.checkPassword &&
        this.signupData.password != ""
      );
    },
  },
  methods: {
    ...mapActions(["signup"]),
    checkEmail() {
      if (checktest(this.signupData.email)) {
        this.emailMessage = null;
        axios
          .get(SERVER.URL + SERVER.ROUTES.checkEmail + this.signupData.email)
          .then((res) => {
            if (res.data.msg == "성공") {
              this.isNotDupEmail = true;
            }
          })
          .catch((err) => {
            if (err.response.data.msg == "이메일 중복") {
              alert("이미 존재하는 이메일입니다!");
            }
          });
      } else {
        this.emailMessage = "올바르지 않은 이메일 형식입니다.";
      }
    },
    checkNickname() {
      axios
        .get(
          SERVER.URL + SERVER.ROUTES.checkNickname + this.signupData.nickname
        )
        .then((res) => {
          if (res.data.msg == "성공") {
            this.isNotDupNickname = true;
          }
        })
        .catch((err) => {
          console.log(err.response);
          if (err.response.data.msg == "이메일 중복") {
            alert("이미 존재하는 닉네임입니다!");
          }
        });
    },
    returnEmail() {
      this.isNotDupEmail = false;
    },
    returnNickname() {
      this.isNotDupNickname = false;
    },
    notChecked() {
      alert("아이디와 닉네임의 중복을 확인해주세요!");
    },
  },
};
</script>

<style scoped>
.signup-form-group .check-btn {
  margin: 0px 0px 10px;
}
</style>