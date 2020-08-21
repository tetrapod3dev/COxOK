<template>
  <div class="wrapper">
    <div class="page-header page-header-mini header-filter" filter-color="black">
      <parallax
        class="page-header-image"
        :style="{ backgroundImage: 'url(http://i3a104.p.ssafy.io/header/mypage.jpg)' }"
      ></parallax>
      <blog-profile />
    </div>
    <!-- 페이지 배너 끝 -->

    <!-- 게시글 작성 시작 -->
    <div class="section">
      <div class="container">
        <blog-menu />
        <!-- 레시피 추가 끝 -->
        <div class="form-group">
          <button @click="preTest" class="btn btn-primary" id="submit">수정</button>
          <button @click="goBackPage" class="btn btn-default" id="submit">취소</button>
        </div>

        <div class="row">
          <div class="col-md-8 ml-auto mr-auto">
            <!-- 제목 -->
            <input
              type="text"
              class="form-control blog-make-border-color"
              placeholder="제목"
              v-model="blogPost.title"
            />
          </div>
        </div>
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto">
            <!-- 텍스트 에디터 -->
            <CxkEditor :value.sync="blogPost.content" />
          </div>
        </div>
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto">
            <!-- 태그 시작 -->
            <b-form-tags v-model="blogTags" no-outer-focus class="mb-2 blog-make-border-color">
              <template v-slot="{ tags, inputAttrs, inputHandlers, tagVariant, addTag, removeTag }">
                <b-input-group v-show="blogTags.length < 3" class="mb-2">
                  <b-form-input
                    v-bind="inputAttrs"
                    v-on="inputHandlers"
                    class="form-control"
                    placeholder="태그 입력"
                  ></b-form-input>
                  <b-input-group-append>
                    <b-button @click="addTag()" variant="primary" class="btn-circle">+</b-button>
                  </b-input-group-append>
                </b-input-group>
                <div class="d-inline-block" style="font-size: 1.5rem;">
                  <b-form-tag
                    v-for="tag in tags"
                    @remove="removeTag(tag)"
                    :key="tag"
                    :title="tag"
                    size="sm"
                    :variant="tagVariant"
                    class="md-2 Cookie-1"
                  >{{ tag }}</b-form-tag>
                </div>
              </template>
            </b-form-tags>
          </div>
        </div>

        <!-- 레시피 추가 시작 -->
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto">
            <card v-if="!selectedRecipe" type="profile">
              <div slot="raw-content" class="row blog-make-border blog-make-border-color custom-cursor-pointer" @click="changeSelectorShow">
                <div class="col-12 align-self-center">
                  <h3 class="card-title text-center" style="padding: 40px">레시피 추가</h3>
                </div>
              </div>
            </card>
            <card v-if="selectedRecipe" type="profile">
              <div slot="raw-content" class="row">
                <div class="col-md-5">
                  <div class="card-image custom-cursor-pointer" @click="changeSelectorShow">
                    <img class="img img-zoom" :src="imageSrc(selectedRecipe)" />
                  </div>
                </div>
                <div class="col-md-7">
                  <div class="card-body text-left">
                    <h3 class="card-title">{{selectedRecipe.recipeName}}</h3>
                    <h6 class="category text-primary text-left">{{selectedRecipe.nickname}}</h6>
                    <div class="row">
                      <h5 class="card-title col-4 text-info custom-cursor-pointer" @click="changeSelectorShow">수정</h5>
                      <h5
                        class="card-title col-4 text-danger custom-cursor-pointer"
                        @click.stop="deleteSelectedRecipe"
                      >삭제</h5>
                    </div>
                  </div>
                </div>
              </div>
            </card>

            <div v-show="showSelector" class="col-12">
              <CategorySelector
                @searchRecipe="categorySubmit"
                @removeSelect="categorySubmit"/>

              <div v-for="recipe in recipes" :key="recipe.id" @click="selectRecipe(recipe)" class="row custom-cursor-pointer m-2" >
                <img :src="imageSrc(recipe)" class="col-4" />
                <div class="col-7">
                  <h3 class="row">{{ recipe.recipeName }}</h3>
                  <p class="row">{{ recipe.recipeDetail }}</p>
                </div>
              </div>
              <div id="bottomSensor"></div>
              <h2 v-if="isEnd">끝!</h2>
            </div>
          </div>
        </div>
        <!-- 게시글 작성 끝 -->
      </div>
    </div>
  </div>
