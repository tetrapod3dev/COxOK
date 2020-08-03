<template>
  <div class="wrapper">
    <div class="page-header page-header-mini">
      <parallax
        class="page-header-image"
        style="background-image: url('https://images.pexels.com/photos/406152/pexels-photo-406152.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260') ;"
      ></parallax>
      <div class="content-center">
        <h1>회원가입</h1>
      </div>
    </div>

    <div class="section section-about-us">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto text-center">
            <div class="row">
              <fg-input
                v-model="signupData.email"
                id="signup-email"
                class="col-8"
                type="text"
                placeholder="이메일"
                @keydown="returnEmail"
              />
              <a v-show="!isNotDupEmail" @click="checkEmail">
                <n-button type="primary" round block>중복 확인</n-button>
              </a>
            </div>
            <p v-show="emailMessage" v-text="emailMessage" class="text-center"></p>

            <div class="row">
              <fg-input
                v-model="signupData.nickname"
                id="signup-nickname"
                class="col-8"
                type="text"
                placeholder="닉네임"
                @keydown="returnNickname"
              />
              <a v-show="!isNotDupNickname" @click="checkNickname">
                <n-button type="primary" round block>중복 확인</n-button>
              </a>
            </div>

            <div class="row">
              <fg-input
                v-model="signupData.password"
                id="signup-password"
                class="col-8"
                type="password"
                placeholder="비밀번호"
              />
            </div>

            <div class="row">
              <fg-input
                v-model="passwordConfirm"
                class="col-8"
                id="signup-passwordConfirm"
                type="password"
                placeholder="비밀번호 확인"
              />
            </div>
            <p v-if="!checkPassword">비밀번호가 일치하지 않습니다.</p>

            <div v-if="isValid" class="mt-5 row justify-content-center">
              <button class="btn btn-danger" @click="signup(signupData)">회원 가입</button>
            </div>

            <div v-else class="mt-5 row justify-content-center">
              <button class="btn" @click="notChecked">회원 가입</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import axios from "axios";

import SERVER from "@/api/api";

import { Button, FormGroupInput as FgInput } from "@/components/global";

function checktest(str) {
  var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
  return regExp.test(str) ? true : false;
}

export default {
  name: "SignupView",
  data() {
    return {
      signupData: {
        email: null,
        nickname: null,
        password: "",
      },
      passwordConfirm: "",
      isNotDupEmail: false,
      isNotDupNickname: false,
      emailMessage: null,
      showDismissibleAlert: false,
    };
  },
  components: {
    FgInput,
    [Button.name]: Button,
  },
  computed: {
    checkPassword() {
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

<style>
</style>