import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Main from '../views/Main.vue'

// Accounts Router
import SignUpView from '../views/accounts/SignUpView.vue'
import UserSettingView from '../views/accounts/UserSettingView.vue'
import EmailAuthView from '../views/accounts/EmailAuthView.vue'

// Recipes Router
import PrevRecipeList from '../views/recipes/PrevRecipeList.vue'
import RecipeListView from '../views/recipes/RecipeListView.vue'
import RecipeDetailView from '../views/recipes/RecipeDetailView.vue'
import RecipeUpdateView from '../views/recipes/RecipeUpdateView.vue'
import RecipeMakeView from '../views/recipes/RecipeMakeView.vue'

// Blog Router
import BlogHomeView from '../views/blog/BlogHomeView.vue'
import BlogPostListView from '../views/blog/BlogPostListView.vue'
import BlogPostMakeView from '../views/blog/BlogPostMakeView.vue'
import BlogPostDetailView from '../views/blog/BlogPostDetailView.vue'
import BlogRecipeListView from '../views/blog/BlogRecipeListView.vue'
import BlogMarkedRecipeListView from '../views/blog/BlogMarkedRecipeListView.vue'
import BlogGraphView from '../views/blog/BlogGraphView.vue'

// Versus Router
import VersusHomeView from '../views/versus/VersusHomeView.vue'
import VersusMakeView from '../views/versus/VersusMakeView.vue'
import VersusDetailView from '../views/versus/VersusDetailView.vue'

// Club Router
import ClubListView from '../views/clubs/ClubListView.vue'
import ClubDetailView from '../views/clubs/ClubDetailView.vue'
import ClubMakeView from '../views/clubs/ClubMakeView.vue'


// Test Router
import EditorTest from '../views/articles/EditorTest.vue'
import About from '../views/About.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/main',
    name: 'Main',
    component: Main
  },
  // Accounts Router
  {
    path: '/accounts/signup',
    name: 'SignUpView',
    component: SignUpView
  },
  {
    path: '/accounts/setting',
    name: 'UserSettingView',
    component: UserSettingView
  },
  {
    path: '/accounts/auth',
    name: 'EmailAuthView',
    component: EmailAuthView
  },
  // Recipes Router
  {
    path: '/recipes/all',
    name: 'PrevRecipeList',
    component: PrevRecipeList
  },
  {
    path: '/recipes/all/:pageNum',
    name: 'RecipeListView',
    component: RecipeListView
  },
  {
    path: '/recipes/make',
    name: 'RecipeMakeView',
    component: RecipeMakeView
  },
  {
    path: '/recipes/detail/:recipe_id',
    name: 'RecipeDetailView',
    component: RecipeDetailView
  },
  {
    path: '/recipes/update/:recipe_id',
    name: 'RecipeUpdateView',
    component: RecipeUpdateView
  },
  // Blog Router
  {
    path: '/blog',
    name: 'BlogHomeView',
    component: BlogHomeView
  },
  {
    path: '/blog/posts/make',
    name: 'BlogPostMakeView',
    component: BlogPostMakeView
  },
  {
    path: '/blog/posts/:pageNum',
    name: 'BlogPostListView',
    component: BlogPostListView
  },
  {
    path: '/blog/recipes/:pageNum',
    name: 'BlogRecipeListView',
    component: BlogRecipeListView
  },
  {
    path: '/blog/markedrecipes/:pageNum',
    name: 'BlogMarkedRecipeListView',
    component: BlogMarkedRecipeListView
  },
  {
    path: '/blog/graph',
    name: 'BlogGraphView',
    component: BlogGraphView
  },
  {
    path: '/blog/posts/detail/:post_id',
    name: 'BlogPostDetailView',
    component: BlogPostDetailView
  },
  // Versus Router
  {
    path: '/versus',
    name: 'VersusHomeView',
    component: VersusHomeView
  },
  {
    path: '/versus/make',
    name: 'VersusMakeView',
    component: VersusMakeView
  },
  {
    path: '/versus/detail/:versus_id',
    name: 'VersusDetailView',
    component: VersusDetailView
  },
  // Clubs Router
  {
    path: '/clubs/make',
    name: 'ClubMakeView',
    component: ClubMakeView
  },
  {
    path: '/clubs/:pageNum',
    name: 'ClubListView',
    component: ClubListView
  },
  {
    path: '/clubs/detail/:club_id',
    name: 'ClubDetailView',
    component: ClubDetailView
  },
  // Test Router
  {
    path: '/test',
    name: 'EditorTest',
    component: EditorTest
  },
  {
    path: '/test2',
    name: 'About',
    component: About
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 인증 관련 필터링(로그인 토큰 기반)
// router.beforeEach((to, from, next) => { // 모든 라우터에 대해 입장하기 전에
//   const publicPages = ['SignUpView', 'Home', 'RecipeListView', 'RecipeItemView', 'BlogHomeView', 'BlogPostListView', 'BlogPostMakeView', 'VersusHome'];
//   // Login 안 해도 되는 페이지
//   const authPages = ['SignUpView']; // Login 되어있으면 안되는 페이지

//   const authRequired = !publicPages.includes(to.name); // 로그인 해야 됨
//   const unauthRequired = authPages.includes(to.name); // 로그인 X여야 됨
//   const isLoggedIn = (localStorage.getItem('auth-token') == null) ? false : true 

//   if (unauthRequired && isLoggedIn) { // 가야하는 페이지가 로그인 X여야 되고, 로그인 된 경우 첫 화면으로 이동
//     next('/');
//   }

//   if (authRequired && !isLoggedIn) { // 로그인 해야되는 페이지로 가려고하고, 로그인 X인 경우 첫 화면으로 이동
//     next('/');
//   } else {
//     next();
//   }
// })

export default router
