<template>
  <div class="review">
    <div v-for="review in reviewList" :key="review.reviewId" class="my-3">
      <!-- <ReviewDetail :review="review" :loginUserId="loginUserId" @deleteReview="deleteComment" @modifyMod="updateComment"/> -->
      <div :id="reviewId(review.reviewId)" class="row">
        <div class="review-nickname col-3">{{ review.nickname }}</div>
        <div class="review-content col-6 text-left">
          <p>{{ review.content }}</p>
        </div>
        <i class="review-rating fas fa-star col-1">{{ review.rating }}</i>
        <div v-if="review.userId == loginUserId" class="upd-del-btn col-2">
          <b-button @click="updateComment(review)">수정</b-button>
          <b-button @click="deleteComment(review.reviewId)">삭제</b-button>
        </div>
      </div>

      <div :id="modifyReviewId(review.reviewId)" style="display:none;">
        <b-form-rating size="lg" variant="warning" v-model="review.rating" inline></b-form-rating>
        <input type="text" v-model="review.content">
        <button @click="updateComment(review)">수정 완료</button>
      </div>
      
    </div>
  </div>
</template>

<script>
// import ReviewDetail from './ReviewDetail.vue'

export default {
  name: "ReviewList",
  data() {
    return {
    }
  },
  components: {
    // ReviewDetail
  },
  props: {
    reviewList: Array,
    loginUserId: Number,
  },
  methods: {
    reviewId(reviewId) {
      return 'review-' + reviewId
    },
    modifyReviewId(reviewId) {
      return 'modify-review-' + reviewId
    },
    deleteComment(reviewId) {
      let response = confirm('정말요???')
      if (response) {
        this.$emit('deleteReview', reviewId)
      }
    },
    updateComment(review) {
      this.$emit('modifyMod', review)
      const reviewForm = document.getElementById(this.reviewId(review.reviewId))
      const modifyReviewForm = document.getElementById(this.modifyReviewId(review.reviewId))
      if (review.changing) {
        reviewForm.style.setProperty('display', 'none')
        modifyReviewForm.style.setProperty('display', 'flex')
      } else {
        reviewForm.style.setProperty('display', 'flex')
        modifyReviewForm.style.setProperty('display', 'none')
        this.$emit('updateReview', review)
      }
    },
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
.row i {
  color:gold;
}

.upd-del-btn button {
  width: 70px;
}
</style>