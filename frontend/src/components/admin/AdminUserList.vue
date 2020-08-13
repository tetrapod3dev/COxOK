<template>
  <div class="container">
    <h2>Admin이 유저 정보를 봅니다..</h2>

    <div v-for="user in users" :key="user.userId" class="row">
      <p class="col-1"> {{ user.userId }} </p>
      <img :src="imageSrc(user.profilePhoto)" class="col-3">
      <div class="col-3">
        <p> {{ user.email }} </p>
        <p> {{ user.nickname }} </p>
      </div>
      <p class="col-3"> {{ user.detail }} </p>
      <p class="col-1"> {{ user.auth }}</p>
      <div class="col-1">
        <b-button v-b-modal.modal @click="selectUser(user)">정보 수정</b-button>
        <b-button @click="deleteUser(user.userId)">회원 삭제</b-button>
      </div>

    </div>
    <b-modal hide-footer id="modal" ref="modal" size="md" title="유저 정보 수정">
      <h3>{{ selectedUser.email }}님의 정보</h3>
      <div class="row">
        <div class="col-6">
          <img :src="imageSrc(selectedUser.profilePhoto)">
          <button @click="clearPhoto">사진 초기화</button>
        </div>
        <div class="col-6">
          <input type="text" v-model="selectedUser.nickname">
          <button @click="clearNickname">닉네임 초기화</button>

          <input type="text" v-model="selectedUser.detail">
          <button @click="clearDetail">자기소개 초기화</button>
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
    imageSrc(imageLink) {
      return SERVER.IMAGE_URL + imageLink
    },
    getUser() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.adminGetUser, this.configs)
        .then(res => {
          this.users = res.data.list
        })
        .catch(err => console.log(err.response))
    },
    selectUser(user) {
      this.selectedUser = user
    },
    clearPhoto() {
      this.selectedUser.profilePhoto = 'dochi.png'
    },
    clearNickname() {
      let date = new Date();
      this.selectedUser.nickname = '테스트닉네임' + (date.getTime() % 1000000007)
    },
    clearDetail() {
      this.selectedUser.detail = null
    },
    sendData(userId) {
      let body = {
        userId: userId,
        nickname: this.selectedUser.nickname,
        detail: this.selectedUser.detail,
        profilePhoto: this.selectedUser.profilePhoto,
      }
      axios
        .put(SERVER.URL + SERVER.ROUTES.adminUpdateUser, body, this.configs)
        .then(() => {
          alert('회원 정보 수정에 성공했습니다!')
          this.$refs['modal'].hide()
        })
        .catch(err => {
          if (err.response.status == 405) {
            alert('문제가 발생했습니다! 닉네임 초기화를 다시 시도해주세요')
          }
        })
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
          .catch(err => console.log(err.response))
      }
    },
  }
}
</script>

<style>

</style>