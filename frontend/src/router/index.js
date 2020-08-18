import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Main from "../views/Main.vue";
import About from "../views/About.vue";
import ErrorPage from "../views/ErrorPage.vue";

import cookies from "vue-cookies"; // cookie 사용(토큰 저장)
import axios from "axios";
import SERVER from "@/api/api";

// Accounts Router
import SignUpView from "../views/accounts/SignUpView.vue";
import UserSettingView from "../views/accounts/UserSettingView.vue";
import EmailAuthView from "../views/accounts/EmailAuthView.vue";

// Recipes Router
import PrevRecipeList from "../views/recipes/PrevRecipeList.vue";
import RecipeListView from "../views/recipes/RecipeListView.vue";
import RecipeDetailView from "../views/recipes/RecipeDetailView.vue";
import RecipeUpdateView from "../views/recipes/RecipeUpdateView.vue";
import RecipeMakeView from "../views/recipes/RecipeMakeView.vue";

// Blog Router
import BlogHomeView from "../views/blog/BlogHomeView.vue";
import BlogPostListView from "../views/blog/BlogPostListView.vue";
import BlogPostMakeView from "../views/blog/BlogPostMakeView.vue";
import BlogPostUpdateView from "../views/blog/BlogPostUpdateView.vue";
import BlogPostDetailView from "../views/blog/BlogPostDetailView.vue";
import BlogRecipeListView from "../views/blog/BlogRecipeListView.vue";
import BlogGraphView from "../views/blog/BlogGraphView.vue";
import BlogCalendarView from "../views/blog/BlogCalendarView.vue";

// Versus Router
import VersusHomeView from "../views/versus/VersusHomeView.vue";
import VersusMakeView from "../views/versus/VersusMakeView.vue";
import VersusDetailView from "../views/versus/VersusDetailView.vue";

// Club Router
import ClubListView from "../views/clubs/ClubListView.vue";
import ClubMakeView from "../views/clubs/ClubMakeView.vue";
import ClubOfflineDetailView from "../views/clubs/ClubOfflineDetailView.vue";
import ClubOnlineDetailView from "../views/clubs/ClubOnlineDetailView.vue";
import ClubOfflineUpdateView from "../views/clubs/ClubOfflineUpdateView.vue";
import ClubOnlineUpdateView from "../views/clubs/ClubOnlineUpdateView.vue";
import ClubOfflineListTypeView from "../views/clubs/ClubOfflineListTypeView.vue";
import ClubOnlineListTypeView from "../views/clubs/ClubOnlineListTypeView.vue";

