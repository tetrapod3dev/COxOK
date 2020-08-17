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
            <div class="card-footer text-left">
              <div class="stats">
                <i class="fas fa-edit"></i>
                {{ author.nickname }}
              </div>
            </div>
          </div>
        </div>
      </card>
    </section>

    <div class="container">

      <div v-if="online.video != null">
        <div class="embed-responsive embed-responsive-16by9">
          <iframe class="embed-responsive-item" :src="videoUrl" allowfullscreen></iframe>
        </div>
      </div>
      <div v-else>
        <a :href="online.link" target="_blank">강의 링크로 이동 {{ online.link }}</a>
      </div>
      
      <p class="meet-subtitle text-left col-12">타입: {{ online.type }}</p>
      <p class="meet-content text-left col-12">날짜: {{ simpleDate(online.date) }}</p>
      <p class="meet-content text-left col-12">시간: {{ simpleTime(online.date) }}</p>
            
      <div v-if="author.id != userId" class="join-btn-div">
        <button class="btn" style="width:100%" @click="changeJoinClub">{{ joinMent }}</button>
      </div>

      <div class="col-12 mt-5">
        <h3 class="text-left">상세 내용</h3>
        <div class="detail-main-content" v-html="online.content" />
      </div>

      <router-link
      v-if="userId == author.id"
      :to="{ name: 'ClubOnlineUpdateView', params: { club_id: online.onlineId } }"
    >
      <div class="section online-view">
        <div class="container">
          <div class="button-container">
            <button class="learn-more submit">수정</button>
            <button class="learn-more" @click="deleteClub" style="margin-left: 20px;">삭제</button>
          </div>
        </div>
      </div>
      </router-link>

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
      return this.isIn ? "취소하기" : "신청하기";
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
    simpleDate(date) {
      return date.slice(0,10);
    },
    simpleTime(date) {
      return date.slice(11,16);
    },
    userProfile(index) {
      return SERVER.IMAGE_URL + this.meet.meetJoinList[index].profilePhoto;
    },
    getUserIndex(index){
      return "user-profile" + index;
    },
    
  },
};
</script>

<style scoped>
.stats {
  font-size: 25px;
}

.detail-main-content {
  min-height: 200px;
  margin-bottom: 10px;
}

button.learn-more {
  font-weight: 600;
  color: #382b22;
  text-transform: uppercase;
  padding: 1.25em 2em;
  background: #fff0f0;
  border: 2px solid #b18597;
  border-radius: 0.75em;
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
  -webkit-transition: background 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: background 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.submit {
  color: #382b22;
  background: #D7FFF1;
  border: 2px solid #77AF9C;
}

button.learn-more::before {
  position: absolute;
  content: '';
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #f9c4d2;
  border-radius: inherit;
  box-shadow: 0 0 0 2px #b18597, 0 0.625em 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0.75em, -1em);
          transform: translate3d(0, 0.75em, -1em);
  -webkit-transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: box-shadow 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), box-shadow 150ms cubic-bezier(0, 0, 0.58, 1), -webkit-transform 150ms cubic-bezier(0, 0, 0.58, 1);
}

button.submit::before {
  background: #67D5B5;
  box-shadow: 0 0 0 2px #77AF9C, 0 0.625em 0 0 #cff0da;
}


button.learn-more:hover {
  background: #ffe9e9;
  -webkit-transform: translate(0, 0.25em);
          transform: translate(0, 0.25em);
}

button.submit:hover {
  background: #D7FFF1;
}

button.learn-more:hover::before {
  box-shadow: 0 0 0 2px #b18597, 0 0.5em 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0.5em, -1em);
          transform: translate3d(0, 0.5em, -1em);
}

button.submit:hover::before {
  box-shadow: 0 0 0 2px #77AF9C, 0 0.5em 0 0 #cff0da;
}

button.learn-more:active {
  background: #ffe9e9;
  -webkit-transform: translate(0em, 0.75em);
          transform: translate(0em, 0.75em);
}

button.submit:active {
  background: #D7FFF1;
}

button.learn-more:active::before {
  box-shadow: 0 0 0 2px #b18597, 0 0 #ffe3e2;
  -webkit-transform: translate3d(0, 0, -1em);
          transform: translate3d(0, 0, -1em);
}

button.submit:active::before {
  box-shadow: 0 0 0 2px #77AF9C, 0 0 #cff0da;
}
</style>