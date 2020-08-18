<template>
  <div class="wrapper">
    <!-- 필터 -->
    <div class="page-header page-header-mini header-filter" filter-color="black">
      <!-- 배너 배경 사진 -->
      <parallax
        class="page-header-image"
        :style="{ backgroundImage: 'url(\'' + require('@/assets/mypage.jpg') + '\')' }"
      ></parallax>
      <!-- 프로필 -->
      <blog-profile />
    </div>
    <div class="section blog-main">
      <div class="container">
        <!-- 블로그 메뉴 -->
        <blog-menu />

        <!-- blog main page start -->
        <div class="container">
          <calendar />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Calendar from "@/components/common/Calendar.vue";
import BlogProfile from "@/components/blog/BlogProfile.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";

export default {
  name: "BlogCalendarView",
  bodyClass: "profile-page",
  data() {
    return {};
  },
  components: {
    Calendar,
    BlogProfile,
    BlogMenu,
  },
  created() {},
};
</script>
