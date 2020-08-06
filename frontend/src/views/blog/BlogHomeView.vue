<template>
  <div class="wrapper">
    <div class="page-header page-header-mini header-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image: url('https://images.pexels.com/photos/406152/pexels-photo-406152.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260')"></parallax>
      <blog-profile/>
    </div>
    <div class="section">
      <div class="container">

        <blog-menu/>

        <div class="row">
          <div class="col-md-12">
            <!-- blog main page start -->
            <div class="about-description text-center">
              <div class="features-3">
                <div class="container">
                  <div class="row">
                    <div class="col-md-4">
                      <div class="info info-hover">
                        <div class="icon icon-success icon-circle">
                          <i class="now-ui-icons education_paper"></i>
                        </div>
                        <h4 class="info-title">26</h4>
                        <p class="description">작성 레시피</p>
                      </div>
                    </div>
                    <div class="col-md-4">
                      <div class="info info-hover">
                        <div class="icon icon-primary icon-circle">
                          <i class="fa fa-heart"></i>
                        </div>
                        <h4 class="info-title">26</h4>
                        <p class="description">좋아하는 레시피</p>
                      </div>
                    </div>
                    <div class="col-md-4">
                      <div class="info info-hover">
                        <div class="icon icon-info icon-circle">
                          <i class="fa fa-group"></i>
                        </div>
                        <h4 class="info-title">48</h4>
                        <p class="description">소모임 활동</p>
                      </div>
                    </div>
                    <div class="social-description">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { Card, Tabs, TabPane, Badge, InfoSection } from "@/components/global";
import BlogProfile from "@/components/blog/BlogProfile.vue";
import BlogMenu from "@/components/blog/BlogMenu.vue";

export default {
  name: "BlogHomeView",
  bodyClass: "profile-page",
  data() {
    return {}
  },
  components: {
    BlogProfile,
    BlogMenu,
  },
  computed: {},
  created() {},
};
</script>

<style></style>