// Admin Router
import AdminMainView from "../views/admin/AdminMainView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/main",
    name: "Main",
    component: Main,
  },
  {
    path: "/about",
    name: "About",
    component: About,
  },
  // Accounts Router
  {
    path: "/accounts/signup",
    name: "SignUpView",
    component: SignUpView,
  },
  {
    path: "/accounts/setting",
    name: "UserSettingView",
    component: UserSettingView,
  },
  {
    path: "/accounts/auth",
    name: "EmailAuthView",
    component: EmailAuthView,
  },
  // Recipes Router
  {
    path: "/recipes/all",
    name: "PrevRecipeList",
    component: PrevRecipeList,
  },
  {
    path: "/recipes/all/:pageNum",
    name: "RecipeListView",
    component: RecipeListView,
  },
  {
    path: "/recipes/make",
    name: "RecipeMakeView",
    component: RecipeMakeView,
  },
  {
    path: "/recipes/detail/:recipe_id",
    name: "RecipeDetailView",
    component: RecipeDetailView,
  },
  {
    path: "/recipes/update/:recipe_id",
    name: "RecipeUpdateView",
    component: RecipeUpdateView,
  },
  // Blog Router
  {
    path: "/blog",
    name: "BlogHomeView",
    component: BlogHomeView,
  },
  {
    path: "/blog/posts/make",
    name: "BlogPostMakeView",
    component: BlogPostMakeView,
  },
  {
    path: "/blog/posts/:pageNum",
    name: "BlogPostListView",
    component: BlogPostListView,
  },
  {
    path: "/blog/recipes/:pageNum",
    name: "BlogRecipeListView",
    component: BlogRecipeListView,
  },
  {
    path: "/blog/graph",
    name: "BlogGraphView",
    component: BlogGraphView,
  },
  {
    path: "/blog/posts/detail/:blogId",
    name: "BlogPostDetailView",
    component: BlogPostDetailView,
  },
  {
    path: "/blog/posts/update/:blogId",
    name: "BlogPostUpdateView",
    component: BlogPostUpdateView,
  },
  {
    path: "/blog/calendar",
    name: "BlogCalendarView",
    component: BlogCalendarView,
  },
  // Versus Router
  {
    path: "/versus",
    name: "VersusHomeView",
    component: VersusHomeView,
  },
  {
    path: "/versus/make",
    name: "VersusMakeView",
    component: VersusMakeView,
  },
  {
    path: "/versus/detail/:versus_id",
    name: "VersusDetailView",
    component: VersusDetailView,
  },
  // Clubs Router
  {
    path: "/clubs/:pageNum",
    name: "ClubListView",
    component: ClubListView,
  },
  {
    path: "/clubs/make",
    name: "ClubMakeView",
    component: ClubMakeView,
  },
  {
    path: "/clubs/list/:type/:pageNum",
    name: "ClubOfflineListTypeView",
    component: ClubOfflineListTypeView,
  },
  {
    path: "/clubs/online/list/:type/:pageNum",
    name: "ClubOnlineListTypeView",
    component: ClubOnlineListTypeView,
  },
  {
    path: "/clubs/detail/:club_id",
    name: "ClubOfflineDetailView",
    component: ClubOfflineDetailView,
  },
  {
    path: "/clubs/online/detail/:club_id",
    name: "ClubOnlineDetailView",
    component: ClubOnlineDetailView,
  },
  {
    path: "/clubs/Update/:club_id",
    name: "ClubOfflineUpdateView",
    component: ClubOfflineUpdateView,
  },
  {
    path: "/clubs/online/Update/:club_id",
    name: "ClubOnlineUpdateView",
    component: ClubOnlineUpdateView,
  },
  // Admin Router
  {
    path: "/admin",
    name: "AdminMainView",
    component: AdminMainView,
  },
  {
    path: "*",
    name: "ErrorPage",
    component: ErrorPage,
  },
];

const router = new VueRouter({
  scrollBehavior() {
    return { x: 0, y: 0 };
  },
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

// 인증 관련 필터링(로그인 토큰 기반)
router.beforeEach(async (to, from, next) => {
  // 모든 라우터에 대해 입장하기 전에
  const publicPages = [
    "SignUpView",
    "Home",
    "About",
    "EmailAuthView",
    "PrevRecipeList",
    "RecipeListView",
    "RecipeDetailView",
    "VersusHomeView",
    "VersusDetailView",
    "ClubListView",
    "ClubOfflineListTypeView",
    "ClubOnlineListTypeView",
    "ErrorPage"
  ];
  // Login 안 해도 되는 페이지

  const authPages = ["SignUpView", "Home", "EmailAuthView"]; // Login 되어있으면 안되는 페이지

  const authRequired = !publicPages.includes(to.name); // 로그인 해야 됨
  const unauthRequired = authPages.includes(to.name); // 로그인 X여야 됨

  const isLoggedIn = cookies.get("auth-token") != null ? true : false;

  if (authRequired && !isLoggedIn) {
    alert("로그인이 필요합니다!");
    next("/");
  } else if (isLoggedIn) {
    axios
      .get(SERVER.URL + SERVER.ROUTES.myPage, {
        headers: {
          Authorization: "Bearer: " + cookies.get("auth-token"),
        },
      })
      .then((res) => {
        if (res.status == 200) {
          if (unauthRequired) {
            if (to.name == "Home") {
              next();
            } else {
              alert("로그인한 상태로 접근할 수 없습니다.");
              next("/main");
            }
          } else {
            next();
          }
        }
      })
      .catch((err) => {
        if (err.response.status == 401) {
          if (authRequired) {
            alert("로그인이 필요합니다!");
            next("/");
          } else {
            next();
          }
        }
      });
  } else {
    next();
  }
});

export default router;