</template>

<script>
import BlogProfile from "@/components/blog/BlogProfile.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";
import { Card } from "@/components/global";

import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";
import CategorySelector from "@/components/recipes/CategorySelector.vue";

import axios from "axios";
import scrollmonitor from "scrollmonitor";

import router from "@/router";
import SERVER from "@/api/api";
import { mapGetters, mapMutations } from "vuex";

export default {
  name: "BlogPostUpdateView",
  bodyClass: "profile-page",
  data() {
    return {
      blogTags: [],
      recipes: [],
      showSelector: false,
      isEnd: false,
      curPage: 1,
      selectedRecipe: {
        recipedId: 0,
        recipeThumbnailSrc: "dochi.png",
      },
      blogPost: {
        title: "",
        content: "",
        tag1: null,
        tag2: null,
        tag3: null,
        recipeId: 0,
        blogId: 0,
      },
    };
  },
  components: {
    CxkEditor,
    BlogProfile,
    CategorySelector,
    BlogMenu,
    Card,
  },
  created() {
    this.getPosts();
    this.getRecipes();
    this.setSearchData({
      'selectedCategory': [],
      'selectedIngredients': [],
      'selectedIngredientsName': [],
      'level': 5,
      'cookTime': 120,
  })},
  mounted() {
    window.addEventListener("scroll", this.indexScrollFuncion);
    this.addScrollWatcher();
  },
  beforeDestory() {
    window.removeEventListener('scroll', this.indexScrollFuncion);
    clearInterval(this.widthInterval);
  },
  updated() {
    if (this.showSelector) {
      if (this.curPage-1 < this.maxPage) {
        this.loadUntilViewportIsFull();
      } else {
        this.isEnd = true
      }
    }
  },
  computed: {
    ...mapGetters(["config", "searchingData"]),
    tagState() {
      return this.blogPost.length > 2 && this.blogPost.legnth < 0;
    },
  },
  methods: {
    ...mapMutations(["setSearchData"]),
    getPosts() {
      let configs = {
        headers: {
          Authorization: this.config,
        },
      };
      axios
        .get(
          SERVER.URL + SERVER.ROUTES.blog + this.$route.params.blogId,
          configs
        )
        .then((res) => {
          this.blogPost = res.data.blog;
          if (this.blogPost.tag1 !== null) {
            this.blogTags.push(this.blogPost.tag1);
          }
          if (this.blogPost.tag2 !== null) {
            this.blogTags.push(this.blogPost.tag2);
          }
          if (this.blogPost.tag3 !== null) {
            this.blogTags.push(this.blogPost.tag3);
          }
          if (this.blogPost.recipeId) {
            axios
              .get(
                SERVER.URL +
                  SERVER.ROUTES.recipeDetail +
                  this.blogPost.recipeId,
                configs
              )
              .then((res) => {
                this.selectedRecipe = res.data.recipe;
              })
              .catch((err) => {
                if (err.response.status == 401) {
                  alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
                  this.logout()
                }});
          }
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    contentHandler(event) {
      this.blogPost.content = event;
    },
    preTest() {
      let problems = [];
      if (this.blogPost.title === null || this.blogPost.title === "") {
        problems.push("게시글 제목");
      }
      if (this.blogPost.content === null || this.blogPost.content === "") {
        problems.push(" 게시글 내용");
      }

      if (problems.length == 0) {
        this.updateBlogPost();
      } else {
        alert("필수 항목이 누락되었습니다.\n\n누락된 항목: " + problems);
      }
    },
    updateBlogPost() {
      let configs = {
        headers: {
          Authorization: this.config,
        },
      };

      this.blogPost.tag1 = null;
      this.blogPost.tag2 = null;
      this.blogPost.tag3 = null;
      if (this.blogTags.length > 2) {
        this.blogPost.tag3 = this.blogTags[2];
      }
      if (this.blogTags.length > 1) {
        this.blogPost.tag2 = this.blogTags[1];
      }
      if (this.blogTags.length > 0) {
        this.blogPost.tag1 = this.blogTags[0];
      }
      if (this.selectedRecipe) {
        this.blogPost.recipeId = this.selectedRecipe.recipeId;
      } else {
        this.blogPost.recipeId = 0
      }

      axios
        .put(SERVER.URL + SERVER.ROUTES.blog, this.blogPost, configs)
        .then(() => {
          router.push({
            name: "BlogPostDetailView",
            params: { blogId: this.$route.params.blogId },
          });
        })
        .catch((err) => {
          if (err.response.status == 401) {
            alert('로그인 정보가 만료되었습니다! 다시 로그인해주세요.')
            this.logout()
          }});
    },
    goBackPage() {
      this.$router.go(-1);
    },
    // 레시피 선택 관련 함수들 시작
    imageSrc(recipe) {
      return SERVER.IMAGE_URL + recipe.recipeThumbnailSrc;
    },

    changePhoto(event) {
      const file = event.target.files[0];
      this.clubPreview = URL.createObjectURL(file);
      this.clubPost.meetPhoto = file;
    },

    changeSelectorShow() {
      this.showSelector = !this.showSelector;
    },

    addScrollWatcher() {
      const bottomSensor = document.querySelector("#bottomSensor");
      const watcher = scrollmonitor.create(bottomSensor);
      if (this.curPage < this.maxPage) {
        watcher.enterViewport(() => {
          setTimeout(() => {
            this.getRecipes();
          }, 1000);
        });
      }
    },
    loadUntilViewportIsFull() {
      const bottomSensor = document.querySelector("#bottomSensor");
      const watcher = scrollmonitor.create(bottomSensor);
      if (watcher.isFullyInViewport) {
        this.getRecipes();
      }
    },
    getRecipes() {
      if (
        (this.searchingData.selectedCategory.length +
          this.searchingData.selectedIngredients.length ==
        0) && (this.searchingData.level == 5) && (this.searchingData.cookTime == 120)
      ) {
        this.allRecipe(this.curPage++);
      } else {
        this.searchRecipe(this.curPage++);
      }
    },
    categorySubmit() {
      this.isEnd = false;
      this.recipes = [];
      this.curPage = 1;
      this.getRecipes();
    },
    allRecipe(page) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.recipeList + (page - 1))
        .then((res) => {
          this.recipes = [...this.recipes, ...res.data.list];
          this.maxPage = parseInt((res.data.total - 1) / 6) + 1;
        })
        .catch((err) => console.log(err));
    },
    searchRecipe(page) {
      let frm = new FormData();

      // categories 배열에는 선택된 카테고리의 ID가 String으로 들어갑니다.
      this.searchingData.selectedCategory.forEach(function (selectedCategory) {
        frm.append("selectedCategory", selectedCategory);
      });
      this.searchingData.selectedIngredients.forEach(function (
        selectedIngredient
      ) {
        frm.append("selectedIngredients", selectedIngredient);
      });

      frm.append("level", this.searchingData.level)

      frm.append("cookTime", this.searchingData.cookTime)

      // recipe/search/{{page}} 라는 주소로 selectedCategory(선택된 카테고리의 id들) / selectedIngredients(선택된 재료들의 id)를 전달합니다.
      axios
        .post(SERVER.URL + SERVER.ROUTES.searchRecipe + (page - 1), frm)
        .then((res) => {
          this.recipes = [...this.recipes, ...res.data.list];
          this.maxPage = parseInt((res.data.total - 1) / 6) + 1;
        })
        .catch((err) => console.log(err));
    },
    selectRecipe(recipe) {
      this.selectedRecipe = recipe;
      this.showSelector = false
    },
    deleteSelectedRecipe() {
      this.selectedRecipe = null
    },
    scrollToTop() {
      scroll(0, 0);
    },
  },
};
</script>

<style scoped>
.btn-circle {
  width: 30px;
  height: 30px;
  margin: 0;
  padding: 6px 0px;
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  text-align: center;
  font-size: 12px;
  line-height: 1.42857;
}
.blog-make-border-color {
  border-color: #999999;
}

.card-image {
  overflow: hidden;
}
.card-image .img-zoom {
  transform: scale(1);
  transition: transform 0.3s ease-in-out;
}

.card-image .img-zoom:hover {
  transform: scale(1.2);
}
.blog-make-border-color .d-inline-block {
  font-size: 0.875rem !important;
}
.custom-cursor-pointer {
  cursor: pointer;
}

</style>