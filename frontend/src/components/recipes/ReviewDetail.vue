<template>
  <div>
    <div :id="reviewId" class="row">
      <div class="review-nickname col-3">{{ review.nickname }}</div>
      <div class="review-content col-6 text-left">
        <p>{{ review.content }}</p>
      </div>

      <i class="review-rating now-ui-icons ui-2_favourite-28 col-1">{{ review.rating }}</i>
      <div v-if="review.userId == loginUserId" class="col-2">
        <button @click="updateComment(review)">수정</button>
        <button @click="deleteComment(review.reviewId)">삭제</button>
      </div>
    </div>

    <div :id="modifyReviewId" style="display:none;">
      <input type="text" v-model="review.content">
      <button @click="updateComment(review)">수정 완료</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReviewDetailView',
  props: {
    review: Object,
    loginUserId: Number,
  },
  computed: {
    reviewId() {
      return 'review-' + this.review.reviewId
    },
    modifyReviewId() {
      return 'modify-review-' + this.review.reviewId
    },
  },
  methods: {
    deleteComment(reviewId) {
      let response = confirm('정말요???')
      if (response) {
        this.$emit('deleteReview', reviewId)
      }
    },
    updateComment(review) {
      this.$emit('modifyMod', review)
      const reviewForm = document.getElementById(this.reviewId)
      const modifyReviewForm = document.getElementById(this.modifyReviewId)
      if (review.changing) {
        reviewForm.style.setProperty('display', 'none')
        modifyReviewForm.style.setProperty('display', 'flex')
      } else {
        reviewForm.style.setProperty('display', 'flex')
        modifyReviewForm.style.setProperty('display', 'none')
      }
    },
  }
}
</script>

<style>

</style>