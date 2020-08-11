import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueCookies from 'vue-cookies'

import { BootstrapVue, IconsPlugin } from "bootstrap-vue";

import { Datetime } from 'vue-datetime'
import 'vue-datetime/dist/vue-datetime.css'
 
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "./registerServiceWorker";
//plugins
import NowUIKit from "./plugins/now-ui-kit";

Vue.config.productionTip = false;

Vue.use(VueCookies);

Vue.use(Datetime)

Vue.use(NowUIKit);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
