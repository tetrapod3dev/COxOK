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
        <p class="description">회원가입을 위해 아래에 내용을 기입해 주세요</p>
        <div class="row">
          <!-- 회원 가입 form start -->
          <div class="col-lg-6 text-center ml-auto mr-auto col-md-8 signup-form-group">
            <!-- 이메일 input -->
            <div class="input-lg ml-auto mr-auto row">
              <div class="versus-name col-10 row">
                <label for="nickname" class="inp">
                  <input type="text" id="nickname" placeholder=" " 
                  v-model="signupData.email" :class="isNotDupEmail ? 'col-12' : 'col-10'" 
                  :state="isNotDupEmail ? true : null" @keydown="returnEmail">
                  <span class="label">이메일</span>
                  <span class="focus-bg"></span>
                </label>
              </div>
              <b-button
                class="check-btn col-2"
                lg="2"
                v-show="!isNotDupEmail"
                @click="checkEmail"
                pill
                variant="primary"
                size="sm"
                style="height:30px; top:20px;"
              >체크</b-button>
            </div>
            <p v-show="emailMessage" v-text="emailMessage" class="text-center"></p>
            <br />
            <!-- 닉네임 input -->
            <div class="input-lg ml-auto mr-auto row">
              <div class="versus-name col-10 row">
                <label for="nickname" class="inp">
                  <input type="text" id="nickname" placeholder=" " 
                  v-model="signupData.nickname" :class="isNotDupNickname ? 'col-12' : 'col-10'" 
                  :state="isNotDupNickname ? true : null" @keydown="returnNickname">
                  <span class="label">닉네임</span>
                  <span class="focus-bg"></span>
                </label>
              </div>
              <b-button
                class="check-btn col-2"
                lg="2"
                v-show="!isNotDupNickname"
                @click="checkNickname"
                pill
                variant="primary"
                size="sm"
                style="height:30px; top:20px;"
              >체크</b-button>
            </div>
            <br />
            <!-- 비밀번호 input -->
            <!-- <b-form-input
              v-model="signupData.password"
              class="col-12"
              id="signup-password"
              type="password"
              placeholder="비밀번호"
            /> -->
            <div class="versus-name">
              <label for="password" class="inp">
                <input type="password" id="password" placeholder=" " 
                v-model="signupData.password" class="col-12">
                <span class="label">비밀번호</span>
                <span class="focus-bg"></span>
              </label>
            </div>
            <br />
            <!-- 비밀번호 확인 input -->
            <!-- <b-form-input
              v-model="passwordConfirm"
              class="col-12 inp"
              id="signup-passwordConfirm"
              type="password"
              placeholder="비밀번호 확인"
              :state="checkPassword"
            /> -->
            <div class="versus-name">
              <label for="passwordConfirm" class="inp">
                <input type="password" id="passwordConfirm" placeholder=" " 
                v-model="passwordConfirm" class="col-12" :state="checkPassword">
                <span class="label">비밀번호 확인</span>
                <span class="focus-bg"></span>
              </label>
            </div>
            <div class="mt-5 row justify-content-center">
              <b-button v-if="isValid" class="btn btn-danger" pill @click="signup(signupData)">
                회원 가입
              </b-button>
              <b-button v-if="!isValid" class="btn" pill @click="notChecked">
                회원 가입
              </b-button>
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
      alert("아이디와 닉네임의 중복을 확인해주세요! 또는 비밀번호가 일치하지 않습니다.");
    },
  },
};
</script>

<style scoped>
.signup-form-group .check-btn {
  margin: 0px 0px 10px;
}

/* 입력 창 */
.versus-name {
  -webkit-text-size-adjust: 100%;
  -webkit-font-smoothing: antialiased;
}

.inp {
  position: relative;
  margin: auto;
  width: 100%;
  max-width: 700px;
  border-radius: 3px;
  overflow: hidden;
  box-sizing: border-box;
}
.inp .label {
  position: absolute;
  top: 20px;
  left: 12px;
  font-size: 16px;
  color: rgba(0, 0, 0, 0.5);
  font-weight: 500;
  -webkit-transform-origin: 0 0;
          transform-origin: 0 0;
  -webkit-transform: translate3d(0, 0, 0);
          transform: translate3d(0, 0, 0);
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
  pointer-events: none;
}
.inp .focus-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: white;
  z-index: -1;
  -webkit-transform: scaleX(0);
          transform: scaleX(0);
  -webkit-transform-origin: left;
          transform-origin: left;
}
.inp input {
  -webkit-appearance: none;
  -moz-appearance: none;
       appearance: none;
  width: 100%;
  border: 0;
  font-family: inherit;
  padding: 16px 12px 0 12px;
  height: 56px;
  font-size: 16px;
  font-weight: 400;
  background: white;
  box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.3);
  color: #000;
  -webkit-transition: all 0.15s ease;
  transition: all 0.15s ease;
}
.inp input:hover {
  background: white;
  box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.5);
}
.inp input:not(:placeholder-shown) + .label {
  color: rgba(0, 0, 0, 0.5);
  -webkit-transform: translate3d(0, -12px, 0) scale(0.75);
          transform: translate3d(0, -12px, 0) scale(0.75);
}
.inp input:focus {
  background: white;
  outline: none;
  box-shadow: inset 0 -2px 0 #0077FF;
}
.inp input:focus + .label {
  color: #0077FF;
  -webkit-transform: translate3d(0, -12px, 0) scale(0.75);
          transform: translate3d(0, -12px, 0) scale(0.75);
}
.inp input:focus + .label + .focus-bg {
  -webkit-transform: scaleX(1);
          transform: scaleX(1);
  -webkit-transition: all 0.1s ease;
  transition: all 0.1s ease;
}
</style>