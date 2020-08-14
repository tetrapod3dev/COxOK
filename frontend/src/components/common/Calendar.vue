<template>
  <v-app>
    <v-main>
      <v-row class="fill-height customize-calendar-frame">
        <v-col>
          <v-sheet height="64">
            <v-toolbar flat color="white">
              <v-btn fab text small color="grey darken-2" @click="prev">
                <v-icon small>mdi-chevron-left</v-icon>
              </v-btn>
              <v-btn fab text small color="grey darken-2" @click="next">
                <v-icon small>mdi-chevron-right</v-icon>
              </v-btn>
              <v-toolbar-title v-if="$refs.calendar">{{ $refs.calendar.title }}</v-toolbar-title>
              <v-spacer></v-spacer>
              <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">오늘</v-btn>
              <v-btn outlined class="mr-4" color="grey darken-2" @click="type = 'month'">월</v-btn>
              <v-btn outlined class="mr-4" color="grey darken-2" @click="type = 'week'">주</v-btn>
            </v-toolbar>
          </v-sheet>
          <v-sheet height="600">
            <v-calendar
              class="customize-calendar"
              ref="calendar"
              v-model="focus"
              color="primary"
              :events="events"
              event-more-text="더보기"
              :event-color="getEventColor"
              :type="type"
              interval-count="0"
              @click:event="movePage"
              @click:more="viewWeek"
              @click:date="viewWeek"
            ></v-calendar>
            <!-- @change="updateRange" -->
            <v-menu
              v-model="selectedOpen"
              :close-on-content-click="false"
              :activator="selectedElement"
              offset-x
              app
            >
              <v-card color="grey lighten-4" min-width="350px" flat>
                <v-toolbar :color="selectedEvent.color" dark>
                  <v-spacer></v-spacer>
                  <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                  <v-spacer></v-spacer>
                </v-toolbar>
                <v-card-actions>
                  <v-btn
                    text
                    color="primary"
                    @click="movePage(selectedEvent.id, selectedEvent.detail)"
                  >게시글</v-btn>
                  <v-btn text color="secondary" @click="selectedOpen = false">Cancel</v-btn>
                </v-card-actions>
              </v-card>
            </v-menu>
          </v-sheet>
        </v-col>
      </v-row>
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/api";
import { mapGetters } from "vuex";

export default {
  data: () => ({
    focus: "",
    type: "month",
    typeToLabel: {
      month: "월",
      week: "주",
    },
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
    colors: {
      홈파티: "blue",
      공유키친: "indigo",
      쿠킹클래스: "deep-purple",
      실시간강의: "cyan",
      유튜브강의: "green",
      empty3: "orange",
      empty4: "grey darken-1",
    },
    valueList: [],
    meetMyList: [],
    meetMyjoinList: [],
    onlineMyList: [],
    onlineMyjoinList: [],
  }),
  created() {
    let configs = {
      headers: {
        Authorization: this.config,
      },
    };
    this.events = [];
    const inputList = [];
    axios
      .get(SERVER.URL + SERVER.ROUTES.meetMy, configs)
      .then((res) => {
        let dataList = res.data.list;
        dataList.forEach((element) => {
          this.events.push({
            id: element.meetId,
            name: element.title,
            start: new Date(element.date),
            // end: second,
            color: this.colors[element.type],
            detail: element.type,
            timed: false,
          });
        });
      })
      .catch((err) => console.log(err.response));
    axios
      .get(SERVER.URL + SERVER.ROUTES.meetMyjoin, configs)
      .then((res) => {
        let dataList = res.data.list;
        dataList.forEach((element) => {
          this.events.push({
            id: element.meetId,
            name: element.title,
            start: new Date(element.date),
            // end: second,
            color: this.colors[element.type],
            detail: element.type,
            timed: false,
          });
        });
      })
      .catch((err) => console.log(err.response));
    axios
      .get(SERVER.URL + SERVER.ROUTES.onlineMy, configs)
      .then((res) => {
        let dataList = res.data.list;
        dataList.forEach((element) => {
          this.events.push({
            id: element.onlineId,
            name: element.title,
            start: new Date(element.date),
            // end: second,
            color: this.colors[element.type],
            detail: element.type,
            timed: false,
          });
        });
      })
      .catch((err) => console.log(err.response));
    axios
      .get(SERVER.URL + SERVER.ROUTES.onlineMyjoin, configs)
      .then((res) => {
        let dataList = res.data.list;
        dataList.forEach((element) => {
          this.events.push({
            id: element.onlineId,
            name: element.title,
            start: new Date(element.date),
            // end: second,
            color: this.colors[element.type],
            detail: element.type,
            timed: false,
          });
        });
      })
      .catch((err) => console.log(err.response));
    this.valueList = inputList;
    this.updateRange();
  },
  computed: {
    ...mapGetters(["config"]),
  },
  methods: {
    viewWeek({ date }) {
      this.focus = date;
      this.type = "week";
    },
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.focus = "";
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        setTimeout(() => (this.selectedOpen = true), 10);
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10);
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    updateRange() {
      const events = [];
      const eventList = this.valueList;
      for (let index = 0; index < eventList.length; index++) {
        const element = this.valueList[index];
        events.push({
          id: element.id,
          name: element.name,
          start: new Date(element.date),
          // end: second,
          color: this.colors[element.type],
          timed: false,
        });
      }
      this.events = events;
    },
    movePage(event) {
      let category = {
        홈파티: "/clubs/detail/",
        공유키친: "/clubs/detail/",
        쿠킹클래스: "/clubs/detail/",
        실시간강의: "/clubs/online/detail/",
        유튜브강의: "/clubs/online/detail/",
      };
      this.$router.push(category[event.event.detail] + event.event.id);
    },
  },
};
</script>

<style>
.customize-calendar .v-calendar-daily__head {
  height: 600px !important;
}
.customize-calendar .v-calendar-daily__body {
  display: none !important;
}
.customize-calendar-frame .v-menu__content {
  top: 0 !important;
}
</style>