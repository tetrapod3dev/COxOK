<template>
  <div class="review">
    <div v-for="review in reviewList" :key="review.reviewId" class="my-3">
      <!-- <ReviewDetail :review="review" :loginUserId="loginUserId" @deleteReview="deleteComment" @modifyMod="updateComment"/> -->
      <div :id="reviewId(review.reviewId)" class="col-10 ml-auto mr-auto">
        <hr>
        <div class="row">
          <div class="col-8 ml-auto mr-auto">
            <div class="review-nickname col-10 col-md-6 col-lg-4 text-left">
              {{ review.nickname }}
              <i class="review-rating fas fa-star ml-2">{{ review.rating }}</i>
            </div>
            <div class="review-content text-left ml-3">
              <p>{{ review.content }}</p>
            </div>
          </div>
          <div class="col-3 ml-auto mr-auto">
            <div v-if="review.userId == loginUserId" class="upd-del-btn col-12 text-right">
              <b-button @click="updateComment(review)">수정</b-button>
              <b-button @click="deleteComment(review.reviewId)">삭제</b-button>
            </div>
          </div>
        </div>   
      </div>

      <div :id="modifyReviewId(review.reviewId)" style="display:none; font-size:17px;" class="row col-10 ml-auto mr-auto">
        <div class="row ml-auto mr-auto">
            <b-form-rating size="lg" variant="warning" class="ml-auto" v-model="review.rating" inline></b-form-rating>    
            <b-form-input v-model="review.content" class="col-6 ml-2"></b-form-input>
            <b-button @click="updateComment(review)" class="mt-auto mb-auto ml-2 mr-auto">수정완료</b-button>
            
        
        </div>
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
        modifyReviewForm.style.setProperty('display', 'block')
      } else {
        reviewForm.style.setProperty('display', 'block')
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
  font-weight: bold;
  color: #000000;
}
.review-content {
  font-size: 15px;
  color: #000000;
}
.row i {
  color:gold;
}

.upd-del-btn button {
  padding: 10px 10px;
  width: 50px;
}
</style>