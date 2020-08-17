<template>
  <div class="wrapper">
    <section id="top">
      <card
        type="background"
        style="background-image: url('https://previews.123rf.com/images/seamartini/seamartini1609/seamartini160900068/62637928-cooking-and-kitchen-utensils-seamless-background-wallpaper-with-vector-pattern-icons-of-pizza-bread-.jpg') ;"
      >
        <div class="card-title text-left">
          <h1 style>{{ online.title }}</h1>
        </div>
        <div class="card-footer text-left">
          <div class="stats">
            {{ author.nickname }}
            <div v-if="author.id == userId">
              <router-link
                :to="{ name: 'ClubOnlineUpdateView', params: { club_id: online.onlineId } }"
              >
                <i class="fas fa-edit"></i>수정
              </router-link>
              <a @click="deleteClub">X삭제</a>
            </div>
          </div>
        </div>
      </card>
    </section>

    <div class="container">
      <div v-if="author.id != userId">
        <button @click="changeJoinClub">{{ joinMent }}</button>
      </div>

      <div v-if="online.video != null">
        <div class="embed-responsive embed-responsive-16by9">
          <iframe class="embed-responsive-item" :src="videoUrl" allowfullscreen></iframe>
        </div>
      </div>

      <div v-else>
        <a :href="online.link" target="_blank">강의 링크로 이동 {{ online.link }}</a>
      </div>
      <h2>현재 접속자: {{ userId }}</h2>

      <div class="detail-main-content col-12" v-html="online.content"></div>
    </div>
  </div>
</template>

<script>
import SERVER from "@/api/api";
import axios from "axios";
import { mapGetters } from "vuex";

import { Card } from "@/components/global";

export default {
  name: "ClubOnlineDetailView",
  data() {
    return {
      online: {
        onlineId: 0,
        title: null,
        content: null,
        type: null,
        date: null,
        link: null,
        thumbnailSrc: "dochi.png",
        video: null,
      },
      author: {
        id: null,
        nickname: null,
      },
      userId: null,
      isIn: false,
    };
  },
  components: {
    Card,
  },
  computed: {
    ...mapGetters(["config"]),
    videoUrl() {
      return `https://youtube.com/embed/${this.online.video}`;
    },
    joinMent() {
      return this.isIn ? "취소" : "참가";
    },
  },
  created() {
    axios
      .get(
        SERVER.URL + SERVER.ROUTES.onlineDetail + this.$route.params.club_id,
        {
          headers: {
            Authorization: this.config,
          },
        }
      )
      .then((res) => {
        this.online = res.data.online;
        this.author = {
          id: res.data.online.userId,
          nickname: res.data.writerNickname,
        };
        this.userId = res.data.userId;
        this.isIn =
          res.data.joinList.indexOf(res.data.userId) >= 0 ? true : false;
      })
      .catch((err) => {
        if (err.response.status == 401) {
          alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
          this.logout()
        }});

  },
  methods: {
    deleteClub() {
      let response = confirm("삭제하실 건가요?");
      if (response) {
        axios
          .delete(
            SERVER.URL +
              SERVER.ROUTES.onlineDelete +
              this.$route.params.club_id,
            {
              headers: {
                Authorization: this.config,
              },
            }
          )
          .then(() => {
            alert("삭제에 성공했습니다!");
            this.$router.push({ name: "ClubListView", params: { pageNum: 1 } });
          })
          .catch((err) => {
            if (err.response.status == 401) {
              alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
              this.logout()
            }});

      }
    },

    changeJoinClub() {
      if (this.isIn) {
        axios
          .delete(
            SERVER.URL +
              SERVER.ROUTES.cancelOnline +
              this.$route.params.club_id,
            {
              headers: {
                Authorization: this.config,
              },
            }
          )
          .then(() => {
            alert("참가를 취소하셨습니다.");
            this.isIn = false;
          })
          .catch((err) => console.log(err.response));
      } else {
        axios
          .post(
            SERVER.URL + SERVER.ROUTES.joinOnline,
            { onlineId: this.$route.params.club_id },
            {
              headers: {
                Authorization: this.config,
              },
            }
          )
          .then(() => {
            alert("참가 신청에 성공했습니다.");
            this.isIn = true;
          })
          .catch((err) => {
            if (err.response.status == 401) {
              alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
              this.logout()
            }});

      }
    },
  },
};
</script>

<style scoped>
.detail-main-content {
  min-height: 200px;
  border: 1px solid;
  margin-bottom: 10px;
}
</style>