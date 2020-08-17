<template>
  <div class="container">
    <div class="row border-bottom mb-0">
      <h3 class="col-1 mb-0">Id</h3>
      <h3 class="col-2 mb-0">프로필</h3>
      <h3 class="col-3 mb-0">아이디</h3>
      <h3 class="col-3 mb-0">자기소개</h3>
      <h3 class="col-1 mb-0">인증</h3>
      <h3 class="col-2 mb-0">동작</h3>
    </div>

    <div v-for="user in users" :key="user.userId" class="row">
      <p class="col-1"> {{ user.userId }} </p>
      <img :src="imageSrc(user.profilePhoto)" class="col-2 user-photos">
      <div class="col-3">
        <p> {{ user.email }} </p>
        <p> {{ user.nickname }} </p>
      </div>
      <p class="col-3"> {{ user.detail }} </p>
      <p class="col-1"> {{ isAuth(user.auth) }}</p>
      <div class="col-2">
        <b-button v-b-modal.modal @click="selectUser(user)">정보 수정</b-button>
        <b-button @click="deleteUser(user.userId)">회원 삭제</b-button>
      </div>

    </div>
    <b-modal hide-footer id="modal" ref="modal" size="md" title="유저 정보 수정">
      <h3>{{ selectedUser.email }}님의 정보</h3>
      <div class="row">
        <div class="col-6">
          <img :src="imageSrc(tempUser.profilePhoto)">
          <b-button @click="clearPhoto">사진 초기화</b-button>
        </div>
        <div class="col-6">
          <input type="text" v-model="tempUser.nickname">
          <b-button @click="clearNickname">닉네임 초기화</b-button>

          <input type="text" v-model="tempUser.detail">
          <b-button @click="clearDetail">자기소개 초기화</b-button>
        </div>


        <b-button @click="sendData(selectedUser.userId)">적용</b-button>
      </div>
    </b-modal>

  </div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/api'

import { mapGetters } from 'vuex'

export default {
  name: 'AdminUserList',
  data() {
    return {
      users: {},
      selectedUser: {},
      tempUser: {
        nickname: null,
        detail: null,
        profilePhoto: null,
      },
    }
  },
  computed: {
    ...mapGetters(['config']),
    configs() {
      return {headers: {
        Authorization: this.config,
      }}
    }
  },
  mounted() {
    this.getUser();
  },
  methods: {
    isAuth(number) {
      return (number == 0) ? '완료' : '미완료'
    },
    imageSrc(imageLink) {
      return SERVER.IMAGE_URL + imageLink
    },
    getUser() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.adminGetUser, this.configs)
        .then(res => {
          this.users = res.data.list
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    selectUser(user) {
      this.selectedUser = user
      this.tempUser.nickname = user.nickname
      this.tempUser.detail = user.detail
      this.tempUser.profilePhoto = user.profilePhoto
    },
    clearPhoto() {
      this.tempUser.profilePhoto = 'dochi.png'
    },
    clearNickname() {
      let date = new Date();
      this.tempUser.nickname = '테스트닉네임' + (date.getTime() % 1000000007)
    },
    clearDetail() {
      this.tempUser.detail = null
    },
    sendData(userId) {
      let body = {
        userId: userId,
        nickname: this.tempUser.nickname,
        detail: this.tempUser.detail,
        profilePhoto: this.tempUser.profilePhoto,
      }
      axios
        .put(SERVER.URL + SERVER.ROUTES.adminUpdateUser, body, this.configs)
        .then(() => {
          alert('회원 정보 수정에 성공했습니다!')
          this.getUser();
          this.$refs['modal'].hide()
        })
        .catch(err => {
          if (err.response.status == 405) {
            alert('문제가 발생했습니다! 닉네임 초기화를 다시 시도해주세요')
          } else if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    deleteUser(userId) {
      let response = confirm('정말로 삭제하실건가요?')
      if (response) {
        axios
          .delete(SERVER.URL + SERVER.ROUTES.adminDeleteUser + userId, this.configs)
          .then(() => {
            alert('회원 정보가 삭제되었습니다!')
            this.getUser()
          })
          .catch((err) => {
            if (err.response.status == 401) {
              alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
              this.logout()
            }});
      }
    },
  }
}
</script>

<style scoped>
.user-photos {
  height: 150px;
}
</style>