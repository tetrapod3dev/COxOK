<template>
  <div class="review">
    <div v-for="review in reviewList" :key="review.reviewId" class="row my-3">
      <div class="review-nickname col-3">{{ review.nickname }}</div>
      <div class="review-content col-8 text-left">
        <p>{{ review.content }}</p>
      </div>

      <i class="review-rating now-ui-icons ui-2_favourite-28"></i>
      {{ review.rating }}
    </div>
  </div>
</template>

<script>
export default {
  name: "ReviewList",
  props: {
    reviewList: Array,
  },
};
</script>

<style scoped>
.review-nickname {
  font-size: 20px;
  color: #000000;
}
.review-rating {
  font-size: 20px;
  color: #000000;
}
.review-content {
  font-size: 20px;
  color: #000000;
}
</style>