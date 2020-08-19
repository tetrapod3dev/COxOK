<template>
  <div>
    <div @click="showModal" class="custom-cursor">
      <i :class="icon"></i>
      <span class="no-icon" @click="showModal">로그인</span>
    </div>

    <div class="modal" id="login-modal">
      <div class="modal-dialog modal-login">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">로그인</h4>
            <button type="button" class="close" @click="hideModal" aria-hidden="true">&times;</button>
          </div>

          <div class="modal-body">
            <div class="form-group">
              <div class="input-group">
                <span class="input-group-addon">
                  <i class="fas fa-user"></i>
                </span>
                <input
                  class="form-control"
                  placeholder="이메일"
                  v-model="loginData.email"
                  id="email"
                  type="text"
                />
              </div>
            </div>
            <div class="form-group">
              <div class="input-group">
                <span class="input-group-addon">
                  <i class="fas fa-lock"></i>
                </span>
                <input
                  class="form-control"
                  placeholder="비밀번호"
                  v-model="loginData.password"
                  id="password"
                  type="password"
                  @keydown.enter.prevent="login(loginData)"
                />
              </div>
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-block btn-lg" @click.prevent="login(loginData)">로그인</button>
            </div>
          </div>

          <!-- <div class="row">
            <i class="fas fa-3x fa-user-circle col-1 offset-8"></i>
            <i class="fas fa-3x fa-user-circle col-1"></i>
            <i class="fas fa-3x fa-user-circle col-1"></i>
          </div>-->

          <div class="modal-footer">
            <p>아직 회원이 아니신가요?</p>
            <span @click="hideModal" class="custom-cursor">
              <router-link to="/accounts/signup" tag="span">회원 가입</router-link>
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "LoginModal",
  data() {
    return {
      loginData: {
        email: null,
        password: null,
      },
    };
  },
  computed: {},
  created() {},
  components: {},
  props: {
    icon: String,
  },
  methods: {
    ...mapActions(["login"]),

    showModal() {
      const loginModal = document.querySelector("#login-modal");
      loginModal.style.display = "block";
    },

    hideModal() {
      const loginModal = document.querySelector("#login-modal");
      loginModal.style.display = "none";
    },
  },
  mounted() {},
};
</script>

<style scoped>
.custom-cursor {
  cursor: pointer;
}

/* modal background */
#login-modal {
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.5);
}

/* modal */
.modal-login {
  color: #636363;
  width: 350px;
}
.modal-login .modal-content {
  padding: 20px;
  border-radius: 5px;
  border: none;
}

/* modal header */
.modal-login .modal-header {
  border-bottom: none;
  position: relative;
  justify-content: center;
}
.modal-login .close {
  position: absolute;
  top: -10px;
  right: -10px;
}
.modal-login h4 {
  color: #636363;
  text-align: center;
  font-size: 26px;
  margin-top: 0;
}

/* modal content */
.modal-login .modal-content {
  color: #999;
  border-radius: 1px;
  margin-bottom: 15px;
  background: #fff;
  border: 1px solid #f3f3f3;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  padding: 25px;
}
.modal-login .form-group {
  margin-bottom: 20px;
}
.modal-login .form-control {
  min-height: 38px;
  padding-left: 5px;
  box-shadow: none !important;
  border-width: 0 0 1px 0;
  border-radius: 0;
}
.modal-login .form-control:focus {
  border-color: #ccc;
}
.modal-login .input-group {
  border-bottom: 1px solid #ced4da;
}
.modal-login .input-group-addon {
  max-width: 42px;
  text-align: center;
  background: none;
  padding-right: 5px;
  border-radius: 0;
}
.modal-login .form-control,
.modal-login .btn {
  font-size: 16px;
  font-weight: bold;
  border-radius: 3px;
  border: none;
  min-width: 140px;
}

/* modal footer */
.modal-login .modal-footer {
  color: #999;
  border-color: #dee4e7;
  text-align: center;
  margin: 0 -25px -25px;
  font-size: 13px;
  justify-content: center;
}

.modal-login .modal-footer span {
  color: #000000;
}
</style>
